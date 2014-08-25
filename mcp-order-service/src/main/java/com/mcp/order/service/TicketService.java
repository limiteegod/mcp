/**
 *
 */
package com.mcp.order.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.dao.OrderDao;
import com.mcp.order.dao.StationDao;
import com.mcp.order.dao.TicketDao;
import com.mcp.order.dao.specification.TicketSpecification;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.ReceiptState;
import com.mcp.order.status.TicketState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author ming.li
 */
@Service("ticketService")
public class TicketService {

    @Autowired
    private TicketDao ticketDao;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private StationDao stationDao;
    
    @Autowired
    private MoneyService moneyService;

    public TTicket findOne(String id) {
        return this.ticketDao.findOne(id);
    }

    public TTicket save(TTicket ticket) {
        return this.ticketDao.save(ticket);
    }
    
    
    /**
     * 增加票的完成场次数
     * @param id
     * @return
     */
    @Transactional
    public TTicket incrFinishedCount(String id)
    {
    	TTicket ticket = this.ticketDao.findOne(id);
    	ticket.setFinishedCount(ticket.getFinishedCount() + 1);
    	return ticket;
    }

    /**
     * 更新出票的station
     * @param printerStationId
     * @param id
     * @return
     */
    @Transactional
    public int updatePrintStationIdById(String printerStationId, String id)
    {
        return this.ticketDao.updatePrintStationIdById(printerStationId, id);
    }

    /**
     * 终端机取状态为等待刷票的票，并设置receiptStatus的状态为已经取走
     * @param terminalId	终端机的id
     * @param gameCode	游戏编码
     * @param termCode	期次编码
     * @param size	每页数目
     * @return
     */
    @Transactional
    public List<TTicket> getAllToPrize(String terminalId, String gameCode, String termCode, int size) {
        PageRequest page = new PageRequest(0, size);
        Specifications<TTicket> specs = Specifications.where(TicketSpecification.isTerminalIdEqual(terminalId));
        specs = specs.and(TicketSpecification.isGameCodeEqual(gameCode));
        specs = specs.and(TicketSpecification.isTermCodeEqual(termCode));
        specs = specs.and(TicketSpecification.isReceiptStatusEqual(ReceiptState.NOT_CLAIM_PRIZE.getCode()));
        specs = specs.and(TicketSpecification.isBigBonusEqual(false));
        Page<TTicket> ticketPage = this.ticketDao.findAll(specs, page);
		Iterator<TTicket> it = ticketPage.iterator();
		while(it.hasNext())
		{
			TTicket t = it.next();
			t.setReceiptStatus(ReceiptState.SYSTEM_TAKEWAY.getCode());
		}
        return ticketPage.getContent();
    }

    /**
     * 获取需要打印的票
     * 当多个线程竞争失败时，会抛出StaleObjectStateException，TTicket已经加了@Version标签实现乐观锁
     * @param stationId
     * @return
     */
    @Transactional
    public Page<TTicket> findAllToPrint(List<String> gameCodeList, String stationId, int size) {
    	Sort sort = new Sort(new Order(Direction.ASC, "acceptTime"));
		PageRequest pr = new PageRequest(0, size, sort);
		Specifications<TTicket> specs = Specifications.where(TicketSpecification.isStatusEqual(TicketState.WAITING_PRINT.getCode()));
		specs = specs.and(TicketSpecification.isPrinterStationIdEqual(stationId));
		if(gameCodeList != null && gameCodeList.size() > 0)
		{
			Specifications<TTicket> gameCodeSpecs = Specifications.where(TicketSpecification.isGameCodeEqual(gameCodeList.get(0)));;
			for(int i = 1; i < gameCodeList.size(); i++)
			{
				gameCodeSpecs = gameCodeSpecs.or(TicketSpecification.isGameCodeEqual(gameCodeList.get(i)));
			}
			specs = specs.and(gameCodeSpecs);
		}
		Page<TTicket> ticketPage = this.ticketDao.findAll(specs, pr);
		Iterator<TTicket> it = ticketPage.iterator();
		Date now = new Date();
		while(it.hasNext())
		{
			TTicket t = it.next();
			t.setStatus(TicketState.TAKE_AWAY.getCode());
			t.setSysTakeTime(now);
		}
        return ticketPage;
    }
    
