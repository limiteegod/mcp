package com.mcp.order.util;

import com.mcp.core.util.MathUtil;

public class LotteryFuShiUtil {
	
	/**
	 * 校验复式的号码的排序及边界
	 * @param number
	 * @param max
	 * @param min
	 */
	public static void checkFuShiNumber(int[] numbers, int max, int min)
	{
		LotteryUtil.checkSortFromMinToMax(numbers);
		LotteryUtil.checkMargin(numbers, max, min);
	}
	
	/**
	 * 校验复式的号码的排序及边界
	 * @param number
	 * @param max
	 * @param min
	 */
	public static void checkFuShiNumber(String number, int max, int min)
	{
		int[] numbers = LotteryUtil.getIntArrayFromStrArray(number.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryUtil.checkSortFromMinToMax(numbers);
		LotteryUtil.checkMargin(numbers, max, min);
	}
	
	/**
	 * 校验复式的号码的排序及边界
	 * @param number
	 * @param max
	 * @param min
	 * @param len 所需号码的长度
	 */
	public static int checkFuShiNumberAndGetCount(int[] numbers, int max, int min, int len)
	{
		LotteryUtil.checkSortFromMinToMax(numbers);
		LotteryUtil.checkMargin(numbers, max, min);
		return MathUtil.getC(numbers.length, len);
	}
	
	/**
	 * 校验复式的号码的排序及边界
	 * @param number
	 * @param max
	 * @param min
	 * @param len 所需号码的长度
	 */
	public static int checkFuShiNumberAndGetCount(String number, int max, int min, int len)
	{
		int[] numbers = LotteryUtil.getIntArrayFromStrArray(number.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryUtil.checkSortFromMinToMax(numbers);
		LotteryUtil.checkMargin(numbers, max, min);
		return MathUtil.getC(numbers.length, len);
	}
	
	/**
	 * 校验（按位）复式的号码的排序及边界
	 * @param number
	 * @param max
	 * @param min
	 * @param len 每一位所需的号码的个数 
	 * @return
	 */
	public static int checkPositionFuShiNumberAndGetCount(String number, int max, int min, int len)
	{
		String[] positionArray = number.split(LotteryUtil.POSITION_REG_SEP);
		int count = 1;
		for(int i = 0; i < positionArray.length; i++)
		{
			count = count*checkFuShiNumberAndGetCount(positionArray[i], max, min, len);
		}
		return count;
	}
}
