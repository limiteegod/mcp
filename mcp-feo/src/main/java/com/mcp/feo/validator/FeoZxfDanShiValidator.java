package com.mcp.feo.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by CH on 2014/10/27.
 */
public class FeoZxfDanShiValidator extends FeoValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        String regex = "^[1-8](,[1-8]){3}(\\;[1-8](,[1-8]){3})*$";
        if (!numbers.matches(regex)){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] numberArray = numbers.split(LotteryUtil.TICKET_SEP);
        return numberArray.length;
    }
}
