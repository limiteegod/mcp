/**
 * 
 */
package com.mcp.ssq.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.Validator;
import com.mcp.ssq.common.SsqConstants;

/**
 * @author ming.li
 *
 */
public abstract class SsqValidator implements Validator {
	
	protected SsqCountDetail getCount(String number)
	{
		int fCount, sCount, tCount, foCount;
		String[] nArray = number.split("\\|");
		String[] redArray = nArray[0].split("\\$");
		String[] blueArray = nArray[1].split("\\$");
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
		if(blueArray.length == 1)
		{
			tCount = 0;
			String[] blueStrArray = blueArray[0].split(",");
			foCount = blueStrArray.length;
			int[] blueBalls = new int[foCount];
			for(int i = 0; i < foCount; i++)
			{
				blueBalls[i] = Integer.parseInt(blueStrArray[i]);
			}
			this.checkSort(blueBalls);
			this.checkBlueMargin(blueBalls);
		}
		else
		{
			String[] tCountArray = blueArray[0].split(",");
			tCount = tCountArray.length;
			String[] foCountArray = blueArray[1].split(",");
			foCount = foCountArray.length;
			int[] tBlueBalls = new int[tCount];
			for(int i = 0; i < tCount; i++)
			{
				tBlueBalls[i] = Integer.parseInt(tCountArray[i]);
			}
			this.checkSort(tBlueBalls);
			this.checkBlueMargin(tBlueBalls);
			int[] foBlueBalls = new int[foCount];
			for(int i = 0; i < foCount; i++)
			{
				foBlueBalls[i] = Integer.parseInt(foCountArray[i]);
			}
			this.checkSort(foBlueBalls);
			this.checkBlueMargin(foBlueBalls);
			this.checkDuplicate(tBlueBalls, foBlueBalls);
		}
		int redCount = MathUtil.getC(sCount, SsqConstants.DANSHI_RED_LEN - fCount);
		int blueCount = MathUtil.getC(foCount, SsqConstants.DANSHI_BLUE_LEN - tCount);
		return new SsqCountDetail(redCount, blueCount);
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
		if(numbers[numbers.length - 1] > SsqConstants.MAX_RED || numbers[0] < SsqConstants.MIN_RED)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
	
	/**
	 * 蓝球号码范围
	 * @param numbers
	 * @throws CoreException
	 */
	public void checkBlueMargin(int[] numbers)
	{
		if(numbers[numbers.length - 1] > SsqConstants.MAX_BLUE || numbers[0] < SsqConstants.MIN_BLUE)
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
