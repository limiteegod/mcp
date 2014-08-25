package com.mcp.kpt.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import org.apache.log4j.Logger;


public class SzOneValidator extends KptValidator {

    public static Logger log = Logger.getLogger(SzOneValidator.class);


    @Override
    public int validator(String numbers) throws CoreException {
        if(!numbers.matches("^\\d{2}$"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        int num = Integer.parseInt(numbers);
        if(num < 1 || num > 12)
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return 1;
    }

}
