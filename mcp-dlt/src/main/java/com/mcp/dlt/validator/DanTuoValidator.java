package com.mcp.dlt.validator;

import com.mcp.dlt.common.DltConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryDanTuoUtil;
import com.mcp.order.util.LotteryFuShiUtil;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;

public class DanTuoValidator extends LotValidator {
	
	private static Logger log = Logger.getLogger(DanTuoValidator.class);

	@Override
	public int validator(String numbers) throws CoreException {
		log.info(numbers);
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
		log.info(splitArray.length);
		if(splitArray.length < 2 || splitArray.length > 3)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
		}
		String reg;
		int type = 1;	//1，前驱担拖，2，后驱担拖，3，前后驱胆拖
		if(splitArray.length == 2)
		{
			if(splitArray[0].contains("|"))	//后驱担拖
			{
				type = 2;
				reg = "^\\d{2}(,\\d{2}){4,}\\|\\d{2}\\$\\d{2}(,\\d{2}){1,}$";
			}
			else	//前驱担拖
			{
				reg = "^\\d{2}(,\\d{2}){0,3}\\$\\d{2}(,\\d{2}){1,}\\|\\d{2}(,\\d{2}){1,}$";
			}
		}
		else	//前后驱胆拖
		{
			type = 3;
			reg = "^\\d{2}(,\\d{2}){0,3}\\$\\d{2}(,\\d{2}){1,}\\|\\d{2}\\$\\d{2}(,\\d{2}){1,}$";
		}
		if(!item.matches(reg))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
		}
		int redCount, blueCount;
		String[] itemNumberArray = item.split(LotteryUtil.POSITION_REG_SEP);
		String redNumber = itemNumberArray[0];
		String blueNumber = itemNumberArray[1];
		if(type == 1)
		{
			redCount = LotteryDanTuoUtil.checkDanTuoNumberAndGetCount(redNumber, DltConstants.MAX_RED, DltConstants.MIN_RED, DltConstants.DANSHI_RED_LEN);
			if(redCount < 2)	//胆拖注数必须大于1
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			blueCount = LotteryFuShiUtil.checkFuShiNumberAndGetCount(blueNumber, DltConstants.MAX_BLUE, DltConstants.MIN_BLUE, DltConstants.DANSHI_BLUE_LEN);
		}
		else if(type == 2)
		{
			redCount = LotteryFuShiUtil.checkFuShiNumberAndGetCount(redNumber, DltConstants.MAX_RED, DltConstants.MIN_RED, DltConstants.DANSHI_RED_LEN);
			blueCount = LotteryDanTuoUtil.checkDanTuoNumberAndGetCount(blueNumber, DltConstants.MAX_BLUE, DltConstants.MIN_BLUE, DltConstants.DANSHI_BLUE_LEN);
			if(blueCount < 2)	//胆拖注数必须大于1
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
		}
		else
		{
			redCount = LotteryDanTuoUtil.checkDanTuoNumberAndGetCount(redNumber, DltConstants.MAX_RED, DltConstants.MIN_RED, DltConstants.DANSHI_RED_LEN);
			if(redCount < 2)	//胆拖注数必须大于1
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			blueCount = LotteryDanTuoUtil.checkDanTuoNumberAndGetCount(blueNumber, DltConstants.MAX_BLUE, DltConstants.MIN_BLUE, DltConstants.DANSHI_BLUE_LEN);
			if(blueCount < 2)	//胆拖注数必须大于1
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
		}
		return redCount*blueCount;
	}

}
