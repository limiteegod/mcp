/**
 *
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.dao.redis.RedisHelp;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.query.*;
import com.mcp.order.inter.util.PageInfoUtil;
import com.mcp.order.model.admin.ClientFileVersion;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.jc.JOdds;
import com.mcp.order.model.mongo.MgNotifyMsg;
import com.mcp.order.model.mongo.MgTermSeal;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgNotifyMsgService;
import com.mcp.order.mongo.service.MgOddsService;
import com.mcp.order.mongo.service.MgTermSealService;
import com.mcp.order.service.*;
import com.mcp.order.service.js.JOddsService;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TermState;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
/**
 * @author ming.li
 */
@Controller
@RequestMapping("/query")
public class QueryControl {

    private static Logger log = Logger.getLogger(QueryControl.class);

    @Autowired
    private TermService termService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ClientFileVersionService clientFileVersionService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private MoneyService moneyService;
    
    @Autowired
    private PromotionService promotionServcie;
    
    @Autowired
    private MoneyLogService moneyLogService;
    
    @Autowired
	private MgOddsService mgOddsService;

    @Autowired
    private MgTermSealService mgTermSealService;

    @Autowired
    private MgNotifyMsgService mgNotifyMsgService;

    /**
     * Q01 期次状态查询。
     */
    @RequestMapping(value = "/getGameTerm.htm")
    public String getGameTerm(@JsonHead(value="head",checkChannel=false) Head head, 
    		@JsonBody(value="body", cmd="Q01") ReqQ01Body body, ModelMap modelMap) throws Exception {
    	RepQ01Body repBody = new RepQ01Body();
    	List<Term> list = new ArrayList<Term>();
        switch (body.getType()) {
            case Constants.CMD_Q01_TYPE_0:
                //type为0，根据游戏编码和期次编码进行查询。
                for (ReqQ01Term term : body.getTerms()) {
                    Term gt = termService.findOneByGameCodeAndCode(term.getGameCode(), term.getTermCode());
                    if (gt != null) {
                        list.add(gt);
                    }
                }
                break;
            case Constants.CMD_Q01_TYPE_1:
                // Type为1，获取所有可用游戏当前在售的期次信息
                list = termService.findAllByStatus(TermState.ON_SALE.getCode());
                break;
            case Constants.CMD_Q01_TYPE_2:
                // type为2，获取所有可用游戏最后一期已经开奖的期次信息
                ArrayList<Object[]> al = termService.findMaxGameCodeGroupByGame(TermState.DRAW.getCode());
                Iterator<Object[]> it = al.iterator();
                while (it.hasNext()) {
                    Object[] ob = (Object[]) it.next();
                    Term gt = termService.findOneByGameCodeAndCode((String) ob[0], (String) ob[1]);
                    list.add(gt);
                }
                break;
            case Constants.CMD_Q01_TYPE_3:
                // Type为3，进行特定彩种的历史期次（已经开奖）分页查询。
                list = termService.findAllByGameCodeAndStatusGreaterThanOrderByCodeDesc(body.getGameCode(), TermState.WAITING_DRAW_NUMBER.getCode(),
                        new PageRequest(body.getStartIndex(), body.getSize()));
                break;
            case Constants.CMD_Q01_TYPE_4:
                // Type为4，获取指定游戏当前在售的期次信息。
                for (ReqQ01Term term : body.getTerms()) {
                    List<Term> gtList = termService.findAllByStatusAndGameCode(TermState.ON_SALE.getCode(), term.getGameCode());
                    if (gtList != null) {
                        list.addAll(gtList);
                    }
                }
                break;
            default:
                throw new CoreException(ErrCode.E0003, "查询的type不正确");

        }
        repBody.setRst(list);
		if(body.isShowGrade())
		{
			modelMap.put("filterCode", "02");
		}
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }
    
