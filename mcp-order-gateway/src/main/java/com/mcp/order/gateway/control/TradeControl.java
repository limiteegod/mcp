/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.exception.NoMoneyException;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.ParamConverterUtil;
import com.mcp.order.gateway.util.StationGameUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.trade.*;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgCurTerm;
import com.mcp.order.model.mongo.MgWaitPay;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgCurTermService;
import com.mcp.order.mongo.service.MgWaitPayService;
import com.mcp.order.service.*;
import com.mcp.order.service.util.OrderStateUtil;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.order.service.util.TicketStateUtil;
import com.mcp.order.status.SchemeState;
import com.mcp.order.status.TermState;
import com.mcp.rmi.inter.SchemeInter;
import com.mcp.scheme.model.SchemeZh;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/trade")
public class TradeControl {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private StationGameService stationGameService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private CustomerPresentService customerPresentService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private MgCurTermService mgCurTermService;
	
	@Autowired
	private MgWaitPayService mgWaitPayService;
	
	@Autowired
	private SchemeInter schemeInter;
	
	private static Logger log = Logger.getLogger(TradeControl.class);
	
	@RequestMapping(value = "/lot.htm")
	public String lot(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="T01") ReqT01Body body,
			ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		log.info("T01-----------------------");
		RepT01Body repBody = new RepT01Body();
		String channelCode = head.getChannelCode();
		
		ReqOrder reqOrder = body.getOrder();
		String gameCode = reqOrder.getGameCode();
		String termCode = reqOrder.getTermCode();
		//彩民投注支持投注站账户或者第三方支付投注
		int payType = body.getPayType();
		String customerId = customer.getId();
		
		if(StringUtil.isEmpty(termCode))
		{
			MgCurTerm mgCurTerm = mgCurTermService.getById(gameCode);
			termCode = mgCurTerm.getCurTermCode();
			reqOrder.setTermCode(termCode);
		}
		Term gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
		if(gt == null)
		{
			throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
		}
		if(gt.getStatus() != TermState.ON_SALE.getCode() && gt.getStatus() != TermState.NOT_ON_SALE.getCode())
		{
			throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
		}
		
