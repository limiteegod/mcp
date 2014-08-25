package com.mcp.order.dao.finance;

import com.mcp.order.dao.CustomerDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.exception.NoMoneyException;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 彩民使用第三方支付，在投注站账户购买彩票
 * @author ming.li
 */
public class MoneyHandlerRU01100 extends AbstractMoneyHandler {

	@Autowired
	private MoneyLogDao moneyLogDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = fromEntityId;
		String fromAccountCode = "RU01";
		String toAccountCode = "RS00";
		
		Customer customer = customerDao.findOne(fromEntityId);
		if(customer.getRecharge() < amount)
		{
			throw new NoMoneyException(ErrCode.E1007, ErrCode.codeToMsg(ErrCode.E1007), customer.getRecharge(), amount);
		}
		customerDao.decrRechageById(amount, fromEntityId);
		long stateBefore = customer.getRecharge();
        long stateAfter = customer.getRecharge() - amount;
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}
}
