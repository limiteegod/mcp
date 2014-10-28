package com.mcp.feo.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by CH on 2014/10/28.
 */
public class FeoRxDanShiValidator extends FeoValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        String[] items = numbers.split(";");
        for(int i = 0; i < items.length; i++) {
            String regex = "^[1-8|_]{1}(\\|[1-8|_]){3}$";
            String item = items[i];
            if(!item.matches("^[1-8|_]{1}(\\|[1-8|_]){3}$"))
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
            String[] posStrArray = item.split(LotteryUtil.POSITION_REG_SEP);
            int count = 0;
            for(int j = 0; j < posStrArray.length; j++)
            {
                if(!posStrArray[j].equals(LotteryUtil.BLANK_SEP))
                {
                    count++;
                }
            }
            if(count < 1)
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
        }
        return items.length;
    }
}
