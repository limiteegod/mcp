package com.mcp.order.dao.finance;

import com.mcp.order.dao.CustomerAccountDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 彩民收入，投注站返奖
 * @author ming.li
 */
public class MoneyHandlerRU02000 extends AbstractMoneyHandler {

	@Autowired
    private MoneyLogDao moneyLogDao;

	@Autowired
    private CustomerAccountDao customerAccountDao;
    
	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = AccountConstants.SYSTEM_USER_ID;
		String fromAccountCode = fromFlag;
		String toAccountCode = "RU02";
		
		CustomerAccount ca = customerAccountDao.findOne(toEntityId);
		customerAccountDao.incrPrizeById(amount, toEntityId);
		
		long stateBefore = ca.getPrize();
        long stateAfter = ca.getPrize() + amount;
        
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}

}
