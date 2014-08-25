package com.mcp.order.inter.query;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ11Body extends ReqBody {
	
	private int status = ConstantValues.Game_Status_Open.getCode();

    public int getStatus() {
		return status;
	}
    
	public void setStatus(int status) {
		this.status = status;
	}

	/* (non-Javadoc)
         * @see com.mcp.core.inter.define.Body#validate()
         */
	@Override
	public void validate() throws CoreException {
		
	}
}
