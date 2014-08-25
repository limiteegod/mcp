/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.dao.specification.OrderSpecification;
import com.mcp.order.dao.specification.TermSpecification;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.CookieUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.jbank.*;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgCurTerm;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgCurTermService;
import com.mcp.order.service.*;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TicketState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/jbank")
public class JBankControl {
	
	private static Logger log = Logger.getLogger(JBankControl.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private MgCurTermService mgCurTermService;
	
	/**
	 * 登录，需要查询payment表获取银行卡信息，查询customeraccount表，获取账户信息
	 * @param head
	 * @param body
	 * @param modelMap
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.htm")
	public String login(@JsonHead(value="head",checkChannel=true) Head head, @McpStation Station station, @JsonBody(value="body", cmd="J01") ReqJ01Body body,
			 ModelMap modelMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		RepJ01Body repBody = new RepJ01Body();
		RepJ01Customer repCustomer = new RepJ01Customer();
		RepJ01Cookie repCookie = new RepJ01Cookie();
		Customer cus = customerService.findOneByNameAndChannelCode(body.getName(), head.getChannelCode());
		if(cus == null)
		{
			if(body.isCreateWhenNotExist())
			{
				//用户不存在，则新增用户
				Date now = new Date();
				cus = new Customer();
				cus.setId(CoreUtil.getUUID());
				cus.setName(body.getName());
				cus.setRegDate(now);
				String pwd = MD5Util.MD5(body.getPassword() + now.getTime()/1000);
				cus.setPassword(pwd);
				cus.setLastLoginTime(now);
				cus.setLastActiveTime(now);
				cus.setStatus(ConstantValues.Customer_Status_Open.getCode());
				cus.setChannelCode(head.getChannelCode());
				cus.setRealName(body.getRealName());
				cus.setIdentityId(body.getIdentityId());
				customerService.save(cus);
				
				//绑定银行卡
				if(!StringUtil.isEmpty(body.getCardNumber()))
				{
					Payment payment = new Payment();
					payment.setId(CoreUtil.getUUID());
					payment.setCardNumber(body.getCardNumber());
					paymentService.save(payment);
				}
				
				//添加账户信息
				CustomerAccount ca = new CustomerAccount();
				ca.setId(CoreUtil.getUUID());
				ca.setCustomerId(cus.getId());
				ca.setPrize(body.getPrize());
				ca.setRecharge(body.getRecharge());
				ca.setIntegral(0);
				ca.setStationId(station.getId());
				ca.setStatus(ConstantValues.CustomerAccount_Status_Open.getCode());
				customerAccountService.save(ca);
				
				//设置用户的cookie信息
				repCookie.setUserId(cus.getId());
				repCookie.setUserType(Constants.USER_TYPE_CUSTOMER);
				String userMd5 = CookieUtil.getUserMd5(cus.getName(), cus.getPassword(), cus.getLastLoginTime().getTime());
				repCookie.setUserMd5(userMd5);
				
				repCustomer.setName(cus.getName());
				repCustomer.setRealName(cus.getRealName());
				repCustomer.setIdentityId(cus.getIdentityId());
				repCustomer.setCardNumber(body.getCardNumber());
				repCustomer.setRecharge(body.getRecharge());
				repCustomer.setPrize(body.getPrize());
				
				repBody.setStatus(true);
				repBody.setCustomer(repCustomer);
				repBody.setCookie(repCookie);
			}
			else
			{
				repBody.setStatus(false);
				repBody.setRepCode(ErrCode.E1003);
				repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1003));
			}
		}
		else
		{
			String uPwd = body.getPassword();
			String sPwd = cus.getPassword();
			String pwd = MD5Util.MD5(uPwd + cus.getRegDate().getTime()/1000);
			log.info("sPwd:" + sPwd + ",pwd:" + pwd);
			if(sPwd.endsWith(pwd))
			{
				cus.setLastLoginTime(new Date());
				customerService.save(cus);
				
				CustomerAccount customerAccount = customerAccountService.findOneByCustomerIdAndStationId(cus.getId(), station.getId());
				//有新的奖金产生，增加用户的奖金账户
				if(body.getPrize() > 0)
				{
					customerAccountService.incrPrizeById(body.getPrize(), customerAccount.getId());
				}
				repCustomer.setRecharge(customerAccount.getRecharge());
				repCustomer.setPrize(customerAccount.getPrize() + body.getPrize());
				
				repCustomer.setName(cus.getName());
				repCustomer.setRealName(cus.getRealName());
				repCustomer.setIdentityId(cus.getIdentityId());
				
				//银行卡号
				List<Payment> paymentList = paymentService.findAllByCustomerId(cus.getId());
				if(paymentList.size() > 0)
				{
					repCustomer.setCardNumber(paymentList.get(0).getCardNumber());
				}
				
				//设置用户的cookie信息
				repCookie.setUserId(cus.getId());
				repCookie.setUserType(Constants.USER_TYPE_CUSTOMER);
				String userMd5 = CookieUtil.getUserMd5(cus.getName(), cus.getPassword(), cus.getLastLoginTime().getTime());
				repCookie.setUserMd5(userMd5);
				
				repBody.setStatus(true);
				repBody.setCustomer(repCustomer);
				repBody.setCookie(repCookie);
			}
			else
			{
				repBody.setRepCode(ErrCode.E1004);
				repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1004));
			}
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 获取用户信息
	 * @param head
	 * @param body
	 * @param customer
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUser.htm")
	public String getUser(@JsonHead(value="head",checkChannel=true) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="J02") ReqJ02Body body,
			 ModelMap modelMap) throws Exception {
		RepJ02Body repBody = new RepJ02Body();
		RepJ02Customer repCustomer = new RepJ02Customer();
		
		Station station = stationService.findOneByCode(customer.getChannelCode());
		
		CustomerAccount customerAccount = customerAccountService.findOneByCustomerIdAndStationId(customer.getId(), station.getId());
		repCustomer.setRecharge(customerAccount.getRecharge());
		repCustomer.setPrize(customerAccount.getPrize());
		
		repCustomer.setName(customer.getName());
		repCustomer.setRealName(customer.getRealName());
		repCustomer.setIdentityId(customer.getIdentityId());
		
		//银行卡号
		List<Payment> paymentList = paymentService.findAllByCustomerId(customer.getId());
		if(paymentList.size() > 0)
		{
			repCustomer.setCardNumber(paymentList.get(0).getCardNumber());
		}
		
		repBody.setCustomer(repCustomer);
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 用户修改自己的信息
	 */
	@RequestMapping(value = "/modifyInfo.htm")
	public String modifyInfo(@JsonHead(value="head",checkChannel=true) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="J03") ReqJ03Body body, ModelMap modelMap) throws Exception {
		RepJ03Body repBody = new RepJ03Body();
		String pwd = body.getPassword();
		
		boolean status = true;
		if(!StringUtil.isEmpty(pwd))
		{
			String spwd = MD5Util.MD5(pwd + customer.getRegDate().getTime()/1000);
			if(!spwd.equals(customer.getPassword()))
			{
				status = false;
				repBody.setRepCode(ErrCode.E1004);
				repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1004));
			}
		}
		
		//校验通过
		if(status)
		{
			customerService.updateInfoById(null, null, null, body.getRealName(), body.getIdentityId(), customer.getId());
			paymentService.updateFirstCardNoByCustomerId(customer.getId(), body.getCardNumber());
		}
		repBody.setStatus(status);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 登录，需要查询payment表获取银行卡信息，查询customeraccount表，获取账户信息
	 * @param head
	 * @param body
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register.htm")
	public String register(@JsonHead(value="head",checkChannel=true) Head head, @McpStation Station station, @JsonBody(value="body", cmd="J04") ReqJ04Body body, ModelMap modelMap) throws Exception {
		RepJ04Body repBody = new RepJ04Body();
		RepJ04Customer repCustomer = new RepJ04Customer();
		RepJ04Cookie repCookie = new RepJ04Cookie();
		Customer cus = customerService.findOneByNameAndChannelCode(body.getName(), head.getChannelCode());
		if(cus != null)
		{
			throw new CoreException(ErrCode.E1005, ErrCode.codeToMsg(ErrCode.E1005));
		}
		else
		{
			//用户不存在，则新增用户
			Date now = new Date();
			cus = new Customer();
			cus.setId(CoreUtil.getUUID());
			cus.setName(body.getName());
			cus.setRegDate(now);
			String pwd = MD5Util.MD5(body.getPassword() + now.getTime()/1000);
			cus.setPassword(pwd);
			cus.setLastLoginTime(now);
			cus.setLastActiveTime(now);
			cus.setStatus(ConstantValues.Customer_Status_Open.getCode());
			cus.setChannelCode(head.getChannelCode());
			cus.setRealName(body.getRealName());
			cus.setIdentityId(body.getIdentityId());
			customerService.save(cus);
			
			//绑定银行卡
			if(!StringUtil.isEmpty(body.getCardNumber()))
			{
				Payment payment = new Payment();
				payment.setId(CoreUtil.getUUID());
				payment.setCardNumber(body.getCardNumber());
				paymentService.save(payment);
			}
			
			//添加账户信息
			CustomerAccount ca = new CustomerAccount();
			ca.setId(CoreUtil.getUUID());
			ca.setCustomerId(cus.getId());
			ca.setPrize(body.getPrize());
			ca.setRecharge(body.getRecharge());
			ca.setIntegral(0);
			ca.setStationId(station.getId());
			ca.setStatus(ConstantValues.CustomerAccount_Status_Open.getCode());
			customerAccountService.save(ca);
			
			//设置用户的cookie信息
			repCookie.setUserId(cus.getId());
			repCookie.setUserType(Constants.USER_TYPE_CUSTOMER);
			String userMd5 = CookieUtil.getUserMd5(cus.getName(), cus.getPassword(), cus.getLastLoginTime().getTime());
			repCookie.setUserMd5(userMd5);
			
			repCustomer.setName(cus.getName());
			repCustomer.setRealName(cus.getRealName());
			repCustomer.setIdentityId(cus.getIdentityId());
			repCustomer.setCardNumber(body.getCardNumber());
			repCustomer.setRecharge(body.getRecharge());
			repCustomer.setPrize(body.getPrize());
			
			repBody.setCustomer(repCustomer);
			repBody.setCookie(repCookie);
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 建行支付之后，支付订单
	 */
	@RequestMapping(value = "/affordFromBank.htm")
	public String affordFromBank(@JsonHead(value="head",checkChannel=true) Head head, @McpStation Station station, @JsonBody(value="body", cmd="J05") ReqJ05Body body,
			 ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepJ05Body repBody = new RepJ05Body();
		
		String orderId = body.getOrderId();
		TOrder order = orderService.findOne(orderId);
		String customerId = order.getCustomerId();
		long amount = order.getAmount();
		
		Station userChannel = stationService.findOneByCode(body.getStationCode());
		moneyService.customerRecharge(userChannel.getId(), customerId, orderId, amount, body.getPayType());
		
		order.setTickets(ticketService.findAllByOrderId(order.getId()));
		String stationId = order.getStationId();
		
		Customer customer = customerService.findOne(customerId);
		
		boolean afford = false;
		try {
			Term gt = termService.findOneByGameCodeAndCode(order.getGameCode(), order.getTermCode());
			orderService.isAcceptable(gt, order);
			
			order.setStatus(OrderState.WAITING_PRINT.getCode());
			List<TTicket> tList = order.getTickets();
			for(TTicket t:tList)
			{
				t.setStatus(TicketState.WAITING_PRINT.getCode());
			}
			orderService.updateStatusById(OrderState.WAITING_PRINT.getCode(), order.getId());
			ticketService.updateStatusByOrderId(TicketState.WAITING_PRINT.getCode(), order.getId());
			afford = true;
		} 
		catch(CoreException e)
		{
			throw new CoreException(e.getErrorCode(), e.getMessage());
		}
		
		if(afford)
		{
			CustomerAccount ca = customerAccountService.findOneByCustomerIdAndStationId(customerId, stationId);
			moneyService.customerBuyLot(customer, ca, order.getId(), order.getAmount(), "", "");
			
			Station pStation = stationService.findOne(order.getPrintStationId());
			if(pStation.getQueueIndex() > 0)
			{
				//发送消息通知出票引擎
				/*ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
				WzlOrderId wzlOrderId = new WzlOrderId();
				wzlOrderId.setOrderId(order.getId());
				wzlOrderId.setGameCode(order.getGameCode());
				wzlOrderId.setTermCode(order.getTermCode());*/
				//mcpMsgSender.sendMsg(context, pStation.getQueueIndex(), wzlOrderId);
			}
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 获取普通游戏的当前期
	 */
	@RequestMapping(value = "/getCurTerm.htm")
	public String getCurTerm(@JsonHead(value="head",checkChannel=true) Head head, @JsonBody(value="body", cmd="J06") ReqJ06Body body, 
			ModelMap modelMap) throws Exception {
		RepJ06Body repBody = new RepJ06Body();
		
		List<MgCurTerm> tList = mgCurTermService.findAll();
		if(tList.size() > 0)
		{
			Specifications<Term> allSpecs = null;
			for(MgCurTerm mct:tList)
			{
				Specifications<Term> specs = Specifications.where(TermSpecification.isGameCodeEqual(mct.getId()));
				specs = specs.and(Specifications.where(TermSpecification.isCodeEqual(mct.getCurTermCode())));
				
				if(allSpecs == null)
				{
					allSpecs = Specifications.where(specs);
				}
				else
				{
					allSpecs = allSpecs.or(specs);
				}
			}
			List<Term> termList = termService.findAll(allSpecs);
			repBody.setRst(termList);
		}
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 获取当期投注记录
	 */
	@RequestMapping(value = "/getCurTermOrders.htm")
	public String getCurTermOrders(@JsonHead(value="head",checkChannel=true) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="J07") ReqJ07Body body,
			ModelMap modelMap) throws Exception {
		RepJ07Body repBody = new RepJ07Body();
		
		Sort sort = new Sort(new Order(Direction.DESC, "createTime"), new Order(Direction.DESC, "termCode"));
		PageRequest p = new PageRequest(body.getStartIndex(), body.getSize(), sort);
		Specifications<TOrder> allSpecs = Specifications.where(OrderSpecification.isCustomerIdEqual(customer.getId()));
		
		Specifications<TOrder> statusSpecs = Specifications.where(OrderSpecification.isStatusEqual(OrderState.WAITING_PRINT.getCode()));
		statusSpecs = statusSpecs.or(OrderSpecification.isStatusEqual(OrderState.SUCCESS.getCode()));
		statusSpecs = statusSpecs.or(OrderSpecification.isStatusEqual(OrderState.PRESALE.getCode()));
		
		allSpecs = allSpecs.and(statusSpecs);
		
		Page<TOrder> page = orderService.findAll(allSpecs, p);
		List<TOrder> oList = page.getContent();
		for(int i = 0; i < oList.size(); i++)
        {
        	TOrder order = oList.get(i);
        	order.setTickets(ticketService.findAllByOrderId(order.getId()));
        }
		repBody.setPageInfo(new PageInfo(page.getNumber(), page.getSize(), page.getTotalPages(), 
    			page.getNumberOfElements(), page.getTotalElements(), page.hasPreviousPage(), page.isFirstPage(),
    			page.hasNextPage(), page.isLastPage()));
		repBody.setRst(oList);
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 获取上期中奖记录
	 */
	@RequestMapping(value = "/getLastTermHit.htm")
	public String getLastTermHit(@JsonHead(value="head",checkChannel=true) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="J08") ReqJ08Body body,
			ModelMap modelMap) throws Exception {
		RepJ08Body repBody = new RepJ08Body();
		
		Sort sort = new Sort(new Order(Direction.DESC, "createTime"), new Order(Direction.DESC, "termCode"));
		PageRequest p = new PageRequest(body.getStartIndex(), body.getSize(), sort);
		Specifications<TOrder> allSpecs = Specifications.where(OrderSpecification.isCustomerIdEqual(customer.getId()));
		allSpecs = allSpecs.and(OrderSpecification.isBonusGreaterThan(0));
		List<MgCurTerm> tList = mgCurTermService.findAll();
		if(tList.size() > 0)
		{
			Specifications<TOrder> termSpecs = null;
			for(MgCurTerm mct:tList)
			{
				Specifications<TOrder> specs = Specifications.where(OrderSpecification.isGameCodeEqual(mct.getId()));
				specs = specs.and(Specifications.where(OrderSpecification.isTermCodeEqual(mct.getLastTermCode())));
				
				if(termSpecs == null)
				{
					termSpecs = Specifications.where(specs);
				}
				else
				{
					termSpecs = termSpecs.or(specs);
				}
			}
			allSpecs = allSpecs.and(termSpecs);
			Page<TOrder> page = orderService.findAll(allSpecs, p);
			List<TOrder> oList = page.getContent();
			for(int i = 0; i < oList.size(); i++)
            {
            	TOrder order = oList.get(i);
            	order.setTickets(ticketService.findAllByOrderId(order.getId()));
            }
			repBody.setPageInfo(new PageInfo(page.getNumber(), page.getSize(), page.getTotalPages(), 
	    			page.getNumberOfElements(), page.getTotalElements(), page.hasPreviousPage(), page.isFirstPage(),
	    			page.hasNextPage(), page.isLastPage()));
			repBody.setRst(oList);
		}
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
}
