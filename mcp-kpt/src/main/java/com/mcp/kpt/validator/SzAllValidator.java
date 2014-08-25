package com.mcp.kpt.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import org.apache.log4j.Logger;


public class SzAllValidator extends KptValidator {

    public static Logger log = Logger.getLogger(SzAllValidator.class);


    @Override
    public int validator(String numbers) throws CoreException {
        if(!numbers.equals("00"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return 1;
    }

}
