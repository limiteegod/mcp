package com.mcp.order.inter.cash;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

/**
 * 用户取现
 * @author ming.li
 */
public class ReqC03Body extends ReqBody {
	
	/**
	 * 要取现的金额
	 */
	private long amount;
	
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
