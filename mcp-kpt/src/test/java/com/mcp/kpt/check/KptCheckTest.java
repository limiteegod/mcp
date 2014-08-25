package com.mcp.kpt.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class KptCheckTest {

    public static Logger log = Logger.getLogger(KptCheckTest.class);
	
	private String drawNumber = "1,2,3";
	
	private String gameCode = "T07";
	
	private PrizeDescription pd;

	@Before
	public void initPD()
	{
        KptCheckContext.getInstance();
		pd = new PrizeDescription();

        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = UUID.randomUUID().toString().replace("-", "");
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("同花包选");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(2200);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);

        GameGrade gameGrade2 = new GameGrade();
        gameGrade2.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade2.setGameCode(gameCode);
        gameGrade2.setCode("LV2");
        gameGrade2.setName("同花单选");
        gameGrade2.setgLevel(Constants.GRADE_LEVEL_SECOND);
        gameGrade2.setBonus(9000);
        gameGrade2.setFixedBonus(true);
        gameGrade2.setStatus(1);

        GameGrade gameGrade3 = new GameGrade();
        gameGrade3.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade3.setGameCode(gameCode);
        gameGrade3.setCode("LV3");
        gameGrade3.setName("同花顺包选");
        gameGrade3.setgLevel(Constants.GRADE_LEVEL_THIRD);
        gameGrade3.setBonus(53500);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);

        GameGrade gameGrade4 = new GameGrade();
        gameGrade4.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade4.setGameCode(gameCode);
        gameGrade4.setCode("LV4");
        gameGrade4.setName("同花顺单选");
        gameGrade4.setgLevel(Constants.GRADE_LEVEL_FOURTH);
        gameGrade4.setBonus(215000);
        gameGrade4.setFixedBonus(true);
        gameGrade4.setStatus(1);


        GameGrade gameGrade5 = new GameGrade();
        gameGrade5.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade5.setGameCode(gameCode);
        gameGrade5.setCode("LV5");
        gameGrade5.setName("顺子包选");
        gameGrade5.setgLevel(Constants.GRADE_LEVEL_FIFTH);
        gameGrade5.setBonus(3300);
        gameGrade5.setFixedBonus(true);
        gameGrade5.setStatus(1);

        GameGrade gameGrade6 = new GameGrade();
        gameGrade6.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade6.setGameCode(gameCode);
        gameGrade6.setCode("LV6");
        gameGrade6.setName("顺子单选");
        gameGrade6.setgLevel(Constants.GRADE_LEVEL_SIXTH);
        gameGrade6.setBonus(40000);
        gameGrade6.setFixedBonus(true);
        gameGrade6.setStatus(1);

        GameGrade gameGrade7 = new GameGrade();
        gameGrade7.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade7.setGameCode(gameCode);
        gameGrade7.setCode("LV7");
        gameGrade7.setName("豹子包选");
        gameGrade7.setgLevel(Constants.GRADE_LEVEL_SEVENTH);
        gameGrade7.setBonus(50000);
        gameGrade7.setFixedBonus(true);
        gameGrade7.setStatus(1);

        GameGrade gameGrade8 = new GameGrade();
        gameGrade8.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade8.setGameCode(gameCode);
        gameGrade8.setCode("LV8");
        gameGrade8.setName("豹子单选");
        gameGrade8.setgLevel(Constants.GRADE_LEVEL_EIGHTH);
        gameGrade8.setBonus(640000);
        gameGrade8.setFixedBonus(true);
        gameGrade8.setStatus(1);

        GameGrade gameGrade9 = new GameGrade();
        gameGrade9.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade9.setGameCode(gameCode);
        gameGrade9.setCode("LV9");
        gameGrade9.setName("对子包选");
        gameGrade9.setgLevel(Constants.GRADE_LEVEL_NINTH);
        gameGrade9.setBonus(700);
        gameGrade9.setFixedBonus(true);
        gameGrade9.setStatus(1);

        GameGrade gameGrade10 = new GameGrade();
        gameGrade10.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade10.setGameCode(gameCode);
        gameGrade10.setCode("LV10");
        gameGrade10.setName("对子单选");
        gameGrade10.setgLevel(Constants.GRADE_LEVEL_TENTH);
        gameGrade10.setBonus(8800);
        gameGrade10.setFixedBonus(true);
        gameGrade10.setStatus(1);

        GameGrade gameGrade11 = new GameGrade();
        gameGrade11.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade11.setGameCode(gameCode);
        gameGrade11.setCode("LV11");
        gameGrade11.setName("任选一");
        gameGrade11.setgLevel(Constants.GRADE_LEVEL_ELEVENTH);
        gameGrade11.setBonus(500);
        gameGrade11.setFixedBonus(true);
        gameGrade11.setStatus(1);

        GameGrade gameGrade12 = new GameGrade();
        gameGrade12.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade12.setGameCode(gameCode);
        gameGrade12.setCode("LV12");
        gameGrade12.setName("任选二");
        gameGrade12.setgLevel(Constants.GRADE_LEVEL_TWELFTH);
        gameGrade12.setBonus(3300);
        gameGrade12.setFixedBonus(true);
        gameGrade12.setStatus(1);

        GameGrade gameGrade13 = new GameGrade();
        gameGrade13.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade13.setGameCode(gameCode);
        gameGrade13.setCode("LV13");
        gameGrade13.setName("任选三");
        gameGrade13.setgLevel(Constants.GRADE_LEVEL_THIRTEENTH);
        gameGrade13.setBonus(11600);
        gameGrade13.setFixedBonus(true);
        gameGrade13.setStatus(1);

        GameGrade gameGrade14 = new GameGrade();
        gameGrade14.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade14.setGameCode(gameCode);
        gameGrade14.setCode("LV14");
        gameGrade14.setName("任选四");
        gameGrade14.setgLevel(Constants.GRADE_LEVEL_FOURTEENTH);
        gameGrade14.setBonus(4600);
        gameGrade14.setFixedBonus(true);
        gameGrade14.setStatus(1);

        GameGrade gameGrade15 = new GameGrade();
        gameGrade15.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade15.setGameCode(gameCode);
        gameGrade15.setCode("LV15");
        gameGrade15.setName("任选五");
        gameGrade15.setgLevel(Constants.GRADE_LEVEL_FIFTEENTH);
        gameGrade15.setBonus(2200);
        gameGrade15.setFixedBonus(true);
        gameGrade15.setStatus(1);

        GameGrade gameGrade16 = new GameGrade();
        gameGrade16.setId(UUID.randomUUID().toString().replace("-", ""));
        gameGrade16.setGameCode(gameCode);
        gameGrade16.setCode("LV16");
        gameGrade16.setName("任选六");
        gameGrade16.setgLevel(Constants.GRADE_LEVEL_SIXTEENTH);
        gameGrade16.setBonus(1200);
        gameGrade16.setFixedBonus(true);
        gameGrade16.setStatus(1);
        
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
        Check check = KptCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
        TTicket ticket = new TTicket();
        ticket.setNumbers(number);
        CheckParam cp = check.check(ticket, drawNumber.split(",|\\|"), pd);
        return cp;
    }
	
	@Test
	public void testValidator() throws Exception
	{
		String numbers = "00";
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "01" + "00");
		log.info(bt.getValidator().validator(numbers));

        numbers = "01";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "02" + "00");
        bt.getValidator().validator(numbers);

        numbers = "00";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "03" + "00");
        log.info(bt.getValidator().validator(numbers));

        numbers = "01";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "04" + "00");
        log.info(bt.getValidator().validator(numbers));

        numbers = "00";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "05" + "00");
        log.info(bt.getValidator().validator(numbers));

        numbers = "01";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "06" + "00");
        bt.getValidator().validator(numbers);

        numbers = "00";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "07" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "08" + "00");
        bt.getValidator().validator(numbers);

        numbers = "00";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "09" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "10" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "11" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "12" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "12" + "01");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "13" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "13" + "01");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "14" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04,05";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "14" + "01");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04,05";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "15" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04,05,06";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "15" + "01");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04,05,06";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "16" + "00");
        bt.getValidator().validator(numbers);

        numbers = "01,02,03,04,05,06,07";
        bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + "16" + "01");
        bt.getValidator().validator(numbers);
	}
	
	@Test
	public void testCheck() throws Exception
	{
		//同花包选
		String numbers = "00";
		CheckParam cp = checkTest(numbers, "01", "00", "101,102,113");
		assertEquals(2200, cp.getBonus());

        //同花单选
        numbers = "01";
        cp = checkTest(numbers, "02", "00", "101,102,103");
        assertEquals(9000, cp.getBonus());

        //同花顺包选
        numbers = "00";
        cp = checkTest(numbers, "03", "00", "101,102,103");
        assertEquals(53500, cp.getBonus());

        //同花顺单选
        numbers = "01";
        cp = checkTest(numbers, "04", "00", "101,102,103");
        assertEquals(215000, cp.getBonus());

        //顺子包选
        numbers = "00";
        cp = checkTest(numbers, "05", "00", "101,202,303");
        assertEquals(3300, cp.getBonus());

        //顺子单选
        numbers = "01";
        cp = checkTest(numbers, "06", "00", "101,202,303");
        assertEquals(40000, cp.getBonus());

        //豹子包选
        numbers = "00";
        cp = checkTest(numbers, "07", "00", "101,201,301");
        assertEquals(50000, cp.getBonus());

        //豹子单选
        numbers = "01";
        cp = checkTest(numbers, "08", "00", "101,201,301");
        assertEquals(640000, cp.getBonus());

        //对子包选
        numbers = "00";
        cp = checkTest(numbers, "09", "00", "101,201,302");
        assertEquals(700, cp.getBonus());

        //对子单选
        numbers = "01";
        cp = checkTest(numbers, "10", "00", "101,201,302");
        assertEquals(8800, cp.getBonus());

        //任一
        numbers = "01";
        cp = checkTest(numbers, "11", "00", "101,201,302");
        assertEquals(500, cp.getBonus());

        //任二
        numbers = "01,02";
        cp = checkTest(numbers, "12", "00", "101,202,303");
        assertEquals(3300, cp.getBonus());

        //任二复式
        numbers = "01,02,03";
        cp = checkTest(numbers, "12", "01", "101,202,303");
        assertEquals(3*3300, cp.getBonus());

        //任三
        numbers = "01,02,03";
        cp = checkTest(numbers, "13", "00", "101,202,303");
        assertEquals(11600, cp.getBonus());

        //任三复式
        numbers = "01,02,03,04";
        cp = checkTest(numbers, "13", "01", "101,202,303");
        assertEquals(11600, cp.getBonus());

        //任四
        numbers = "01,02,03,04";
        cp = checkTest(numbers, "14", "00", "101,202,303");
        assertEquals(4600, cp.getBonus());

        //任四复式
        numbers = "01,02,03,04,05";
        cp = checkTest(numbers, "14", "01", "101,202,303");
        assertEquals(4600, cp.getBonus());

        //任五
        numbers = "01,02,03,04,05";
        cp = checkTest(numbers, "15", "00", "101,202,303");
        assertEquals(2200, cp.getBonus());

        //任五复式
        numbers = "01,02,03,04,05,06";
        cp = checkTest(numbers, "15", "01", "101,202,303");
        assertEquals(2200, cp.getBonus());

        //任六
        numbers = "01,02,03,04,05,06";
        cp = checkTest(numbers, "16", "00", "101,202,303");
        assertEquals(1200, cp.getBonus());

        //任六复式
        numbers = "01,02,03,04,05,06,07";
        cp = checkTest(numbers, "16", "01", "101,202,303");
        assertEquals(1200, cp.getBonus());
    }
}
