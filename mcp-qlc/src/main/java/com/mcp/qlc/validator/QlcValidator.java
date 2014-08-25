/**
 * 
 */
package com.mcp.qlc.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.Validator;
import com.mcp.qlc.common.QlcConstants;

/**
 * @author ming.li
 *
 */
public abstract class QlcValidator implements Validator {
	
	protected int getCount(String number)
	{
		int fCount, sCount;
		String[] redArray = number.split("\\$");
		if(redArray.length == 1)
		{
			fCount = 0;
			String[] redStrArray = redArray[0].split(",");
			sCount = redStrArray.length;
			int[] redBalls = new int[sCount];
			for(int i = 0; i < sCount; i++)
			{
				redBalls[i] = Integer.parseInt(redStrArray[i]);
			}
			this.checkSort(redBalls);
			this.checkRedMargin(redBalls);
		}
		else
		{
			String[] fCountArray = redArray[0].split(",");
			fCount = fCountArray.length;
			String[] sCountArray = redArray[1].split(",");
			sCount = sCountArray.length;
			int[] fRedBalls = new int[fCount];
			for(int i = 0; i < fCount; i++)
			{
				fRedBalls[i] = Integer.parseInt(fCountArray[i]);
			}
			this.checkSort(fRedBalls);
			this.checkRedMargin(fRedBalls);
			int[] sRedBalls = new int[sCount];
			for(int i = 0; i < sCount; i++)
			{
				sRedBalls[i] = Integer.parseInt(sCountArray[i]);
			}
			this.checkSort(sRedBalls);
			this.checkRedMargin(sRedBalls);
			this.checkDuplicate(fRedBalls, sRedBalls);
		}
		int redCount = MathUtil.getC(sCount, QlcConstants.DANSHI_RED_LEN - fCount);
		return redCount;
	}
	
	/**
	 * 号码必须从小到大排列
	 * @param numbers
	 * @throws CoreException
	 */
	public void checkSort(int[] numbers)
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
	 * 红球号码范围
	 * @param numbers
	 * @throws CoreException
	 */
	public void checkRedMargin(int[] numbers)
	{
		if(numbers[numbers.length - 1] > QlcConstants.MAX_RED || numbers[0] < QlcConstants.MIN_RED)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
	
	/**
	 * 判断是否有重复号码
	 * @param numbers
	 */
	public void checkDuplicate(int[] arrayOne, int[] arrayTwo)
	{
		for(int i = 0; i < arrayOne.length; i++)
		{
			for(int j = 0; j < arrayTwo.length; j++)
			{
				if(arrayOne[i] == arrayTwo[j])
				{
					throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
				}
			}
		}
	}
}
