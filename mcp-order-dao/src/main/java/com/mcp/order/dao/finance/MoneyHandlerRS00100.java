package com.mcp.order.dao.finance;

import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.StationDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.exception.NoMoneyException;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 投注站结算账户支出，提款
 * @author ming.li
 */
public class MoneyHandlerRS00100 extends AbstractMoneyHandler {

	@Autowired
    private MoneyLogDao moneyLogDao;

    @Autowired
    private StationDao stationDao;
    
	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = AccountConstants.SYSTEM_USER_ID;
		String fromAccountCode = "RS00";
		String toAccountCode = "";
		
		Station station = stationDao.findOne(fromEntityId);
		if(station.getBalance() < amount)
		{
			throw new NoMoneyException(ErrCode.E1007, ErrCode.codeToMsg(ErrCode.E1007), station.getBalance(), amount);
		}
		stationDao.decrBalanceById(amount, fromEntityId);
		long stateBefore = station.getBalance();
        long stateAfter = station.getBalance() - amount;
        
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}

}
