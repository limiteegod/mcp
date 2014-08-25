package com.mcp.pls.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.pls.common.PlsConstants;

public class PlsZuLiuDanTuoValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		//胆码只能为1或者2个，拖码必须两个以上。
		if(!numbers.matches("^\\d{1}(,\\d{1}){0,1}\\$\\d{1}(,\\d{1}){1,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		
		int fCount, sCount;
		String[] redArray = numbers.split("\\$");
		
		String[] fCountArray = redArray[0].split(",");
		fCount = fCountArray.length;
		String[] sCountArray = redArray[1].split(",");
		sCount = sCountArray.length;
		int[] fRedBalls = new int[fCount];
		for(int i = 0; i < fCount; i++)
		{
			fRedBalls[i] = Integer.parseInt(fCountArray[i]);
		}
		super.checkSort(fRedBalls);
		super.checkMargin(fRedBalls, PlsConstants.MAX, PlsConstants.MIN);
		int[] sRedBalls = new int[sCount];
		for(int i = 0; i < sCount; i++)
		{
			sRedBalls[i] = Integer.parseInt(sCountArray[i]);
		}
		super.checkSort(sRedBalls);
		super.checkMargin(sRedBalls, PlsConstants.MAX, PlsConstants.MIN);
		this.checkDuplicate(fRedBalls, sRedBalls);
		
		int count = MathUtil.getC(sCount, 3 - fCount);
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
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
