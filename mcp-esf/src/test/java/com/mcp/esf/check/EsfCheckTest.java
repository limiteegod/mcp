package com.mcp.esf.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.model.ts.TTicket;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EsfCheckTest {
	
	private String drawNumber = "01|02|03|04|05";
	
	private String gameCode = "T05";
	
	private PrizeDescription pd;

	@Before
	public void initPD()
	{
		EsfCheckContext.getInstance();
		pd = new PrizeDescription();
		String gameCode = "T05";
		/**
         * 任选一
         */
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = UUID.randomUUID().toString().replace("-", "");
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("任选一");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(1300);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);
        
        /**
         * 任选二
         */
        GameGrade gameGrade2 = new GameGrade();
        gameGrade2.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("任选二");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(600);
        gameGrade2.setFixedBonus(true);
        gameGrade2.setStatus(1);
        
        /**
         * 任选三
         */
        GameGrade gameGrade3 = new GameGrade();
        gameGrade3.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("任选三");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(1900);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);
        
        /**
         * 任选四
         */
        GameGrade gameGrade4 = new GameGrade();
        gameGrade4.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade4.setGameCode(gameCode);
        gameGrade4.setCode("LV4");
        gameGrade4.setName("任选四");
        gameGrade4.setgLevel(Constants.GRADE_LEVEL_FOURTH);
        gameGrade4.setBonus(7800);
        gameGrade4.setFixedBonus(true);
        gameGrade4.setStatus(1);
        
        /**
         * 任选五
         */
        GameGrade gameGrade5 = new GameGrade();
        gameGrade5.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade5.setGameCode(gameCode);
        gameGrade5.setCode("LV5");
        gameGrade5.setName("任选五");
        gameGrade5.setgLevel(Constants.GRADE_LEVEL_FIFTH);
        gameGrade5.setBonus(54000);
        gameGrade5.setFixedBonus(true);
        gameGrade5.setStatus(1);
        
        /**
         * 任选六
         */
        GameGrade gameGrade6 = new GameGrade();
        gameGrade6.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade6.setGameCode(gameCode);
        gameGrade6.setCode("LV6");
        gameGrade6.setName("任选六");
        gameGrade6.setgLevel(Constants.GRADE_LEVEL_SIXTH);
        gameGrade6.setBonus(9000);
        gameGrade6.setFixedBonus(true);
        gameGrade6.setStatus(1);
        
        /**
         * 任选七
         */
        GameGrade gameGrade7 = new GameGrade();
        gameGrade7.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade7.setGameCode(gameCode);
        gameGrade7.setCode("LV7");
        gameGrade7.setName("任选七");
        gameGrade7.setgLevel(Constants.GRADE_LEVEL_SEVENTH);
        gameGrade7.setBonus(2600);
        gameGrade7.setFixedBonus(true);
        gameGrade7.setStatus(1);
        
        /**
         * 任选八
         */
        GameGrade gameGrade8 = new GameGrade();
        gameGrade8.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade8.setGameCode(gameCode);
        gameGrade8.setCode("LV8");
        gameGrade8.setName("任选八");
        gameGrade8.setgLevel(Constants.GRADE_LEVEL_EIGHTH);
        gameGrade8.setBonus(900);
        gameGrade8.setFixedBonus(true);
        gameGrade8.setStatus(1);
        
        /**
         * 前二组选
         */
        GameGrade gameGrade9 = new GameGrade();
        gameGrade9.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade9.setGameCode(gameCode);
        gameGrade9.setCode("LV9");
        gameGrade9.setName("前二组选");
        gameGrade9.setgLevel(Constants.GRADE_LEVEL_NINTH);
        gameGrade9.setBonus(6500);
        gameGrade9.setFixedBonus(true);
        gameGrade9.setStatus(1);
        
        /**
         * 前二直选
         */
        GameGrade gameGrade10 = new GameGrade();
        gameGrade10.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade10.setGameCode(gameCode);
        gameGrade10.setCode("LV10");
        gameGrade10.setName("前二直选");
        gameGrade10.setgLevel(Constants.GRADE_LEVEL_TENTH);
        gameGrade10.setBonus(13000);
        gameGrade10.setFixedBonus(true);
        gameGrade10.setStatus(1);
        
        /**
         * 前三组选
         */
        GameGrade gameGrade11 = new GameGrade();
        gameGrade11.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade11.setGameCode(gameCode);
        gameGrade11.setCode("LV11");
        gameGrade11.setName("前三组选");
        gameGrade11.setgLevel(Constants.GRADE_LEVEL_ELEVENTH);
        gameGrade11.setBonus(19500);
        gameGrade11.setFixedBonus(true);
        gameGrade11.setStatus(1);
        
        /**
         * 前三直选
         */
        GameGrade gameGrade12 = new GameGrade();
        gameGrade12.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade12.setGameCode(gameCode);
        gameGrade12.setCode("LV12");
        gameGrade12.setName("前三直选");
        gameGrade12.setgLevel(Constants.GRADE_LEVEL_TWELFTH);
        gameGrade12.setBonus(117000);
        gameGrade12.setFixedBonus(true);
        gameGrade12.setStatus(1);
        
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		gradeList.add(gameGrade4);
		gradeList.add(gameGrade5);
		gradeList.add(gameGrade6);
		gradeList.add(gameGrade7);
		gradeList.add(gameGrade8);
		gradeList.add(gameGrade9);
		gradeList.add(gameGrade10);
		gradeList.add(gameGrade11);
		gradeList.add(gameGrade12);
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
		Check check = EsfCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
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
		//任一复式
		String numbers = "01,02";
		CheckParam cp = checkTest(numbers, "21", "01");
		assertEquals(1300, cp.getBonus());
		
		numbers = "04,05";
		cp = checkTest(numbers, "21", "01");
		assertEquals(000, cp.getBonus());
		
		//任二单式
		numbers = "04,05";
		cp = checkTest(numbers, "22", "00");
		assertEquals(600, cp.getBonus());
		
		numbers = "04,05;01,02";
		cp = checkTest(numbers, "22", "00");
		assertEquals(1200, cp.getBonus());
		
		numbers = "05,06";
		cp = checkTest(numbers, "22", "00");
		assertEquals(000, cp.getBonus());
		
		//任二复式
		numbers = "04,05,06";
		cp = checkTest(numbers, "22", "01");
		assertEquals(600, cp.getBonus());
		
		numbers = "01,02,04";
		cp = checkTest(numbers, "22", "01");
		assertEquals(1800, cp.getBonus());
		
		numbers = "01,05,06";
		cp = checkTest(numbers, "22", "01");
		assertEquals(600, cp.getBonus());
		
		numbers = "05,06,07";
		cp = checkTest(numbers, "22", "01");
		assertEquals(0, cp.getBonus());
		
		//任二胆拖
		numbers = "01$05,06";
		cp = checkTest(numbers, "22", "02");
		assertEquals(600, cp.getBonus());
		
		numbers = "01$04,05";
		cp = checkTest(numbers, "22", "02");
		assertEquals(1200, cp.getBonus());
		
		numbers = "06$04,05";
		cp = checkTest(numbers, "22", "02");
		assertEquals(0, cp.getBonus());
		
		//任三单式
		numbers = "01,03,04";
		cp = checkTest(numbers, "23", "00");
		assertEquals(1900, cp.getBonus());
		
		numbers = "01,03,06";
		cp = checkTest(numbers, "23", "00");
		assertEquals(0, cp.getBonus());
		
		numbers = "01,03,04;01,03,06";
		cp = checkTest(numbers, "23", "00");
		assertEquals(1900, cp.getBonus());
		
		//任三复式
		numbers = "01,03,04,05,06";
		cp = checkTest(numbers, "23", "01");
		assertEquals(7600, cp.getBonus());
		
		numbers = "01,03,05,06";
		cp = checkTest(numbers, "23", "01");
		assertEquals(1900, cp.getBonus());
		
		numbers = "01,03,06,07";
		cp = checkTest(numbers, "23", "01");
		assertEquals(0, cp.getBonus());
		
		//任三胆拖
		numbers = "01$03,04,05,06";
		cp = checkTest(numbers, "23", "02");
		assertEquals(5700, cp.getBonus());
		
		numbers = "01$03,05,06";
		cp = checkTest(numbers, "23", "02");
		assertEquals(1900, cp.getBonus());
		
		numbers = "01$03,06,07";
		cp = checkTest(numbers, "23", "02");
		assertEquals(0, cp.getBonus());
		
		//任四单式
		numbers = "01,03,04,05";
		cp = checkTest(numbers, "24", "00");
		assertEquals(7800, cp.getBonus());
		
		numbers = "01,03,06,07";
		cp = checkTest(numbers, "24", "00");
		assertEquals(0, cp.getBonus());
		
		numbers = "01,03,04,05;01,03,06,07";
		cp = checkTest(numbers, "24", "00");
		assertEquals(7800, cp.getBonus());
		
		//任四复式
		numbers = "01,03,04,05,06,07";
		cp = checkTest(numbers, "24", "01");
		assertEquals(7800, cp.getBonus());
		
		numbers = "01,02,03,04,05,06,07";
		cp = checkTest(numbers, "24", "01");
		assertEquals(39000, cp.getBonus());
		
		//任五单式
		numbers = "01,02,03,04,05;01,02,03,04,06";
		cp = checkTest(numbers, "25", "00");
		assertEquals(54000, cp.getBonus());
		
		//任六单式
		numbers = "01,02,03,04,05,06;01,02,03,04,06,07";
		cp = checkTest(numbers, "26", "00");
		assertEquals(9000, cp.getBonus());
		
		//任七单式
		numbers = "01,02,03,04,05,06,08;01,02,03,04,06,07,08";
		cp = checkTest(numbers, "27", "00");
		assertEquals(2600, cp.getBonus());
		
		//任八单式
		numbers = "01,02,03,04,05,06,08,09;01,02,03,04,06,07,08,09";
		cp = checkTest(numbers, "28", "00");
		assertEquals(900, cp.getBonus());
		
		//前二组选单式
		numbers = "01,02;02,03;01,02";
		cp = checkTest(numbers, "29", "00");
		assertEquals(13000, cp.getBonus());
		
		//前二直选单式
		numbers = "01|02;02|03;01|02";
		cp = checkTest(numbers, "30", "00");
		assertEquals(26000, cp.getBonus());
		
		numbers = "02|01;02|03;06|07";
		cp = checkTest(numbers, "30", "00");
		assertEquals(0, cp.getBonus());
		
		//前三组选单式
		numbers = "01,02,03;02,03,04;01,02,03";
		cp = checkTest(numbers, "31", "00");
		assertEquals(39000, cp.getBonus());
		
		//前三直选单式
		numbers = "01|02|03;02|03|04;01|02|03";
		cp = checkTest(numbers, "32", "00");
		assertEquals(234000, cp.getBonus());
	}
}
