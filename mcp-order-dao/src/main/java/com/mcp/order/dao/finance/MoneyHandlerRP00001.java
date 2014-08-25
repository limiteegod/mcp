package com.mcp.order.dao.finance;

import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.admin.CPlatformDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.admin.CPlatform;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台收取业务费用
 * @author ming.li
 */
public class MoneyHandlerRP00001 extends AbstractMoneyHandler {

    @Autowired
    private MoneyLogDao moneyLogDao;
    
    @Autowired
    private CPlatformDao cPlatformDao;

	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = AccountConstants.SYSTEM_USER_ID;
		String fromAccountCode = "";
		String toAccountCode = "RP00";
		//系统结算账户
		CPlatform balanceForm = cPlatformDao.findOne(toEntityId);
		cPlatformDao.incrLongValueById(amount, toEntityId);
		long stateBefore = balanceForm.getFormLongValue();
		long stateAfter = balanceForm.getFormLongValue() + amount;
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}
}
