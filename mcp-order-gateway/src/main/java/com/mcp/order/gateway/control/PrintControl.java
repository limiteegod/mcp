/**
 * 
 */
package com.mcp.order.gateway.control;


import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.CookieUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.print.*;
import com.mcp.order.inter.util.PageInfoUtil;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.admin.StationGame;
import com.mcp.order.model.admin.Terminal;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.mongo.MgPrint;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgPrintService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.mongo.util.NotifyUtil;
import com.mcp.order.service.*;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TicketState;
import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/print")
public class PrintControl {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private TerminalService terminalService;

    @Autowired
    private TermService termService;

    @Autowired
    private StationGameService stationGameService;
    
    @Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private MgTicketService mgTicketService;

    @Autowired
    private MgPrintService mgPrintService;
	
	@Autowired
	private MoneyService moneyService;
	
	private static Logger log = Logger.getLogger(PrintControl.class);

    /**
     * 取票接口
     * @param head 消息头
     * @param body  消息体
     * @param station 机构
     * @param modelMap 模型属性
     * @return  plainJsonView
     * @throws Exception
     */
	@RequestMapping(value = "/getOrders.htm")
	public String getOrders(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P01") ReqP01Body body,
			 ModelMap modelMap) throws Exception {
		RepP01Body repBody = new RepP01Body();
		String stationId = station.getId();
		List<String> gameCodeList = body.getGameCodes();
		int size = body.getSize();
		
		List<TTicket> ticketList;
		try {
			ticketList = ticketService.findAllToPrint(gameCodeList, stationId, size).getContent();
		} 
		catch(StaleObjectStateException e)	//如果竞争失败，直接返回空记录
		{
			ticketList = new ArrayList<TTicket>();
		}
		
		List<RepP01Ticket> repTicketList = new ArrayList<RepP01Ticket>();
		for(int j = 0; j < ticketList.size(); j++)
		{
			TTicket ticket = ticketList.get(j);
			RepP01Ticket repTicket = new RepP01Ticket();
			repTicket.setTicketId(ticket.getId());
			repTicket.setBetType(ticket.getBetTypeCode());

            String termCode = ticket.getTermCode();
            Game game = LotteryContext.getInstance().getGameByCode(ticket.getGameCode());
            if(game.getType() == ConstantValues.Game_Type_Jingcai.getCode())
            {
                termCode = termCode.split(Constants.SEP_COMMA)[0];
            }
			//票的截止打印时间应计算
			Term gt = termService.findOneByGameCodeAndCode(ticket.getGameCode(), termCode);
			StationGame stationGame = stationGameService.findOneByStationIdAndGameCodeAndStatus(stationId, ticket.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
			log.info("ticketId:-----" + ticket.getId());
			Date endDate = gt.getEndTime();
			int minute = stationGame.getEarlyStopBufferSimplex();
			long millisecond = minute*60*1000;
			repTicket.setDeadline(new Date(endDate.getTime() - millisecond/2));
			
			repTicket.setGameCode(ticket.getGameCode());
			repTicket.setMultiple(ticket.getMultiple());
			repTicket.setNumbers(ticket.getNumbers());
			repTicket.setOrderId(ticket.getOrderId());
			repTicket.setPlayType(ticket.getPlayTypeCode());
			repTicket.setTermIndex(ticket.getTermIndex());
			repTicket.setTermIndexDeadline(ticket.getTermIndexDeadline());
			repTicketList.add(repTicket);
		}
		repBody.setTickets(repTicketList);
		repBody.setSize(repTicketList.size());
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/printBack.htm")
	public String printBack(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P02") ReqP02Body body,
			 ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepP02Body repBody = new RepP02Body();

		String ticketId = body.getTicketId();
		int code = body.getCode();
		TTicket ticketBack = ticketService.printBack(ticketId, code, "", null, body.getStubInfo(), body.getrNumber(), body.isPaper());
		//如果是出票成功，则还需要更新订单状态
		if(code == Constants.TICKET_PRINT_RECEIPT_SUCCESS)
		{
			//保存到mongodb，供算奖使用
			Game game = LotteryContext.getInstance().getGameByCode(ticketBack.getGameCode());
			mgTicketService.save(ticketBack, game.getType());

            TOrder torder = null;
            boolean success = false;
            int count = 0;
            while(!success && count < 10)
            {
                try {
                    torder = orderService.findOne(ticketBack.getOrderId());
                    success = orderService.incrPrintCount(torder);
                }
                catch (StaleObjectStateException e)
                {
                    log.error("出票已经成功，订单更新失败，id:" + ticketBack.getOrderId());
                }
                finally {
                    count++;
                }
            }
            if(!success)
            {
                throw new CoreException(ErrCode.E0999);
            }
            //print station get the money
            StationGame sg = stationGameService.findOneByStationIdAndGameCodeAndStatus(ticketBack.getPrinterStationId(), torder.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
            int factor = sg.getpFactor();
            long amount = ticketBack.getAmount()*factor/10000;
            moneyService.orderPrintSuccess(ticketBack.getPrinterStationId(), ticketBack.getId(), amount);

            //sale station get the sale percentage
            sg = stationGameService.findOneByStationIdAndGameCodeAndStatus(torder.getStationId(), torder.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
            int rFactor = sg.getrFactor();
            if(rFactor > 0)     //如果有转票提成，则收取一定比例费用
            {
                long rAmount = ticketBack.getAmount()*rFactor/10000;
                moneyService.orderPrintSuccessSalePercentage(torder.getStationId(), ticketBack.getId(), rAmount);
            }

            if(torder.getStatus() == OrderState.SUCCESS.getCode())
			{
                //发送出票成功通知
                ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
                NotifyUtil.sendN02(context, torder);
			}
		}
		repBody.setTicketId(ticketBack.getId());
		repBody.setAmount(ticketBack.getAmount());
		repBody.setCode(code);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 终端机兑奖
	 */
	@RequestMapping(value = "/getAllToPrize.htm")
	public String getAllToPrize(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P03") ReqP03Body body,
			 ModelMap modelMap) throws Exception {
		RepP03Body repBody = new RepP03Body();
		Terminal terminal = terminalService.findOneByCode(body.getTerminalCode());
		if(terminal == null)
		{
			throw new CoreException(ErrCode.E2039, ErrCode.codeToMsg(ErrCode.E2039));
		}
		List<TTicket> ticketList;
		try {
			ticketList = ticketService.getAllToPrize(terminal.getId(), body.getGameCode(), body.getGameTermCode(), body.getSize());
		} 
		catch(StaleObjectStateException e)	//如果竞争失败，直接返回空记录
		{
			ticketList = new ArrayList<TTicket>();
		}
		List<RepP03Ticket> repTicketList = new ArrayList<RepP03Ticket>();
		for(int i = 0; i < ticketList.size(); i++)
		{
			TTicket ticket = ticketList.get(i);
			RepP03Ticket repTicket = new RepP03Ticket();
			repTicket.setTicketId(ticket.getId());
			repTicket.setMultiple(ticket.getMultiple());
			repTicket.setNumbers(ticket.getNumbers());
			repTicket.setStubInfo(ticket.getStubInfo());
			repTicket.setBonus(ticket.getBonus());
			repTicketList.add(repTicket);
		}
		repBody.setTickets(repTicketList);
		repBody.setSize(repTicketList.size());
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 终端机兑奖返回回执
	 */
	@RequestMapping(value = "/prizeBack.htm")
	public String prizeBack(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P04") ReqP04Body body,
			 ModelMap modelMap) throws Exception {
		RepP04Body repBody = new RepP04Body();
		TTicket ticket = ticketService.prizeBack(body.getTicketId(), body.getCode());
		repBody.setTicketId(ticket.getId());
		repBody.setBonus(ticket.getBonus());
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

	@RequestMapping(value = "/login.htm")
	public String login(@JsonHead(value="head",checkChannel=false) Head head, @McpStation(required = false) Station station, @JsonBody(value="body", cmd="P05") ReqP05Body body,
                        ModelMap modelMap,
			            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		RepP05Body repBody = new RepP05Body();
		if(body.getType() == Constants.STATION_OP_LOGIN)
		{
			Station loginStation = stationService.findOneByCode(body.getCode());
			if(loginStation != null && loginStation.getPassword().equals(body.getPassword()))
			{
				ticketService.resetAllTakenAway(loginStation.getId());

				repBody.setStatus(true);
				loginStation.setLastLoginTime(new Date());
				stationService.save(loginStation);

				//更新cookie
				String userMd5 = CookieUtil.getUserMd5(loginStation.getId(), loginStation.getPassword(), loginStation.getLastLoginTime().getTime());
				CookieUtil.updateUserInfoCookie(httpServletRequest, httpServletResponse, loginStation.getId(), Constants.USER_TYPE_STATION, userMd5);
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
			//更新已取走的票为待打印状态。TTicket的status值从2更新为1.
			ticketService.resetAllTakenAway(station.getId());
			repBody.setStatus(true);
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

	/**
	 * 出票接口
	 * @param head
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/printByOrderId.htm")
	public String printByOrderId(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, 
			@JsonBody(value="body", cmd="P06") ReqP06Body body, ModelMap modelMap) throws Exception {
		RepP06Body repBody = new RepP06Body();
		String stationId = station.getId();
		String orderId = body.getOrderId();
		
		List<TTicket> ticketList;
		try {
			ticketList = ticketService.findAllToPrintByOrderId(orderId, stationId);
		} 
		catch(StaleObjectStateException e)	//如果竞争失败，直接返回空记录
		{
			ticketList = new ArrayList<TTicket>();
		}
		repBody.setTickets(ticketList);
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 投注站直接为用户注册
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/regUser.htm")
	public String regUser(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, 
			@JsonBody(value="body", cmd="P07") ReqP07Body body, ModelMap modelMap) throws Exception {
		RepP07Body repBody = new RepP07Body();
		
		Customer cus = body.getCustomer();
		//验证用户名的唯一性
		Customer cusInDb = customerService.findOneByNameAndChannelCode(cus.getName(), head.getChannelCode());
		if(cusInDb != null)
		{   
			String uPwd = cus.getPassword();
			customerService.updatePasswordById(uPwd, cusInDb.getId());
			
			cusInDb.setPassword(uPwd);
			repBody.setCustomer(cusInDb);
		}
		else
		{
			cus.setId(CoreUtil.getUUID());
			cus.setEmail("");
			Date now = new Date();
			cus.setRegDate(now);
			String uPwd = cus.getPassword();
			String pwd = MD5Util.MD5(uPwd + now.getTime()/1000);
			cus.setPassword(pwd);
			cus.setLastActiveTime(now);
			cus.setRecharge(0);
			cus.setIntegral(0);
			cus.setLastLoginTime(now);
			cus.setStatus(ConstantValues.CustomerAccount_Status_Open.getCode());
			cus.setChannelCode(head.getChannelCode());
			Customer customerAfterSave = customerService.save(cus);
			
			String stationId = station.getId();
			//绑定投注站
			CustomerAccount ca = new CustomerAccount();
			ca.setId(CoreUtil.getUUID());
			ca.setCustomerId(cus.getId());
			ca.setIntegral(0);
			ca.setPrize(0);
			ca.setRecharge(0);
			ca.setStationId(stationId);
			ca.setStatus(ConstantValues.CustomerAccount_Status_Open.getCode());
			customerAccountService.save(ca);
			
			customerAfterSave.setPassword(uPwd);
			repBody.setCustomer(customerAfterSave);
		}
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 出票中心按订单出票成功
	 * @param message
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/printBackByOrder.htm")
	public String printBackByOrder(@JsonHead(value="head",checkChannel=false) Head head, @JsonBody(value="body", cmd="P08") ReqP08Body body, @McpUser Station station, ModelMap modelMap) throws Exception {
		RepP08Body repBody = new RepP08Body();
		TOrder order = body.getOrder();
		
		int succCount = 0;
		List<TTicket> tList = order.getTickets();
		for(TTicket t:tList)
		{
			TTicket bTicket = ticketService.printBack(t.getStatus(), order.getId(), t.getSeq(), t.getrNumber());
			if(bTicket.getStatus() == TicketState.PRINT_SUCCESS.getCode())
			{
				Game game = LotteryContext.getInstance().getGameByCode(bTicket.getGameCode());
				mgTicketService.save(bTicket, game.getType());
				succCount++;
			}
		}
		TOrder torder = orderService.incrPrintCount(order.getId(), succCount);
		
		//出票成功，出票中心收出票款
		StationGame sg = stationGameService.findOneByStationIdAndGameCodeAndStatus(torder.getPrintStationId(), torder.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
		int factor = sg.getpFactor();
		long amount = torder.getAmount()*factor/10000;
		moneyService.orderPrintSuccess(torder.getPrintStationId(), torder.getId(), amount);
		//记录平台收入
		long pAmount  = torder.getAmount() - amount;	
		
		//如果发生了转票，则销售方收取一定比例费用
		if(!torder.getStationId().equals(torder.getPrintStationId()))
		{
			sg = stationGameService.findOneByStationIdAndGameCodeAndStatus(torder.getStationId(), torder.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
			int rFactor = sg.getrFactor();
			long rAmount = torder.getAmount()*rFactor/10000;
			moneyService.orderPrintSuccessSalePercentage(torder.getStationId(), torder.getId(), rAmount);
			pAmount = pAmount - rAmount;
		}
		
		//记录平台收入
		moneyService.platFormSalePercentage(torder.getId(), pAmount);
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}*/
	
	/**
	 * 投注站取某一个用户未打印纸质票的ticket，进行打印
	 * @param head
	 * @param body
	 * @param station
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/printPaper.htm")
	public String printPaper(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P09") ReqP09Body body,
			 ModelMap modelMap) throws Exception {
		RepP09Body repBody = new RepP09Body();
		List<TTicket> ticketList;
		try {
			ticketList = ticketService.findToPrintPaper(body.getCustomerId(), station.getId(), 10).getContent();
		} 
		catch(StaleObjectStateException e)	//如果竞争失败，直接返回空记录
		{
			ticketList = new ArrayList<TTicket>();
		}
		repBody.setTtickets(ticketList);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

    /**
     * 投注站取某一个用户未打印纸质票的ticket，进行打印
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetTakeAway.htm")
    public String resetTakeAway(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P10") ReqP10Body body,
                              ModelMap modelMap) throws Exception {
        RepP10Body repBody = new RepP10Body();
        this.ticketService.resetAllTakenAway(station.getId());
        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    /**
     * 出票失败
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/printFailure.htm")
    public String printFailure(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P11") ReqP11Body body,
                                 ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        RepP11Body repBody = new RepP11Body();
        TTicket t = this.ticketService.findOne(body.getTicketId());
        if(t.getStatus() != TicketState.PRINT_FAILURE.getCode())
        {
            throw new CoreException(ErrCode.E2054, ErrCode.codeToMsg(ErrCode.E2054));
        }
        TOrder order = this.orderService.incrFailureTicket(t.getOrderId());
        if(order.getStatus() == OrderState.REFUNDED.getCode())
        {
            String customerId = order.getCustomerId();
            if(order.getType() == TOrderType.CHANNEL)
            {
                this.moneyService.refundToStation(customerId, t.getOrderId(), t.getId(), t.getAmount());
            }
            else
            {
                this.moneyService.refundToCustomer(t.getStationId(), customerId, t.getOrderId(), t.getId(), t.getAmount());
            }
            //发送退款通知
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
            NotifyUtil.sendN02(context, order);
        }
        this.ticketService.updateStatusById(TicketState.REFUNDED.getCode(), t.getId());

        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    /**
     * 投注站获得需要出票的订单id的队列
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPrintQueen.htm")
    public String getPrintQueen(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P12") ReqP12Body body,
                                ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        RepP12Body repBody = new RepP12Body();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "_id"));
        PageRequest pr = new PageRequest(0, body.getSize(), sort);
        Page<MgPrint> pageList = this.mgPrintService.find(station.getCode(), pr);
        List<MgPrint> oList = pageList.getContent();
        if(oList.size() > 0)
        {
            long maxId = oList.get(oList.size() - 1).getId();
            this.mgPrintService.deleteAllByIdLessThanOrEqualTo(station.getCode(), maxId);
        }
        repBody.setRst(pageList.getContent());
        repBody.setPi(PageInfoUtil.getPageInfo(pageList));
        modelMap.put("response", repBody);
        return "plainJsonView";
    }


    /**
     * 投注站获得需要出票的订单id的队列,下的所有票信息
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getNewPrintQueen.htm")
    public String getNewPrintQueen(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P20") ReqP20Body body,
                                ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        RepP20Body repBody = new RepP20Body();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "_id"));
        PageRequest pr = new PageRequest(0, body.getSize(), sort);
        Page<MgPrint> pageList = this.mgPrintService.find(station.getCode(), pr);
        List<MgPrint> oList = pageList.getContent();
        if(oList.size() > 0)
        {
            long maxId = oList.get(oList.size() - 1).getId();
            this.mgPrintService.deleteAllByIdLessThanOrEqualTo(station.getCode(), maxId);
        }
        List<TTicket> ticketList = new ArrayList<TTicket>();
        try {
            for (MgPrint mgPrint : oList){
                ticketList.addAll(ticketService.findAllToPrintByOrderId(mgPrint.getOrderId(), station.getId()));
            }
        }
        catch(StaleObjectStateException e)	//如果竞争失败，直接返回空记录
        {
            ticketList = new ArrayList<TTicket>();
        }
        repBody.setRst(ticketList);
        repBody.setPi(PageInfoUtil.getPageInfo(pageList));
        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    @RequestMapping(value = "/newPrintBack.htm")
    public String newPrintBack(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="P21") ReqP21Body body,
                            ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        List<ReqP02Body> reqP02BodyList = body.getReqP02BodyList();
        RepP21Body repP21Body = new RepP21Body();
        List<RepP02Body> repP02Bodies = new ArrayList<RepP02Body>();
        for (ReqP02Body reqP02Body : reqP02BodyList){
            RepP02Body repBody = new RepP02Body();
            String ticketId = reqP02Body.getTicketId();
            int code = reqP02Body.getCode();
            TTicket ticketBack = ticketService.printBack(ticketId, code, "", null, reqP02Body.getStubInfo(), reqP02Body.getrNumber(), reqP02Body.isPaper());
            //如果是出票成功，则还需要更新订单状态
            if(code == Constants.TICKET_PRINT_RECEIPT_SUCCESS)
            {
                //保存到mongodb，供算奖使用
                Game game = LotteryContext.getInstance().getGameByCode(ticketBack.getGameCode());
                mgTicketService.save(ticketBack, game.getType());

                TOrder torder = null;
                boolean success = false;
                int count = 0;
                while(!success && count < 10)
                {
                    try {
                        torder = orderService.findOne(ticketBack.getOrderId());
                        success = orderService.incrPrintCount(torder);
                    }
                    catch (StaleObjectStateException e)
                    {
                        log.error("出票已经成功，订单更新失败，id:" + ticketBack.getOrderId());
                    }
                    finally {
                        count++;
                    }
                }
                if(!success)
                {
                    throw new CoreException(ErrCode.E0999);
                }
                //print station get the money
                StationGame sg = stationGameService.findOneByStationIdAndGameCodeAndStatus(ticketBack.getPrinterStationId(), torder.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
                int factor = sg.getpFactor();
                long amount = ticketBack.getAmount()*factor/10000;
                moneyService.orderPrintSuccess(ticketBack.getPrinterStationId(), ticketBack.getId(), amount);

                //sale station get the sale percentage
                sg = stationGameService.findOneByStationIdAndGameCodeAndStatus(torder.getStationId(), torder.getGameCode(), ConstantValues.StationGame_Status_Open.getCode());
                int rFactor = sg.getrFactor();
                if(rFactor > 0)     //如果有转票提成，则收取一定比例费用
                {
                    long rAmount = ticketBack.getAmount()*rFactor/10000;
                    moneyService.orderPrintSuccessSalePercentage(torder.getStationId(), ticketBack.getId(), rAmount);
                }

                if(torder.getStatus() == OrderState.SUCCESS.getCode())
                {
                    //发送出票成功通知
                    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
                    NotifyUtil.sendN02(context, torder);
                }
            }
            repBody.setTicketId(ticketBack.getId());
            repBody.setAmount(ticketBack.getAmount());
            repBody.setCode(code);

            repP02Bodies.add(repBody);
        }
        modelMap.put("response", repP21Body);
        return "plainJsonView";
    }


}
