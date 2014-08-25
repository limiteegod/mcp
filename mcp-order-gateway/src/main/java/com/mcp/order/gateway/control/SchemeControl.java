/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.SchemeType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.exception.NoMoneyException;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.StationGameUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.scheme.*;
import com.mcp.order.inter.trade.RepStation;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgWaitPay;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgCurTermService;
import com.mcp.order.mongo.service.MgWaitPayService;
import com.mcp.order.service.*;
import com.mcp.order.service.util.OrderStateUtil;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.order.service.util.TicketStateUtil;
import com.mcp.order.status.*;
import com.mcp.order.util.DateTimeUtil;
import com.mcp.rmi.inter.SchemeInter;
import com.mcp.scheme.model.SchemeHm;
import com.mcp.scheme.model.SchemeShare;
import com.mcp.scheme.model.SchemeZh;
import com.mcp.scheme.service.SchemeZhService;
import com.mcp.scheme.util.SchemeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
@RequestMapping("/scheme")
public class SchemeControl {
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private StationGameService stationGameService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private SchemeInter schemeInter;
	
	@Autowired
	private MgWaitPayService mgWaitPayService;

    @Autowired
    private MgCurTermService mgCurTermService;

    @Autowired
    private SchemeZhService schemeZhService;

	//private static Logger log = Logger.getLogger(SchemeControl.class);
	
	@RequestMapping(value = "/zh.htm")
	public String zh(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="S01") ReqS01Body body,
			ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepS01Body repBody = new RepS01Body();
		String channelCode = head.getChannelCode();
		SchemeZh scheme = body.getScheme();
		String gameCode = scheme.getGameCode();
		String startTermCode = scheme.getStartTermCode();
		
		Term t = termService.findOneByGameCodeAndCode(gameCode, startTermCode);
		if(t == null)
		{
			throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
		}
		if(t.getStatus() != TermState.ON_SALE.getCode() && t.getStatus() != TermState.NOT_ON_SALE.getCode())
		{
			throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
		}
		
