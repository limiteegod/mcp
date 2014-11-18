package com.mcp.dlt.check;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DltCheckTest {

    private static Logger log = Logger.getLogger(DltCheckTest.class);
	
	private String drawNumber = "11,13,20,28,35|01,05";
	
	private String gameCode = "T01";
	
	private PrizeDescription pd;

	@Before
	public void initPD() throws Exception
	{
		DltCheckContext.getInstance();
		pd = new PrizeDescription();
		
		/**
		 * 大乐透一等奖
		 */
		GameGrade gameGrade1 = new GameGrade();
		String gameGradeId = CoreUtil.getUUID();
		gameGrade1.setId(gameGradeId);
		gameGrade1.setGameCode(gameCode);
		gameGrade1.setCode("LV1");
		gameGrade1.setName("一等奖");
		gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
		gameGrade1.setBonus(400000000);
		gameGrade1.setFixedBonus(false);
		gameGrade1.setStatus(1);

		/**
		 * 大乐透二等奖
		 */
		GameGrade gameGrade2 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade2.setId(gameGradeId);
		gameGrade2.setGameCode(gameCode);
		gameGrade2.setCode("LV2");
		gameGrade2.setName("二等奖");
		gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
		gameGrade2.setBonus(200000000);
		gameGrade2.setFixedBonus(false);
		gameGrade2.setStatus(1);

		/**
		 * 大乐透三等奖
		 */
		GameGrade gameGrade3 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade3.setId(gameGradeId);
		gameGrade3.setGameCode(gameCode);
		gameGrade3.setCode("LV3");
		gameGrade3.setName("三等奖");
		gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
		gameGrade3.setBonus(100000000);
		gameGrade3.setFixedBonus(false);
		gameGrade3.setStatus(1);

		/**
		 * 大乐透四等奖
		 */
		GameGrade gameGrade4 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade4.setId(gameGradeId);
		gameGrade4.setGameCode(gameCode);
		gameGrade4.setCode("LV4");
		gameGrade4.setName("四等奖");
		gameGrade4.setgLevel(Constants.GRADE_LEVEL_FOURTH);
		gameGrade4.setBonus(20000);
		gameGrade4.setFixedBonus(true);
		gameGrade4.setStatus(1);

		/**
		 * 大乐透五等奖
		 */
		GameGrade gameGrade5 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade5.setId(gameGradeId);
		gameGrade5.setGameCode(gameCode);
		gameGrade5.setCode("LV5");
		gameGrade5.setName("五等奖");
		gameGrade5.setgLevel(Constants.GRADE_LEVEL_FIFTH);
		gameGrade5.setBonus(1000);
		gameGrade5.setFixedBonus(true);
		gameGrade5.setStatus(1);

		/**
		 * 大乐透六等奖
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

		/**
		 * 大乐透一等奖追加
		 */
		GameGrade gameGrade9 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade9.setId(gameGradeId);
		gameGrade9.setGameCode(gameCode);
		gameGrade9.setCode("LV7");
		gameGrade9.setName("一等奖追加");
		gameGrade9.setgLevel(Constants.GRADE_LEVEL_SEVENTH);
		gameGrade9.setBonus(240000000);
		gameGrade9.setFixedBonus(false);
		gameGrade9.setStatus(1);

		/**
		 * 大乐透二等奖追加
		 */
		GameGrade gameGrade10 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade10.setId(gameGradeId);
		gameGrade10.setGameCode(gameCode);
		gameGrade10.setCode("LV8");
		gameGrade10.setName("二等奖追加");
		gameGrade10.setgLevel(Constants.GRADE_LEVEL_EIGHTH);
		gameGrade10.setBonus(120000000);
		gameGrade10.setFixedBonus(false);
		gameGrade10.setStatus(1);

		/**
		 * 大乐透三等奖追加
		 */
		GameGrade gameGrade11 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade11.setId(gameGradeId);
		gameGrade11.setGameCode(gameCode);
		gameGrade11.setCode("LV9");
		gameGrade11.setName("三等奖追加");
		gameGrade11.setgLevel(Constants.GRADE_LEVEL_NINTH);
		gameGrade11.setBonus(60000000);
		gameGrade11.setFixedBonus(false);
		gameGrade11.setStatus(1);

		/**
		 * 大乐透四等奖追加
		 */
		GameGrade gameGrade12 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade12.setId(gameGradeId);
		gameGrade12.setGameCode(gameCode);
		gameGrade12.setCode("LV10");
		gameGrade12.setName("四等奖追加");
		gameGrade12.setgLevel(Constants.GRADE_LEVEL_TENTH);
		gameGrade12.setBonus(10000);
		gameGrade12.setFixedBonus(true);
		gameGrade12.setStatus(1);

		/**
		 * 大乐透五等奖追加
		 */
		GameGrade gameGrade13 = new GameGrade();
		gameGradeId = CoreUtil.getUUID();
		gameGrade13.setId(gameGradeId);
		gameGrade13.setGameCode(gameCode);
		gameGrade13.setCode("LV11");
		gameGrade13.setName("五等奖追加");
		gameGrade13.setgLevel(Constants.GRADE_LEVEL_ELEVENTH);
		gameGrade13.setBonus(500);
		gameGrade13.setFixedBonus(true);
		gameGrade13.setStatus(1);
        
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		gradeList.add(gameGrade4);
		gradeList.add(gameGrade5);
		gradeList.add(gameGrade6);
		gradeList.add(gameGrade9);
		gradeList.add(gameGrade10);
		gradeList.add(gameGrade11);
		gradeList.add(gameGrade12);
		gradeList.add(gameGrade13);
		pd.setGrades(gradeList);

        ObjectMapper om = new ObjectMapper();
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
		Check check = DltCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
		TTicket ticket = new TTicket();
		ticket.setNumbers(number);
		ticket.setPlayTypeCode(playType);
		ticket.setBetTypeCode(betType);
		CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
		return cp;
	}

    /**
     * 校验算奖算法的正确性
     * @param number
     * @param playType
     * @param betType
     */
    public CheckParam checkTest(String dNumber, String number, String playType, String betType) throws Exception
    {
        Check check = DltCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
        TTicket ticket = new TTicket();
        ticket.setNumbers(number);
        ticket.setPlayTypeCode(playType);
        ticket.setBetTypeCode(betType);
        CheckParam cp = check.check(ticket, dNumber.split(",|\\|"), pd);
        return cp;
    }
	
	@Test
	public void testValidator() throws Exception
	{
		String playType = "00";
		String betType = "01";
		String numbers = "06,08,09,13,17,21,23,28,34|01,02,03,04,05,06,07,08,09,10,11,12";
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		log.info(bt.getValidator().validator(numbers));
	}
	
	@Test
	public void testCheck() throws Exception
	{
		//任一复式
		//String numbers = "01,02,03,04$05,06,07,08,09,10,11,12,13,14,15,16|01$02,03,04,05";
		String numbers = "01,02,03,05,06,07,08,09,10,11,12,13,14,15,16|04,05$02,03,05";
		//CheckParam cp = checkTest(numbers, "00", "02");
		CheckParam cp = checkTest("01,02,03,04,05|04,05",numbers, "00", "02");
		assertEquals(6000, cp.getBonusBeforeTax());

        //复式
        numbers = "06,08,09,13,17,21,23,28,34|01,02,03,04,05,06,07,08,09,10,11,12";
        cp = checkTest("02,11,17,27,30|03,04", numbers, "00", "01");
        assertEquals(63000, cp.getBonusBeforeTax());

        //复式
        numbers = "09,13,16,20,22,27,30|01,02,03,04,05,06,07,08,09,10,11,12";
        cp = checkTest("02,11,17,27,30|03,04", numbers, "00", "01");
        assertEquals(115500, cp.getBonusBeforeTax());
	}

}
