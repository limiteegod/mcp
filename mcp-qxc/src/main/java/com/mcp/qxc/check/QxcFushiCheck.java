package com.mcp.qxc.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.qxc.common.QxcConstants;

public class QxcFushiCheck extends QxcCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketArray = ticketNumber.split("\\|");
		String[] firstArray = ticketArray[0].split(",");
		String[] secondArray = ticketArray[1].split(",");
		String[] thirdArray = ticketArray[2].split(",");
		String[] fourthArray = ticketArray[3].split(",");
		String[] fifthArray = ticketArray[4].split(",");
		String[] sixthArray = ticketArray[5].split(",");
		String[] seventhArray = ticketArray[6].split(",");
		int first, second, third, fourth, fifth, sixth, seventh;
		for(first = 0; first < firstArray.length; first++)
		{
			for(second = 0; second < secondArray.length; second++)
			{
				for(third = 0; third < thirdArray.length; third++)
				{
					for(fourth = 0; fourth < fourthArray.length; fourth++)
					{
						for(fifth = 0; fifth < fifthArray.length; fifth++)
						{
							for(sixth = 0; sixth < sixthArray.length; sixth++)
							{
								for(seventh = 0; seventh < seventhArray.length; seventh++)
								{
									String[] ticketNumberArray = new String[]{firstArray[first], secondArray[second], thirdArray[third], fourthArray[fourth], fifthArray[fifth], sixthArray[sixth], seventhArray[seventh]};
									int maxHit = 0, hit = 0, gradeLevel = -1;;
									for(int j = 0; j < QxcConstants.DANSHI_LEN; j++)
									{
										if(ticketNumberArray[j].equals(number[j]))
										{
											hit++;
										}
										else
										{
											if(hit > maxHit)
											{
												maxHit = hit;
											}
											hit = 0;
										}
									}
									if(hit > maxHit)	//循环结束再判断一次，因为当最后一个号码匹配时，hit有可能比maxHit大
									{
										maxHit = hit;
									}
									int[][] rule = QxcConstants.CHECK_RULE;
									for(int k = rule.length - 1; k >= 0; k--)
									{
										int[] detailRule = rule[k];
										if(detailRule[0] == maxHit)
										{
											gradeLevel = detailRule[1];
											break;
										}
									}
									if(gradeLevel > 0)
									{
										super.getPrizeByLevel(cp, gradeLevel, 1, prizeDescription);
									}
								}
							}
						}
					}
				}
			}
		}
		return cp;
	}

}
