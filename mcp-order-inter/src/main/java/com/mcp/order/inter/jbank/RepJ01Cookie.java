/**
 * 
 */
package com.mcp.order.inter.jbank;

/**
 * @author ming.li
 *
 */
public class RepJ01Cookie {
	
	private String userId;
	
	private int userType;
	
	private String userMd5;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserMd5() {
		return userMd5;
	}

	public void setUserMd5(String userMd5) {
		this.userMd5 = userMd5;
	}
}
