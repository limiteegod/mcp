package com.mcp.rmi.inter;

import com.mcp.scheme.model.SchemeHm;
import com.mcp.scheme.model.SchemeShare;
import com.mcp.scheme.model.SchemeZh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 方案引擎提供的内部调用接口
 * @author ming.li
 */
public interface SchemeInter extends Remote {
	
	/**
	 * 根据id查询追号方案
	 * @param schemeZhId 追号方案的id
	 * @return
	 * @throws RemoteException
	 */
	public SchemeZh queryZhById(String schemeZhId) throws RemoteException;
	
	public Page<SchemeZh> query(String customerId, String gameCode, int status, Pageable page) throws RemoteException;
	
	/**
	 * 取消追号方案
	 * @param schemeId
	 */
	public void cancelSchemeZh(String schemeId) throws RemoteException;
	
	/**
	 * 保存追号方案
	 * @param scheme
	 */
	public void saveZh(SchemeZh scheme) throws RemoteException;

    /**
     * 保存合买方案
     * @param scheme
     * @throws RemoteException
     */
    public void saveHm(SchemeHm scheme) throws RemoteException;

    /**
     * 通过id查询合买方案
     * @param id
     * @return
     * @throws RemoteException
     */
    public SchemeHm queryHmById(String id) throws RemoteException;

    /**
     * 保存合买方案份额
     * @param schemeShare
     * @throws RemoteException
     */
    public SchemeHm saveSchemeShare(SchemeShare schemeShare) throws RemoteException;
	
	
	/**
	 * 方案中奖
	 * @param schemeId
	 * @param schemeType
	 * @param bonus
	 * @throws RemoteException
	 */
	public void hit(String schemeId, int schemeType, long bonus, long bonusBeforeTax) throws RemoteException;
	
	/**
	 * 取消未支付的方案
	 * @param gameCode
	 * @param startTermCode
	 * @throws RemoteException
	 */
	public void cancelUnAffordedZh(String gameCode, String startTermCode) throws RemoteException;
	
	/**
	 * 支付一个未支付的订单
	 * @param schemeId 方案的id
	 * @param schemeId 方案的类型
	 * @throws RemoteException
	 */
	public void affordScheme(String schemeId, int schemeType) throws RemoteException;

    /**
     * 期次状态改变
     * @param gameCode
     * @param termCode
     * @param nextTermCode
     * @param status
     * @throws RemoteException
     */
    public void termChange(String gameCode, String termCode, String nextTermCode, int status) throws RemoteException;
}
