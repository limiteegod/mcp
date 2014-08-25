package com.mcp.order.inter.scheme;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PlayType;
import com.mcp.scheme.model.SchemeZh;
import org.apache.log4j.Logger;

public class ReqS01Body extends ReqBody {
	
	private static Logger log = Logger.getLogger(ReqS01Body.class);
	
	private SchemeZh scheme;

	public SchemeZh getScheme() {
		return scheme;
	}

	public void setScheme(SchemeZh scheme) {
		this.scheme = scheme;
	}

	@Override
	public void validate() throws CoreException {
		String gameCode = scheme.getGameCode();
		
		int orderCount = scheme.getOrderCount();
		if(orderCount < 2)
		{
			throw new CoreException(ErrCode.E2048, ErrCode.codeToMsg(ErrCode.E2048));
		}
		
		long amount = 0;
		String[] numList = scheme.getNumList().split("!");
		for(int j = 0; j < numList.length; j++)
		{
			String ticketString = numList[j];
			String[] ticketArray = ticketString.split("~");
			String playType = ticketArray[0];
			String betType = ticketArray[1];
			String number = ticketArray[2];
			long tAmount = Long.parseLong(ticketArray[3]);
			int multiple = Integer.parseInt(ticketArray[4]);
			
			PlayType pt = LotteryContext.getInstance().getPlayTypeByCode(gameCode + playType);
			if(pt == null || pt.getStatus() == ConstantValues.PlayType_Status_Stop.getCode())
			{
				throw new CoreException(ErrCode.E2031, ErrCode.codeToMsg(ErrCode.E2031));
			}
			BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
			if(bt == null || bt.getStatus() == ConstantValues.BetType_Status_Stop.getCode())
			{
				throw new CoreException(ErrCode.E2032, ErrCode.codeToMsg(ErrCode.E2032));
			}
			if(StringUtil.isEmpty(number))
			{
				throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
			}
			
			int count = bt.getValidator().validator(number);
			if(tAmount != pt.getPrice()*count*multiple)
			{
				throw new CoreException(ErrCode.E2025, ErrCode.codeToMsg(ErrCode.E2025));
			}
			log.info(tAmount);
			amount += tAmount;
		}
		log.info(amount);
		/**
		 * 校验金额是否相符
		 */
		if(amount*orderCount != scheme.getAmount())
		{
			throw new CoreException(ErrCode.E2025, ErrCode.codeToMsg(ErrCode.E2025));
		}
	}
}
