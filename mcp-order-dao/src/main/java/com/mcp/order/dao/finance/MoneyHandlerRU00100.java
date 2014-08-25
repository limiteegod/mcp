package com.mcp.order.dao.finance;

import com.mcp.order.dao.CustomerAccountDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.exception.NoMoneyException;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 彩民使用投注站账户购买彩票
 * @author ming.li
 */
public class MoneyHandlerRU00100 extends AbstractMoneyHandler {

	@Autowired
	private MoneyLogDao moneyLogDao;
	
	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = null;
		if(user != null)
		{
			Customer cus = (Customer)user;
			userId = cus.getId();
		}
		CustomerAccount ca = customerAccountDao.findOne(fromEntityId);
		if(ca.getRecharge() < amount)
		{
			throw new NoMoneyException(ErrCode.E1007, ErrCode.codeToMsg(ErrCode.E1007), ca.getRecharge(), amount);
		}
		String toAccountCode = toFlag;
		customerAccountDao.decrRechageById(amount, fromEntityId);
		long stateBefore = ca.getRecharge();
        long stateAfter = ca.getRecharge() - amount;
		MoneyLog log = super.generateLog(userId, type.getType().substring(0, 4), fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}

}
