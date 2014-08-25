/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;


/**
 * @author ming.li
 *
 */
public class ReqN03Body extends ReqBody {
	
	private String gameCode;
	
	private String matchCode;
	
	private String drawNumber;
	
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

	public String getDrawNumber() {
		return drawNumber;
	}

	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(this.gameCode))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
		if(StringUtil.isEmpty(this.matchCode))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
		if(StringUtil.isEmpty(this.drawNumber))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
