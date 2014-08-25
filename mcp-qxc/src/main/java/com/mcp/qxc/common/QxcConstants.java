/**
 * 
 */
package com.mcp.qxc.common;

import com.mcp.order.common.Constants;

/**
 * @author ming.li
 *
 */
public class QxcConstants {

	/**
	 * 七星彩中奖规则，第一个数字为连续的中奖号码个数，第二个数字为对应的奖级
	 */
	public static final int[][] CHECK_RULE = new int[][]{{7,Constants.GRADE_LEVEL_FIRST}, {6,Constants.GRADE_LEVEL_SECOND}, {5,Constants.GRADE_LEVEL_THIRD}, {4,Constants.GRADE_LEVEL_FOURTH}, {3,Constants.GRADE_LEVEL_FIFTH}, {2,Constants.GRADE_LEVEL_SIXTH}};
	
	/**
	 * 单式长度
	 */
	public static final int DANSHI_LEN = 7;
	
	/**
	 * 最大球号
	 */
	public static final int MAX = 9;
	
	/**
	 * 最小球号
	 */
	public static final int MIN = 0;

}
