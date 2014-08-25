package com.mcp.order.inter.account;

import com.mcp.order.inter.RepBody;

public class RepA03Body extends RepBody {
	
	/**
	 * 状态，密码更新成功为true，更新失败则为false
	 */
	private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
