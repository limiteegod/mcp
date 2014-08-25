/**
 * 
 */
package com.mcp.jc.check;

import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.jc.common.BunchMap;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * 竞彩场次取消之后，所有赔率按1计算，为了简便，当一个场次选了多个选项，比如，3,1，串关时，赔率就乘以选项的个数。
 * 取消的场次，所有玩法的开奖号码以*表示
 * @author ming.li
 */
public class DefaultJcCheck extends JcCheck {
	
	private static final Logger log = Logger.getLogger(DefaultJcCheck.class);

	/* (non-Javadoc)
	 * @see com.mcp.order.batch.check.Check#check(com.mcp.order.batch.model.TicketBatch, java.lang.String[])
	 */
	@Override
	public CheckParam check(TTicket jTicket, String[] arg1, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String number = jTicket.getrNumber();
        if(StringUtil.isEmpty(number))
        {
            log.error("无返回的赔率信息,错误的彩票id:" + jTicket.getId());
            return cp;
        }
		String[] nArray = number.split(";");
		 
		String betTypeCode = jTicket.getBetTypeCode();
		
		int m = Integer.parseInt(betTypeCode.substring(0, 1));
		int n = Integer.parseInt(betTypeCode.substring(1));
		String mxn = String.valueOf(m) + String.valueOf(n);
    	String str = BunchMap.bunchMap.get(mxn); //mxn为串关描述符号。5串26
       
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == '1') {
            	int dN = j + 1;
            	int[][] data = MathUtil.getDetailC(m, dN);
            	
            	for(int k = 0; k < data.length; k++)
            	{
            		int[] set = data[k];
            		long bonus = 200;
            		double totalMultiplier = 1;
            		for(int l = 0; l < set.length; l++)
            		{
            			String mNum = nArray[set[l]];
            			
            			String[] mNumArray = mNum.split("\\|");
            			String odds = mNumArray[2];
            			String matchCode = mNumArray[1];
            			String dPlayTypeCode = mNumArray[0];
            			
            			//直接从缓存的信息中取开奖号码
            			String dNumber = prizeDescription.getJcDrawNumber(matchCode + dPlayTypeCode);
            			String[] oddsArray = odds.split(",");
            			
            			double multiplier = 0;
            			if(dNumber.equals("*"))
            			{
            				//场次取消
            				multiplier = oddsArray.length;
            			}
            			else
            			{
            				for(int p = 0; p < oddsArray.length; p++)
                			{
                				String odd = oddsArray[p];
                				String[] oddArray = odd.split("@");
                				
                				if(oddArray[0].equals(dNumber))
                				{
                					multiplier = Double.parseDouble(oddArray[1]);
                				}
                			}
            			}
            			
            			totalMultiplier = totalMultiplier*multiplier;
            		}
            		bonus = Math.round(bonus*totalMultiplier);
                    if(bonus > 0)
                    {
                        cp.incrBonus(dN, 1, bonus);
                    }
            	}
            }
        }
        return cp;
	}

}
