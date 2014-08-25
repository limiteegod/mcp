package com.mcp.jc.check;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.model.ts.TTicket;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JcCheckTest {

	private String gameCode = "T51";
	
	private PrizeDescription pd;

	@Before
	public void initPD()
	{
        this.pd = new PrizeDescription();
        this.pd.putJcDrawNumber("201406135003", "01|1;02|3;03|10;04|1;05|13");
        this.pd.putJcDrawNumber("201406135004", "01|0;02|0;03|15;04|6;05|10");
        this.pd.putJcDrawNumber("201406135005", "01|3;02|3;03|31;04|4;05|33");
        this.pd.putJcDrawNumber("201406146006", "01|3;02|3;03|30;04|3;05|33");
        this.pd.putJcDrawNumber("201406146007", "01|0;02|0;03|13;04|4;05|30");
        this.pd.putJcDrawNumber("201406146008", "01|1;02|0;03|12;04|3;05|10");
        this.pd.putJcDrawNumber("201406146009", "01|1;02|3;03|21;04|3;05|03");
        this.pd.putJcDrawNumber("201406146061", "01|0;02|0;03|01;04|1;05|00");
        this.pd.putJcDrawNumber("201406146062", "01|0;02|1;03|11;04|2;05|11");
        this.pd.putJcDrawNumber("201406146063", "01|3;02|3;03|21;04|3;05|13");
        this.pd.putJcDrawNumber("201406146064", "01|3;02|3;03|30;04|3;05|33");
        this.pd.putJcDrawNumber("201406157004", "01|1;02|3;03|21;04|3;05|03");
        this.pd.putJcDrawNumber("201406157005", "01|3;02|3;03|30;04|3;05|33");
        this.pd.putJcDrawNumber("201406157006", "01|1;02|3;03|21;04|3;05|33");
        this.pd.putJcDrawNumber("201406161003", "01|3;02|3;03|40;04|4;05|33");
        this.pd.putJcDrawNumber("201406161004", "01|3;02|1;03|00;04|0;05|11");
        this.pd.putJcDrawNumber("201406161005", "01|0;02|0;03|12;04|3;05|00");
        this.pd.putJcDrawNumber("201406172004", "01|1;02|3;03|21;04|3;05|03");
        this.pd.putJcDrawNumber("201406172005", "01|0;02|1;03|00;04|0;05|11");
        this.pd.putJcDrawNumber("201406183002", "01|1;02|0;03|23;04|5;05|10");
        this.pd.putJcDrawNumber("201406183003", "01|1;02|0;03|23;04|5;05|10");
        this.pd.putJcDrawNumber("201406183004", "01|0;02|0;03|04;04|4;05|00");
        this.pd.putJcDrawNumber("201406194002", "01|1;02|3;03|21;04|3;05|13");
        this.pd.putJcDrawNumber("201406194003", "01|3;02|3;03|21;04|3;05|33");
        this.pd.putJcDrawNumber("201406194004", "01|0;02|1;03|00;04|0;05|11");

        this.pd.putJcDrawNumber("201406216010", "01|1;02|3;03|10;04|1;05|13");
        this.pd.putJcDrawNumber("201406216011", "01|0;02|1;03|22;04|4;05|11");
        this.pd.putJcDrawNumber("201406216012", "01|3;02|3;03|10;04|1;05|33");
	}
	
	/**
	 * 校验算奖算法的正确性
	 * @param number
	 * @param playType
	 * @param betType
	 */
	public CheckParam checkTest(String number, String playType, String betType) throws Exception
	{
		Check check = JcCheckContext.getInstance().getCheckByCode(gameCode + playType + "00");
		TTicket ticket = new TTicket();
        ticket.setPlayTypeCode(playType);
        ticket.setBetTypeCode(betType);
		ticket.setNumbers(number);
        ticket.setrNumber(number);
		CheckParam cp = check.check(ticket, null, pd);
		return cp;
	}
	
	@Test
	public void testValidator() throws Exception
	{
	}
	
	@Test
	public void testCheck() throws Exception
	{
		String numbers = "01|201406135003|1@3.550;01|201406135005|1@3.150;02|201406146007|3@1.320;02|201406146009|3@2.480;01|201406157004|3@5.650;01|201406157005|3@1.880";
		CheckParam cp = checkTest(numbers, "06", "61");
		assertEquals(0, cp.getBonus());

        numbers = "02|201406183002|0@1.120;01|201406183004|3@2.210,1@3.400,0@2.620";
        cp = checkTest(numbers, "06", "21");
        assertEquals(0, cp.getBonus());
	}
}
