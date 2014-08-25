/**
 * 
 */
package com.mcp.pls.common;

import com.mcp.order.common.Constants;

/**
 * @author ming.li
 *
 */
public class PlsConstants {
	
	/**
	 * pls中奖规则，第一个数字为playtype，第二个数字为对应的奖级
	 */
	public static final int[][] CHECK_RULE = new int[][]{{1,Constants.GRADE_LEVEL_FIRST}, {2,Constants.GRADE_LEVEL_SECOND}, {3,Constants.GRADE_LEVEL_THIRD}};
	
	
	/**
	 * pls直选和值，注数对照表，索引为和值的大小，值为注数
	 */
	public static final int[] ZHIXUAN_HEZHI_COUNT = new int[]{1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1};
	
	/**
	 * pls跨度注数对照表
	 */
	public static final int[] KUADU_COUNT = new int[]{10, 54, 96, 126, 144, 150, 144, 126, 96, 54};
	
	
	/**
	 * pls组选和值，注数对照表，索引为和值的大小，值为注数
	 */
	public static final int[] ZUXUAN_HEZHI_COUNT = new int[]{0, 1, 2, 2, 4, 5, 6, 8, 10, 11, 13, 14, 14, 15, 15, 14, 14, 13, 11, 10, 8, 6, 5, 4, 2, 2, 1};
	
	
	/**
	 * pls组三跨度注数对照表，索引为跨度的大小，值为注数
	 */
	public static final int[] ZUSAN_KUADU_COUNT = new int[]{0, 18, 16, 14, 12, 10, 8, 6, 4, 2};
	
	/**
	 * pls组六跨度注数对照表，索引为跨度的大小，值为注数
	 */
	public static final int[] ZULIU_KUADU_COUNT = new int[]{0, 0, 8, 14, 18, 20, 20, 18, 14, 8};
	
	/**
	 * 单式长度
	 */
	public static final int DANSHI_LEN = 3;
	
	/**
	 * 直选和值最大值
	 */
	public static final int ZHIXUAN_HEZHI_MAX = 27;
	
	/**
	 * 直选和值最小值
	 */
	public static final int ZHIXUAN_HEZHI_MIN = 0;
	
	/**
	 * 组三跨度最大值
	 */
	public static final int ZUSAN_KUADU_MAX = 9;
	
	/**
	 * 组三跨度最小值
	 */
	public static final int ZUSAN_KUADU_MIN = 1;
	
	/**
	 * 组六跨度最大值
	 */
	public static final int ZULIU_KUADU_MAX = 9;
	
	/**
	 * 组六跨度最小值
	 */
	public static final int ZULIU_KUADU_MIN = 2;
	
	/**
	 * 组选和值最大值
	 */
	public static final int ZUXUAN_HEZHI_MAX = 26;
	
	/**
	 * 组选和值最小值
	 */
	public static final int ZUXUAN_HEZHI_MIN = 1;
	
	/**
	 * 最大球号
	 */
	public static final int MAX = 9;
	
	/**
	 * 最小球号
	 */
	public static final int MIN = 0;
	
	/**
	 * 三连号
	 */
	public static final int NUMBER_TYPE_BAOZI = 1;
	
	/**
	 * 组三号
	 */
	public static final int NUMBER_TYPE_ZUSAN = 2;
	
	/**
	 * 组六号
	 */
	public static final int NUMBER_TYPE_ZULIU = 3;

	/**
	 * 获得号码的类型
	 * @param number
	 * @return
	 */
	public static int getNubmerType(int[] number)
	{
		if(number[0] == number[1] && number[1] == number[2])
		{
			return NUMBER_TYPE_BAOZI;
		}
		if(number[0] != number[1] && number[1] != number[2] && number[0] != number[2])
		{
			return NUMBER_TYPE_ZULIU;
		}
		return NUMBER_TYPE_ZUSAN;
	}
	
	/**
	 * 获得单式号码的跨度
	 * @param number
	 * @return
	 */
	public static int getKuaDu(int[] number)
	{
		int kuaDu = 0;
		int kuaDu1 = Math.abs(number[2] - number[1]);
		if(kuaDu1 > kuaDu)
		{
			kuaDu = kuaDu1;
		}
		int kuaDu2 = Math.abs(number[1] - number[0]);
		if(kuaDu2 > kuaDu)
		{
			kuaDu = kuaDu2;
		}
		int kuaDu3 = Math.abs(number[2] - number[0]);
		if(kuaDu3 > kuaDu)
		{
			kuaDu = kuaDu3;
		}
		return kuaDu;
	}
}
