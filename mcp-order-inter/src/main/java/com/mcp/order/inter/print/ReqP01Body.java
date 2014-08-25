package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

import java.util.List;


public class ReqP01Body extends ReqBody {
	
	private List<String> gameCodes;
	
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<String> getGameCodes() {
		return gameCodes;
	}

	public void setGameCodes(List<String> gameCodes) {
		this.gameCodes = gameCodes;
	}

	@Override
	public void validate() throws CoreException {
		if(this.size <= 0)
		{
			throw new CoreException(ErrCode.E0003, "size必须大于0");
		}
	}
}
