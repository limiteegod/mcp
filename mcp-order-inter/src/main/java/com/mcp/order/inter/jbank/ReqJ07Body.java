package com.mcp.order.inter.jbank;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

/**
 * 当期投注记录查询
 * @author ming.li
 */
public class ReqJ07Body extends ReqBody {
	
	/**
     * 记录起始地址
     */
    private int startIndex = 0;
    /**
     * 返回的分页大小
     */
    private int size = 10;

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
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
