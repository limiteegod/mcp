package com.mcp.scheduler.rmi;

import com.mcp.order.model.ts.Game;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.Term;
import com.mcp.order.service.GameService;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.util.OrderStateUtil;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.order.util.DateTimeUtil;
import com.mcp.rmi.inter.OrderInter;
import com.mcp.scheduler.common.SchConstants;
import com.mcp.scheduler.common.SchedulerContext;
import com.mcp.scheme.model.SchemeZh;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RmiOrderImpl implements OrderInter {
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private OrderService orderService;

	@Override
	public Term getLatestSaleTerm(String gameCode) {
		//TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
		return termService.findFirstSaleTerm(gameCode);
	}

	@Override
	public List<String> getLastDayPrizedFileList(String gameCode, String channelCode) {
		String littlePrizeFileName = "orders_little_prize_in.txt";
		String bigPrizeFileName = "orders_big_prize_in.txt";
		List<String> fList = new ArrayList<String>();
		//TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
		//GameService gameService = SchedulerContext.getInstance().getBean("gameService", GameService.class);
		Game g = gameService.findOneByCode(gameCode);
		//第一步，读取昨日封存的期次
		String sealFolder = SchConstants.getSealTimeFolder(DateTimeUtil.getLastDate(), gameCode);
		File sFolder = new File(sealFolder);
		if(sFolder.exists())	//文件夹存在
		{
			String[] fNameArray = sFolder.list();
			for(int j = 0; j < fNameArray.length; j++)
			{
				String termCode = fNameArray[j].split("\\.")[0];
				Term t = termService.findOneByGameCodeAndCode(gameCode, termCode);
				String issueFolder = SchConstants.getIssueFolder(gameCode, termCode, g.getType(), t.getEndTime());
				String lPrizeFilePath = issueFolder + "/" + channelCode + "/" + littlePrizeFileName;
				fList.add(lPrizeFilePath);
				
				String bPrizeFilePath = issueFolder + "/" + channelCode + "/" + bigPrizeFileName;
				fList.add(bPrizeFilePath);
			}
		}
		return fList;
	}

	@Override
	public void refundSchemeZh(SchemeZh scheme) {
		//MoneyService moneyService = SchedulerContext.getInstance().getBean("moneyService", MoneyService.class);
		long amount = scheme.getAmount() * (scheme.getOrderCount() - scheme.getFinishedOrderCount())/scheme.getOrderCount();
		moneyService.customerCancelSchemeZh(scheme.getStationId(), scheme.getCustomerId(), scheme.getId(), amount);
	}

	/**
	 * 保存订单
	 */
	@Override
	public void saveOrder(TOrder order) throws RemoteException {
		Term t = termService.findOneByGameCodeAndCode(order.getGameCode(), order.getTermCode());
		OrderStateUtil.updateOrderStatus(order, true, t.getStatus());
		orderService.save(order);
		PrintUtil.newOrder(SchedulerContext.getInstance().getSpringContext(), order);
	}

    /**
     *
     * @param customerId
     * @param stationId
     * @param amount
     * @param flag
     * @throws RemoteException
     */
    @Override
    public void refundToCustomer(String customerId, String stationId, String orderId, long amount, String flag) throws RemoteException
    {
        this.moneyService.refundToCustomerWhenCancelHm(stationId, customerId, orderId, flag, amount);
    }

    /**
     * 给用户返奖
     * @param customerId
     * @param stationId
     * @param orderId
     * @param amount
     * @param flag
     * @throws RemoteException
     */
    public void prizeToCustomer(String customerId, String stationId, String orderId, long amount, String flag) throws RemoteException
    {
        this.moneyService.rewardToCustomer(stationId, customerId, orderId, amount, flag);
    }
}
