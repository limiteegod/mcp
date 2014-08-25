package com.mcp.order.inter.cash;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

/**
 * 奖金转投注金
 * @author ming.li
 */
public class ReqC01Body extends ReqBody {
	
	/**
	 * 要转账的金额
	 */
	private long amount;
	
	/**
	 * 要实施转账操作的目标投注站
	 */
	private String stationId;
	
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Override
	public void validate() throws CoreException {
		if(amount <= 0)
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
