package com.mcp.order.dao.finance;

import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.StationDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 代理商投注触发的资金操作
 * @author ming.li
 */
public class MoneyHandlerRS00001 extends AbstractMoneyHandler {

	@Autowired
    private MoneyLogDao moneyLogDao;

    @Autowired
    private StationDao stationDao;
    
	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = AccountConstants.SYSTEM_USER_ID;
		String fromAccountCode = "";
		String toAccountCode = "RC00";
		
		Station station = stationDao.findOne(toEntityId);
		stationDao.incrBalanceById(amount, toEntityId);
		long stateBefore = station.getBalance();
        long stateAfter = station.getBalance() + amount;
        
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}

}
