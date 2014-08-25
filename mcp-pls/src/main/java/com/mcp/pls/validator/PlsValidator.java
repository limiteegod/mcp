/**
 * 
 */
package com.mcp.pls.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.Validator;
import com.mcp.pls.common.PlsConstants;

/**
 * @author ming.li
 *
 */
public abstract class PlsValidator implements Validator {
	
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
		if(numbers[numbers.length - 1] > PlsConstants.MAX || numbers[0] < PlsConstants.MIN)
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
		if(number > PlsConstants.MAX || number < PlsConstants.MIN)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
}
