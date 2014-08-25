/**
 * 
 */
package com.mcp.order.dao.finance;

import com.mcp.order.exception.CoreException;
import com.mcp.order.model.ts.MoneyLog;

import java.util.UUID;

/**
 * @author ming.li
 * 
 */
public abstract class AbstractMoneyHandler implements MoneyHandler {

	/**
	 * 根据参数获取日志记录对象
	 * @param userId 操作员id
	 * @param fromAccountCode 来源的账户编码
	 * @param fromEntityId	来源的实体id
	 * @param toAccountCode	去向的账户编码
	 * @param toEntityId	去向的实体id
	 * @param amount	资金额度
	 * @param orderId	订单号
	 * @param type	账户操作类型
	 * @param stateBefore	操作之前的状态
	 * @param stateAfter	操作之后的状态
	 * @return	日志记录对象
	 * @throws CoreException
	 */
	public MoneyLog generateLog(String userId, String fromAccountCode, String fromEntityId,
			String toAccountCode, String toEntityId, long amount, String orderId,
			AccountOperatorType type, long stateBefore, long stateAfter) throws CoreException {
		MoneyLog moneyLog = new MoneyLog();
		moneyLog.setId(UUID.randomUUID().toString().replace("-", ""));
		AccountOperatorType parentType = AccountOperator.getInstance()
				.getParent(type.getType());
		moneyLog.setHandlerCode(parentType.getType()); // 与MoneyHandler00000对应
		moneyLog.setOperationCode(type.getType()); // 与accountOperatorType.xml中的条目对应。
		moneyLog.setSubject(type.getName());
		moneyLog.setOrderId(orderId);// 如果没有对应订单，则为空。
		moneyLog.setUserId(userId); // 操作员ID，用于操作日志。由于目前投注站账户未分角色，所以用投注站ID代替。
		moneyLog.setCreateTimeStamp(System.currentTimeMillis());// 创建流水时间
		moneyLog.setAcceptTimeStamp(System.currentTimeMillis()); // 实际记录流水时间，如果异步记账，则和创建时间有个时间差。
		moneyLog.setFromAccountCode(fromAccountCode); // 外部账户，在accountOperatorType中没有定义。accountOperatorType暂时只记录内部账户。
		moneyLog.setFromEntityId(fromEntityId);
		moneyLog.setAmount(amount);
		moneyLog.setToAccountCode(toAccountCode); // 彩民--投注站预存账户--收入
		moneyLog.setToEntityId(toEntityId);// 给自己充值。写入彩民的customerAccount的id值。
		moneyLog.setStateBefore(stateBefore);
        moneyLog.setStateAfter(stateAfter);
		return moneyLog;
	}
	
}
