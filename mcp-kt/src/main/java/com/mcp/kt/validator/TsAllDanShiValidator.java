package com.mcp.kt.validator;

import com.mcp.kt.check.KtCheck;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;


public class TsAllDanShiValidator extends KtValidator {

    public static Logger log = Logger.getLogger(TsAllDanShiValidator.class);


    @Override
    public int validator(String numbers) throws CoreException {
        if(!numbers.equals("111,222,333,444,555,666"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return 1;
    }
}
