package com.mcp.order.dao.finance;

import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.StationDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 代理商结算账户的收入，平台返点。
 * @author ming.li
 */
public class MoneyHandlerRS00002 extends AbstractMoneyHandler {

	@Autowired
    private MoneyLogDao moneyLogDao;

    @Autowired
    private StationDao stationDao;
    
	@Override
	public MoneyLog handle(Object user, String fromEntityId, String fromFlag, String toEntityId, String toFlag,
			long amount, String orderId, AccountOperatorType type)
			throws CoreException {
		String userId = AccountConstants.SYSTEM_USER_ID;
		String fromAccountCode;
		String toAccountCode = "RS00";
		String opType = type.getType();
		
		if(opType.equals("RS0000200"))
		{
			//平台返点
			fromAccountCode = "RP00";
		}
		else if(opType.equals("RS0000201"))
		{
			//转票时，资金从销售方流向了出票中心的投注站
			fromAccountCode = "RS00";
		}
		else
		{
			throw new CoreException(ErrCode.E1021, ErrCode.codeToMsg(ErrCode.E1021));
		}
		
		Station station = stationDao.findOne(toEntityId);
		stationDao.incrBalanceById(amount, toEntityId);
		long stateBefore = station.getBalance();
        long stateAfter = station.getBalance() + amount;
        
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}

}
