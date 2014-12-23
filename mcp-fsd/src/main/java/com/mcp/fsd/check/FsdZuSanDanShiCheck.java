package com.mcp.fsd.check;

import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

import java.util.Map;
import java.util.Set;

public class FsdZuSanDanShiCheck extends FsdCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
        int[] drawIntArray = LotteryUtil.getIntArrayFromStrArray(number);
        int type = FsdConstants.getNubmerType(drawIntArray);
        //先判断开奖号码是否是组三类型
        if(type == FsdConstants.NUMBER_TYPE_ZUSAN)
        {
            Map<Integer, Integer> drawInfo = LotteryUtil.getInfo(drawIntArray);
            Set<Integer> keys = drawInfo.keySet();
            String ticketNumber  = ticket.getNumbers();
            String[] items = ticketNumber.split(";");
            for(int i = 0; i < items.length; i++)
            {
                String item = items[i];
                item = item.replaceAll("\\|" ,",");
                int[] itemIntArray = LotteryUtil.getIntArrayFromStrArray(item.split(LotteryUtil.FUSHI_SEP));
                Map<Integer, Integer> itemInfo = LotteryUtil.getInfo(itemIntArray);
                boolean hit = true;
                for(Integer key:keys)
                {
                    if(itemInfo.get(key) == null || itemInfo.get(key) != drawInfo.get(key))
                    {
                        hit = false;
                        break;
                    }
                }
                if(hit)
                {
                    super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
                }
            }
        }
		return cp;
	}

}
