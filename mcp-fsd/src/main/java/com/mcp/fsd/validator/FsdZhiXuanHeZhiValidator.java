/**
 * 
 */
package com.mcp.fsd.validator;

import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

/**
 * @author ming.li
 *
 */
public class FsdZhiXuanHeZhiValidator extends FsdValidator {

	/* (non-Javadoc)
	 * @see com.mcp.core.validator.Validator#validator(java.lang.String)
	 */
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
		if(!item.matches("^\\d{1,2}(,\\d{1,2}){0,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		
		String[] detailNumberStrArray = item.split(",");
		int[] detailNumberArray = new int[detailNumberStrArray.length];
		for(int j = 0; j < detailNumberStrArray.length; j++)
		{
			detailNumberArray[j] = Integer.parseInt(detailNumberStrArray[j]);
		}
		super.checkSort(detailNumberArray);
		super.checkMargin(detailNumberArray, FsdConstants.ZHIXUAN_HEZHI_MAX, FsdConstants.ZHIXUAN_HEZHI_MIN);
		int count = 0;
		for(int j = 0; j < detailNumberArray.length; j++)
		{
			count += FsdConstants.ZHIXUAN_HEZHI_COUNT[detailNumberArray[j]];
		}
		return count;
	}

}
