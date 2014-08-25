package com.mcp.order.inter.account;

import com.mcp.order.inter.RepBody;

public class RepA07Body extends RepBody {
	
	private String name;
	
	private String code;
	
	private int status;
	
	private long balance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}
	
}
