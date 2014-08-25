package com.mcp.kt.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("hezhiPuTongCheck")
public class HezhiPuTongCheck extends KtCheck {

    public static Logger log = Logger.getLogger(HezhiPuTongCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
        int heZhi = LotteryUtil.getHeZhi(number);

		CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketArray = ticketNumber.split(LotteryUtil.FUSHI_REG_SEP);
        int[] ticketIntArray = LotteryUtil.getIntArrayFromStrArray(ticketArray);
        int hitCount = LotteryUtil.getHitCount(new int[]{heZhi}, ticketIntArray);
        if(hitCount == 1)
        {
            super.getPrizeByLevel(cp, super.getLevelByHeZhi(heZhi), 1, prizeDescription);
        }
		return cp;
	}

}
