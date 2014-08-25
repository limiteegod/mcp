/**
 * 投注校验时，统计投注号码用的参数类
 */
package com.mcp.ssq.validator;

/**
 * @author ming.li
 *
 */
public class SsqCountDetail {
	
	/**
	 * 红球注数
	 */
	private int redCount;
	
	/**
	 * 蓝球注数
	 */
	private int blueCount;
	
	/**
	 * 总注数
	 */
	private int count;
	
	public SsqCountDetail(int redCount, int blueCount)
	{
		this.redCount = redCount;
		this.blueCount = blueCount;
		this.count = this.redCount*this.blueCount;
	}

	public int getRedCount() {
		return redCount;
	}

	public void setRedCount(int redCount) {
		this.redCount = redCount;
	}

	public int getBlueCount() {
		return blueCount;
	}

	public void setBlueCount(int blueCount) {
		this.blueCount = blueCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
