package com.mcp.order.dao.finance;

import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.StationDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 投注站结算账户支出，支付业务费用给平台
 * @author ming.li
 */
public class MoneyHandlerRS00101 extends AbstractMoneyHandler {

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
		String toAccountCode;
		
		String opType = type.getType();
		if(opType.equals("RS0010100"))
		{
			//向平台支付业务费用
			toAccountCode = "RP00";
		}
		else if(opType.equals("RS0010101"))
		{
			//转票时，资金流向了出票中心的投注站
			toAccountCode = "RS00";
		}
		else
		{
			throw new CoreException(ErrCode.E1021, ErrCode.codeToMsg(ErrCode.E1021));
		}
		
		Station station = stationDao.findOne(fromEntityId);
		stationDao.decrBalanceById(amount, fromEntityId);
		long stateBefore = station.getBalance();
        long stateAfter = station.getBalance() - amount;
        
		MoneyLog log = super.generateLog(userId, fromAccountCode, fromEntityId, toAccountCode, toEntityId, amount, orderId, type, stateBefore, stateAfter);
		return moneyLogDao.save(log);
	}

}
