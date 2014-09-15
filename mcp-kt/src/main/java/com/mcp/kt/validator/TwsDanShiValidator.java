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

import java.util.Map;


public class TwsDanShiValidator extends KtValidator {

    public static Logger log = Logger.getLogger(TwsDanShiValidator.class);


    @Override
    public int validator(String numbers) throws CoreException {
        String[] items = numbers.split(LotteryUtil.TICKET_REG_SEP);
        for(int i = 0; i < items.length; i++)
        {
            if(!items[i].matches("^\\d{1}(,\\d{1}){2}$"))
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
            String[] numArray = items[i].split(LotteryUtil.FUSHI_REG_SEP);
            int[] numIntArray = LotteryUtil.getIntArrayFromStrArray(numArray);
            Map<Integer, Integer> info = LotteryUtil.getInfo(numIntArray);
            if(info.keySet().size() != 2)
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
            LotteryUtil.checkSortFromMinToMaxCanEqual(numIntArray);
            LotteryUtil.checkMargin(numIntArray, KtConstants.MAX, KtConstants.MIN);
        }
        return items.length;
    }
}
