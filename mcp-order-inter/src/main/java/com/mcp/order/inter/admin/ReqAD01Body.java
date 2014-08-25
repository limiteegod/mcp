package com.mcp.order.inter.admin;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqAD01Body extends ReqBody {
	
    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
	public void validate() throws CoreException {
        if(StringUtil.isEmpty(this.name) || StringUtil.isEmpty(this.password))
        {
            throw new CoreException(ErrCode.E0003);
        }
	}
}
