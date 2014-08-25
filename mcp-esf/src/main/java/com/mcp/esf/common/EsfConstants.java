/**
 * 
 */
package com.mcp.esf.common;

/**
 * @author ming.li
 *
 */
public class EsfConstants {
	
	/**
	 * 前二组选和值注数对照表
	 * 索引为和值的大小，值为对应的注数
	 */
	public static final int[] QTZU_HEZHI_COUNT = new int[]{0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1};
	
	/**
	 * 前二组选跨度注数对照表
	 * 索引为跨度的大小，值为对应的注数
	 */
	public static final int[] QTZU_KUADU_COUNT = new int[]{0, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	
	/**
	 * 前三组选和值注数对照表
	 * 索引为和值的大小，值为对应的注数
	 */
	public static final int[] QTHZU_HEZHI_COUNT = new int[]{0, 0, 0, 0, 0, 0, 1, 1, 2, 3, 4, 5, 7, 8, 10, 11, 12, 12, 13, 12, 12, 11, 10, 8, 7, 5, 4, 3, 2, 1, 1};
	
	/**
	 * 前三组选跨度注数对照表
	 * 索引为跨度的大小，值为对应的注数
	 */
	public static final int[] QTHZU_KUADU_COUNT = new int[]{0, 0, 9, 16, 21, 24, 25, 24, 21, 16, 9};
	
	/**
	 * 最大球号
	 */
	public static final int MAX = 11;
	
	/**
	 * 最小球号
	 */
	public static final int MIN = 1;
	
	/**
	 * 前二组选和值，最大值
	 */
	public static final int QTZU_HEZHI_MAX = 21;
	
	/**
	 * 前二组选和值，最小值
	 */
	public static final int QTZU_HEZHI_MIN = 3;
	
	/**
	 * 前二组选跨度，最大值
	 */
	public static final int QTZU_KUADU_MAX = 10;
	
	/**
	 * 前二组选跨度，最小值
	 */
	public static final int QTZU_KUADU_MIN = 1;
	
	/**
	 * 前三组选和值，最大值
	 */
	public static final int QTHZU_HEZHI_MAX = 30;
	
	/**
	 * 前三组选和值，最小值
	 */
	public static final int QTHZU_HEZHI_MIN = 6;
	
	/**
	 * 前三组选跨度，最大值
	 */
	public static final int QTHZU_KUADU_MAX = 10;
	
	/**
	 * 前三组选跨度，最小值
	 */
	public static final int QTHZU_KUADU_MIN = 2;
	
	/**
	 * 获得单式号码的跨度，len=2或者3
	 * @param number
	 * @return
	 */
	public static int getKuaDu(int[] number, int len)
	{
		if(len == 2)
		{
			return Math.abs(number[1] - number[0]);
		}
		else
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
	
	public static int getHitCount(int[] numberArrayOne, int[] numberArrayTwo)
	{
		int hitCount = 0;
		for(int i = 0; i < numberArrayOne.length; i++)
		{
			for(int j = 0; j < numberArrayTwo.length; j++)
			{
				if(numberArrayOne[i] == numberArrayTwo[j])
				{
					hitCount++;
					break;
				}
			}
		}
		return hitCount;
	}
	
	/**
	 * 两个数组的长度必须一致，按顺序的匹配数目
	 * @param numberArrayOne
	 * @param numberArrayTwo
	 * @return
	 */
	public static int getHitCountByOrder(int[] numberArrayOne, int[] numberArrayTwo)
	{
		int hitCount = 0;
		for(int i = 0; i < numberArrayOne.length; i++)
		{
			if(numberArrayOne[i] == numberArrayTwo[i])
			{
				hitCount++;
			}
		}
		return hitCount;
	}
}
