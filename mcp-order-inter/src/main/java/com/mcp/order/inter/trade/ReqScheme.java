/**
 * 
 */
package com.mcp.order.inter.trade;

/**
 * @author ming.li
 *
 */
public class ReqScheme {
	
	/**
	 * 方案的类型
	 */
	private int type = -1;
	
	/**
	 * 方案执行的总期数
	 */
	private int totalIssue = -1;
	
	/**
	 * 0,中奖后继续，1，中奖后停止，2、中奖后多少金额停追
	 */
	private int winStop = -1;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTotalIssue() {
		return totalIssue;
	}

	public void setTotalIssue(int totalIssue) {
		this.totalIssue = totalIssue;
	}

	public int getWinStop() {
		return winStop;
	}

	public void setWinStop(int winStop) {
		this.winStop = winStop;
	}
}
