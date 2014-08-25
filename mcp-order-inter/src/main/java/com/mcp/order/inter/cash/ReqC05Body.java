package com.mcp.order.inter.cash;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

import java.util.Date;

/**
 *
 * @author ming.li
 */
public class ReqC05Body extends ReqBody {

    private int page = 0;

    private int size = 10;

    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
	public void validate() throws CoreException {

	}

}
