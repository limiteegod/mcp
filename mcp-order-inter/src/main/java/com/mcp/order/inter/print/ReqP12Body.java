package com.mcp.order.inter.print;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqP12Body extends ReqBody {

    private int size = 10;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
	public void validate() throws CoreException {
        if(size < 1)
        {
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }
	}

}
