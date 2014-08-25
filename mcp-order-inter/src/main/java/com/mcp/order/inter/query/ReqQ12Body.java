package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

import java.util.Date;

public class ReqQ12Body extends ReqBody {
	
	private int size = 10;

	private Date time;
	
	private int type;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mcp.core.inter.define.Body#validate()
	 */
	@Override
	public void validate() throws CoreException {

	}
}
