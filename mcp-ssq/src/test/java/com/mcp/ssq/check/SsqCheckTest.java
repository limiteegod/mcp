package com.mcp.ssq.check;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.ResourceUtil;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SsqCheckTest {
	
	private String drawNumber = "09,14,17,18,21,25|15";
	
	private String gameCode = "F01";
	
	private PrizeDescription pd;

	@Before
	public void initPD() throws Exception
	{
		SsqCheckContext.getInstance();
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
        gameGrade1.setBonus(400000000);
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
        gameGrade2.setBonus(100000000);
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
        gameGrade3.setBonus(300000);
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
        gameGrade4.setBonus(20000);
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
        gameGrade5.setBonus(1000);
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
        
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		gradeList.add(gameGrade4);
		gradeList.add(gameGrade5);
		gradeList.add(gameGrade6);
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
		Check check = SsqCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
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
        Check check = SsqCheckContext.getInstance().getCheckByCode(gameCode + playType + betType);
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
        String fileName = "ticket.txt";
        URL url = ResourceUtil.getURL(fileName);
        FileReader fileReader = new FileReader(url.getPath());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        int count = 0;
        while((line = bufferedReader.readLine()) != null)
        {
            String[] lineArray = line.split("\t");
            String oId = lineArray[0];
            String outerId = lineArray[1];
            String tId = lineArray[2];
            String playTypeCode = lineArray[3];
            String betTypeCode = lineArray[4];
            String number = lineArray[5];

            CheckParam cp = checkTest(number, playTypeCode, betTypeCode);
            System.out.println(oId + "," + outerId + "," + cp.getBonus() + "," + cp.getBonusBeforeTax());
            count += cp.getBonus();
        }
        bufferedReader.close();

        System.out.println(count);
	}
}
