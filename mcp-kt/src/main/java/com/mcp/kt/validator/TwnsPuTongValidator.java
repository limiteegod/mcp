package com.mcp.kt.validator;

import com.mcp.kt.check.KtCheck;
import com.mcp.kt.common.KtConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;


public class TwnsPuTongValidator extends KtValidator {

    public static Logger log = Logger.getLogger(TwnsPuTongValidator.class);


    @Override
    public int validator(String numbers) throws CoreException {
        String[] items = numbers.split(LotteryUtil.TICKET_REG_SEP);
        for(int i = 0; i < items.length; i++)
        {
            if(!items[i].matches("^\\d{2}$"))
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
            int[] numIntArray = LotteryUtil.getIntArrayFromCharArray(items[i].toCharArray());
            LotteryUtil.checkSortFromMinToMax(numIntArray);
            LotteryUtil.checkMargin(numIntArray, KtConstants.MAX, KtConstants.MIN);
        }
        return items.length;
    }
}
