package com.mcp.esf.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.esf.common.EsfConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class EsfZsixDanTuoValidator extends EsfValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{2}(,\\d{2}){0,4}\\$\\d{2}(,\\d{2}){1,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = getCount(numbers);
		if(count < 2)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

	private int getCount(String item)
	{
		int fCount, sCount;
		String[] redArray = item.split("\\$");
		
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
		super.checkMargin(fRedBalls, EsfConstants.MAX, EsfConstants.MIN);
		int[] sRedBalls = new int[sCount];
		for(int i = 0; i < sCount; i++)
		{
			sRedBalls[i] = Integer.parseInt(sCountArray[i]);
		}
		super.checkSort(sRedBalls);
		super.checkMargin(sRedBalls, EsfConstants.MAX, EsfConstants.MIN);
		this.checkDuplicate(fRedBalls, sRedBalls);
		
		int count = MathUtil.getC(sCount, 6 - fCount);
		return count;
	}
}
