package com.mcp.kt.check;

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
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class KtCheckTest {

    public static Logger log = Logger.getLogger(KtCheckTest.class);
	
	private String drawNumber = "1,2,3";
	
	private String gameCode = "F04";
	
	private PrizeDescription pd;

	@Before
	public void initPD() throws Exception
	{
        KtCheckContext.getInstance();
		pd = new PrizeDescription();

        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = UUID.randomUUID().toString().replace("-", "");
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("和值4");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(8000);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);

        GameGrade gameGrade2 = new GameGrade();
        gameGrade2.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("和值5");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(4000);
        gameGrade2.setFixedBonus(true);
        gameGrade2.setStatus(1);

        GameGrade gameGrade3 = new GameGrade();
        gameGrade3.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("和值6");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(2500);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);


        GameGrade gameGrade4 = new GameGrade();
        gameGrade4.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade4.setGameCode(gameCode);
        gameGrade4.setCode("LV4");
        gameGrade4.setName("和值7");
        gameGrade4.setgLevel(Constants.GRADE_LEVEL_FOURTH);
        gameGrade4.setBonus(1600);
        gameGrade4.setFixedBonus(true);
        gameGrade4.setStatus(1);


        GameGrade gameGrade5 = new GameGrade();
        gameGrade5.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade5.setGameCode(gameCode);
        gameGrade5.setCode("LV5");
        gameGrade5.setName("和值8");
        gameGrade5.setgLevel(Constants.GRADE_LEVEL_FIFTH);
        gameGrade5.setBonus(1200);
        gameGrade5.setFixedBonus(true);
        gameGrade5.setStatus(1);

        GameGrade gameGrade6 = new GameGrade();
        gameGrade6.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade6.setGameCode(gameCode);
        gameGrade6.setCode("LV6");
        gameGrade6.setName("和值9");
        gameGrade6.setgLevel(Constants.GRADE_LEVEL_SIXTH);
        gameGrade6.setBonus(1000);
        gameGrade6.setFixedBonus(true);
        gameGrade6.setStatus(1);

        GameGrade gameGrade7 = new GameGrade();
        gameGrade7.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade7.setGameCode(gameCode);
        gameGrade7.setCode("LV7");
        gameGrade7.setName("和值10");
        gameGrade7.setgLevel(Constants.GRADE_LEVEL_SEVENTH);
        gameGrade7.setBonus(900);
        gameGrade7.setFixedBonus(true);
        gameGrade7.setStatus(1);

        GameGrade gameGrade8 = new GameGrade();
        gameGrade8.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade8.setGameCode(gameCode);
        gameGrade8.setCode("LV8");
        gameGrade8.setName("和值11");
        gameGrade8.setgLevel(Constants.GRADE_LEVEL_EIGHTH);
        gameGrade8.setBonus(900);
        gameGrade8.setFixedBonus(true);
        gameGrade8.setStatus(1);

        GameGrade gameGrade9 = new GameGrade();
        gameGrade9.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade9.setGameCode(gameCode);
        gameGrade9.setCode("LV9");
        gameGrade9.setName("和值12");
        gameGrade9.setgLevel(Constants.GRADE_LEVEL_NINTH);
        gameGrade9.setBonus(1000);
        gameGrade9.setFixedBonus(true);
        gameGrade9.setStatus(1);

        GameGrade gameGrade10 = new GameGrade();
        gameGrade10.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade10.setGameCode(gameCode);
        gameGrade10.setCode("LV10");
        gameGrade10.setName("和值13");
        gameGrade10.setgLevel(Constants.GRADE_LEVEL_TENTH);
        gameGrade10.setBonus(1200);
        gameGrade10.setFixedBonus(true);
        gameGrade10.setStatus(1);

        GameGrade gameGrade11 = new GameGrade();
        gameGrade11.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade11.setGameCode(gameCode);
        gameGrade11.setCode("LV11");
        gameGrade11.setName("和值14");
        gameGrade11.setgLevel(Constants.GRADE_LEVEL_ELEVENTH);
        gameGrade11.setBonus(1600);
        gameGrade11.setFixedBonus(true);
        gameGrade11.setStatus(1);

        GameGrade gameGrade12 = new GameGrade();
        gameGrade12.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade12.setGameCode(gameCode);
        gameGrade12.setCode("LV12");
        gameGrade12.setName("和值15");
        gameGrade12.setgLevel(Constants.GRADE_LEVEL_TWELFTH);
        gameGrade12.setBonus(2500);
        gameGrade12.setFixedBonus(true);
        gameGrade12.setStatus(1);

        GameGrade gameGrade13 = new GameGrade();
        gameGrade13.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade13.setGameCode(gameCode);
        gameGrade13.setCode("LV13");
        gameGrade13.setName("和值16");
        gameGrade13.setgLevel(Constants.GRADE_LEVEL_THIRTEENTH);
        gameGrade13.setBonus(4000);
        gameGrade13.setFixedBonus(true);
        gameGrade13.setStatus(1);

        GameGrade gameGrade14 = new GameGrade();
        gameGrade14.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade14.setGameCode(gameCode);
        gameGrade14.setCode("LV14");
        gameGrade14.setName("和值17");
        gameGrade14.setgLevel(Constants.GRADE_LEVEL_FOURTEENTH);
        gameGrade14.setBonus(8000);
        gameGrade14.setFixedBonus(true);
        gameGrade14.setStatus(1);

        GameGrade gameGrade15 = new GameGrade();
        gameGrade15.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade15.setGameCode(gameCode);
        gameGrade15.setCode("LV15");
        gameGrade15.setName("三同号通选");
        gameGrade15.setgLevel(Constants.GRADE_LEVEL_FIFTEENTH);
        gameGrade15.setBonus(4000);
        gameGrade15.setFixedBonus(true);
        gameGrade15.setStatus(1);

        GameGrade gameGrade16 = new GameGrade();
        gameGrade16.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade16.setGameCode(gameCode);
        gameGrade16.setCode("LV16");
        gameGrade16.setName("三同号单选");
        gameGrade16.setgLevel(Constants.GRADE_LEVEL_SIXTEENTH);
        gameGrade16.setBonus(24000);
        gameGrade16.setFixedBonus(true);
        gameGrade16.setStatus(1);

        GameGrade gameGrade17 = new GameGrade();
        gameGrade17.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade17.setGameCode(gameCode);
        gameGrade17.setCode("LV17");
        gameGrade17.setName("三同号复选");
        gameGrade17.setgLevel(Constants.GRADE_LEVEL_SEVENTEENTH);
        gameGrade17.setBonus(1500);
        gameGrade17.setFixedBonus(true);
        gameGrade17.setStatus(1);

        GameGrade gameGrade18 = new GameGrade();
        gameGrade18.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade18.setGameCode(gameCode);
        gameGrade18.setCode("LV18");
        gameGrade18.setName("二同号单选");
        gameGrade18.setgLevel(Constants.GRADE_LEVEL_EIGHTEENTH);
        gameGrade18.setBonus(8000);
        gameGrade18.setFixedBonus(true);
        gameGrade18.setStatus(1);

        GameGrade gameGrade19 = new GameGrade();
        gameGrade19.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade19.setGameCode(gameCode);
        gameGrade19.setCode("LV19");
        gameGrade19.setName("三不同号单选");
        gameGrade19.setgLevel(Constants.GRADE_LEVEL_NINETEENTH);
        gameGrade19.setBonus(4000);
        gameGrade19.setFixedBonus(true);
        gameGrade19.setStatus(1);

        GameGrade gameGrade20 = new GameGrade();
        gameGrade20.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade20.setGameCode(gameCode);
        gameGrade20.setCode("LV20");
        gameGrade20.setName("二不同号复选");
        gameGrade20.setgLevel(Constants.GRADE_LEVEL_TWENTIETH);
        gameGrade20.setBonus(800);
        gameGrade20.setFixedBonus(true);
        gameGrade20.setStatus(1);

        GameGrade gameGrade21 = new GameGrade();
        gameGrade21.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade21.setGameCode(gameCode);
        gameGrade21.setCode("LV21");
        gameGrade21.setName("三连号通选");
        gameGrade21.setgLevel(Constants.GRADE_LEVEL_TWENTY_FIRST);
        gameGrade21.setBonus(1000);
        gameGrade21.setFixedBonus(true);
        gameGrade21.setStatus(1);
        
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
        gradeList.add(gameGrade13);
        gradeList.add(gameGrade14);
        gradeList.add(gameGrade15);
        gradeList.add(gameGrade16);
        gradeList.add(gameGrade17);
        gradeList.add(gameGrade18);
        gradeList.add(gameGrade19);
        gradeList.add(gameGrade20);
        gradeList.add(gameGrade21);
		pd.setGrades(gradeList);

        ObjectMapper om = new ObjectMapper();
        log.info(om.writeValueAsString(pd));
	}
	
	/**
	 * 校验算奖算法的正确性
	 * @param number
	 * @param playType
	 * @param betType
	 */
	public CheckParam checkTest(String number, String playType, String betType) throws Exception
	{
		return checkTest(number, playType, betType, this.drawNumber);
	}

    /**
     * 校验算奖算法的正确性
     * @param number
     * @param playType
     * @param betType
     */
    public CheckParam checkTest(String number, String playType, String betType, String drawNumber) throws Exception
    {
        Check check = KtCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
        TTicket ticket = new TTicket();
        ticket.setNumbers(number);
        CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
        return cp;
    }
	
	@Test
	public void testValidator() throws Exception
	{
		String numbers = "1,2,3;1,2,3";
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "01" + "00");
		log.info(bt.getValidator().validator(numbers));

        numbers = "1,1,2";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "02" + "00");
        bt.getValidator().validator(numbers);

        numbers = "1,1,1;3,3,3";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "03" + "00");
        log.info(bt.getValidator().validator(numbers));

        numbers = "4,10";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "04" + "01");
        log.info(bt.getValidator().validator(numbers));

        numbers = "12;23;34";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "05" + "01");
        log.info(bt.getValidator().validator(numbers));

        numbers = "11;22";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "06" + "01");
        bt.getValidator().validator(numbers);

        numbers = "111,222,333,444,555,666";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "07" + "00");
        bt.getValidator().validator(numbers);

        numbers = "123,234,345,456";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "08" + "00");
        bt.getValidator().validator(numbers);
	}
	
	@Test
	public void testCheck() throws Exception
	{
		//三不同
		String numbers = "1,2,3;1,2,3";
		CheckParam cp = checkTest(numbers, "01", "00", "1,2,3");
		assertEquals(8000, cp.getBonus());

        //三不同
        numbers = "1,2,3;4,5,6";
        cp = checkTest(numbers, "01", "00", "1,2,3");
        assertEquals(4000, cp.getBonus());

        //二同
		numbers = "1,1,2";
		cp = checkTest(numbers, "02", "00", "1,1,2");
		assertEquals(8000, cp.getBonus());

        //三同
        numbers = "1,1,1";
        cp = checkTest(numbers, "03", "00", "1,1,1");
        assertEquals(24000, cp.getBonus());

        //和值
        numbers = "4";
        cp = checkTest(numbers, "04", "01", "1,1,2");
        assertEquals(8000, cp.getBonus());

        //和值
        numbers = "10";
        cp = checkTest(numbers, "04", "01", "6,2,2");
        assertEquals(900, cp.getBonus());

        //二不同普通
        numbers = "12;23";
        cp = checkTest(numbers, "05", "01", "1,2,3");
        assertEquals(1600, cp.getBonus());

        //二同复选普通
        numbers = "11;22";
        cp = checkTest(numbers, "06", "01", "1,1,2");
        assertEquals(1500, cp.getBonus());

        //三同通选单式
        numbers = "111,222,333,444,555,666";
        cp = checkTest(numbers, "07", "00", "1,1,1");
        assertEquals(4000, cp.getBonus());

        //三连通选单式
        numbers = "123,234,345,456";
        cp = checkTest(numbers, "08", "00", "1,2,3");
        assertEquals(1000, cp.getBonus());
    }
}
