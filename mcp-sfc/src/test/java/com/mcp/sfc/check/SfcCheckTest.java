package com.mcp.sfc.check;


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

import static org.junit.Assert.*;

public class SfcCheckTest {
	
	private String drawNumber = "3|1|0|3|1|0|0|1|3|1|3|3|1|1";
	
	private String gameCode = "T53";
	
	private PrizeDescription pd;

	@Before
	public void initPD() throws Exception
	{
		SfcCheckContext.getInstance();
		pd = new PrizeDescription();
		/**
         * 14场全中
         */
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = UUID.randomUUID().toString().replace("-", "");
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("14场一等奖");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(4000000);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);
        
        /**
         * 中13场
         */
        GameGrade gameGrade2 = new GameGrade();
        gameGrade2.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("14场二等奖");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(40000);
        gameGrade2.setFixedBonus(true);
        gameGrade2.setStatus(1);
        
        /**
         * 任9场全中
         */
        GameGrade gameGrade3 = new GameGrade();
        gameGrade3.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("任9场一等奖");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(400);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);
        
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		
		pd.setGrades(gradeList);
        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(pd));
	}
	
	/**
	 * 校验算奖算法的正确性
	 * @param number
	 * @param gameCode
	 * @param playType
	 * @param betType
	 */
	public CheckParam checkTest(String number, String playType, String betType) throws Exception
	{
		Check check = SfcCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
		TTicket ticket = new TTicket();
		ticket.setNumbers(number);
		CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
		return cp;
	}
	
	/**
	 * 投注校验算法测试
	 * @param playType
	 * @param betType
	 * @param numbers
	 * @return
	 * @throws Excepton
	 */
	public int validatorTest(String playType, String betType, String numbers) throws Exception
	{
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		return bt.getValidator().validator(numbers);
	}
	
	@Test
	public void testValidator() throws Exception
	{
		//14场单式
		String playType = "01";
		String betType = "00";
		String numbers = "3|0|0|0|0|0|0|0|0|0|0|0|0|0";
		assertEquals(validatorTest(playType, betType, numbers), 1);
		
		numbers = "3|0|0|0|0|0|0|0|1|0|0|0|0|1";
		assertEquals(validatorTest(playType, betType, numbers), 1);
		
		//14场复式
		playType = "01";
		betType = "01";
		numbers = "3,1|0|0|0|0|0|0,1|0|0,1,3|0|0|0|0|0";
		assertEquals(validatorTest(playType, betType, numbers), 12);
		
		numbers = "3,1|0|0|0|0|0|0,1|0|0,1,3|0|0,3|0|0|0";
		assertEquals(validatorTest(playType, betType, numbers), 24);
		
		//任9单式
		playType = "02";
		betType = "00";
		numbers = "1|0|0|0|0|0|1|0|3|_|_|_|_|_";
		assertEquals(validatorTest(playType, betType, numbers), 1);
		
		//任9复式
		playType = "02";
		betType = "01";
		numbers = "1,3|0,1,3|0|0|0|0|1|_|3|_|_|_|_|0";
		assertEquals(validatorTest(playType, betType, numbers), 6);
	}
	
	@Test
	public void testCheck() throws Exception
	{
		//14场单式，中14场
		String numbers = "3|1|0|3|1|0|0|1|3|1|3|3|1|1";
		CheckParam cp = checkTest(numbers, "01", "00");
		assertEquals(4000000, cp.getBonusBeforeTax());
		
		//中13场
		numbers = "3|1|0|3|1|0|0|1|3|1|3|3|1|0";
		cp = checkTest(numbers, "01", "00");
		assertEquals(40000, cp.getBonusBeforeTax());
		
		//复式，中一个14场，一个13场
		numbers = "3|1|0|3|1|0|0|1|3|1|3|3|1|0,1";
		cp = checkTest(numbers, "01", "01");
		assertEquals(4040000, cp.getBonusBeforeTax());
		
		//复式，中一个14场，3个13场
		numbers = "3|1|0|3|1|0|0|1|3|1|3|3|1,0,3|0,1";
		cp = checkTest(numbers, "01", "01");
		assertEquals(4120000, cp.getBonusBeforeTax());
		
		//任9场，单式
		numbers = "3|1|0|_|_|0|0|1|3|1|3|_|_|_";
		cp = checkTest(numbers, "02", "00");
		assertEquals(400, cp.getBonusBeforeTax());
		
		numbers = "3|1|0|_|_|0|0|1|1|1|3|_|_|_";
		cp = checkTest(numbers, "02", "00");
		assertEquals(0, cp.getBonusBeforeTax());
		
		numbers = "3|1|0|_|_|0,1,3|0|1|3|1|3|_|_|_";
		cp = checkTest(numbers, "02", "01");
		assertEquals(400, cp.getBonusBeforeTax());
	}
}