    /**
     * 取未打印纸质票的ticket，进行打印
     * @param customerId
     * @param stationId
     * @return
     */
    @Transactional
    public Page<TTicket> findToPrintPaper(String customerId, String stationId, int size)
    {
    	Sort sort = new Sort(new Order(Direction.ASC, "acceptTime"));
		PageRequest pr = new PageRequest(0, size, sort);
		Specifications<TTicket> specs = Specifications.where(TicketSpecification.isStationIdEqual(stationId));
		specs = specs.and(TicketSpecification.isCustomerIdEqual(customerId));
		specs = specs.and(TicketSpecification.isPaperEqual(false));
		specs = specs.and(TicketSpecification.isStatusEqual(TicketState.PRINT_SUCCESS.getCode()));
		specs = specs.and(TicketSpecification.isReceiptStatusNotEqual(ReceiptState.SYSTEM_TAKEWAY.getCode()));
		Page<TTicket> ticketPage = this.ticketDao.findAll(specs, pr);
		Iterator<TTicket> it = ticketPage.iterator();
		while(it.hasNext())
		{
			TTicket t = it.next();
			t.setPaper(true);
		}
        return ticketPage;
    }
    
    /**
     * 获取需要打印的票
     * 当多个线程竞争失败时，会抛出StaleObjectStateException，TTicket已经加了@Version标签实现乐观锁
     * @param stationId
     * @return
     */
    @Transactional
    public List<TTicket> findAllToPrintByOrderId(String orderId, String stationId) {
    	Specifications<TTicket> specs = Specifications.where(TicketSpecification.isStatusEqual(TicketState.WAITING_PRINT.getCode()));
		specs = specs.and(TicketSpecification.isOrderIdEqual(orderId));
		
    	List<TTicket> tList = this.ticketDao.findAll(specs);
    	Date now = new Date();
    	for(TTicket t:tList)
    	{
    		t.setStatus(TicketState.TAKE_AWAY.getCode());
    		t.setSysTakeTime(now);
    	}
        return tList;
    }

    public List<TTicket> findAllByOrderId(String orderId) {
        return this.ticketDao.findAllByOrderId(orderId);
    }

    @Transactional
    public int updateStatusById(int status, String id) {
        return this.ticketDao.updateStatusById(status, id);
    }
    
    @Transactional
    public int updateStatusByOrderId(int status, String orderId) {
        return this.ticketDao.updateStatusByOrderId(status, orderId);
    }

    /**
     * 出票失败
     * @param id
     * @return
     */
    @Transactional
    public TOrder printFailure(String id)
    {
        TTicket t = this.ticketDao.findOne(id);
        t.setStatus(TicketState.PRINT_FAILURE.getCode());
        int failureCount = (int)this.getCountByOrderIdAndStatus(t.getOrderId(), TicketState.PRINT_FAILURE.getCode());
        TOrder order = this.orderDao.findOne(t.getOrderId());
        if(failureCount == order.getTicketCount())
        {
            order.setStatus(OrderState.PRINT_FAILURE.getCode());
        }
        return order;
    }
    
    @Transactional
    public TTicket printBack(int status, String orderId, int seq, String rNumber) {
    	TTicket t = this.ticketDao.findOneByOrderIdAndSeq(orderId, seq);
    	if(t.getStatus() == TicketState.TAKE_AWAY.getCode())
    	{
    		t.setStatus(status);
    	}
    	if(!StringUtil.isEmpty(rNumber))
    	{
    		t.setrNumber(rNumber);
    	}
    	return t;
    }

    @Transactional
    public int updateReceiptStatusById(int receiptStatus, String id) {
        return this.ticketDao.updateReceiptStatusById(receiptStatus, id);
    }

    /**
     * 算奖之后，更新票的信息
     * 两部分信息：1、中奖金额，2、更改刷票状态为等待刷票
     * @param bonus
     * @param id
     * @return
     */
    @Transactional
    public void updateCheckInfo(String dNumber, long bonus, long bonusBeforeTax, String id) {
    	TTicket tic = this.ticketDao.findOne(id);
    	if(bonus > 0)
    	{
    		tic.setReceiptStatus(ReceiptState.NOT_CLAIM_PRIZE.getCode());
    	}
    	else
    	{
    		tic.setReceiptStatus(ReceiptState.NOT_HIT.getCode());
    	}
    	tic.setBonus(bonus);
        tic.setBonusBeforeTax(bonusBeforeTax);
    	tic.setdNumber(dNumber);
    }
    
