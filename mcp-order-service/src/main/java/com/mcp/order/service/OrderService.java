/**
 *
 */
package com.mcp.order.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.dao.*;
import com.mcp.order.dao.specification.OrderSpecification;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.model.ts.Term;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TermState;
import com.mcp.order.status.TicketState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ming.li
 */
@Service("orderService")
public class OrderService {

    private static Logger log = Logger.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private StationGameDao stationGameDao;

    @Autowired
    private TermDao termDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private MoneyService moneyService;

    public TOrder findOne(String id) {
        return this.orderDao.findOne(id);
    }

    /**
     * 查找订单，并返回订单和彩票两级
     * @param id
     * @return
     */
    public TOrder findOneAndTickets(String id)
    {
        TOrder order = this.orderDao.findOne(id);
        order.setTickets(this.ticketDao.findAllByOrderId(id));
        return order;
    }

    public TOrder findOneBySchemeIdAndTermCode(String schemeId, String termCode, boolean showTicket)
    {
        TOrder order = this.orderDao.findOneBySchemeIdAndTermCode(schemeId, termCode);
        if(showTicket)
        {
            order.setTickets(this.ticketDao.findAllByOrderId(order.getId()));
        }
        return order;
    }
    
    public Page<TOrder> findAll(Specification<TOrder> spec, Pageable p)
    {
    	return this.orderDao.findAll(spec, p);
    }
    
    public List<TOrder> findAll(Specification<TOrder> spec)
    {
    	return this.orderDao.findAll(spec);
    }

    @Transactional
    public int updateBonusById(long bonus, String id) {
        return this.orderDao.updateBonusById(bonus, id);
    }
    
    /**
     * 订单所属的一张票出票失败
     * @return
     */
    @Transactional
    public TOrder incrFailureTicket(String id)
    {
    	TOrder order = this.orderDao.findOne(id);
        int curFinishedTicketCount = order.getFinishedTicketCount() + 1;
        order.setFinishedTicketCount(curFinishedTicketCount);
        int printFailCount = order.getPrintFailCount() + 1;
        order.setPrintFailCount(printFailCount);
        if(curFinishedTicketCount == order.getTicketCount())
        {
        	if(order.getPrintCount() == 0)
        	{
        		order.setStatus(OrderState.REFUNDED.getCode());
        	}
        	else
        	{
        		long curBonus = order.getBonus();
        		if(order.getBonusBeforeTax() > curBonus)
            	{
            		order.setStatus(OrderState.WAITING_BIG_PRIZING.getCode());
            	}
            	else if(curBonus > 0)
            	{
            		order.setStatus(OrderState.HIT.getCode());
            	}
            	else
            	{
            		order.setStatus(OrderState.NOT_HIT.getCode());
            	}
        	}
        }
        return order;
    }

    /**
     * 增加订单的中奖金额，每增加一次，代表着票完成一张，同时，如果票都完成，则表示订单也已经完成
     * @param bonus
     * @param id
     */
    @Transactional
    public TOrder incrBonusById(String dNumber, long bonus, long bonusBeforeTax, String id) {
    	TOrder order = this.orderDao.findOne(id);
        int curFinishedTicketCount = order.getFinishedTicketCount() + 1;
        order.setFinishedTicketCount(curFinishedTicketCount);
        long curBonus = order.getBonus() + bonus;
        order.setBonus(curBonus);
        order.setBonusBeforeTax(order.getBonusBeforeTax() + bonusBeforeTax);
        if(curFinishedTicketCount == order.getTicketCount())
        {
        	if(bonus < bonusBeforeTax)          //如果要交税的话，则走大奖流程，人工处理
        	{
        		order.setStatus(OrderState.WAITING_BIG_PRIZING.getCode());
        	}
        	else if(curBonus > 0)
        	{
        		order.setStatus(OrderState.HIT.getCode());
        	}
        	else
        	{
        		order.setStatus(OrderState.NOT_HIT.getCode());
        	}
        }
        order.setdNumber(dNumber);
        return order;
    }
    
    @Transactional
    public void save(TOrder order) {
        orderDao.save(order);
        List<TTicket> tList = order.getTickets();
        if(tList != null)
        {
        	ticketDao.save(tList);
        }
    }

    @Transactional
    public void saveOrder(TOrder order, List<TTicket> tickets) {
        orderDao.save(order);
        for (int i = 0; i < tickets.size(); i++) {
            ticketDao.save(tickets.get(i));
        }
    }

