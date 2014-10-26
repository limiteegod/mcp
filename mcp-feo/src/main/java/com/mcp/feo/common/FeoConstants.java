/**
 * 
 */
package com.mcp.feo.common;

import com.mcp.core.util.MathUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author ming.li
 *
 */
public class FeoConstants {

	/**
	 * 最大球号
	 */
	public static final int MAX = 8;
	
	/**
	 * 最小球号
	 */
	public static final int MIN = 1;
	
	/**
	 * 组24
	 */
	public static final int NUMBER_TYPE_ZTF = 1;
	
	/**
	 * 组12
	 */
	public static final int NUMBER_TYPE_ZOT = 2;
	
	
	/**
	 * 组6
	 */
	public static final int NUMBER_TYPE_ZS = 3;
	
	/**
	 * 组4
	 */
	public static final int NUMBER_TYPE_ZF = 4;
	
	/**
	 * 获得号码的类型，组24，组12，组6，组4
	 * @param numbers
	 * @return
	 */
	public static int getNumberType(int[] numbers)
	{
		Map<Integer, Integer> info = LotteryUtil.getInfo(numbers);
		return getNumberType(info);
	}

    /**
     * 获得号码的类型，组24，组12，组6，组4
     * @param info
     * @return
     */
    public static int getNumberType(Map<Integer, Integer> info)
    {
        Set<Integer> keys = info.keySet();
        int size = keys.size();
        if(size == 4)
        {
            return NUMBER_TYPE_ZTF;
        }
        else if(size == 3)
        {
            return NUMBER_TYPE_ZOT;
        }
        else if(size == 2)
        {
            boolean hasThree = false;
            for(Integer key:keys)
            {
                if(info.get(key) == 3)
                {
                    hasThree = true;
                }
            }

            if(hasThree)
            {
                return NUMBER_TYPE_ZF;
            }
            else
            {
                return NUMBER_TYPE_ZS;
            }
        }
        return -1;
    }
	
	/**
	 * 获得任n的号码的注数
	 * @return
	 */
	public static int getRNumberCount(int[] lenArray, int n)
	{
		int all = 0;
		//先获得所有可能的组合
		int[][] cInfo = MathUtil.getDetailC(4, n);
		for(int i = 0; i < cInfo.length; i++)
		{
			int[] indexInfo = cInfo[i];
			int count = 1;
			for(int j = 0; j < indexInfo.length; j++)
			{
				count = count*lenArray[indexInfo[j]]; 
			}
			all += count;
		}
		return all;
	}

    /**
     * 获得任n的号码的注数
     * @return
     */
    public static int getRNumberCount(String[] posStrArray, int n)
    {
        int[] lenArray = new int[posStrArray.length];
        for(int i = 0; i < posStrArray.length; i++)
        {
            if(posStrArray[i].equals(LotteryUtil.BLANK_SEP))
            {
                lenArray[i] = 0;
            }
            else
            {
                lenArray[i] = posStrArray[i].split(LotteryUtil.FUSHI_REG_SEP).length;
            }
        }
        return getRNumberCount(lenArray, n);
    }
	
	/**
	 * 对任n的每一位都进行校验
	 * @param strArray
	 */
	public static void checkRNumber(String[] strArray)
	{
		for(int i = 0; i < strArray.length; i++)
		{
			if(!strArray[i].equals(LotteryUtil.BLANK_SEP))
			{
				int[] posIntArray = LotteryUtil.getIntArrayFromStrArray(strArray[i].split(LotteryUtil.FUSHI_REG_SEP));
				LotteryUtil.checkMargin(posIntArray, FeoConstants.MAX, FeoConstants.MIN);
				LotteryUtil.checkSortFromMinToMax(posIntArray);
			}
		}
	}

    /**
     *
     * @param number
     * @return
     */
    public static int getRNumberHitCount(String number, String[] dNumber)
    {
        String[] numberStrArray = number.split(LotteryUtil.POSITION_REG_SEP);
        int hitCount = 0;
        for(int i = 0; i < numberStrArray.length; i++)
        {
            if(numberStrArray[i].indexOf(dNumber[i]) > -1)
            {
                hitCount++;
            }
        }
        return hitCount;
    }

    /**
     *
     * 任选二全包 注数
     */

    public static int getRTwoNumberQuanBaoCount(int[] intPosArray) throws CoreException{
        int count = 0;
        if (intPosArray.length !=2){
            throw  new CoreException(ErrCode.E1023);
        }
        int num1 = intPosArray[0];
        int num2 = intPosArray[1];
        if (num1 == num2){
            count = 6;
        }else{
            count = 12;
        }
        return count;
    }

    /**
     * 判断任选二全包是否中奖，返回中奖注数
     */

    public static  int getRTwoNumberQuanBaoPrizeCount(String numbers, String[] dNumber){
        int count = 0;
        //获取开奖号码 每个数 出现的次数
        Map<Integer, Integer> info = LotteryUtil.getInfo(LotteryUtil.getIntArrayFromStrArray(dNumber));
        int type = getNumberType(info);
        int[] numArray = LotteryUtil.getIntArrayFromStrArray(numbers.split(LotteryUtil.FUSHI_REG_SEP));
        if (numArray[0] == numArray[1]){
            if (info.containsKey(numArray[0])){
                count = MathUtil.getC(info.get(numArray[0]), 2);
                return count;
            }
        }
        if (info.containsKey(numArray[0]) && info.containsKey(numArray[1])){
            if (type == NUMBER_TYPE_ZTF){
                count = 1;
            }else if (type == NUMBER_TYPE_ZS){
                count = 4;
            }else {
                count = getMax(info.get(numArray[0]), info.get(numArray[1]));
            }
        }else{
            count = 0;
        }
        return count;

    }

    public static int getRtHeZhiCount(int[] numberArray){
        int count = 0;
        for (int i=0; i<numberArray.length ; i++ ){
            int sum = numberArray[i];
            int middle = (sum)/2;
            for (int j = middle; j > 0 && j<= 8 ; j-- ){
                if ((sum - j)<=8 && (sum - j) >=1){
                    if (j == (sum -j)){
                        count += 1;
                    }else {
                        count += 2;
                    }
                }
            }
        }
        return count;
    }

    public static int getMax (int m, int n){
        if (m < n ){
            return n;
        }else{
            return  m;
        }
    }

    public static int getRtHeZhiHitCount(String ticketNumber, String[] number) {
        int[] swimArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.POSITION_REG_SEP)[0].split(LotteryUtil.FUSHI_REG_SEP));
        String chooseNumber = ticketNumber.split(LotteryUtil.POSITION_REG_SEP)[1];
        int hitSum = 0;
        int hitCount = 0;
        for (int i = 0; i< swimArray.length; i++ ){
            hitSum += Integer.parseInt(number[swimArray[i]-1]);
        }
        if (chooseNumber.indexOf(String.format("%1$02d",hitSum)) > -1){
            int [] hitsum = {hitSum};
            hitCount = getRtHeZhiCount(hitsum);
        }
        return hitCount;
    }
}
