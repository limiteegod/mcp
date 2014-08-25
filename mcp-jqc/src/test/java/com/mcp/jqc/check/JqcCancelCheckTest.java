package com.mcp.jqc.check;


import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.model.ts.TTicket;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class JqcCancelCheckTest {
	
	private String drawNumber = "3|*|0|3|1|0|0|1";
	
	private String gameCode = "T54";
	
	private PrizeDescription pd;

	@Before
	public void initPD()
	{
		JqcCheckContext.getInstance();
		pd = new PrizeDescription();
		
		/**
         * 4场全中
         */
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = UUID.randomUUID().toString().replace("-", "");
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("一等奖");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(4000000);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);
        
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		
		pd.setGrades(gradeList);
	}
	
	/**
	 * 校验算奖算法的正确性
	 * @param number
	 * @param playType
	 * @param betType
	 */
	public CheckParam checkTest(String number, String playType, String betType) throws Exception
	{
		Check check = JqcCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
		TTicket ticket = new TTicket();
		ticket.setNumbers(number);
		CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
		return cp;
	}
	
	@Test
	public void testCheck() throws Exception
	{
		//单式
		String numbers = "3|1|0|3|1|0|0|1";
		CheckParam cp = checkTest(numbers, "01", "00");
		assertEquals(4000000, cp.getBonus());
		
		//单式，未中
		numbers = "3|1|0|3|1|0|0|2";
		cp = checkTest(numbers, "01", "00");
		assertEquals(0, cp.getBonus());
		
		//复式
		numbers = "3|1|0,2|3,1|1|0|0|1";
		cp = checkTest(numbers, "01", "01");
		assertEquals(4000000, cp.getBonus());
		
		//场次取消的复式
		numbers = "3|1,2|0,2|3,1|1|0|0|1";
		cp = checkTest(numbers, "01", "01");
		assertEquals(2*4000000, cp.getBonus());
	}
}
