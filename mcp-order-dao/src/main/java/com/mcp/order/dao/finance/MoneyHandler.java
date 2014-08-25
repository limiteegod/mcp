/**
 * 
 */
package com.mcp.order.dao.finance;

import com.mcp.order.exception.CoreException;
import com.mcp.order.model.ts.MoneyLog;

/**
 * @author ming.li
 *
 */
public interface MoneyHandler {

	/**
	 * 资金处理 ，财务出入帐记账操作。
	 * @param user	操作员
	 * @param fromEntityId	借方id
	 * @param fromFlag	来源的标志，当和外部账户交互时使用
	 * @param toEntityId	贷方id
	 * @param toFlag	去向的标志，当和外部账户交互时使用
	 * @param amount	涉及资金
	 * @param orderId	业务订单号
	 * @param type	操作类型
	 * @return
	 * @throws Exception
	 */
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag, long amount, String orderId, AccountOperatorType type) throws CoreException;
}
