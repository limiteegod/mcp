package com.mcp.qxc.check;

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
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class QxcCheckTest {
	
	private String drawNumber = "4|9|5|0|4|6|3";
	
	private String gameCode = "T02";
	
	private PrizeDescription pd;

	@Before
	public void initPD()
	{
		QxcCheckContext.getInstance();
		pd = new PrizeDescription();
        /**
         * 一等奖
         */
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = CoreUtil.getUUID();
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("一等奖");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(-1);
        gameGrade1.setFixedBonus(false);
        gameGrade1.setStatus(1);

        /**
         * 二等奖
         */
        GameGrade gameGrade2 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade2.setId(gameGradeId);
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("二等奖");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(-1);
        gameGrade2.setFixedBonus(false);
        gameGrade2.setStatus(1);

        /**
         * 三等奖
         */
        GameGrade gameGrade3 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade3.setId(gameGradeId);
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("三等奖");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(180000);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);

        /**
         * 四等奖
         */
        GameGrade gameGrade4 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade4.setId(gameGradeId);
        gameGrade4.setGameCode(gameCode);
        gameGrade4.setCode("LV4");
        gameGrade4.setName("四等奖");
        gameGrade4.setgLevel(Constants.GRADE_LEVEL_FOURTH);
        gameGrade4.setBonus(30000);
        gameGrade4.setFixedBonus(true);
        gameGrade4.setStatus(1);

        /**
         * 五等奖
         */
        GameGrade gameGrade5 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade5.setId(gameGradeId);
        gameGrade5.setGameCode(gameCode);
        gameGrade5.setCode("LV5");
        gameGrade5.setName("五等奖");
        gameGrade5.setgLevel(Constants.GRADE_LEVEL_FIFTH);
        gameGrade5.setBonus(2000);
        gameGrade5.setFixedBonus(true);
        gameGrade5.setStatus(1);

        /**
         * 六等奖
         */
        GameGrade gameGrade6 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade6.setId(gameGradeId);
        gameGrade6.setGameCode(gameCode);
        gameGrade6.setCode("LV6");
        gameGrade6.setName("六等奖");
        gameGrade6.setgLevel(Constants.GRADE_LEVEL_SIXTH);
        gameGrade6.setBonus(500);
        gameGrade6.setFixedBonus(true);
        gameGrade6.setStatus(1);

        ObjectMapper om = new ObjectMapper();
        List<GameGrade> gradeList = new ArrayList<GameGrade>();
        gameGrade1.setBonus(400000000);
        gameGrade1.setgCount(2);
        gradeList.add(gameGrade1);
        gameGrade2.setBonus(200000000);
        gameGrade2.setgCount(20);
        gradeList.add(gameGrade2);
        gradeList.add(gameGrade3);
        gradeList.add(gameGrade4);
        gradeList.add(gameGrade5);
        gradeList.add(gameGrade6);
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
		Check check = QxcCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
		TTicket ticket = new TTicket();
		ticket.setNumbers(number);
		CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
		return cp;
	}
	
	@Test
	public void testValidator() throws Exception
	{
		String playType = "32";
		String betType = "07";
		String numbers = "01,02|02,03|03";
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		bt.getValidator().validator(numbers);
	}
	
	@Test
	public void testCheck() throws Exception
	{
		/*String numbers = "8|7|6|3|0|1|2";
		CheckParam cp = checkTest(numbers, "00", "00");
		assertEquals(500, cp.getBonus());

        numbers = "0|1|5|1|1|8|9;5|3|6|0|7|7|1;1|2|0|0|2|0|0;0|2|0|8|5|2|8;2|0|8|5|2|9|0";
        cp = checkTest(numbers, "00", "00");
        assertEquals(500, cp.getBonus());

        numbers = "0|1|5|1|1|8|9;5|3|6|0|7|7|1;1|2|0|0|2|0|0;0|2|0|8|5|2|8;2|0|8|5|2|9|0";
        cp = checkTest(numbers, "00", "00");
        assertEquals(500, cp.getBonus());

        numbers = "8|3|5|2|5|9|1";
        cp = checkTest(numbers, "00", "00");
        assertEquals(500, cp.getBonus());

        numbers = "9|3|5|6|0|7|1";
        cp = checkTest(numbers, "00", "00");
        assertEquals(500, cp.getBonus());

        numbers = "7|4|5|1|9|0|0";
        cp = checkTest(numbers, "00", "00");
        assertEquals(2000, cp.getBonus());

        numbers = "0|1|5|1|1|8|9;5|3|6|0|7|7|1;1|2|0|0|2|0|0;0|2|0|8|5|2|8;2|0|8|5|2|9|0";
        cp = checkTest(numbers, "00", "00");
        assertEquals(500, cp.getBonus());*/

        //4|9|5|0|4|6|3
        String numbers = "6|9|2|3|5|4|8;5|4|2|7|8|6|3;8|9|1|0|2|7|6;2|6|0|4|5|1|8;0|1|9|7|2|4|5";
        CheckParam cp = checkTest(numbers, "00", "00");
        assertEquals(500, cp.getBonus());
	}
}
