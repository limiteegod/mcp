package com.mcp.order.exception;

/**
 * 账户余额不足
 */
public class NoMoneyException extends CoreException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8996735996690605174L;
	
	/**
	 * 当前余额
	 */
	private long balance = 0;
	
	/**
	 * 需要的余额
	 */
	private long tBalance = 0;
	
	public NoMoneyException(String errorCode, String message, long balance, long tBalance)
	{
		super(errorCode, message);
		this.balance = balance;
		this.tBalance = tBalance;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long gettBalance() {
		return tBalance;
	}

	public void settBalance(long tBalance) {
		this.tBalance = tBalance;
	}
}