    /**
     * Q02 方案查询
     */
    /*@RequestMapping(value = "/getScheme.htm")
    public String getScheme(@JsonHead(value="head",checkChannel=false) Head head, @JsonBody(value="body", cmd="Q02") ReqQ02Body body, @McpUser Customer customer, ModelMap modelMap) throws Exception {
    	RepQ02Body repBody = new RepQ02Body();
    	String schemeId = body.getSchemeId();
    	if(StringUtil.isEmpty(schemeId))
    	{
    		Sort sort = new Sort(new Order(Direction.DESC, "createTime"));
    		PageRequest pr = new PageRequest(body.getStartIndex(), body.getSize(), sort);
    		List<TScheme> schemeList = schemeService.query(body.getGameCode(), body.getStationId(), customer.getId(), body.getSchemeType(), pr).getContent();
    		repBody.setRst(schemeList);
    	}
    	else	//直接通过schemeId查询
    	{
    		List<TScheme> schemeList = new ArrayList<TScheme>();
    		TScheme scheme = schemeService.findOne(schemeId);
    		if(scheme != null && scheme.getCustomerId().equals(customer.getId()))
    		{
    			schemeList.add(scheme);
    		}
    		repBody.setRst(schemeList);
    	}
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }*/

    /**
     * Q03 投注记录查询
     */
    @RequestMapping(value = "/getBetHistory.htm")
    public String getBetHistory(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer user, @JsonBody(value="body", cmd="Q03") ReqQ03Body body,
    		ModelMap modelMap) throws Exception {
    	RepQ03Body repBody = new RepQ03Body();
    	String gameCode = body.getGameCode();
        String stationId = body.getStationId();
        String exOrderStatus = body.getExOrderStatus();
        if(StringUtil.isEmpty(exOrderStatus))
        {
        	exOrderStatus = OrderState.INIT.getCode() + "";
        }
        else
        {
        	exOrderStatus += "," + OrderState.INIT.getCode();
        }
        String status = body.getOrderStatus();
        int schemeType = body.getSchemeType();
        String orderId = body.getOrderId();
        String schemeId = body.getSchemeId();
        //TODO 按createTime倒序
        Sort sort = new Sort(new Order(Direction.DESC, "createTime"), new Order(Direction.DESC, "termCode"));
        PageRequest p = new PageRequest(body.getStartIndex(), body.getSize(), sort);
        Page<TOrder> page = orderService.userQuery(user, gameCode, orderId, schemeId, schemeType, stationId, status, exOrderStatus, p);
        List<TOrder> oList = page.getContent();
        if(body.isShowTickets())	//如果需要票级别的信息
        {
        	for(int i = 0; i < oList.size(); i++)
            {
            	TOrder order = oList.get(i);
            	order.setTickets(ticketService.findAllByOrderId(order.getId()));
            }
        }
        repBody.setRst(oList);
        repBody.setPageInfo(new PageInfo(page.getNumber(), page.getSize(), page.getTotalPages(), 
    			page.getNumberOfElements(), page.getTotalElements(), page.hasPreviousPage(), page.isFirstPage(),
    			page.hasNextPage(), page.isLastPage()));
        modelMap.put("response", repBody);
		return "plainJsonView";
    }

