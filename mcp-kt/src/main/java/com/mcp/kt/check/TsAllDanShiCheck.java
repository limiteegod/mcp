package com.mcp.kt.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("tsAllDanShiCheck")
public class TsAllDanShiCheck extends KtCheck {

    public static Logger log = Logger.getLogger(TsAllDanShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
        int[] draw = LotteryUtil.getIntArrayFromStrArray(number);
        MathUtil.bubbleSort(draw);

        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketArray = ticketNumber.split(LotteryUtil.FUSHI_REG_SEP);
        for(int i = 0; i < ticketArray.length; i++)
        {
            String nStr = ticketArray[i];
            int[] nIntArray = LotteryUtil.getIntArrayFromCharArray(nStr.toCharArray());
            if(LotteryUtil.getHitCountByOrder(draw, nIntArray) == 3)
            {
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIFTEENTH, 1, prizeDescription);
            }
        }
		return cp;
	}

}
