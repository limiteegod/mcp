package com.mcp.feo.check;

import com.mcp.core.util.MathUtil;
import com.mcp.feo.common.FeoConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.springframework.stereotype.Component;

/**
 * Created by liming on 14-9-2.
 */
@Component("feoRtQuanBaoCheck")
public class FeoRtQuanBaoCheck extends FeoCheck {

    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        int hitCount = FeoConstants.getRTwoNumberQuanBaoPrizeCount(ticketNumber , number);
        if(hitCount >= 1)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, hitCount, prizeDescription);
        }
        return cp;
    }
}
