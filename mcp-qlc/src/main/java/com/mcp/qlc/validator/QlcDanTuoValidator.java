package com.mcp.qlc.validator;
import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class QlcDanTuoValidator extends QlcValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		String reg = "^\\d{2}(,\\d{2}){0,5}\\$\\d{2}(,\\d{2}){1,}$";
		if(!numbers.matches(reg))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = super.getCount(numbers);
		if(count < 2)	//注数必须大于1
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

}
