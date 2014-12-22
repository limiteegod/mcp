package com.mcp.fsd.check;

import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;

/**
 * Created by CH on 2014/12/22.
 */

public class FsdZuXuanDanShiCheck extends  FsdCheck {
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
        String[] ticketArray = ticketNumber.split(";");
        for (String items : ticketArray){
            String[] numArray = items.split("\\|");
            int[] intTicketNumberArray = new int[]{Integer.parseInt(numArray[0]), Integer.parseInt(numArray[1]), Integer.parseInt(numArray[2])};
            if(intNumber[0] == intTicketNumberArray[0] && intNumber[1] == intTicketNumberArray[1] && intNumber[2] == intTicketNumberArray[2])
            {
                if(FsdConstants.getNubmerType(intNumber) == FsdConstants.NUMBER_TYPE_ZUSAN)
                {
                    super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
                }
                else
                {
                    super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
                }
            }

        }
        return cp;
    }
}