		/**
		 * 如果是投注站的公共渠道，则stationId为订单的投注站的id，而从渠道来的票，stationId则为渠道的id
		 */
		Station channel = stationService.findOneByCode(channelCode);
		List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, channel, reqOrder.getStationId(), gameCode, gt.getEndTime());
		String stationId = sIdList.get(0);
		String printStationId = sIdList.get(1);
		
		TOrder order = ParamConverterUtil.getTOrderFromReqOrder(reqOrder, customerId, channelCode, payType, stationId, printStationId);
		orderService.isAcceptable(gt, order);
		
		String filterCode = "01";
		
		boolean afford = false;
		if(payType == ConstantValues.TOrder_PayType_Cash.getCode())
		{
			filterCode += "01";
			long recharge;
			//投注站账户支付，才会付款
			try {
				CustomerAccount ca = customerAccountService.findOneByCustomerIdAndStationId(customerId, stationId);
				List<MoneyLog> logList = moneyService.customerBuyLot(customer, ca, order.getId(), order.getAmount(), "", "");
				afford = true;
				recharge = logList.get(0).getStateAfter();
			} 
			catch (NoMoneyException e)
			{
				recharge = e.getBalance();
				repBody.setRepCode(ErrCode.E1007);
				repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1007));
			}
			
			//设置余额信息
			RepStation repStation = new RepStation();
			repStation.setId(stationId);
			repStation.setRecharge(recharge);
			repBody.setStation(repStation);
		}
		else if(payType == ConstantValues.TOrder_PayType_Company.getCode())
		{
			//通过第三方支付
			filterCode += "02";
		}
		else
		{
			throw new CoreException(ErrCode.E1027, ErrCode.codeToMsg(ErrCode.E1027));
		}
		
		int orderStatus = OrderStateUtil.getOrderStatus(afford, gt.getStatus());
		int ticketStatus = TicketStateUtil.getTicketStatus(afford, gt.getStatus());
		order.setStatus(orderStatus);
		List<TTicket> tList = order.getTickets();
		for(TTicket t:tList)
		{
			t.setStatus(ticketStatus);
		}
		
		if(afford)
		{
			Date acceptTime = new Date();
			order.setAcceptTime(acceptTime);
			for(TTicket t:tList)
			{
				t.setAcceptTime(acceptTime);
			}
		}
		
		orderService.save(order);
		
		if(afford)
		{
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
			PrintUtil.newOrder(context, order);
		}
		else
		{
			//未支付的订单，记录类型到mongodb
			MgWaitPay mgWaitPay = new MgWaitPay();
			mgWaitPay.setId(order.getId());
			mgWaitPay.setSchemeType(ConstantValues.TScheme_Type_Default.getCode());
			mgWaitPay.setCreateTime(new Date());
			this.mgWaitPayService.save(mgWaitPay);
		}
		
		RepOrder repOrder = new RepOrder();
		repOrder.setId(order.getId());
		repOrder.setStatus(order.getStatus());
		repBody.setOrder(repOrder);
		
		modelMap.put("filterCode", filterCode);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 彩民使用投注站账户支付未支付的订单
	 * @param head
	 * @param body
	 * @param station
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/afford.htm")
	public String afford(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="T02") ReqT02Body body,
			 ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepT02Body repBody = new RepT02Body();
		String orderId = body.getOrderId();
		
		MgWaitPay mwp = this.mgWaitPayService.findOne(orderId);
		if(mwp == null)
		{
			throw new CoreException(ErrCode.E1029, ErrCode.codeToMsg(ErrCode.E1029));
		}
		int type = mwp.getSchemeType();
		
		long amount;
		String gameCode, termCode, customerId;
		List<TOrder> orderList = new ArrayList<TOrder>();;
		if(type == ConstantValues.TScheme_Type_Default.getCode())
		{
			TOrder order = orderService.findOne(orderId);
			gameCode = order.getGameCode();
			termCode = order.getTermCode();
			customerId = order.getCustomerId();
			amount = order.getAmount();
			orderList.add(order);
		}
		else if(type == ConstantValues.TScheme_Type_Follow.getCode())
		{
			SchemeZh zh = schemeInter.queryZhById(orderId);
			gameCode = zh.getGameCode();
			termCode = zh.getStartTermCode();
			customerId = zh.getCustomerId();
			amount = zh.getAmount();
			orderList.add(this.orderService.findAllBySchemeId(zh.getId()).get(0));
		}
		else 
		{
			throw new CoreException(ErrCode.E1026, ErrCode.codeToMsg(ErrCode.E1026));
		}
		
		Term gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
		if(gt == null)
		{
			throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
		}
		if(gt.getStatus() != TermState.ON_SALE.getCode() && gt.getStatus() != TermState.NOT_ON_SALE.getCode())
		{
			throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
		}
		
		StationGameUtil.checkStationGame(stationGameService, station, "", gameCode, gt.getEndTime());
		
		for(TOrder order:orderList)
		{
			order.setTickets(ticketService.findAllByOrderId(order.getId()));
			orderService.isAcceptable(gt, order);
		}
		
		long recharge;
		boolean afford = false;
		//此接口只能使用投注站账户支付
		try {
			CustomerAccount ca = customerAccountService.findOneByCustomerIdAndStationId(customerId, station.getId());
			List<MoneyLog> logList = moneyService.customerBuyLot(null, ca, orderId, amount, "", "");
			recharge = logList.get(0).getStateAfter();
			afford = true;
		}
		catch (NoMoneyException e)
		{
			recharge = e.getBalance();
			repBody.setRepCode(ErrCode.E1007);
			repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1007));
		}
		
		int orderStatus = OrderStateUtil.getOrderStatus(afford, gt.getStatus());
		int ticketStatus = TicketStateUtil.getTicketStatus(afford, gt.getStatus());
		if(afford)
		{
			for(TOrder order:orderList)
			{
				orderService.afford(order.getId(), orderStatus, ticketStatus);
				order.setStatus(orderStatus);
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
				PrintUtil.newOrder(context, order);
			}
		}
		
		int status = -1;
		if(type == ConstantValues.TScheme_Type_Default.getCode())
		{
			status = orderStatus;
		}
		else
		{
			if(afford)
			{
				status = SchemeState.RUNNING.getCode();
			}
			else
			{
				status = SchemeState.NOT_AVAILABLE.getCode();
			}
		}
		
		if(afford && type != ConstantValues.TScheme_Type_Default.getCode())
		{
			//方案已经支付
			schemeInter.affordScheme(orderId, type);
		}
		
		//设置余额信息
		RepStation repStation = new RepStation();
		repStation.setId(station.getId());
		repStation.setRecharge(recharge);
		repBody.setStation(repStation);
		
		RepOrder repOrder = new RepOrder();
		repOrder.setId(orderId);
		repOrder.setStatus(status);
		repOrder.setSchemeType(type);
		repBody.setOrder(repOrder);
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

    /**
     * 机构投注接口
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/clot.htm")
	public String clot(@JsonHead(value="head", checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="T03") ReqT03Body body,
			ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepT03Body repBody = new RepT03Body();
		ReqOrder reqOrder = body.getOrder();
		String gameCode = reqOrder.getGameCode();
		String termCode = reqOrder.getTermCode();
		String stationId = station.getId();

        String outerOrderId = reqOrder.getOuterId();
        if(StringUtil.isEmpty(outerOrderId))
        {
            throw new CoreException(ErrCode.E2052, ErrCode.codeToMsg(ErrCode.E2052));
        }
        TOrder tmpOrder  = orderService.findOneByChannelCodeAndOuterId(station.getCode(), outerOrderId);
        if(tmpOrder != null)
        {
            throw new CoreException(ErrCode.E1302, ErrCode.codeToMsg(ErrCode.E1302));
        }

		Term gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
		if(gt == null)
		{
			throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
		}
		if(gt.getStatus() != TermState.ON_SALE.getCode() && gt.getStatus() != TermState.NOT_ON_SALE.getCode())
		{
			throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
		}
		
		List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, station, reqOrder.getStationId(), gameCode, gt.getEndTime());
		String printStationId = sIdList.get(1);
		
		TOrder order = ParamConverterUtil.getTOrderFromReqOrder(reqOrder, stationId, station.getCode(), 
				ConstantValues.TOrder_PayType_Cash.getCode(), stationId, printStationId);
		orderService.isAcceptable(gt, order);
		
		//设置订单的类型为渠道订单
		order.setType(TOrderType.CHANNEL);
		
		long recharge = 0;
		boolean afford = false;
		try {
			MoneyLog mLog = moneyService.stationLot(stationId, order.getId(), order.getAmount());
			afford = true;
			recharge = mLog.getStateAfter();
		} 
		catch (NoMoneyException e)
		{
			recharge = e.getBalance();
			repBody.setRepCode(ErrCode.E1007);
			repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1007));
		}
		
		//设置余额信息
		RepStation repStation = new RepStation();
		repStation.setId(stationId);
		repStation.setRecharge(recharge);
		repBody.setStation(repStation);
		
		if(afford)
		{
			int orderStatus = OrderStateUtil.getOrderStatus(afford, gt.getStatus());
			int ticketStatus = TicketStateUtil.getTicketStatus(afford, gt.getStatus());
			Date acceptTime = new Date();
			order.setAcceptTime(acceptTime);
			order.setStatus(orderStatus);
			List<TTicket> tList = order.getTickets();
			for(TTicket t:tList)
			{
				t.setAcceptTime(acceptTime);
				t.setStatus(ticketStatus);
			}
			
			orderService.save(order);
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
			PrintUtil.newOrder(context, order);
			
			RepOrder repOrder = new RepOrder();
			repOrder.setId(order.getId());
			repOrder.setStatus(order.getStatus());
			repBody.setOrder(repOrder);
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

    /**
     * 判断一个订单是否可接受
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cValidate.htm")
    public String cValidate(@JsonHead(value="head", checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="T04") ReqT04Body body,
                        ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        RepT04Body repBody = new RepT04Body();
        ReqOrder reqOrder = body.getOrder();
        String gameCode = reqOrder.getGameCode();
        String termCode = reqOrder.getTermCode();
        String stationId = station.getId();
        Term gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
        if(gt == null)
        {
            throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
        }
        if(gt.getStatus() != TermState.ON_SALE.getCode() && gt.getStatus() != TermState.NOT_ON_SALE.getCode())
        {
            throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
        }
        List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, station, reqOrder.getStationId(), gameCode, gt.getEndTime());
        String printStationId = sIdList.get(1);
        TOrder order = ParamConverterUtil.getTOrderFromReqOrder(reqOrder, stationId, station.getCode(),
                ConstantValues.TOrder_PayType_Cash.getCode(), stationId, printStationId);
        orderService.isAcceptable(gt, order);
        modelMap.put("response", repBody);
        return "plainJsonView";
    }
}
