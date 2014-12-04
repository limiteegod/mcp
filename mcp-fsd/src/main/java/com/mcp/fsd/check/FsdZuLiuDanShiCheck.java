package com.mcp.fsd.check;

import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

import java.util.Map;

public class FsdZuLiuDanShiCheck extends FsdCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int type = FsdConstants.getNubmerType(intNumber);
		if(type == FsdConstants.NUMBER_TYPE_ZULIU)
        {
            String[] items = ticketNumber.split(";");
            for(int i = 0; i < items.length; i++)
            {
                String item = items[i];
                int[] itemIntArray = LotteryUtil.getIntArrayFromStrArray(item.split(LotteryUtil.FUSHI_SEP));
                int hitCount = LotteryUtil.getHitCount(intNumber, itemIntArray);
                if(hitCount == 3)
                {
                    super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
                }
            }
		}
		return cp;
	}

}