    @Transactional
    public void saveOrder(List<TOrder> orderList, List<TTicket> tickets) {
        for (int i = 0; i < orderList.size(); i++) {
            orderDao.save(orderList.get(i));
        }
        for (int i = 0; i < tickets.size(); i++) {
            ticketDao.save(tickets.get(i));
        }
    }

    @Transactional
    public void deleteByCustomerId(String customerId) {
        orderDao.deleteByCustomerId(customerId);
        ticketDao.deleteByCustomerId(customerId);
    }

    @Transactional
    public void deleteByStationId(String stationId) {
        orderDao.deleteByStationId(stationId);
        ticketDao.deleteByStationId(stationId);
    }

    @Transactional
    public void delete(String id) {
        orderDao.delete(id);
        ticketDao.deleteByOrderId(id);
    }

    public List<TOrder> getOrdersToPrint(String stationId) {
        return this.orderDao.findAllByStationIdOrderByAcceptTimeAsc(stationId);
    }

    public List<TTicket> getTicketsByOrderId(String orderId) {
        return this.ticketDao.findAllByOrderId(orderId);
    }

    @Transactional
    public int updateStatusById(int status, String id) {
        return this.orderDao.updateStatusById(status, id);
    }
    
    /**
     * 支付一个订单
     * @param orderId
     */
    @Transactional
    public void afford(String orderId, int orderStatus, int ticketStatus)
    {
    	TOrder order = this.orderDao.findOne(orderId);
    	order.setStatus(orderStatus);
    	order.setAcceptTime(new Date());
    	
    	List<TTicket> tList = this.ticketDao.findAllByOrderId(orderId);
    	for(TTicket t:tList)
    	{
    		t.setStatus(ticketStatus);
    		t.setAcceptTime(new Date());
    	}
    }

    /**
     * 支付一个订单
     * @param orderId
     */
    @Transactional
    public void afford(String orderId, int orderStatus, int ticketStatus, String termCode)
    {
        TOrder order = this.orderDao.findOne(orderId);
        order.setStatus(orderStatus);
        order.setAcceptTime(new Date());
        order.setTermCode(termCode);

        List<TTicket> tList = this.ticketDao.findAllByOrderId(orderId);
        for(TTicket t:tList)
        {
            t.setStatus(ticketStatus);
            t.setAcceptTime(new Date());
            t.setTermCode(termCode);
        }
    }



    
    /**
     * 订单的票据出票成功
     * @param id	订单id
     * @param step 出票成功的票据数量
     * @return
     */
    @Transactional
    public TOrder incrPrintCount(String id, int step) {
    	TOrder order = this.orderDao.findOne(id);
        int printCount = order.getPrintCount() + step;
        order.setPrintCount(printCount);
        if(order.getTicketCount() == printCount)
        {
            order.setStatus(OrderState.SUCCESS.getCode());
            order.setPrintTime(new Date());
        }
        return order;
    }

