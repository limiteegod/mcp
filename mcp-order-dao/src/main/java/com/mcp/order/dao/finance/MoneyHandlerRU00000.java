package com.mcp.order.dao.finance;

import com.mcp.order.dao.CustomerAccountDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 彩民充值
 *
 * @author ming.li
 */
public class MoneyHandlerRU00000 extends AbstractMoneyHandler {

    @Autowired
    private MoneyLogDao moneyLogDao;

    @Autowired
    private CustomerAccountDao customerAccountDao;

	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String fromAccountCode = "";
		String toAccountCode = "RU00";
		CustomerAccount ca = customerAccountDao.findOne(toEntityId);
		customerAccountDao.incrRechageById(amount, toEntityId);
		long stateBefore = ca.getRecharge();
        long stateAfter = ca.getRecharge() + amount;
		MoneyLog log = super.generateLog("", fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}
}
