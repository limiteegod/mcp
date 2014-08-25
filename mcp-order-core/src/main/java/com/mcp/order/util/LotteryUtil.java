/**
 * 
 */
package com.mcp.order.util;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 *
 */
public class LotteryUtil {
	
	//private static Logger log = Logger.getLogger(LotteryUtil.class);
	
	/**
	 * 胆拖分割符
	 */
	public static final String DANTUO_SEP = "$";
	
	/**
	 * 胆拖分割符，适用于需要正则表达式的场景
	 */
	public static final String DANTUO_REG_SEP = "\\$";
	
	/**
	 * 复式分割符
	 */
	public static final String FUSHI_SEP = ",";
	
	/**
	 * 复式分割符，适用于需要正则表达式的场景
	 */
	public static final String FUSHI_REG_SEP = ",";
	
	/**
	 * 定位分割符
	 */
	public static final String POSITION_SEP = "|";
	
	/**
	 * 定位分割符，适用于需要正则表达式的场景
	 */
	public static final String POSITION_REG_SEP = "\\|";
	
	/**
	 * 单式号码之间的分割符
	 */
	public static final String TICKET_SEP = ";";
	
	/**
	 * 单式号码之间的分割符，适用于需要正则表达式的场景
	 */
	public static final String TICKET_REG_SEP = ";";
	
