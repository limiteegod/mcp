/**
 * 
 */
package com.mcp.plw.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.Validator;
import com.mcp.plw.common.PlwConstants;

/**
 * @author ming.li
 *
 */
public abstract class PlwValidator implements Validator {
	
	protected int getCount(String number)
	{
		String[] nArray = number.split("\\|");
		int count = 1;
		for(int i = 0; i < nArray.length; i++)
		{
			String[] detailNumberStrArray = nArray[i].split(",");
			int[] detailNumberArray = new int[detailNumberStrArray.length];
			for(int j = 0; j < detailNumberStrArray.length; j++)
			{
				detailNumberArray[j] = Integer.parseInt(detailNumberStrArray[j]);
			}
			checkSort(detailNumberArray);
			checkMargin(detailNumberArray);
			count *= detailNumberArray.length;
		}
		return count;
	}
	
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
		if(numbers[numbers.length - 1] > PlwConstants.MAX || numbers[0] < PlwConstants.MIN)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
	}
}
