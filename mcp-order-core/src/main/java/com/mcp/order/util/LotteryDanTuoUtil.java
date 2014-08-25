/**
 * 
 */
package com.mcp.order.util;

import com.mcp.core.util.MathUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

/**
 * @author ming.li
 *
 */
public class LotteryDanTuoUtil {
	
	/**
	 * 获得胆拖号码的注数
	 * @param danArray 胆码的数组
	 * @param tuoArray 拖码的数组
	 * @param len	所需号码的长度
	 * @return
	 */
	public static int getDanTuoCount(int danLen, int tuoLen, int len)
	{
		return MathUtil.getC(tuoLen, len - danLen);
	}
	
	/**
	 * 检验胆拖号码，胆码及拖码必须从小到大，且不超过边界
	 * @param danNumber
	 * @param tuoNumber
	 * @param max
	 * @param min
	 * @return
	 */
	public static void checkDanTuoNumber(int[] danNumber, int[] tuoNumber, int max, int min)
	{
		LotteryUtil.checkSortFromMinToMax(danNumber);
		LotteryUtil.checkMargin(danNumber, max, min);
		
		LotteryUtil.checkSortFromMinToMax(tuoNumber);
		LotteryUtil.checkMargin(tuoNumber, max, min);
	}
	
	/**
	 * 检验胆拖号码，胆码及拖码必须从小到大，且不超过边界
	 * @param danNumber
	 * @param tuoNumber
	 * @param max
	 * @param min
	 * @param len 所需号码的长度
	 * @return
	 */
	public static int checkDanTuoNumberAndGetCount(int[] danNumber, int[] tuoNumber, int max, int min, int len)
	{
		LotteryUtil.checkSortFromMinToMax(danNumber);
		LotteryUtil.checkMargin(danNumber, max, min);
		
		LotteryUtil.checkSortFromMinToMax(tuoNumber);
		LotteryUtil.checkMargin(tuoNumber, max, min);
		return getDanTuoCount(danNumber.length, tuoNumber.length, len);
	}
	
	/**
	 * 检验number的格式
	 * @param number
	 * @param max
	 * @param min
	 */
	public static void checkDanTuoNumber(String number, int max, int min)
	{
		String[] numArray = number.split(LotteryUtil.DANTUO_REG_SEP);
		if(numArray.length != 2)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int[] danNumber = LotteryUtil.getIntArrayFromStrArray(numArray[0].split(LotteryUtil.FUSHI_REG_SEP));
		int[] tuoNumber = LotteryUtil.getIntArrayFromStrArray(numArray[1].split(LotteryUtil.FUSHI_REG_SEP));
		checkDanTuoNumber(danNumber, tuoNumber, max, min);
	}
	
	/**
	 * 检验number的格式
	 * @param number
	 * @param max
	 * @param min
	 * @param len
	 * @return 胆拖号的注数
	 */
	public static int checkDanTuoNumberAndGetCount(String number, int max, int min, int len)
	{
		String[] numArray = number.split(LotteryUtil.DANTUO_REG_SEP);
		if(numArray.length != 2)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int[] danNumber = LotteryUtil.getIntArrayFromStrArray(numArray[0].split(LotteryUtil.FUSHI_REG_SEP));
		int[] tuoNumber = LotteryUtil.getIntArrayFromStrArray(numArray[1].split(LotteryUtil.FUSHI_REG_SEP));
		return checkDanTuoNumberAndGetCount(danNumber, tuoNumber, max, min, len);
	}
}