		Station channel = stationService.findOneByCode(channelCode);
		List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, channel, scheme.getStationId(), gameCode, t.getEndTime());
		String stationId = sIdList.get(0);
		String printStationId = sIdList.get(1);
		
		scheme.setStationId(stationId);
		scheme.setPrintStationId(printStationId);
		scheme.setId(CoreUtil.getUUID());
		scheme.setChannelCode(channelCode);
		scheme.setCreateTime(new Date());
		scheme.setStatus(SchemeState.NOT_AVAILABLE.getCode());
		String customerId = customer.getId();
		scheme.setCustomerId(customerId);
		scheme.setCurTermCode(startTermCode);
		
		CustomerAccount customerAccount = customerAccountService.findOneByCustomerIdAndStationId(customerId, stationId);
		if(customerAccount == null)
		{
			throw new CoreException(ErrCode.E2047, ErrCode.codeToMsg(ErrCode.E2047));
		}
		
		long amount = scheme.getAmount();
		long recharge;
		boolean afford = false;
		try {
			List<MoneyLog> logList = moneyService.customerBuyLot(customer, customerAccount, scheme.getId(), amount, "", "");
			scheme.setStatus(SchemeState.RUNNING.getCode());
			scheme.setFinishedOrderCount(1);	//支付成功之后，会生成头一期的订单
            scheme.setAcceptTime(new Date());
			recharge = logList.get(0).getStateAfter();
			afford = true;
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

		repBody.setScheme(scheme);

		int orderStatus = OrderStateUtil.getOrderStatus(afford, t.getStatus());
		int ticketStatus = TicketStateUtil.getTicketStatus(afford, t.getStatus());
		TOrder order = SchemeUtil.getOrderFromScheme(scheme, stationId, printStationId, orderStatus, ticketStatus);
		this.orderService.save(order);
		this.schemeZhService.save(scheme);

		if(afford)
		{
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
			PrintUtil.newOrder(context, order);
		}
		else
		{
			//未支付的方案，记录类型到mongodb
			MgWaitPay mgWaitPay = new MgWaitPay();
			mgWaitPay.setId(scheme.getId());
			mgWaitPay.setSchemeType(ConstantValues.TScheme_Type_Follow.getCode());
			mgWaitPay.setCreateTime(new Date());
			this.mgWaitPayService.save(mgWaitPay);
		}

		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	@RequestMapping(value = "/query.htm")
	public String query(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="S02") ReqS02Body body, ModelMap modelMap) throws Exception {
		RepS02Body repBody = new RepS02Body();

		if(StringUtil.isEmpty(body.getSchemeId()))
		{
			PageRequest pr = new PageRequest(body.getStartIndex(), body.getSize(), new Sort(Direction.DESC, "acceptTime"));
			Page<SchemeZh> page = this.schemeZhService.query(customer.getId(), body.getGameCode(), body.getStatus(), pr);
			repBody.setRst(page.getContent());
		}
		else	//根据方案id查询
		{
			List<SchemeZh> rst = new ArrayList<SchemeZh>();
			SchemeZh schemeZh = this.schemeZhService.findOne(body.getSchemeId());
			rst.add(schemeZh);
			repBody.setRst(rst);
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 取消方案
	 * @param head
	 * @param body
	 * @param customer
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancel.htm")
	public String cancel(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="S03") ReqS03Body body,
			 ModelMap modelMap) throws Exception {
		RepS03Body repBody = new RepS03Body();
		
		schemeInter.cancelSchemeZh(body.getSchemeId());
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

    /**
     * 用户发起一个合买方案
     * @param head
     * @param body
     * @param customer
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hm.htm")
    public String hm(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="S04") ReqS04Body body,
                          ModelMap modelMap) throws Exception {
        RepS04Body repBody = new RepS04Body();
        SchemeHm scheme = body.getScheme();
        int hmType = scheme.getType();
        String channelCode = head.getChannelCode();
        Station station = stationService.findOneByCode(channelCode);
        String oStationId = scheme.getStationId();
        //默认渠道必须指定stationId
        if(station.getStationType() == ConstantValues.Station_StationType_DEFAULT.getCode())
        {
            if(StringUtil.isEmpty(oStationId))
            {
                throw new CoreException(ErrCode.E2035, ErrCode.codeToMsg(ErrCode.E2035));
            }
        }
        else
        {
            scheme.setStationId(station.getId());
            oStationId = station.getId();
        }

        scheme.setId(CoreUtil.getUUID());
        scheme.setCustomerId(customer.getId());
        Date now = new Date();
        scheme.setCreateTime(now);
        scheme.setAcceptTime(now);
        scheme.setStatus(SchemeState.RUNNING.getCode());
        scheme.setChannelCode(channelCode);

        if(hmType == ConstantValues.TSchemeHm_Type_Normal.getCode()) //已经完成的金额为发起人认购的金额
        {
            scheme.setfAmount(scheme.getcAmount());
            scheme.setJoinCount(1); //发起合买方案的时候，有发起人自己参与
        }
        else    //其它合买方案已经完成的份额为0
        {
            scheme.setfAmount(0);
            //活动的方案金额必须和发起人的认购份额相同
            if(scheme.getAmount() != scheme.getcAmount())
            {
                throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
            }
            //由前端指定有多少人参与
            //scheme.setJoinCount(0);
        }

        long endTimeStamp = 0;
        //检查方案的订单是否可以销售
        List<TOrder> oList = scheme.getOrders();
        scheme.setOrderCount(oList.size());
        scheme.setFinishedOrderCount(0);

        for(TOrder order:oList)
        {
            String gameCode = order.getGameCode();
            Date gtEndTime = DateTimeUtil.getDateAfterMilliseconds(7*24*60*60*1000L);
            Term gt = null;
            if(hmType == ConstantValues.TSchemeHm_Type_Normal.getCode())
            {
                //普通合买，需要根据期次信息设置过期时间
                String termCode = order.getTermCode();
                gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
                if(gt == null)
                {
                    throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
                }
                if(gt.getStatus() != TermState.ON_SALE.getCode() && gt.getStatus() != TermState.NOT_ON_SALE.getCode())
                {
                    throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
                }
                gtEndTime = gt.getEndTime();
                long gtEndTimeStamp = gtEndTime.getTime();
                if(endTimeStamp == 0)
                {
                    endTimeStamp = gtEndTimeStamp;
                }
                else
                {
                    if(endTimeStamp > gtEndTimeStamp)
                    {
                        endTimeStamp = gtEndTimeStamp;
                    }
                }
            }
            else
            {
                endTimeStamp = gtEndTime.getTime();
            }
            /**
             * 如果是投注站的公共渠道，则stationId为订单的投注站的id，而从渠道来的票，stationId则为渠道的id
             */
            Station channel = stationService.findOneByCode(channelCode);
            List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, channel, oStationId, gameCode, gtEndTime);
            String stationId = sIdList.get(0);
            String printStationId = sIdList.get(1);

            //设置订单和票据信息
            order.setId(CoreUtil.getUUID());
            order.setCreateTime(now);
            order.setAcceptTime(now);
            order.setSchemeId(scheme.getId());
            order.setChannelCode(channelCode);
            order.setStationId(stationId);
            order.setCustomerId(customer.getId());
            order.setPrintStationId(printStationId);
            order.setSchemeType(SchemeType.HEMAI);
            order.setStatus(OrderState.INIT.getCode());
            List<TTicket> tList = order.getTickets();
            order.setTicketCount(tList.size());
            for(TTicket ticket:tList)
            {
                ticket.setId(CoreUtil.getUUID());
                ticket.setChannelCode(channelCode);
                ticket.setOrderId(order.getId());
                ticket.setCreateTime(now);
                ticket.setAcceptTime(now);
                ticket.setStationId(stationId);
                ticket.setPrinterStationId(printStationId);
                ticket.setStatus(TicketState.INIT.getCode());
                ticket.setGameCode(gameCode);
                ticket.setTermCode(order.getTermCode());
                ticket.setCustomerId(customer.getId());
                ticket.setReceiptStatus(ReceiptState.NOT_TAKE_AWAY.getCode());
            }
            if(hmType == ConstantValues.TSchemeHm_Type_Normal.getCode())
            {
                orderService.isAcceptable(gt, order);
            }
        }

        endTimeStamp = endTimeStamp - 20*60*1000;    //方案在期次截止前20分钟失效
        if(endTimeStamp < System.currentTimeMillis())
        {
            throw new CoreException(ErrCode.E2002, ErrCode.codeToMsg(ErrCode.E2002));
        }
        scheme.setEndTime(new Date(endTimeStamp));  //设置方案的失效时间

        long amount = scheme.getcAmount();
        if(hmType == ConstantValues.TSchemeHm_Type_Normal.getCode() || hmType == ConstantValues.TSchemeHm_Type_Gift.getCode())
        {
            //用户彩金支付
            CustomerAccount ca = customerAccountService.findOneByCustomerIdAndStationId(customer.getId(), oStationId);
            moneyService.customerBuyLot(customer, ca, scheme.getId(), amount, "", "scheme-hm");
        }
        else if(hmType == ConstantValues.TSchemeHm_Type_Sys_Gift.getCode())
        {
            moneyService.platFormPayPromotion(scheme.getId(), amount, "scheme-hm");
        }
        //如果支付成功
        schemeInter.saveHm(scheme);
        for(TOrder order:oList)
        {
            this.orderService.save(order);
        }
        repBody.setScheme(scheme);
        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    /**
     * 用户参与合买
     * @param head
     * @param body
     * @param customer
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/joinHm.htm")
    public String joinHm(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="S05") ReqS05Body body,
                      ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        RepS05Body repBody = new RepS05Body();

        SchemeHm scheme = this.schemeInter.queryHmById(body.getSchemeId());
        if(scheme == null)  //方案必须存在
        {
            throw new CoreException(ErrCode.E2018, ErrCode.codeToMsg(ErrCode.E2018));
        }
        if(scheme.getStatus() != SchemeState.RUNNING.getCode())
        {
            throw new CoreException(ErrCode.E2012, ErrCode.codeToMsg(ErrCode.E2012));
        }
        long availableShare = scheme.getAmount() - scheme.getfAmount();
        if(scheme.getType() == ConstantValues.TSchemeHm_Type_Normal.getCode())
        {
            if(body.getAmount() > availableShare) //方案必须有足够的份额供购买
            {
                throw new CoreException(ErrCode.E2019, ErrCode.codeToMsg(ErrCode.E2019));
            }
        }

        else if(scheme.getType() == ConstantValues.TSchemeHm_Type_Gift.getCode() || scheme.getType() == ConstantValues.TSchemeHm_Type_Sys_Gift.getCode())
        {
            //最后一个人应该获得所有的剩余份额
            long sharePerPerson = scheme.getAmount()/scheme.gettJoinCount();
            if(scheme.gettJoinCount() - scheme.getJoinCount() == 1)
            {
                sharePerPerson = availableShare;
            }
            body.setAmount(sharePerPerson);
        }

        //只有正常模式才会扣款
        if(scheme.getType() == ConstantValues.TSchemeHm_Type_Normal.getCode())
        {
            CustomerAccount ca = customerAccountService.findOneByCustomerIdAndStationId(customer.getId(), scheme.getStationId());
            moneyService.customerBuyLot(customer, ca, scheme.getId(), body.getAmount(), "", "scheme-hm");
        }

        SchemeShare schemeShare = new SchemeShare();
        schemeShare.setId(CoreUtil.getUUID());
        schemeShare.setCustomerId(customer.getId());
        schemeShare.setAmount(body.getAmount());
        schemeShare.setSchemeId(body.getSchemeId());
        schemeShare.setCreateTime(new Date());

        SchemeHm schemeBack = this.schemeInter.saveSchemeShare(schemeShare);
        //合买方案完成之后，更新订单、票据状态，发送出票通知
        if(schemeBack.getStatus() == SchemeState.FINISHED.getCode())
        {
            List<TOrder> oList = this.orderService.findAllBySchemeId(schemeBack.getId());
            for(TOrder order:oList)
            {
                String termCode = this.mgCurTermService.getById(order.getGameCode()).getCurTermCode();
                Term t = this.termService.findOneByGameCodeAndCode(order.getGameCode(), termCode);
                int orderStatus = OrderStateUtil.getOrderStatus(true, t.getStatus());
                int ticketStatus = TicketStateUtil.getTicketStatus(true, t.getStatus());
                orderService.afford(order.getId(), orderStatus, ticketStatus, termCode);
                order.setStatus(orderStatus);
                ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
                PrintUtil.newOrder(context, order);
            }
        }
        modelMap.put("response", repBody);
        return "plainJsonView";
    }
}
