package com.mcp.ssq.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class SsqDanTuoValidator extends SsqValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		if(items.length != 1)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		
		String item = items[0];
		if(item.length() == 0)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		String[] splitArray = item.split("\\$");
		if(splitArray.length < 2 || splitArray.length > 3)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
		}
		String reg;
		//1，前驱担拖
		if(splitArray.length == 2)
		{
			reg = "^\\d{2}(,\\d{2}){0,4}\\$\\d{2}(,\\d{2}){1,}\\|\\d{2}(,\\d{2}){0,}$";
		}
		else	//没有前后驱胆拖
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
		}
		if(!item.matches(reg))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
		}
		SsqCountDetail cd = super.getCount(item);
		if(cd.getRedCount() <= 1)	//前驱担拖情况下，前驱注数必须大于1
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
		}
		return cd.getCount();
	}

}
