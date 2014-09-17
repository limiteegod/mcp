package com.mcp.fsd.check;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.model.ts.TTicket;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FsdCheckTest {
	
	private String drawNumber = "1|7|7";
	
	private String gameCode = "F02";
	
	private PrizeDescription pd;

	@Before
	public void initPD() throws Exception
	{
		FsdCheckContext.getInstance();
		pd = new PrizeDescription();

        /**
         * 一等奖
         */
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = CoreUtil.getUUID();
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("直选");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(104000);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);


        /**
         * 二等奖
         */
        GameGrade gameGrade2 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade2.setId(gameGradeId);
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("组选3");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(34600);
        gameGrade2.setFixedBonus(true);
        gameGrade2.setStatus(1);


        /**
         * 三等奖
         */
        GameGrade gameGrade3 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade3.setId(gameGradeId);
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("组选6");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(17300);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);

        ObjectMapper om = new ObjectMapper();
        List<GameGrade> gradeList = new ArrayList<GameGrade>();
        gradeList.add(gameGrade1);
        gradeList.add(gameGrade2);
        gradeList.add(gameGrade3);
        pd.setGrades(gradeList);

        System.out.println(om.writeValueAsString(this.pd));
	}
	
	/**
	 * 校验算奖算法的正确性
	 * @param number
	 * @param playType
	 * @param betType
	 */
	public CheckParam checkTest(String number, String playType, String betType) throws Exception
	{
		Check check = FsdCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
		TTicket ticket = new TTicket();
		ticket.setNumbers(number);
		CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
		return cp;
	}
	
	@Test
	public void testValidator() throws Exception
	{
	}
	
	@Test
	public void testCheck() throws Exception
	{
        //4|9|5|0|4|6|3
        String numbers = "1|7|7";
        CheckParam cp = checkTest(numbers, "01", "00");
        assertEquals(104000, cp.getBonus());
	}
}
