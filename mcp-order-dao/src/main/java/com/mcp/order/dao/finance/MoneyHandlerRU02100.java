package com.mcp.order.dao.finance;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.CustomerAccountDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.exception.NoMoneyException;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 奖金账户支出，（提现、转投注金）
 * @author ming.li
 */
public class MoneyHandlerRU02100 extends AbstractMoneyHandler {

	@Autowired
    private MoneyLogDao moneyLogDao;

	@Autowired
    private CustomerAccountDao customerAccountDao;
    
	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = AccountConstants.SYSTEM_USER_ID;
		String fromAccountCode = "RU02";
		String toAccountCode = "";
		
		int status = ConstantValues.MoneyLog_Status_FINISHED.getCode();
		if(type.getType().equals("RU0210001"))
		{
			//彩金转投注金
			toAccountCode = "RU00";
		}
		else if(type.getType().equals("RU0210000"))
		{
			//提现，则设置账户记录为处理中
			status = ConstantValues.MoneyLog_Status_HANDLING.getCode();
		}
		
		CustomerAccount ca = customerAccountDao.findOne(fromEntityId);
		if(ca.getPrize() < amount)
		{
			throw new NoMoneyException(ErrCode.E1007, ErrCode.codeToMsg(ErrCode.E1007), ca.getRecharge(), amount);
		}
		customerAccountDao.decrPrizeById(amount, fromEntityId);
		
		long stateBefore = ca.getPrize();
        long stateAfter = ca.getPrize() - amount;
        
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		log.setStatus(status);
		
		return moneyLogDao.save(log);
	}

}
