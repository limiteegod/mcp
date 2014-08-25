package com.mcp.order.dao.finance;

import com.mcp.order.dao.CustomerDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 彩民通过第三方渠道充值
 * @author ming.li
 */
public class MoneyHandlerRU01000 extends AbstractMoneyHandler {

    @Autowired
    private MoneyLogDao moneyLogDao;

    @Autowired
    private CustomerDao customerDao;

	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		
		String userId = toEntityId;
		
		String fromAccountCode = fromFlag;
		String toAccountCode = "RU01";
		
		Customer customer = customerDao.findOne(toEntityId);
		customerDao.incrRechageById(amount, toEntityId);
		long stateBefore = customer.getRecharge();
        long stateAfter = customer.getRecharge() + amount;
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}
}
