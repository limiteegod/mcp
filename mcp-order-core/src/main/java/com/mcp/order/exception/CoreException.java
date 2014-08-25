package com.mcp.order.exception;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:31
 * To be the best of me!
 */
public class CoreException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;
    
    //异常编号
    private String errorCode;
    
    public CoreException(String errorCode, String message) {
    	super(message);
    	this.errorCode = errorCode;
    }

    public CoreException(String errorCode) {
        this(errorCode, ErrCode.codeToMsg(errorCode));
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}