    /**
     * Q04 彩民交易流水查询
     */
    @RequestMapping(value = "/getBetAccountLog.htm")
    public String getBetAccountLog(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="Q04") ReqQ04Body body,
    		 ModelMap modelMap) throws Exception {
    	RepQ04Body repBody = new RepQ04Body();
    	List<String> entityIds = body.getEntityIds();
    	if(entityIds == null || entityIds.size() == 0)
    	{
    		entityIds = new ArrayList<String>();
    		entityIds.add(customer.getId());
    		List<CustomerAccount> caList = customerAccountService.findAllByCustomerId(customer.getId());
    		for(int i = 0; i < caList.size(); i++)
    		{
    			entityIds.add(caList.get(i).getId());
    		}
    	}
    	Page<MoneyLog> page = moneyLogService.customerQueryRecord(entityIds, body.getAccountType(), body.getStartTime(), body.getEndTime(), body.getStartIndex(), body.getSize());
    	repBody.setRst(page.getContent());
    	repBody.setPageInfo(new PageInfo(page.getNumber(), page.getSize(), page.getTotalPages(), 
    			page.getNumberOfElements(), page.getTotalElements(), page.hasPreviousPage(), page.isFirstPage(),
    			page.hasNextPage(), page.isLastPage()));
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }

    /**
     * Q05 投注站查询
     */
    @RequestMapping(value = "/getStation.htm")
    public String getStation(@JsonHead(value="head",checkChannel=false) Head head, 
    		@JsonBody(value="body", cmd="Q05") ReqQ05Body body, ModelMap modelMap) throws Exception {
    	RepQ05Body repBody = new RepQ05Body();
    	//TODO 转到betshop表的查询
    	/*Sort sort = new Sort(new Order(Direction.ASC, "code"));
		PageRequest pr = new PageRequest(body.getStartIndex(), body.getSize(), sort);
    	Page<Station> page = stationService.query(body.getStationId(), body.getLongitude(), body.getLongitudeGap(), 
    			body.getLatitude(), body.getLatitudeGap(), body.getKeyword(), pr);
    	
    	repBody.setPageInfo(new PageInfo(page.getNumber(), page.getSize(), page.getTotalPages(), 
    			page.getNumberOfElements(), page.getTotalElements(), page.hasPreviousPage(), page.isFirstPage(),
    			page.hasNextPage(), page.isLastPage()));
    	repBody.setRst(page.getContent());*/
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }

    /**
     * Q07 系统时间查询
     */
    @RequestMapping(value = "/getSystemTime.htm")
    public String getSystemTime(@JsonHead(value="head",checkChannel=false) Head head, 
    		@JsonBody(value="body", cmd="Q07") ReqQ07Body body, ModelMap modelMap) throws Exception {
    	RepQ07Body repBody = new RepQ07Body();
    	repBody.setTime(new Date());
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }
    
    /**
     * Q08 第三方支付通知接口
     */
    /*@RequestMapping(value = "/infoCollect.htm")
    public String infoCollect(@JsonHead(value="head",checkChannel=false) Head head, @JsonBody(value="body", cmd="Q08") ReqQ08Body body, ModelMap modelMap) throws Exception {
    	RepQ08Body repBody = new RepQ08Body();
    	String orderId = body.getOrderId();
    	int orderPayType = ConstantValues.TOrder_PayType_Company.getCode();
    	String orderType = redisHelp.get(RedisKey.getOrderType(orderId));
    	String fromFlag = body.getPayment();
		int paymentType = body.getPaymentType();
		TScheme scheme = null;
		List<TOrder> orderList = new ArrayList<TOrder>();
		
		//TODO 正式环境打开金额校验
		//long amount = body.getAmount();
		
    	if(orderType.equals("RU0110000"))
    	{
    		scheme = new TScheme();
			scheme.setType(ConstantValues.TScheme_Type_Default.getCode());
			TOrder order = orderService.findOne(orderId);
			scheme.setId(order.getId());
			scheme.setTotalAmount(order.getAmount());
			scheme.setStationId(order.getStationId());
			scheme.setCustomerId(order.getCustomerId());
			orderList.add(order);
    	}
    	else if(orderType.equals("RU0110001"))
    	{
    		scheme = schemeService.findOne(orderId);
			orderList = orderService.findAllBySchemeId(orderId);
    	}
    	for(int i = 0; i < orderList.size(); i++)
		{
			TOrder order = orderList.get(i);
			order.setTickets(ticketService.findAllByOrderId(order.getId()));
		}
		scheme.setOrderList(orderList);
		String customerId = scheme.getCustomerId();
		Customer customer = customerService.findOne(customerId);
		schemeService.customerPayForAnSchemeNoCheck(customer, scheme, orderPayType, paymentType, fromFlag);
		schemeService.isSchemeAcceptable(customer, scheme);
		schemeService.updateStatusAfterPaySuccess(scheme);
		schemeService.updateDbAfterPaySuccess(scheme);
		
		//增加出票池
		stationService.updatePoolSize(scheme.getOrderList());
		TOrder order = scheme.getOrderList().get(0);
		//统计实时销售额
		String gameCode = order.getGameCode();
		String termCode = order.getTermCode();
		long step = scheme.getTotalAmount();
		redisHelp.incrBy(RedisKey.getGameTermChannelSale(gameCode, termCode, order.getChannelCode()), step);
		redisHelp.incrBy(RedisKey.getGameTermSale(gameCode, termCode), step);
		redisHelp.incrBy(RedisKey.getGameSale(gameCode), step);
		
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }*/

    /**
     * Q09 帮助信息查询
     */
    @RequestMapping(value = "/getFiles.htm")
    public String getFiles(@JsonHead(value="head",checkChannel=false) Head head, @JsonBody(value="body", cmd="Q09") ReqQ09Body body, ModelMap modelMap) throws Exception {
    	RepQ09Body repBody = new RepQ09Body();
    	ClientFileVersion cfs = clientFileVersionService.getLatest(body.getClientCode(), body.getFileTypeCode());
        if(cfs == null)
        {
        	repBody.setLatest(true);
        	repBody.setFileUrl("");
        }
        else
        {
        	if(Double.parseDouble(cfs.getFileVersionCode()) > Double.parseDouble(body.getFileVersionCode()))
        	{
        		repBody.setLatest(false);
            	repBody.setFileUrl(cfs.getFileUrl());
        	}
        	else
        	{
        		repBody.setLatest(true);
            	repBody.setFileUrl(cfs.getFileUrl());
        	}
        }
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }

    /**
     * Q10 彩票查询
     */
    @RequestMapping(value = "/getTicket.htm")
    public String getTicket(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer user, @JsonBody(value="body", cmd="Q10") ReqQ10Body body,
    		 ModelMap modelMap) throws Exception {
    	RepQ10Body repBody = new RepQ10Body();
    	String fromUserId = user.getId();
    	List<TTicket> list = new ArrayList<TTicket>();
        if(StringUtil.isEmpty(body.getTicketId()))
        {
        	list = ticketService.findAllByOrderId(body.getOrderId());
        }
        else
        {
        	TTicket ticket = ticketService.findOne(body.getTicketId());
            list.add(ticket);
        }
        if(list.size() > 0)	//如果有数据的话，校验权限
        {
        	String toUserId = list.get(0).getCustomerId();
        	if(!fromUserId.equals(toUserId))
        	{
        		throw new CoreException(ErrCode.E1024, ErrCode.codeToMsg(ErrCode.E1024));
        	}
        }
        repBody.setRepCode(Constants.SUCCESS);
        repBody.setCounts(list.size());
        repBody.setRst(list);
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }
    
    /**
     * Q11 根据状态查询当前游戏
     */
    @RequestMapping(value = "/getGamesByStatus.htm")
    public String getGamesByStatus(@JsonHead(value="head",checkChannel=false) Head head, @JsonBody(value="body", cmd="Q11") ReqQ11Body body, 
    		ModelMap modelMap) throws Exception {
    	RepQ11Body repBody = new RepQ11Body();
    	List<Game> gameList = gameService.findAllByStatus(body.getStatus());
    	for(int i = 0; i < gameList.size(); i++)
    	{
    		Game g = gameList.get(i);
    		g.setCurTerm(termService.findFirstSaleTerm(g.getCode()));
    	}
    	repBody.setGames(gameList);
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }
    
    /**
     * Q12 彩民获取系统发给自己的通知
     */
    @RequestMapping(value = "/getMessages.htm")
    public String getMessages(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="Q12") ReqQ12Body body, ModelMap modelMap) throws Exception {
    	RepQ12Body repBody = new RepQ12Body();
    	/*int type = body.getType();
    	Date time = body.getTime();
    	int size = body.getSize();
    	repBody.setMessages(redisHelp.getCustomerMsg(customer.getId(), type, time, size));
    	*/
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }
    
    /**
     * Q13 获取系统活动
     */
    @RequestMapping(value = "/getPromotions.htm")
    public String getPromotions(@JsonHead(value="head",checkChannel=false) Head head, 
    		@JsonBody(value="body", cmd="Q13") ReqQ13Body body, ModelMap modelMap) throws Exception {
    	RepQ13Body repBody = new RepQ13Body();
    	/*List<Promotion> proList = promotionServcie.getAllAvailablePromotions();
    	repBody.setPromotions(proList);*/
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }
    
    /**
     * Q14 查询竞彩场次信息，同时返回赔率信息
     */
    @RequestMapping(value = "/getJcMatches.htm")
    public String getJcMatches(@JsonHead(value="head",checkChannel=false) Head head, 
    		@JsonBody(value="body", cmd="Q14") ReqQ14Body body, ModelMap modelMap) throws Exception {
    	RepQ14Body repBody = new RepQ14Body();
    	String gameCode = body.getGameCode();
    	String matchCode = body.getMatchCode();
    	List<Term> mList = termService.query(gameCode, matchCode, body.getStatus(), body.getExStatus(), new Sort(Sort.Direction.ASC, "code"));
    	List<String> oCodeList = body.getOddsCode();
    	List<String> playTypeList = body.getPlayTypeCode();
    	List<String> suffixKeyList = new ArrayList<String>();
    	List<String> idList = new ArrayList<String>();
    	if(playTypeList == null)	//如果没有指定玩法代码，则查询所有的玩法
    	{
    		playTypeList = new ArrayList<String>();
    		Map<Integer, ConstantValues> playMap = ConstantValues.getValuesMap().get("Jc_Odds_PlayType");
			Iterator<Integer> keys = playMap.keySet().iterator();
			while(keys.hasNext())
			{
				String playTypeCode = playMap.get(keys.next()).getValue();
				playTypeList.add(playTypeCode);
			}
    	}
    	if(oCodeList == null)
    	{
    		oCodeList = new ArrayList<String>();
    	}
    	for(String oCode:oCodeList)
    	{
    		for(String playTypeCode:playTypeList)
    		{
    			suffixKeyList.add(oCode + playTypeCode);
    		}
    	}
    	for(Term t:mList)
    	{
    		for(String suffixKey:suffixKeyList)
        	{
        		idList.add(t.getGameCode() + t.getCode() + suffixKey);
        	}
    	}
    	List<JOdds> oList = this.mgOddsService.findByIdList(idList);
    	Map<String, JOdds> oMap = new HashMap<String, JOdds>();
    	for(JOdds odds:oList)
    	{
    		oMap.put(odds.getId(), odds);
    	}
    	for(Term t:mList)
    	{
    		Map<String, JOdds> tMap = new HashMap<String, JOdds>();
    		for(String suffixKey:suffixKeyList)
        	{
    			tMap.put(suffixKey, oMap.get(t.getGameCode() + t.getCode() + suffixKey));
        	}
    		t.setOddsMap(tMap);
    	}
    	repBody.setRst(mList);
    	modelMap.put("response", repBody);
		return "plainJsonView";
    }

    /**
     * 渠道查询投注记录接口
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cGetBetHistory.htm")
    public String cGetBetHistory(@JsonHead(value="head", checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="Q15") ReqQ15Body body,
                                 ModelMap modelMap) throws Exception {
        RepQ15Body repBody = new RepQ15Body();
        String outerId = body.getOuterId();
        String orderId = body.getOrderId();
        List<TOrder> oList = orderService.stationQuery(station, orderId, outerId);
        TOrder order = null;
        if(oList.size() > 0)
        {
            order = oList.get(0);
            if(body.isShowTickets())	//如果需要票级别的信息
            {
                order.setTickets(ticketService.findAllByOrderId(order.getId()));
            }
        }
        repBody.setOrder(order);
        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    /**
     * 渠道查询期次接口
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cGetTerm.htm")
    public String cGetTerm(@JsonHead(value="head", checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="Q16") ReqQ16Body body,
                                  ModelMap modelMap) throws Exception {
        RepQ16Body repBody = new RepQ16Body();
        Term t = this.termService.findOneByGameCodeAndCode(body.getGameCode(), body.getTermCode());
        if(t == null)
        {
            throw new CoreException(ErrCode.E2003);
        }
        repBody.setTerm(t);
        if(t.getStatus() == TermState.SEAL.getCode())   //如果期次已经封存，告诉外部系统封存文件的位置
        {
            MgTermSeal mgTermSeal = this.mgTermSealService.findOne(body.getGameCode(), body.getTermCode());
            log.info(DateTimeUtil.getDateString(mgTermSeal.getsTime()));
            if(mgTermSeal != null)
            {
                repBody.setTip(mgTermSeal.getFilePath());
            }
        }
        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    /**
     * 渠道查询自己的通知
     * @param head
     * @param body
     * @param station
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cGetNotifyMsg.htm")
    public String cGetNotifyMsg(@JsonHead(value="head", checkChannel=false) Head head, @McpStation Station station, @JsonBody(value="body", cmd="Q17") ReqQ17Body body,
                            ModelMap modelMap) throws Exception {
        RepQ17Body repBody = new RepQ17Body();
        Sort sort = new Sort(new Order(Direction.ASC, "_id"));
        PageRequest pr = new PageRequest(body.getStartIndex(), body.getSize(), sort);
        List<MgNotifyMsg> msgList = this.mgNotifyMsgService.find(body.getMinId(), station.getCode(), pr).getContent();
        repBody.setMsgList(msgList);
        modelMap.put("response", repBody);
        return "plainJsonView";
    }
}
