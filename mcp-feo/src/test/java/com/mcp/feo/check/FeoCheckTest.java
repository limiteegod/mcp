package com.mcp.feo.check;

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

public class FeoCheckTest {
	
	private String drawNumber = "1|2|3|4";
	
	private String gameCode = "T06";
	
	private PrizeDescription pd;

	@Before
	public void initPD() throws Exception
	{
		FeoCheckContext.getInstance();
		pd = new PrizeDescription();
		
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = UUID.randomUUID().toString().replace("-", "");
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("组选24");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(19700);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);
        
        GameGrade gameGrade2 = new GameGrade();
        gameGrade2.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("组选12");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(39500);
        gameGrade2.setFixedBonus(true);
        gameGrade2.setStatus(1);
        
        GameGrade gameGrade3 = new GameGrade();
        gameGrade3.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("组选6");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(79100);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);
        
        GameGrade gameGrade4 = new GameGrade();
        gameGrade4.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade4.setGameCode(gameCode);
        gameGrade4.setCode("LV4");
        gameGrade4.setName("组选4");
        gameGrade4.setgLevel(Constants.GRADE_LEVEL_FOURTH);
        gameGrade4.setBonus(118700);
        gameGrade4.setFixedBonus(true);
        gameGrade4.setStatus(1);
        
        GameGrade gameGrade5 = new GameGrade();
        gameGrade5.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade5.setGameCode(gameCode);
        gameGrade5.setCode("LV5");
        gameGrade5.setName("任选一");
        gameGrade5.setgLevel(Constants.GRADE_LEVEL_FIFTH);
        gameGrade5.setBonus(900);
        gameGrade5.setFixedBonus(true);
        gameGrade5.setStatus(1);
        
        GameGrade gameGrade6 = new GameGrade();
        gameGrade6.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade6.setGameCode(gameCode);
        gameGrade6.setCode("LV6");
        gameGrade6.setName("任选二");
        gameGrade6.setgLevel(Constants.GRADE_LEVEL_SIXTH);
        gameGrade6.setBonus(7400);
        gameGrade6.setFixedBonus(true);
        gameGrade6.setStatus(1);
        
        GameGrade gameGrade7 = new GameGrade();
        gameGrade7.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade7.setGameCode(gameCode);
        gameGrade7.setCode("LV7");
        gameGrade7.setName("任选三");
        gameGrade7.setgLevel(Constants.GRADE_LEVEL_SEVENTH);
        gameGrade7.setBonus(59300);
        gameGrade7.setFixedBonus(true);
        gameGrade7.setStatus(1);
        
        GameGrade gameGrade8 = new GameGrade();
        gameGrade8.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade8.setGameCode(gameCode);
        gameGrade8.setCode("LV8");
        gameGrade8.setName("任选四");
        gameGrade8.setgLevel(Constants.GRADE_LEVEL_EIGHTH);
        gameGrade8.setBonus(475100);
        gameGrade8.setFixedBonus(true);
        gameGrade8.setStatus(1);
        
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		gradeList.add(gameGrade4);
		gradeList.add(gameGrade5);
		gradeList.add(gameGrade6);
		gradeList.add(gameGrade7);
		gradeList.add(gameGrade8);
		pd.setGrades(gradeList);

        ObjectMapper om = new ObjectMapper();
        String prizeDes = om.writeValueAsString(this.pd);
        System.out.println(prizeDes);
	}
	
	/**
	 * 校验算奖算法的正确性
	 * @param number
	 * @param playType
	 * @param betType
	 */
	public CheckParam checkTest(String number, String playType, String betType) throws Exception
	{
		Check check = FeoCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
		TTicket ticket = new TTicket();
		ticket.setNumbers(number);
		CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
		return cp;
	}

    /**
     * 校验算奖算法的正确性
     * @param number
     * @param playType
     * @param betType
     */
    public CheckParam checkTest(String number, String playType, String betType, String drawNumber) throws Exception
    {
        Check check = FeoCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
        TTicket ticket = new TTicket();
        ticket.setNumbers(number);
        CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
        return cp;
    }
	
	@Test
	public void testValidator() throws Exception
	{
		String playType = "01";
		String betType = "00";
		String numbers = "1,2,3,4";
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(1, bt.getValidator().validator(numbers));
		
		numbers = "1,2,3,4;2,3,4,5";
		assertEquals(2, bt.getValidator().validator(numbers));
		
		playType = "01";
		betType = "01";
		numbers = "1,2,3,4,5";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(5, bt.getValidator().validator(numbers));
		
		playType = "01";
		betType = "02";
		numbers = "1$2,3,4,5";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(4, bt.getValidator().validator(numbers));
		
		playType = "02";
		betType = "00";
		numbers = "1,2,2,3";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(1, bt.getValidator().validator(numbers));
		
		playType = "02";
		betType = "01";
		numbers = "1,2,3,4";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(12, bt.getValidator().validator(numbers));
		
		playType = "02";
		betType = "02";
		numbers = "1$2,3,4";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(9, bt.getValidator().validator(numbers));

        playType = "02";
        betType = "08";
        numbers = "7$2,3,4,5,6";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(10, bt.getValidator().validator(numbers));
		
		playType = "03";
		betType = "00";
		numbers = "2,2,3,3";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(1, bt.getValidator().validator(numbers));
		
		playType = "03";
		betType = "01";
		numbers = "1,2,3,4";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(6, bt.getValidator().validator(numbers));
		
		playType = "03";
		betType = "02";
		numbers = "1$2,3,4";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(3, bt.getValidator().validator(numbers));
		
		playType = "04";
		betType = "00";
		numbers = "1,2,2,2";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(1, bt.getValidator().validator(numbers));
		
		playType = "04";
		betType = "01";
		numbers = "1,2,3";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(6, bt.getValidator().validator(numbers));
		
		playType = "04";
		betType = "02";
		numbers = "1$2,3";
		bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
		assertEquals(4, bt.getValidator().validator(numbers));

        playType = "04";
        betType = "08";
        numbers = "1$2,3,4,5,6,7";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(6, bt.getValidator().validator(numbers));

        playType = "05";
        betType = "00";
        numbers = "8|_|_|_";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(1, bt.getValidator().validator(numbers));

        playType = "05";
        betType = "01";
        numbers = "2,8|_|1,3|_";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(4, bt.getValidator().validator(numbers));

        playType = "06";
        betType = "00";
        numbers = "8|_|2|_";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(1, bt.getValidator().validator(numbers));

        playType = "06";
        betType = "01";
        numbers = "8|_|2,3|1";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(5, bt.getValidator().validator(numbers));

        playType = "07";
        betType = "00";
        numbers = "8|_|2|1";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(1, bt.getValidator().validator(numbers));

        playType = "06";
        betType = "09";
        numbers = "4,4";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(6, bt.getValidator().validator(numbers));

        playType = "06";
        betType = "03";
        numbers = "2,3|04,08";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(10, bt.getValidator().validator(numbers));

        playType = "06";
        betType = "06";
        numbers = "2,3|4,7";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(10, bt.getValidator().validator(numbers));


        playType = "07";
        betType = "01";
        numbers = "8|_|2,3|1";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(2, bt.getValidator().validator(numbers));

        playType = "08";
        betType = "00";
        numbers = "8|1|2|1";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(1, bt.getValidator().validator(numbers));

        playType = "08";
        betType = "01";
        numbers = "8|1,2|2,3|1";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + playType + betType);
        assertEquals(4, bt.getValidator().validator(numbers));
	}
	
	@Test
	public void testCheck() throws Exception
	{
        String numbers = "1,2,3,4;1,2,3,4";
        CheckParam cp = checkTest(numbers, "01", "00", "1,2,3,4");
        assertEquals(19700*2, cp.getBonus());

        numbers = "1,2,3,4,5";
        cp = checkTest(numbers, "01", "01", "1,2,3,4");
        assertEquals(19700, cp.getBonus());

        numbers = "1$2,3,4,5";
        cp = checkTest(numbers, "01", "02", "1,2,3,4");
        assertEquals(19700, cp.getBonus());

        numbers = "1,2,2,4";
        cp = checkTest(numbers, "02", "00", "1,2,2,4");
        assertEquals(39500, cp.getBonus());

        numbers = "1,2,2,4";
        cp = checkTest(numbers, "02", "00", "1,2,3,4");
        assertEquals(0, cp.getBonus());

        numbers = "1,2,3,4";
        cp = checkTest(numbers, "02", "01", "1,2,2,4");
        assertEquals(39500, cp.getBonus());

        numbers = "1,3,4,5";
        cp = checkTest(numbers, "02", "01", "1,2,2,4");
        assertEquals(0, cp.getBonus());

        numbers = "1$2,4,5";
        cp = checkTest(numbers, "02", "02", "1,2,2,4");
        assertEquals(39500, cp.getBonus());

        numbers = "1,3$2,4,5";
        cp = checkTest(numbers, "02", "02", "1,2,2,4");
        assertEquals(0, cp.getBonus());

        numbers = "1$2,4,5";
        cp = checkTest(numbers, "02", "08", "1,2,1,4");
        assertEquals(39500, cp.getBonus());

        numbers = "2,2,4,4";
        cp = checkTest(numbers, "03", "00", "4,2,2,4");
        assertEquals(79100, cp.getBonus());

        numbers = "2,2,4,4";
        cp = checkTest(numbers, "03", "00", "2,2,2,4");
        assertEquals(0, cp.getBonus());

        numbers = "1,2,4";
        cp = checkTest(numbers, "03", "01", "4,2,2,4");
        assertEquals(79100, cp.getBonus());

        numbers = "2$1,4";
        cp = checkTest(numbers, "03", "02", "4,2,2,4");
        assertEquals(79100, cp.getBonus());

        numbers = "2,2,2,4";
        cp = checkTest(numbers, "04", "00", "4,2,2,2");
        assertEquals(118700, cp.getBonus());

        numbers = "2,4";
        cp = checkTest(numbers, "04", "01", "4,2,2,2");
        assertEquals(118700, cp.getBonus());

        numbers = "2$4,5";
        cp = checkTest(numbers, "04", "02", "4,2,2,2");
        assertEquals(118700, cp.getBonus());

        numbers = "4$6";
        cp = checkTest(numbers, "04", "08", "6,4,4,4");
        assertEquals(118700, cp.getBonus());

        numbers = "4|_|_|_";
        cp = checkTest(numbers, "05", "00", "4,2,2,2");
        assertEquals(900, cp.getBonus());

        numbers = "4|_|1,2|_";
        cp = checkTest(numbers, "05", "01", "4,2,2,2");
        assertEquals(1800, cp.getBonus());

        numbers = "4|_|2|_;4|_|2|_";
        cp = checkTest(numbers, "06", "00", "4,2,2,2");
        assertEquals(14800, cp.getBonus());

        numbers = "4|_|1,2|1";
        cp = checkTest(numbers, "06", "01", "4,2,2,2");
        assertEquals(7400, cp.getBonus());

        numbers = "4,4";
        cp = checkTest(numbers, "06", "09", "4,4,4,4");
        assertEquals(44400, cp.getBonus());

        numbers = "2,3|04,07,15";
        cp = checkTest(numbers, "06", "03", "1,1,6,8");
        assertEquals(7400, cp.getBonus());

        numbers = "2,3|1";
        cp = checkTest(numbers, "06", "06", "1,3,4,5");
        assertEquals(7400, cp.getBonus());

        numbers = "3,1";
        cp = checkTest(numbers, "06", "09", "1,3,4,3");
        assertEquals(14800, cp.getBonus());

        numbers = "4|_|2|2;4|_|2|1";
        cp = checkTest(numbers, "07", "00", "4,2,2,2");
        assertEquals(59300, cp.getBonus());

        numbers = "4|_|1,2|2";
        cp = checkTest(numbers, "07", "01", "4,2,2,2");
        assertEquals(59300, cp.getBonus());

        numbers = "1,2,3";
        cp = checkTest(numbers, "07", "09", "4,2,2,2");
        assertEquals(59300, cp.getBonus());

        numbers = "4|2|2|2;4|1|2|1";
        cp = checkTest(numbers, "08", "00", "4,2,2,2");
        assertEquals(475100, cp.getBonus());

        numbers = "4|2|1,2|2,4";
        cp = checkTest(numbers, "08", "01", "4,2,2,2");
        assertEquals(475100, cp.getBonus());

	}
}
