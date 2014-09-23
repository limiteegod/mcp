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
import com.mcp.order.gateway.util.JcOrderUtil;
import com.mcp.order.gateway.util.StationGameUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.trade.*;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgWaitPay;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgWaitPayService;
import com.mcp.order.service.*;
import com.mcp.order.service.util.OrderStateUtil;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.order.service.util.TicketStateUtil;
import com.mcp.order.status.TermState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/jctrade")
public class JcTradeControl {
	
	//private static Logger log = Logger.getLogger(JcTradeControl.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private StationGameService stationGameService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private MgWaitPayService mgWaitPayService;
	
	@RequestMapping(value = "/jcLot.htm")
	public String jcLot(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="T05") ReqT05Body body,
			 ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepT05Body repBody = new RepT05Body();
		ReqOrder reqOrder = body.getOrder();
		String gameCode = reqOrder.getGameCode();
		String channelCode = head.getChannelCode();
		
		/**
		 * 校验第一场是否停售即可
		 */
		String number = reqOrder.getNumber();
		String[] matchStrArray = number.split(";");
		String matchStr = matchStrArray[0];
		String termCode = matchStr.split("\\|")[1];
		Term gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
		if(gt == null || gt.getStatus() != TermState.ON_SALE.getCode())
		{
			throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
		}
		
		Station channel = stationService.findOneByCode(channelCode);
		List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, channel, reqOrder.getStationId(), gameCode, gt.getEndTime());
		String stationId = sIdList.get(0);
		String printStationId = sIdList.get(1);
		int payType = body.getPayType();
		
		TOrder order = JcOrderUtil.getOrder(reqOrder, channelCode, customer.getId(), stationId, printStationId, payType);
		
		
		boolean afford = false;
		String filterCode = "01";
		if(payType == ConstantValues.TOrder_PayType_Cash.getCode())
		{
			filterCode += "01";
			long recharge = 0;
			try {
				//扣款
				CustomerAccount ca = customerAccountService.findOneByCustomerIdAndStationId(customer.getId(), stationId);
				List<MoneyLog> logList = moneyService.customerBuyLot(customer, ca, order.getId(), order.getAmount(), "JC", "JC");
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
            t.setTermIndexDeadline(gt.getEndTime());
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

    @RequestMapping(value = "/cJcLot.htm")
    public String cJcLot(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="T06") ReqT06Body body,
                          ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        RepT06Body repBody = new RepT06Body();
        ReqOrder reqOrder = body.getOrder();
        String gameCode = reqOrder.getGameCode();
        String channelCode = head.getChannelCode();

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

        /**
         * 校验第一场是否停售即可
         */
        String number = reqOrder.getNumber();
        String[] matchStrArray = number.split(";");
        String matchStr = matchStrArray[0];
        String termCode = matchStr.split("\\|")[1];
        Term gt = termService.findOneByGameCodeAndCode(gameCode, termCode);
        if(gt == null || gt.getStatus() != TermState.ON_SALE.getCode())
        {
            throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
        }
        List<String> sIdList = StationGameUtil.checkStationGame(stationGameService, station, reqOrder.getStationId(), gameCode, gt.getEndTime());
        String printStationId = sIdList.get(1);
        TOrder order = JcOrderUtil.getOrder(reqOrder, channelCode, station.getId(), station.getId(), printStationId, ConstantValues.TOrder_PayType_Cash.getCode());
        //设置订单的类型为渠道订单
        order.setType(TOrderType.CHANNEL);

        boolean afford = false;
        long recharge = 0;
        try {
            MoneyLog mLog = moneyService.stationLot(station.getId(), order.getId(), order.getAmount());
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
        repStation.setId(station.getId());
        repStation.setRecharge(recharge);
        repBody.setStation(repStation);

        if(afford)
        {
            Date acceptTime = new Date();
            int orderStatus = OrderStateUtil.getOrderStatus(afford, gt.getStatus());
            int ticketStatus = TicketStateUtil.getTicketStatus(afford, gt.getStatus());
            order.setStatus(orderStatus);
            order.setAcceptTime(acceptTime);
            List<TTicket> tList = order.getTickets();
            for(TTicket t:tList)
            {
                t.setStatus(ticketStatus);
                t.setAcceptTime(acceptTime);
                t.setTermIndexDeadline(gt.getEndTime());
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
}