    /**
     * 订单的票据出票成功
     * @return
     */
    @Transactional
    public boolean incrPrintCount(TOrder order) {
        int printCount = order.getPrintCount() + 1;
        order.setPrintCount(printCount);
        int version = order.getVersion();
        order.setVersion(version + 1);

        int updateCount = 0;
        if(order.getTicketCount() == printCount)
        {
            order.setStatus(OrderState.SUCCESS.getCode());
            order.setPrintTime(new Date());

            updateCount = this.orderDao.updateStatusAndPrintCountById(order.getStatus(),
                    order.getPrintCount(), order.getPrintTime(), version, order.getId());
        }
        else
        {
            updateCount = this.orderDao.updatePrintCountById(order.getPrintCount(), version, order.getId());
        }
        if(updateCount > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Transactional
    public void updateCheckInfoById(String dNumber, int status, String id) {
    	TOrder order = this.orderDao.findOne(id);
    	order.setdNumber(dNumber);
    	order.setStatus(status);
    }

    public List<TOrder> findAllByStatus(String customerId,
                                        int status, Pageable p) {
        return this.orderDao
                .findAllByStatus(customerId, status, p);
    }

    public List<TOrder> findAllByStatusAndGame(String customerId,
                                               String gameCode, int status, Pageable p) {
        return this.orderDao.findAllByStatusAndGame(customerId, gameCode, status, p);
    }

    public List<Object[]> getDailyClearData(String date) throws Exception {
        String format = "yyyy-MM-dd HH:mm:ss";
        String begin = date + " 00:00:00";
        String end = date + " 23:59:59";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return this.orderDao.getDailyClearData(dateFormat.parse(begin), dateFormat.parse(end));
    }

    /**
     * 所有预售的票改为等待打印
     */
    @Transactional
    public void preparePrintForPresale(TOrder t) {
    	t.setStatus(OrderState.WAITING_PRINT.getCode());
    	this.orderDao.updateStatusById(OrderState.WAITING_PRINT.getCode(), t.getId());
    	this.ticketDao.updateStatusByOrderId(TicketState.WAITING_PRINT.getCode(), t.getId());
    }

    /**
     *
     * @param user
     * @param channelCode
     */
    public void isSchemeAcceptable(Object user, String channelCode, List<TOrder> orderList) {
        Map<String, Integer> poolSizeMap = new HashMap<String, Integer>();
        for (int i = 0; i < orderList.size(); i++) {
            TOrder order = orderList.get(i);
            String gameCode = order.getGameCode();
            String termCode = order.getTermCode();
            //如果t==null，抛出期次不存在的异常
            Term t = termDao.findOneByGameCodeAndCode(gameCode, termCode);
            if (t == null) {
                throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
            }
            int termStatus = t.getStatus();
            if (termStatus == TermState.ON_SALE.getCode()) //期次正处于销售状态
            {
                String stationId = order.getStationId();
                int curNeedSize = order.getTicketCount();
                if (poolSizeMap.containsKey(stationId)) {
                    curNeedSize += poolSizeMap.get(stationId);
                }
                poolSizeMap.put(stationId, curNeedSize);
                isAcceptable(t, order);
            } else {
                //如果期次处于销售之后的阶段，不能投注
                if (termStatus > TermState.ON_SALE.getCode()) {
                    throw new CoreException(ErrCode.E2008, ErrCode.codeToMsg(ErrCode.E2008));
                }
            }
        }
    }

    /**
     * 订单是否可接受支付
     * 会校验票据的玩法是否已经提前停售
     * @return
     */
    public void isAcceptable(Term gt, TOrder order) {
        String gameCode = order.getGameCode();
        //检查玩法是否已经提前停止销售
        Date endTime = gt.getEndTime();
        List<TTicket> ticketList = order.getTickets();
        for (int i = 0; i < ticketList.size(); i++) {
            TTicket ticket = ticketList.get(i);
            BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode, ticket.getPlayTypeCode(), ticket.getBetTypeCode());
            if (System.currentTimeMillis() + bt.getOffset() * 1000L > endTime.getTime())    //玩法已经停售
            {
                throw new CoreException(ErrCode.E2046, ErrCode.codeToMsg(ErrCode.E2046));
            }
        }
    }

    /**
     * 支付成功之后，更新订单的状态
     *
     * @param user
     * @param orderList
     */
    @Transactional
    public void updateSchemeAndOrderAfterPay(Object user, List<TOrder> orderList)
    {
    	
		for(int i = 0; i < orderList.size(); i++)
    	{
    		TOrder order = orderList.get(i);
    		String gameCode = order.getGameCode();
    		String termCode = order.getTermCode();
    		
    		Term t = termDao.findOneByGameCodeAndCode(gameCode, termCode);
    		int termStatus = t.getStatus();
    		log.info(termStatus + "----" + termCode);
			if(termStatus == TermState.ON_SALE.getCode()) //期次正处于销售状态
			{
				orderDao.updateStatusAndAcceptTimeById(OrderState.WAITING_PRINT.getCode(), new Date(), order.getId());
				ticketDao.updateStatusByOrderId(TicketState.WAITING_PRINT.getCode(), order.getId());
				order.setStatus(OrderState.WAITING_PRINT.getCode());
			}
			else if(termStatus == TermState.INIT.getCode() || termStatus == TermState.NOT_ON_SALE.getCode())
			{
				orderDao.updateStatusAndAcceptTimeById(OrderState.PRESALE.getCode(), new Date(), order.getId());
				ticketDao.updateStatusByOrderId(TicketState.PRESALE.getCode(), order.getId());
				order.setStatus(OrderState.PRESALE.getCode());
			}
			else
			{
				throw new CoreException(ErrCode.E2011, ErrCode.codeToMsg(ErrCode.E2011));
			}
    	}
    }

    /**
     * 账户直接支付
     * 彩民支付一个方案，支付订单的时候，不再判断投注站池的大小
     */
    /*@Transactional
    public List<MoneyLog> payForAnScheme(String channelCode, Object user, List<TOrder> orderList, int orderPayType, int paymentType, String fromFlag)
    {
		if(scheme != null && scheme.getStatus() != SchemeState.NOT_AVAILABLE.getCode())
		{
			throw new CoreException(ErrCode.E2017, ErrCode.codeToMsg(ErrCode.E2017));
		}
    	this.isSchemeAcceptable(user, channelCode, scheme, orderList);
    	return this.payForAnSchemeNoCheck(scheme, user, orderList, orderPayType, paymentType, fromFlag);
    }*/

    /*@Transactional
    public void payForAnOrderByBank(String orderId, Customer user, long totalAmount, int thirdPartyType, String interactiveId) {
        this.orderDao.updateStatusAndAcceptTimeById(OrderState.WAITING_PRINT.getCode(), new Date(), orderId);
        this.ticketDao.updateStatusByOrderId(TicketState.WAITING_PRINT.getCode(), orderId);

        if (thirdPartyType == AccountConstants.THIRD_PARTY_TYPE_ALIPAY) {
            moneyService.customerRechargeByAlipay(user.getId(), interactiveId, orderId, totalAmount);
        } else {
            moneyService.customerRechargeByBank(user.getId(), interactiveId, orderId, totalAmount);
        }
        //写redis消息
        redisHelp.writeOrderNotify(user.getId(), orderId, totalAmount);
    }*/

    @Transactional
    public int updateStatusAndAcceptTimeById(int status, Date acceptTime, String id) {
        return this.orderDao.updateStatusAndAcceptTimeById(status, acceptTime, id);
    }

    public List<TOrder> findAll(Pageable p) {
        return this.orderDao.findAll(p).getContent();
    }

    public long countAll() {
        return this.orderDao.count();
    }

    /**
     * 把当期所有的订单设置为成功
     * @param gameCode
     * @param termCode
     */
    @Transactional
    public void successAllOrder(String gameCode, String termCode)
    {
    	this.orderDao.updateStatusByGameCodeAndTermCode(OrderState.SUCCESS.getCode(), gameCode, termCode);
    }
    
    /**
     * 查询一个方案的所有订单
     *
     * @param schemeId
     * @return
     */
    public List<TOrder> findAllBySchemeId(String schemeId) {
        Specifications<TOrder> specs = Specifications.where(OrderSpecification.isSchemeIdEqual(schemeId));
        return this.orderDao.findAll(specs);
    }
    
    /**
     * 分页查找打印失败的订单
     * @param pageable
     */
    public Page<TOrder> queryFailure(String gameCode, String termCode, String channelCode, Pageable pageable)
    {
    	Specifications<TOrder> specs = Specifications.where(OrderSpecification.isGameCodeEqual(gameCode));
    	specs = specs.and(OrderSpecification.isTermCodeEqual(termCode));
    	specs = specs.and(OrderSpecification.isChannelCodeEqual(channelCode));
    	
    	Specifications<TOrder> statusSpecs = Specifications.where(OrderSpecification.isStatusEqual(OrderState.WAITING_PRINT.getCode()));
    	statusSpecs = statusSpecs.or(OrderSpecification.isStatusEqual(OrderState.PRINT_FAILURE.getCode()));
    	
    	specs = specs.and(statusSpecs);
    	return this.orderDao.findAll(specs, pageable);
    }
    
    /**
     * 分页查找预售的订单
     * @param pageable
     */
    public Page<TOrder> queryPresale(String gameCode, String termCode, Pageable pageable)
    {
    	Specifications<TOrder> specs = Specifications.where(OrderSpecification.isGameCodeEqual(gameCode));
    	specs = specs.and(OrderSpecification.isTermCodeEqual(termCode));
    	
    	Specifications<TOrder> statusSpecs = Specifications.where(OrderSpecification.isStatusEqual(OrderState.PRESALE.getCode()));
    	
    	specs = specs.and(statusSpecs);
    	return this.orderDao.findAll(specs, pageable);
    }

    /**
     * 用户查询订单，对于status和prizeStatus，-1表示所有，gameCode及statoinId，null或者""表示所有。
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<TOrder> stationQuery(Station station, String orderId, String outerId) {
        Specifications<TOrder> specs;
        specs = Specifications.where(OrderSpecification.isChannelCodeEqual(station.getCode()));
        if(!StringUtil.isEmpty(orderId))
        {
            specs = specs.and(OrderSpecification.isIdEqual(orderId));
        }
        if(!StringUtil.isEmpty(outerId))
        {
            specs = specs.and(OrderSpecification.isOuterIdEqual(outerId));
        }
        return this.orderDao.findAll(specs);
    }

    /**
     * 渠道根据外部订单号查询订单
     * @param channelCode
     * @param outerId
     * @return
     */
    public TOrder findOneByChannelCodeAndOuterId(String channelCode, String outerId)
    {
        Specifications<TOrder> specs = Specifications.where(OrderSpecification.isChannelCodeEqual(channelCode));
        specs = specs.and(OrderSpecification.isOuterIdEqual(outerId));
        List<TOrder> oList = this.orderDao.findAll(specs);
        if(oList.size() == 0)
        {
            return null;
        }
        else
        {
            return oList.get(0);
        }
    }


    /**
     * 订单查询
     * @param gameCodeList
     * @param termCode
     * @param schemeId
     * @param schemeType
     * @param statusList
     * @param exStatusList
     * @param page
     * @return
     */
    public Page<TOrder> query(List<String> gameCodeList, String termCode, String schemeId, int schemeType,
                              List<Integer> statusList, List<Integer> exStatusList, Pageable page)
    {
        Specifications<TOrder> specs = Specifications.where(OrderSpecification.isIdNotNull());
        if(gameCodeList != null && gameCodeList.size() > 0)
        {
            Specifications<TOrder> gameCodeSpecs = Specifications.where(OrderSpecification.isGameCodeEqual(gameCodeList.get(0)));
            for(int i = 1; i < gameCodeList.size(); i++)
            {
                gameCodeSpecs = gameCodeSpecs.or(OrderSpecification.isGameCodeEqual(gameCodeList.get(i)));
            }
            specs = specs.and(gameCodeSpecs);
        }
        if(!StringUtil.isEmpty(termCode))
        {
            specs = specs.and(OrderSpecification.isTermCodeEqual(termCode));
        }
        if(!StringUtil.isEmpty(schemeId))
        {
            specs = specs.and(OrderSpecification.isSchemeIdEqual(schemeId));
        }
        if(schemeType >= 0)
        {
            specs = specs.and(OrderSpecification.isSchemeTypeEqual(schemeType));
        }
        if(statusList != null && statusList.size() > 0)
        {
            Specifications<TOrder> statusSpecs = Specifications.where(OrderSpecification.isStatusEqual(statusList.get(0)));
            for (int i = 1; i < statusList.size(); i++) {
                statusSpecs = statusSpecs.or(OrderSpecification.isStatusEqual(statusList.get(i)));
            }
            specs = specs.and(statusSpecs);
        }
        if(exStatusList != null && exStatusList.size() > 0)
        {
            for(int i = 0; i < exStatusList.size(); i++)
            {
                specs = specs.and(OrderSpecification.isStatusNotEqual(exStatusList.get(i)));
            }
        }
        return this.orderDao.findAll(specs, page);
    }

    /**
     * 用户查询订单，对于status和prizeStatus，-1表示所有，gameCode及statoinId，null或者""表示所有。
     *
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public Page<TOrder> userQuery(Object user, String gameCode, String orderId, String schemeId, int schemeType, String stationId, String status, String exOrderStatus, Pageable p) {
        Specifications<TOrder> specs;
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            specs = Specifications.where(OrderSpecification.isCustomerIdEqual(customer.getId()));
        } else {
            Station channel = (Station) user;
            specs = Specifications.where(OrderSpecification.isChannelCodeEqual(channel.getCode()));
        }
        if (!StringUtil.isEmpty(orderId))    //id查询
        {
            specs = specs.and(OrderSpecification.isIdEqual(orderId));
        } else {
            if (!StringUtil.isEmpty(exOrderStatus)) {
                String[] exOrderStatusArray = exOrderStatus.split(",");
                for (int i = 0; i < exOrderStatusArray.length; i++) {
                    int exStatus = Integer.parseInt(exOrderStatusArray[i]);
                    specs = specs.and(OrderSpecification.isStatusNotEqual(exStatus));
                }
            }
            if (!StringUtil.isEmpty(schemeId))    //根据方案号查询
            {
                specs = specs.and(OrderSpecification.isSchemeIdEqual(schemeId));
            }
            if (!StringUtil.isEmpty(gameCode)) {
                specs = specs.and(OrderSpecification.isGameCodeEqual(gameCode));
            }
            if (!StringUtil.isEmpty(stationId)) {
                specs = specs.and(OrderSpecification.isStationIdEqual(stationId));
            }
            if (!StringUtil.isEmpty(status)) {
            	Specifications<TOrder> statusSpecs;
            	String[] orderStatusArray = status.split(",");
            	statusSpecs = Specifications.where(OrderSpecification.isStatusEqual(Integer.parseInt(orderStatusArray[0])));
                for (int i = 1; i < orderStatusArray.length; i++) {
                    int intStatus = Integer.parseInt(orderStatusArray[i]);
                    statusSpecs = statusSpecs.or(OrderSpecification.isStatusEqual(intStatus));
                }
                specs = specs.and(statusSpecs);
            }
            if (schemeType > 0) {
                specs = specs.and(OrderSpecification.isSchemeTypeEqual(schemeType));
            }
        }
        return this.orderDao.findAll(specs, p);
    }

    /**
     * 用户查询订单，对于status和prizeStatus，-1表示所有，gameCode及statoinId，null或者""表示所有。
     *
     * @return
     */
    public Page<TOrder> userQuery(Object user, String gameCode, String orderId, String schemeId, int schemeType, String stationId, String status, Pageable p) {
        return this.userQuery(user, gameCode, orderId, schemeId, schemeType, stationId, status, null, p);
    }

    public Page<TOrder> customerOrderQuery(Map<String, String> paras, Pageable p) {


        String customerId = paras.get("customerId");
        String stationId = paras.get("stationId");
        String gameCode = paras.get("gameCode");
        String termCode = paras.get("termCode");
        String saleChannel = paras.get("saleChannel");
        String start = paras.get("start");
        String end = paras.get("end");
        String platform = paras.get("platform");
        int status = Integer.parseInt(paras.get("orderStatus"));
        int payType = Integer.parseInt(paras.get("payType"));
        int schemeType = Integer.parseInt(paras.get("schemeType"));


        Specifications<TOrder> specs = Specifications.where(OrderSpecification.isIdNotNull()).and(OrderSpecification.orderByCreateTimeDesc());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (customerId != null && customerId.length() > 0) {
            specs = specs.and(OrderSpecification.isCustomerIdEqual(customerId));
        }
        if (gameCode != null && gameCode.length() > 0) {
            specs = specs.and(OrderSpecification.isGameCodeEqual(gameCode));
        }
        if (stationId != null && stationId.length() > 0) {
            //页面传入的stationId实际上是stationCode，需要转换为真实的stationId.
        	Station station = stationDao.findOneByCode(stationId);
            if(station != null){
                specs = specs.and(OrderSpecification.isStationIdEqual(station.getId()));
            }
        }
        if (termCode != null && termCode.length() > 0) {
            specs = specs.and(OrderSpecification.isTermCodeEqual(termCode));
        }
        if (status > 0) {
            specs = specs.and(OrderSpecification.isStatusEqual(status));
        }
        if (start != null && start.length() > 0) {
            try {
                specs = specs.and(OrderSpecification.isAcceptTimeAfter(sdf.parse(start)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (end != null && end.length() > 0) {
            try {
                specs = specs.and(OrderSpecification.isAcceptTimeBefore(sdf.parse(end)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (saleChannel != null && saleChannel.length() > 0) {
            specs = specs.and(OrderSpecification.isSalesChannelEqual(saleChannel));
        }
        if (platform != null && platform.length() > 0) {
            specs = specs.and(OrderSpecification.isPlatformEqual(platform));
        }
        //TODO -1是所有状态的保留字段
        if (payType!= -1) {
            specs = specs.and(OrderSpecification.isPayTypeEqual(payType));
        }
        if (schemeType != -1) {
            specs = specs.and(OrderSpecification.isSchemeTypeEqual(schemeType));
        }
        return this.orderDao.findAll(specs, p);
    }
}
