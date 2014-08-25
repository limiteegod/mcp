package com.mcp.rmi.inter;

import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.Term;
import com.mcp.scheme.model.SchemeZh;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 订单引擎提供的内部调用接口
 * @author ming.li
 */
public interface OrderInter extends Remote {
	
	/**
	 * 获得指定游戏最近一期可以购买(开售与未开售状态)的期次信息
	 * @param gameCode
	 * @return
	 */
	public Term getLatestSaleTerm(String gameCode) throws RemoteException;
	
	
	/**
	 * 获得上一天封存的期次的返奖文件的列表
	 * @param gameCode	游戏的代码
	 * @param channelCode	渠道号
	 * @return
	 * @throws RemoteException
	 */
	public List<String> getLastDayPrizedFileList(String gameCode, String channelCode) throws RemoteException;
	
	/**
	 * 给追号方案退款
	 * @throws RemoteException
	 */
	public void refundSchemeZh(SchemeZh schemeZh) throws RemoteException;
	
	/**
	 * 保存订单
	 * @param order
	 * @throws RemoteException
	 */
	public void saveOrder(TOrder order) throws RemoteException;

    /**
     * 给用户退款
     * @param customerId
     * @param stationId
     * @param amount
     * @param flag
     * @throws RemoteException
     */
    public void refundToCustomer(String customerId, String stationId, String orderId, long amount, String flag) throws RemoteException;

    /**
     * 给用户返奖
     * @param customerId
     * @param stationId
     * @param orderId
     * @param amount
     * @param flag
     * @throws RemoteException
     */
    public void prizeToCustomer(String customerId, String stationId, String orderId, long amount, String flag) throws RemoteException;

}