    /**
     * 把当期所有的票设置为成功状态
     * @param gameCode
     * @param termCode
     */
    @Transactional
    public void successAllTicket(String gameCode, String termCode)
    {
    	this.ticketDao.updateStatusByGameCodeAndTermCode(TicketState.PRINT_SUCCESS.getCode(), gameCode, termCode);
    }
    
    /**
     * 分页查找打印失败的票据，注意termCode，竞彩的termCode包含多个，所以要用like
     * @param pageable
     */
    public Page<TTicket> queryFailure(String gameCode, String termCode, String channelCode, Pageable pageable)
    {
    	Specifications<TTicket> specs = Specifications.where(TicketSpecification.isGameCodeEqual(gameCode));
    	specs = specs.and(TicketSpecification.isTermCodeLike(termCode));
    	specs = specs.and(TicketSpecification.isChannelCodeEqual(channelCode));
    	
    	Specifications<TTicket> statusSpecs = Specifications.where(TicketSpecification.isStatusEqual(TicketState.PRINT_ERROR.getCode()));
    	statusSpecs = statusSpecs.or(TicketSpecification.isStatusEqual(TicketState.PRINT_FAILURE.getCode()));
    	
    	specs = specs.and(statusSpecs);
    	return this.ticketDao.findAll(specs, pageable);
    }
    
    /**
     * 奖期停售时，更新票的状态
     * 初始状态的票，设置为打印取消状态；
     * 等待打印、程序取走、需要更新的票，设置为打印失败
     */
    @Transactional
    public void issueClose(String gameCode, String termCode)
    {
    	String lCode = "%" + termCode + "%";
    	//初始状态的票，设置为打印取消状态
    	this.ticketDao.updateStatusByStatusAndGameCodeAndTermCode(TicketState.CANCELED.getCode(), TicketState.INIT.getCode(), gameCode, lCode);
    	//等待打印、程序取走、需要更新的票，设置为打印失败（不再这样直接处理，放在导出失败票中处理）
    	//this.ticketDao.updateStatusByStatusAndGameCodeAndTermCode(TicketState.PRINT_FAILURE.getCode(), TicketState.WAITING_PRINT.getCode(), gameCode, lCode);
    	//this.ticketDao.updateStatusByStatusAndGameCodeAndTermCode(TicketState.PRINT_FAILURE.getCode(), TicketState.TAKE_AWAY.getCode(), gameCode, lCode);
    	//this.ticketDao.updateStatusByStatusAndGameCodeAndTermCode(TicketState.PRINT_FAILURE.getCode(), TicketState.NEED_UPDATE.getCode(), gameCode, lCode);
    }
    
    /**
     * 出票回执
     */
    @Transactional
    public TTicket printBack(String ticketId, int code, String terminalId, String serialNumber, String stubInfo, String rNumber, boolean paper)
    {
    	TTicket ticket = ticketDao.findOne(ticketId);
    	//对已经出票成功的票，返回已经出票成功的错误码
		if(ticket.getStatus() == TicketState.PRINT_SUCCESS.getCode())
		{
			throw new CoreException(ErrCode.E2040, ErrCode.codeToMsg(ErrCode.E2040));
		}
		if(code == Constants.TICKET_PRINT_RECEIPT_SUCCESS)
		{
			ticket.setStatus(TicketState.PRINT_SUCCESS.getCode());
			ticket.setTerminalId(terminalId);
			ticket.setSerialNumber(serialNumber);
			ticket.setStubInfo(stubInfo);
			ticket.setPrintTime(new Date());
			ticket.setReceiptStatus(ReceiptState.NOT_TAKE_AWAY.getCode());
			ticket.setPaper(paper);
			ticket.setrNumber(rNumber);
		}
		else if(code == Constants.TICKET_PRINT_RECEIPT_FAILURE)
		{
			ticket.setStatus(TicketState.PRINT_FAILURE.getCode());
		}
		else
		{
			ticket.setStatus(TicketState.NEED_UPDATE.getCode());
		}
		return ticket;
    }

    /**
     * 兑奖回执
     * @param ticketId
     * @param code
     * @return
     */
    @Transactional
    public TTicket prizeBack(String ticketId, int code) {
        TTicket ticket = ticketDao.findOne(ticketId);
        if (code == 0) {
            ticket.setReceiptStatus(ReceiptState.CLAIM_SUCCESS.getCode());
            ticket.setPaper(true);
        } else {
            ticket.setReceiptStatus(ReceiptState.CLAIM_FAILURE.getCode());
        }
        return ticket;
    }

