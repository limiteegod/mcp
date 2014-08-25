/**
 * 
 */
package com.mcp.feo.common;

import com.mcp.core.util.MathUtil;
import com.mcp.order.util.LotteryUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author ming.li
 *
 */
public class FeoConstants {

	/**
	 * 最大球号
	 */
	public static final int MAX = 8;
	
	/**
	 * 最小球号
	 */
	public static final int MIN = 1;
	
	/**
	 * 组24
	 */
	public static final int NUMBER_TYPE_ZTF = 1;
	
	/**
	 * 组12
	 */
	public static final int NUMBER_TYPE_ZOT = 2;
	
	
	/**
	 * 组6
	 */
	public static final int NUMBER_TYPE_ZS = 3;
	
	/**
	 * 组4
	 */
	public static final int NUMBER_TYPE_ZF = 4;
	
	/**
	 * 获得号码的类型，组24，组12，组6，组4
	 * @param numbers
	 * @return
	 */
	public static int getNumberType(int[] numbers)
	{
		Map<Integer, Integer> info = LotteryUtil.getInfo(numbers);
		Set<Integer> keys = info.keySet();
		int size = keys.size();
		if(size == 4)
		{
			return NUMBER_TYPE_ZTF;
		}
		else if(size == 3)
		{
			return NUMBER_TYPE_ZOT;
		}
		else if(size == 2)
		{
			boolean hasThree = false;
			for(Integer key:keys)
			{
				if(info.get(key) == 3)
				{
					hasThree = true;
				}
			}
			
			if(hasThree)
			{
				return NUMBER_TYPE_ZF;
			}
			else
			{
				return NUMBER_TYPE_ZS;
			}
		}
		return -1;
	}
	
	/**
	 * 获得任n的号码的注数
	 * @return
	 */
	public static int getRNumberCount(int[] lenArray, int n)
	{
		int all = 0;
		//先获得所有可能的组合
		int[][] cInfo = MathUtil.getDetailC(4, n);
		for(int i = 0; i < cInfo.length; i++)
		{
			int[] indexInfo = cInfo[i];
			int count = 1;
			for(int j = 0; j < indexInfo.length; j++)
			{
				count = count*lenArray[indexInfo[j]]; 
			}
			all += count;
		}
		return all;
	}
	
	/**
	 * 对任n的每一位都进行校验
	 * @param strArray
	 */
	public static void checkRNumber(String[] strArray)
	{
		for(int i = 0; i < strArray.length; i++)
		{
			if(!strArray[i].equals("_"))
			{
				int[] posIntArray = LotteryUtil.getIntArrayFromStrArray(strArray[i].split(LotteryUtil.FUSHI_REG_SEP));
				LotteryUtil.checkMargin(posIntArray, FeoConstants.MAX, FeoConstants.MIN);
				LotteryUtil.checkSortFromMinToMax(posIntArray);
			}
		}
	}
}
