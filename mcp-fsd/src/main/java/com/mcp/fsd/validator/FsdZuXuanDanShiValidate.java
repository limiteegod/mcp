package com.mcp.fsd.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

/**
 * Created by CH on 2014/12/22.
 */
public class FsdZuXuanDanShiValidate extends FsdValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        if(StringUtil.isEmpty(numbers))
        {
            throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
        }
        String[] numArrays = numbers.split(";");
        String reg = "^\\d(\\|\\d){2}$";
        for (String item  : numArrays ){
            item = item.replaceAll(",", "\\|");
            if (!item.matches(reg)){
                throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
            }
            String[] everyNums = item.split("\\|");
            int [] intArray = new int[everyNums.length];
            for (int i =0 ; i < everyNums.length; i ++){
                intArray[i] = Integer.parseInt(everyNums[i]);
            }
           int type =  FsdConstants.getNubmerType(intArray);
            if (type == FsdConstants.NUMBER_TYPE_BAOZI){
                throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
            }
            //this.checkSort(intArray);
            super.checkMargin(intArray, FsdConstants.MAX, FsdConstants.MIN);
        }
        return numArrays.length;
    }


    /**
     * 号码必须从小到大排列，可以相等
     * @param numbers
     * @throws CoreException
     */
    protected void checkSort(int[] numbers)
    {
        if(numbers.length > 1)
        {
            for(int i = 1; i < numbers.length; i++)
            {
                if(numbers[i] < numbers[i - 1])
                {
                    throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
                }
            }
        }
    }
}