	/**
	 * 把字符串数组转换成整形数组
	 */
	public static int[] getIntArrayFromStrArray(String[] strArray)
	{
		if(isArrayEmpty(strArray))
		{
			return new int[0];
		}
		int[] intArray = new int[strArray.length];
		for(int i = 0; i < strArray.length; i++)
		{
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		return intArray;
	}

    /**
     * 把字符串数组转换成整形数组
     */
    public static int[] getIntArrayFromCharArray(char[] charArray)
    {
        if(isArrayEmpty(charArray))
        {
            return new int[0];
        }
        int[] intArray = new int[charArray.length];
        for(int i = 0; i < charArray.length; i++)
        {
            intArray[i] = Integer.parseInt(String.valueOf(charArray[i]));
        }
        return intArray;
    }

    /**
     * 获得单式号码的和值
     * @param number
     * @return
     */
    public static int getHeZhi(String[] number)
    {
        int value = 0;
        for(int i = 0; i < number.length; i++)
        {
            value += Integer.parseInt(number[i]);
        }
        return value;
    }

	
	/**
	 * 把字符串数组转换成整形数组
	 * @param start 开始位置，从0开始
	 * @param len 目标数组的长度，如果超出了原数组的长度，则多余的会被忽略
	 */
	public static int[] getIntArrayFromStrArray(String[] strArray, int start, int len)
	{
		if(isArrayEmpty(strArray))
		{
			return new int[0];
		}
		int end = start + len;
		if(end > strArray.length)
		{
			end = strArray.length;
		}
		int[] intArray = new int[len];
		int index = 0;
		for(int i = start; i < end; i++)
		{
			intArray[index] = Integer.parseInt(strArray[i]);
			index++;
		}
		return intArray;
	}
	
	/**
	 * 字符串数组是否为空
	 * @param array
	 * @return
	 */
	public static boolean isArrayEmpty(String[] array)
	{
		if(array == null || array.length == 0)
		{
			return true;
		}
		return false;
	}

    /**
     * 字符串数组是否为空
     * @param array
     * @return
     */
    public static boolean isArrayEmpty(char[] array)
    {
        if(array == null || array.length == 0)
        {
            return true;
        }
        return false;
    }
	
	/**
	 * 整数数组是否为空
	 * @param array
	 * @return
	 */
	public static boolean isArrayEmpty(int[] array)
	{
		if(array == null || array.length == 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 获取两个数组中的相同号码的个数
	 * @param arrayOne	不能包含相同号码
	 * @param arrayTwo	不能包含相同号码
	 * @return
	 */
	public static int getHitCount(int[] arrayOne, int[] arrayTwo)
	{
		if(isArrayEmpty(arrayOne) || isArrayEmpty(arrayTwo))
		{
			return 0;
		}
		int hitCount = 0;
		for(int i = 0; i < arrayOne.length; i++)
		{
			for(int j = 0; j < arrayTwo.length; j++)
			{
				if(arrayOne[i] == arrayTwo[j])
				{
					hitCount++;
					break;
				}
			}
		}
		return hitCount;
	}

    /**
     * 获取两个数组中的相同号码的个数
     * @param value	值
     * @param arrayTwo	不能包含相同号码
     * @return
     */
    public static int getHitCount(int value, int[] arrayTwo)
    {
        if(isArrayEmpty(arrayTwo))
        {
            return 0;
        }
        int hitCount = 0;
        for(int j = 0; j < arrayTwo.length; j++)
        {
            if(value == arrayTwo[j])
            {
                hitCount++;
            }
        }
        return hitCount;
    }
	
	/**
	 * 按顺序的匹配数目
	 * @param numberArrayOne
	 * @param numberArrayTwo
	 * @return
	 */
	public static int getHitCountByOrder(int[] numberArrayOne, int[] numberArrayTwo)
	{
		int hitCount = 0;
		int size = numberArrayOne.length;
		if(size > numberArrayTwo.length)
		{
			size = numberArrayTwo.length;
		}
		for(int i = 0; i < size; i++)
		{
			if(numberArrayOne[i] == numberArrayTwo[i])
			{
				hitCount++;
			}
		}
		return hitCount;
	}
	
	/**
	 * 计算注数，二维数组的第一维表示位，第二维是号码
	 * 号码的最小值不能小于0
	 * @param numArray
	 * @param max 号码的最大值
	 * @param canDumplicate	是否可以有重复号出现
	 */
	public static int getCount(int[][] numArray, int max, boolean canDumplicate)
	{
		if(numArray == null || numArray.length == 0)
		{
			return 0;
		}
		int count = 1;
		for(int i = 0; i < numArray.length; i++)
		{
			count = count*numArray[i].length;
		}
		if(canDumplicate || numArray.length == 1)
		{
			return count;
		}
		else	//不可以重复出现
		{
			int[][] allNum = new int[count][numArray.length];
			//每一层的份数为当层的长度numIArray.length乘以上一层的份数，第一层的份数为第一层的长度
			int splitCount = 1;	
			for(int i = 0; i < numArray.length; i++)
			{
				int[] numIArray = numArray[i];
				splitCount = splitCount*numIArray.length;
				
				int countPerNum = count/splitCount;
				int index = 0;	//当前要填的索引
				while(index < count)
				{
					for(int j = 0; j < numIArray.length; j++)
					{
						for(int k = 0; k < countPerNum; k++)
						{
							allNum[index][i] = numIArray[j];
							index++;
						}
					}
				}
			}
			int dumplicateCount = 0;	//包含重复号码的个数
			for(int i = 0; i < allNum.length; i++)
			{
				if(hasDumplicate(allNum[i], max))
				{
					dumplicateCount++;
				}
			}
			return count - dumplicateCount;
		}
	}
	
	/**
	 * 判断一个数组是否有重复号码
	 * @param number
	 * @return
	 */
	public static boolean hasDumplicate(int[] number, int max)
	{
		if(isArrayEmpty(number))
		{
			return false;
		}
		int[] countArray = new int[max + 1];
		for(int i = 0; i < number.length; i++)
		{
			if(countArray[number[i]] == 1)
			{
				return true;
			}
			countArray[number[i]] = 1;
		}
		return false;
	}
	
	/**
	 * 号码必须从小到大排列
	 * @param numbers
	 * @throws CoreException
	 */
	public static void checkSortFromMinToMax(int[] numbers)
	{
		for(int i = 1; i < numbers.length; i++)
		{
			if(numbers[i] <= numbers[i - 1])
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
		}
	}
	
	
	/**
	 * 号码必须从小到大排列
	 * @param numbers
	 * @throws CoreException
	 */
	public static void checkSortFromMinToMaxCanEqual(int[] numbers)
	{
		for(int i = 1; i < numbers.length; i++)
		{
			if(numbers[i] < numbers[i - 1])
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
		}
	}
	
	/**
	 * 校验号码范围，只校验号码的左右边界
	 * @param numbers
	 * @throws CoreException
	 */
	public static void checkMargin(int[] numbers, int max, int min)
	{
		if(!isArrayEmpty(numbers))
		{
			if(numbers[numbers.length - 1] > max || numbers[0] < min)
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
		}
	}
	
	/**
	 * 校验号码范围
	 * @param number
	 * @throws CoreException
	 */
	public static void checkMargin(int number, int max, int min)
	{
		if(number > max || number < min)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
	
	/**
	 * 获得号码的跨度，先求得最大值和最小值
	 * @param number
	 * @return
	 */
	public static int getKuaDu(int[] number)
	{
		if(number == null || number.length == 0 || number.length == 1)
		{
			return 0;
		}
		int min = number[0], max = number[0];
		for(int i = 1; i < number.length; i++)
		{
			if(min > number[i])
			{
				min = number[i];
			}
			if(number[i] > max)
			{
				max = number[i];
			}
		}
		return max - min;
	}
	
	/**
	 * 获得所有号码的出现次数信息
	 * @param numbers
	 * @return
	 */
	public static Map<Integer, Integer> getInfo(int[] numbers)
	{
		Map<Integer, Integer> info = new HashMap<Integer, Integer>();
		for(int i = 0; i < numbers.length; i++)
		{
			if(info.containsKey(numbers[i]))
			{
				info.put(numbers[i], info.get(numbers[i]) + 1);
			}
			else
			{
				info.put(numbers[i], 1);
			}
		}
		return info;
	}
	
	/**
	 * 获得号码重复的信息，号码必须是已经排序好
	 * @param numbers
	 * @return Map<Integer, Integer> key为重复的号码，value为重复的次数
	 */
	public static Map<Integer, Integer> getDumplicateInfo(int[] numbers)
	{
		Map<Integer, Integer> info = new HashMap<Integer, Integer>();
		for(int i = 1; i < numbers.length; i++)
		{
			if(numbers[i] == numbers[i - 1])
			{
				if(info.containsKey(numbers[i]))
				{
					info.put(numbers[i], info.get(numbers[i]) + 1);
				}
				else
				{
					info.put(numbers[i], 1);
				}
			}
		}
		return info;
	}
	
	public static void main(String[] args)
	{
		int[][] numArray = new int[][]{{01,02}, {01,02}, {02, 04, 06}};
		System.out.println(getCount(numArray, 10, false));
	}
}
