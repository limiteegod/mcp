package com.mcp.pls.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.pls.common.PlsConstants;

public class PlsZuLiuKuaDuValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{1}(,\\d{1}){0,7}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		String[] detailNumberStrArray = numbers.split(",");
		int[] detailNumberArray = new int[detailNumberStrArray.length];
		for(int j = 0; j < detailNumberStrArray.length; j++)
		{
			detailNumberArray[j] = Integer.parseInt(detailNumberStrArray[j]);
		}
		super.checkSort(detailNumberArray);
		super.checkMargin(detailNumberArray, PlsConstants.ZULIU_KUADU_MAX, PlsConstants.ZULIU_KUADU_MIN);
		int count = 0;
		for(int j = 0; j < detailNumberArray.length; j++)
		{
			count += PlsConstants.ZULIU_KUADU_COUNT[detailNumberArray[j]];
		}
		return count;
	}

}
