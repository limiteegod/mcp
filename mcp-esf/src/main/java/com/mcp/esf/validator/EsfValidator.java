/**
 * 
 */
package com.mcp.esf.validator;

import com.mcp.esf.common.EsfConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.Validator;

/**
 * @author ming.li
 *
 */
public abstract class EsfValidator implements Validator {
	
	/**
	 * 号码必须从小到大排列
	 * @param numbers
	 * @throws CoreException
	 */
	protected void checkSort(int[] numbers)
	{
		if(numbers.length > 1)
		{
			for(int i = 1; i < numbers.length; i++)
			{
				if(numbers[i] <= numbers[i - 1])
				{
					throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
				}
			}
		}
	}
	
	/**
	 * 校验号码范围
	 * @param numbers
	 * @throws CoreException
	 */
	protected void checkMargin(int[] numbers)
	{
		if(numbers[numbers.length - 1] > EsfConstants.MAX || numbers[0] < EsfConstants.MIN)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
	
	/**
	 * 校验号码范围
	 * @param numbers
	 * @throws CoreException
	 */
	protected void checkMargin(int[] numbers, int max, int min)
	{
		if(numbers[numbers.length - 1] > max || numbers[0] < min)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
	
	/**
	 * 校验号码范围
	 * @param numbers
	 * @throws CoreException
	 */
	protected void checkMargin(int number)
	{
		if(number > EsfConstants.MAX || number < EsfConstants.MIN)
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