    /**
     * 在投注站登录、退出的时候，重置所有这个投注站的已经取走的票的状态为等待取票
     */
    @Transactional
    public void resetAllTakenAway(String printerAccountId) {
        this.ticketDao.updateStatusByStatusAndPrinterStationId(TicketState.WAITING_PRINT.getCode(), TicketState.TAKE_AWAY.getCode(), printerAccountId);
        this.ticketDao.updateReceiptStatusByReceiptStatusAndPrinterStationId(ReceiptState.NOT_CLAIM_PRIZE.getCode(), ReceiptState.SYSTEM_TAKEWAY.getCode(), printerAccountId);
    }

    public long getCountByOrderIdAndStatus(String orderId, int status) {
        return this.ticketDao.getCountByOrderIdAndStatus(orderId, status);
    }

    public Page<TTicket> findAllByGameCode(String gameCode, PageRequest page) {
        return this.ticketDao.findAllByGameCode(gameCode, page);
    }
    public Page<TTicket> findAllByGameCodeAndTermCode(String gameCode, String termCode,PageRequest page) {
        return this.ticketDao.findAllByGameCodeAndTermCode(gameCode,termCode, page);
    }

    public Iterable<TTicket> findAll() {
        return this.ticketDao.findAll();
    }

    public Page<TTicket> findAll(Pageable p) {
        return this.ticketDao.findAll(p);
    }

    @Transactional
    public int updateIdById(String newId, String oldId)
    {
        return this.ticketDao.updateIdById(newId, oldId);
    }
    
    /**
     * 根据条件分页查询
     * @param specs
     * @param p
     * @return
     */
    public Page<TTicket> findAll(Specifications<TTicket> specs, Pageable p) {
        return this.ticketDao.findAll(specs, p);
    }
    
    public long countAll(){
        return this.ticketDao.count();
    }
    public Page<TTicket> findAllByReceiptStatus(int receiptStatus,Pageable p){
        return this.ticketDao.findAllByReceiptStatus(receiptStatus,p);
    }
    public Page<TTicket> findAllByGameCodeAndReceiptStatus(String gameCode,int receiptStatus,Pageable p){
        return this.ticketDao.findAllByGameCodeAndReceiptStatus(gameCode,receiptStatus,p);
    }
    public Page<TTicket> findAllByGameCodeAndTermCodeAndReceiptStatus(String gameCode,String termCode,int receiptStatus,Pageable p){
        return this.ticketDao.findAllByGameCodeAndTermCodeAndReceiptStatus(gameCode,termCode,receiptStatus,p);
    }
    public Page<TTicket> findAllByStationIdAndReceiptStatus(String stationId,int receiptStatus,Pageable p){
        return this.ticketDao.findAllByStationIdAndReceiptStatus(stationId,receiptStatus,p);
    }
    public Page<TTicket> findAllByStationIdAndGameCodeAndReceiptStatus(String stationId,String gameCode,int receiptStatus,Pageable p){
        return this.ticketDao.findAllByStationIdAndGameCodeAndReceiptStatus(stationId,gameCode,receiptStatus,p);
    }
    public Page<TTicket> findAllByStationIdAndGameCodeAndTermCodeAndReceiptStatus(String stationId,String gameCode,String termCode,int receiptStatus,Pageable p){
        return this.ticketDao.findAllByStationIdAndGameCodeAndTermCodeAndReceiptStatus(stationId,gameCode,termCode,receiptStatus,p);
    }
    public Page<TTicket> findAllByStationIdAndStatus(String stationId,int status,Pageable page){
        return this.ticketDao.findAllByStationIdAndStatus(stationId,status,page);
    }
    public Page<TTicket> findAllByStationIdAndStatusAndPrintTimeAfter(String stationId,int status,Date today,Pageable page){
        return this.ticketDao.findAllByStationIdAndStatusAndPrintTimeAfter(stationId,status,today,page);
    }
    public  long getTodayCountByStationIdAndStatus(String stationId,int status,Date today){
        Object object = this.ticketDao.getTodayCountByStationIdAndStatus(stationId, status,today);
        if(object==null){
            return  0;
        }
         return Double.valueOf(object.toString()).longValue();
    }

}
