package com.mcp.deploy.database;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.core.util.cons.OperationType;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.deploy.common.DeployContext;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.dao.finance.AccountConstants;
import com.mcp.order.model.admin.*;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.jc.JOdds;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.service.MgOddsService;
import com.mcp.order.service.*;
import com.mcp.order.service.js.JOddsService;
import com.mcp.order.status.TermState;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InitDatabase {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			Constants.DATE_FORMAT);

    /**
     * 添加管理员
     */
    public static void addAdmini() {
        AdminiService adminiService = DeployContext.getInstance().getBean("adminiService", AdminiService.class);
        Admini admini = new Admini();
        admini.setId(CoreUtil.getUUID());
        admini.setName("admini");
        admini.setPassword("0okmnhy6");
        admini.setType(SystemUserType.ADMINISTRATOR.getCode());
        adminiService.save(admini);

        OperationService operationService = DeployContext.getInstance().getBean("operationService", OperationService.class);
        UserOperationService userOperationService = DeployContext.getInstance().getBean("userOperationService", UserOperationService.class);
        Operation operation = new Operation();
        String parentId = CoreUtil.getUUID();
        operation.setId(parentId);
        operation.setName("权限管理");
        operation.setUrl("");
        operation.setType(OperationType.GROUP.getCode());
        operationService.save(operation);

        UserOperation userOperation = new UserOperation();
        userOperation.setId(CoreUtil.getUUID());
        userOperation.setOperationId(operation.getId());
        userOperation.setUserType(SystemUserType.ADMINISTRATOR.getCode());
        userOperationService.save(userOperation);

        operation = new Operation();
        operation.setId(CoreUtil.getUUID());
        operation.setName("添加项目");
        operation.setUrl("operation/addOperation.jsp");
        operation.setType(OperationType.DETAIL.getCode());
        operation.setParentId(parentId);
        operationService.save(operation);

        userOperation = new UserOperation();
        userOperation.setId(CoreUtil.getUUID());
        userOperation.setOperationId(operation.getId());
        userOperation.setUserType(SystemUserType.ADMINISTRATOR.getCode());
        userOperationService.save(userOperation);

        operation = new Operation();
        operation.setId(CoreUtil.getUUID());
        operation.setName("权限设置");
        operation.setUrl("operation/setOperation.jsp");
        operation.setType(OperationType.DETAIL.getCode());
        operation.setParentId(parentId);
        operationService.save(operation);

        userOperation = new UserOperation();
        userOperation.setId(CoreUtil.getUUID());
        userOperation.setOperationId(operation.getId());
        userOperation.setUserType(SystemUserType.ADMINISTRATOR.getCode());
        userOperationService.save(userOperation);
    }

	public static void addCustomer() {
		CustomerService customerService = DeployContext.getInstance().getBean(
				"customerService", CustomerService.class);
		// 新增加一个彩民
		Date now = new Date();
		Customer customerl2 = new Customer();
		customerl2.setId("047c9af002174b0692a75320d372f24b");
		customerl2.setName("limitee2");
		String pwd = "123456";
		customerl2.setPassword(pwd);
		customerl2.setNickyName("明tai2");
		customerl2.setPhone("01099998889");
		customerl2.setEmail("test222@qq.com");
		customerl2.setRealName("李明2");
		customerl2.setIdentityId("32014219881202475233");
		customerl2.setRegDate(now);
		customerl2.setLastActiveTime(now);
		customerl2.setLastLoginTime(now);
		customerl2.setIntroducer("");
		customerl2.setRecharge(1000000);
		customerl2.setIntegral(0);
		customerl2.setStatus(1);
		customerl2.setChannelCode("Q0001");
		customerService.save(customerl2);

		// 新增加一个彩民
		Customer customer = new Customer();
		String customerId = "d95e5e0207c548bd9200e9072052bd66";
		customer.setId(customerId);
		customer.setName("limitee");
		Date now2 = new Date();
		String pwd2 = "123456";
		customer.setPassword(pwd2);
		customer.setNickyName("明tai");
		customer.setPhone("01099998888");
		customer.setEmail("test@qq.com");
		customer.setRealName("李明");
		customer.setIdentityId("320142198812024752");
		customer.setRegDate(new Date());
		customer.setLastActiveTime(new Date());
		customer.setLastLoginTime(new Date());
		customer.setIntroducer("");
		customer.setRecharge(0);
		customer.setIntegral(0);
		customer.setStatus(1);
		customer.setChannelCode("Q0001");
		customerService.save(customer);
		
		customer = new Customer();
		customer.setId("cc5b0c0202db405998d219f506533887");
		customer.setName("limitee2");
		pwd = "123456";
		customer.setPassword(pwd);
		customer.setNickyName("明tai2");
		customer.setPhone("01099998889");
		customer.setEmail("test222@qq.com");
		customer.setRealName("李明");
		customer.setIdentityId("32014219881202475233");
		customer.setRegDate(now);
		customer.setLastActiveTime(now);
		customer.setLastLoginTime(now);
		customer.setIntroducer("");
		customer.setRecharge(1000000);
		customer.setIntegral(0);
		customer.setStatus(1);
		customer.setChannelCode("Q0002");
		customerService.save(customer);
		
		//九歌渠道用户
		customer = new Customer();
		customer.setId("04f94fc02f0a4069ba75d44b4097bce2");
		customer.setName("limitee2");
		pwd = "123456";
		customer.setPassword(pwd);
		customer.setNickyName("明tai2");
		customer.setPhone("01099998889");
		customer.setEmail("test222@qq.com");
		customer.setRealName("李明");
		customer.setIdentityId("32014219881202475233");
		customer.setRegDate(now);
		customer.setLastActiveTime(now);
		customer.setLastLoginTime(now);
		customer.setIntroducer("");
		customer.setRecharge(1000000);
		customer.setIntegral(0);
		customer.setStatus(1);
		customer.setChannelCode("Q0003");
		customerService.save(customer);
	}


	public static void addJcGame() {
		Game g = new Game();
		String gId = "db8baa27590e450080aa7655ca433556";
		g.setId(gId);
		g.setCode("T51");
		g.setDescription("竞彩");
		g.setName("竞彩足球");
		g.setStatus(1);
		g.setSynchro(false);
		g.setType(3);
		g.setPeriod("");
		g.setPublishDesc("竞彩足球");

		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		gameService.save(g);
	}

	public static void addJcMatch(String gameCode) throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);

		String df = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(df);
		Term m = new Term();
		m.setId(CoreUtil.getUUID());
		m.setCode("201312172001");
		m.setGameCode(gameCode);
		m.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m.setConcedePoints(0);
		m.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m.setOpenTime(dateFormat.parse("2013-12-20 00:00:00"));
		m.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m.setStatus(TermState.ON_SALE.getCode());
		termService.save(m);
        addJcOdds("CN", "中国赔率", gameCode, "201312172001", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172001", "02", "2.22|3.12|2.62");

		Term m2 = new Term();
		m2.setId(CoreUtil.getUUID());
		m2.setCode("201312172002");
		m2.setGameCode(gameCode);
		m2.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m2.setConcedePoints(0);
		m2.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m2.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m2.setOpenTime(dateFormat.parse("2013-12-21 00:00:00"));
		m2.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m2.setStatus(TermState.ON_SALE.getCode());
		termService.save(m2);
        addJcOdds("CN", "中国赔率", gameCode, "201312172002", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172002", "02", "2.22|3.12|2.62");

		Term m3 = new Term();
		m3.setId(CoreUtil.getUUID());
		m3.setCode("201312172003");
		m3.setGameCode(gameCode);
		m3.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m3.setConcedePoints(0);
		m3.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m3.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m3.setOpenTime(dateFormat.parse("2013-12-21 00:00:00"));
		m3.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m3.setStatus(TermState.ON_SALE.getCode());
		termService.save(m3);
        addJcOdds("CN", "中国赔率", gameCode, "201312172003", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172003", "02", "2.22|3.12|2.62");

		Term m4 = new Term();
		m4.setId(CoreUtil.getUUID());
		m4.setCode("201312172004");
		m4.setGameCode(gameCode);
		m4.setConcedePoints(0);
		m4.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m4.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m4.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m4.setOpenTime(dateFormat.parse("2013-12-21 00:00:00"));
		m4.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m4.setStatus(TermState.ON_SALE.getCode());
		termService.save(m4);
        addJcOdds("CN", "中国赔率", gameCode, "201312172004", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172004", "02", "2.22|3.12|2.62");

		Term m5 = new Term();
		m5.setId(CoreUtil.getUUID());
		m5.setCode("201312172005");
		m5.setGameCode(gameCode);
		m5.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m5.setConcedePoints(0);
		m5.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m5.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m5.setOpenTime(dateFormat.parse("2013-12-21 00:00:00"));
		m5.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m5.setStatus(TermState.ON_SALE.getCode());
		termService.save(m5);
        addJcOdds("CN", "中国赔率", gameCode, "201312172005", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172005", "02", "2.22|3.12|2.62");

		Term m6 = new Term();
		m6.setId(CoreUtil.getUUID());
		m6.setCode("201312172006");
		m6.setGameCode(gameCode);
		m6.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m6.setConcedePoints(0);
		m6.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m6.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m6.setOpenTime(dateFormat.parse("2013-12-21 00:00:00"));
		m6.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m6.setStatus(TermState.ON_SALE.getCode());
		termService.save(m6);
        addJcOdds("CN", "中国赔率", gameCode, "201312172006", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172006", "02", "2.22|3.12|2.62");

		Term m7 = new Term();
		m7.setId(CoreUtil.getUUID());
		m7.setCode("201312172007");
		m7.setGameCode(gameCode);
		m7.setDetailInfo("中国一队|中国二队|中国联赛|-1");
		m7.setConcedePoints(0);
		m7.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		m7.setMatchTime(dateFormat.parse("2013-12-25 00:00:00"));
		m7.setOpenTime(dateFormat.parse("2013-12-21 00:00:00"));
		m7.setEndTime(dateFormat.parse("2014-12-20 00:00:00"));
		m7.setStatus(TermState.ON_SALE.getCode());
		termService.save(m7);
        addJcOdds("CN", "中国赔率", gameCode, "201312172007", "01", "4.22|3.12|2.02");
        addJcOdds("CN", "中国赔率", gameCode, "201312172007", "02", "2.22|3.12|2.62");
	}

	/**
	 * 添加竞彩赔率信息
	 * 
	 * @param oCode
	 * @param oName
	 * @param gameCode
	 * @param matchCode
	 * @param playTypeCode
	 * @param oddsInfo
	 */
	public static void addJcOdds(String oCode, String oName, String gameCode,
			String matchCode, String playTypeCode, String oddsInfo) {
		JOddsService jOddsService = DeployContext.getInstance().getBean(
				"jOddsService", JOddsService.class);
        MgOddsService mgOddsService = DeployContext.getInstance().getBean(
                "mgOddsService", MgOddsService.class);
		JOdds jod = new JOdds();
		jod.setId(CoreUtil.getUUID());
		jod.setGameCode(gameCode);
		jod.setMatchCode(matchCode);
		jod.setPlayTypeCode(playTypeCode);
		jod.setOddsCode(oCode);
		jod.setOddsName(oName);
		jod.setOddsInfo(oddsInfo);
		jod.setCreateTime(new Date());
		jOddsService.save(jod);
        mgOddsService.save(jod);
	}

	public static void addCustomerAccount(String customerId, String stationId,
			long recharge, long prize, int status) {
		CustomerAccountService customerAccountService = DeployContext
				.getInstance().getBean("customerAccountService",
						CustomerAccountService.class);
		CustomerAccount ca = new CustomerAccount();
		ca.setId(CoreUtil.getUUID());
		ca.setCustomerId(customerId);
		ca.setIntegral(0);
		ca.setPrize(prize);
		ca.setRecharge(recharge);
		ca.setStationId(stationId);
		ca.setStatus(status);
		customerAccountService.save(ca);
	}
	
	public static void addF03() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		String gameCode = "F03";
        Game qlc = new Game();
        qlc.setId("2d63d7489cd243e3bea69bcd39b3003e");
        qlc.setCode(gameCode);
        qlc.setName("七乐彩");
        qlc.setPeriod("");
        qlc.setPublishDesc("七乐彩");
        qlc.setStatus(ConstantValues.Game_Status_Open.getCode());
        qlc.setDescription("今日开奖");
        gameService.save(qlc);
	}
	
	public static void addF02() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "F02";
        Game pls = new Game();
        pls.setId("0a9ff8d36dde4e9bb255c758b6460306");
        pls.setCode(gameCode);
        pls.setName("福彩3d");
        pls.setPeriod("");
        pls.setPublishDesc("福彩3d");
        pls.setStatus(ConstantValues.Game_Status_Open.getCode());
        pls.setDescription("今日开奖");
        gameService.save(pls);
	}
	
	public static void addF01() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "F01";
		
		Game ssq = new Game();
        ssq.setId("1d642a84ed7641cf8945739dc435415d");
        ssq.setCode(gameCode);
        ssq.setName("双色球");
        ssq.setPeriod("");
        ssq.setPublishDesc("福彩双色球");
        ssq.setStatus(ConstantValues.Game_Status_Open.getCode());
        ssq.setDescription("今日开奖");
        gameService.save(ssq);
	}
	
	public static void addT05() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "T05";
        Game esf = new Game();
        esf.setId("83209c48bf2b415bad2b3ee946b31cd0");
        esf.setCode(gameCode);
        esf.setName("11选5");
        esf.setPeriod("");
        esf.setPublishDesc("体彩11选5");
        esf.setStatus(ConstantValues.Game_Status_Open.getCode());
        esf.setDescription("高频");
        esf.setType(ConstantValues.Game_Type_Gaopin.getCode());
        gameService.save(esf);
	}
	
	public static void addT04() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "T04";
        Game plw = new Game();
        plw.setId("9dedbc50feb2432d871c5bc6ac72db41");
        plw.setCode(gameCode);
        plw.setName("排列五");
        plw.setPeriod("");
        plw.setPublishDesc("体彩排列五");
        plw.setStatus(ConstantValues.Game_Status_Open.getCode());
        plw.setDescription("今日开奖");
        gameService.save(plw);
	}
	
	public static void addT53() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "T53";
        Game sfc = new Game();
        sfc.setId("2f0b95dc08884252a0e7f9bf65295646");
        sfc.setCode(gameCode);
        sfc.setName("胜负彩");
        sfc.setPeriod("");
        sfc.setPublishDesc("胜负彩");
        sfc.setStatus(ConstantValues.Game_Status_Open.getCode());
        sfc.setDescription("胜负彩");
        gameService.save(sfc);
	}
	
	public static void addT54() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "T54";
        Game jqc = new Game();
        jqc.setId("220baaf14449411fb00f149e90c55e69");
        jqc.setCode(gameCode);
        jqc.setName("四场进球");
        jqc.setPeriod("");
        jqc.setPublishDesc("四场进球");
        jqc.setStatus(ConstantValues.Game_Status_Open.getCode());
        jqc.setDescription("四场进球");
        gameService.save(jqc);
	}
	
	public static void addT03() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "T03";
        Game pls = new Game();
        pls.setId("cdb659de44b5478c8e2648d670da1c44");
        pls.setCode(gameCode);
        pls.setName("排列三");
        pls.setPeriod("");
        pls.setPublishDesc("体彩排列三");
        pls.setStatus(ConstantValues.Game_Status_Open.getCode());
        pls.setDescription("今日开奖");
        gameService.save(pls);
	}

	public static void addT02() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);
		
		String gameCode = "T02";
        Game ssq = new Game();
        ssq.setId("fd9127c2e88f4f45bab1bf1b9f7288c5");
        ssq.setCode(gameCode);
        ssq.setName("七星彩");
        ssq.setPeriod("");
        ssq.setPublishDesc("体彩七星彩");
        ssq.setStatus(ConstantValues.Game_Status_Open.getCode());
        ssq.setDescription("今日开奖");
        gameService.save(ssq);
	}

	public static void addT01() throws Exception {
		GameService gameService = DeployContext.getInstance().getBean(
				"gameService", GameService.class);

		String gameCode = "T01";
		String glbDltId = "e7528d479beb4c6989b9e6134ba6f4ca";
		Game game = new Game();
		game.setId(glbDltId);
		game.setCode(gameCode);
		game.setName("大乐透");
		game.setPeriod("");
		game.setPublishDesc("体彩大乐透");
		game.setStatus(ConstantValues.Game_Status_Open.getCode());
		game.setDescription("今日开奖");
		gameService.save(game);
	}

    public static void addF04() throws Exception {
        GameService gameService = DeployContext.getInstance().getBean(
                "gameService", GameService.class);
        String gameCode = "F04";
        String glbDltId = "eba5a4c6c8224097b501765ae2819fa2";
        Game game = new Game();
        game.setId(glbDltId);
        game.setCode(gameCode);
        game.setName("快3");
        game.setPeriod("");
        game.setPublishDesc("快3");
        game.setStatus(ConstantValues.Game_Status_Open.getCode());
        game.setDescription("今日开奖");
        gameService.save(game);
    }

	/**
	 * 添加T01的期次
	 * 
	 * @throws Exception
	 */
	public static void addT01Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);

		String prizeDesc = addT01PrizeDesc(false);
		String gameCode = "T01";
		Term gt = new Term();
		gt.setId("b61f175e9c7b418d8b16c70abc029747");
		gt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		gt.setOpenTime(dateFormat.parse("2013-07-27T22:30:00.000+0800"));
		gt.setEndTime(dateFormat.parse("2013-07-29T20:30:00.000+0800"));
		gt.setCode("2014001");
		gt.setName("第2014001期");
		gt.setNextCode("2014002");
		gt.setStatus(TermState.NOT_ON_SALE.getCode());
		gt.setPrizePool(42617129);
		gt.setPrizeDesc(prizeDesc);
		gt.setWinningNumber("09,12,22,32,34|02,04");
		gt.setGameCode(gameCode);
		termService.save(gt);

		Term nextGt = new Term();
		String gameTermCode = "2014002";
		String gameTermId = "d367b3b8701d41bbab058b0f6f7aedf6";
		nextGt.setId(gameTermId);
		nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
		nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
		nextGt.setCode(gameTermCode);
		nextGt.setName("第2014002期");
		nextGt.setNextCode("2014003");
		nextGt.setStatus(TermState.NOT_ON_SALE.getCode());
		nextGt.setPrizePool(-1);
		nextGt.setPrizeDesc(prizeDesc);
		nextGt.setWinningNumber("09,12,14,32,34|02,04");
		nextGt.setGameCode(gameCode);
		termService.save(nextGt);

		Term gt13089 = new Term();
		gameTermCode = "2014003";
		gameTermId = "2995ad752616491db76c70f247b4a9d1";
		gt13089.setId(gameTermId);
		gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
		gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
		gt13089.setCode(gameTermCode);
		gt13089.setName("第2014003期");
		gt13089.setNextCode("2014004");
		gt13089.setStatus(TermState.NOT_ON_SALE.getCode());
		gt13089.setPrizePool(-1);
		gt13089.setWinningNumber("01,02,03,04,34|02,04");
		gt13089.setGameCode(gameCode);
		gt13089.setPrizeDesc(prizeDesc);
		termService.save(gt13089);

		Term gt13090 = new Term();
		gameTermCode = "2014004";
		gameTermId = "e75da8464ef147e084cff8d88df96428";
		gt13090.setId(gameTermId);
		gt13090.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		gt13090.setOpenTime(dateFormat.parse("2015-08-30T22:30:00.000+0800"));
		gt13090.setEndTime(dateFormat.parse("2015-09-01T20:30:00.000+0800"));
		gt13090.setCode(gameTermCode);
		gt13090.setName("第2014004期");
		gt13090.setNextCode("2014005");
		gt13090.setStatus(TermState.NOT_ON_SALE.getCode());
		gt13090.setPrizePool(-1);
		gt13090.setPrizeDesc(prizeDesc);
		gt13090.setWinningNumber("09,12,22,32,34|02,04");
		gt13090.setGameCode(gameCode);
		termService.save(gt13090);

		Term gt13091 = new Term();
		gameTermCode = "2014005";
		gameTermId = "fbbb963b63fc4dafafd8544b5b55d626";
		gt13091.setId(gameTermId);
		gt13091.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		gt13091.setOpenTime(dateFormat.parse("2015-09-01T22:30:00.000+0800"));
		gt13091.setEndTime(dateFormat.parse("2015-09-02T20:30:00.000+0800"));
		gt13091.setCode(gameTermCode);
		gt13091.setName("第2014005期");
		gt13091.setNextCode("2014006");
		gt13091.setStatus(TermState.NOT_ON_SALE.getCode());
		gt13091.setPrizePool(-1);
		gt13091.setPrizeDesc(prizeDesc);
		gt13091.setWinningNumber("09,12,22,32,34|02,04");
		gt13091.setGameCode(gameCode);
		termService.save(gt13091);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addF03Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "F03";
		String prizeDesc = addF03PrizeDesc(false);
		
		Term nextGt = new Term();
        String gameTermCode = "2013088";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2013088期");
        nextGt.setNextCode("2013089");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("01,02,09,12,14,19,30|04");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "2013089";
        gameTermId = "3231444b3d4849cdb0c7aed38f315f7f";
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第2013089期");
        gt13089.setNextCode("2013090");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("01,02,09,12,14,19,30|04");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
	}

    /**
     * @throws Exception
     */
    public static void addF04Term() throws Exception {
        TermService termService = DeployContext.getInstance().getBean(
                "termService", TermService.class);
        String gameCode = "F04";
        String prizeDesc = addF03PrizeDesc(false);

        Term nextGt = new Term();
        String gameTermCode = "2013088";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2013088期");
        nextGt.setNextCode("2013089");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("1,2,3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "2013089";
        gameTermId = CoreUtil.getUUID();
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第2013089期");
        gt13089.setNextCode("2013090");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("1,2,3");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
    }
	
	/**
	 * @throws Exception
	 */
	public static void addT53Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "T53";
		String prizeDesc = addT53PrizeDesc(false);
		
		Term nextGt = new Term();
        String gameTermCode = "2014022";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2014-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2014022期");
        nextGt.setNextCode("2014023");
        nextGt.setStatus(TermState.ON_SALE.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setDetailInfo("20140629000000|世界杯|巴　西|智　利|1.57 4.01 5.68;20140629040000|世界杯|哥伦比|乌拉圭|1.96 3.35 3.92;20140630000000|世界杯|荷　兰|墨西哥|2.08 3.31 3.56;20140630040000|世界杯|哥斯达|希　腊|2.46 3.04 3.03;20140701000000|世界杯|法　国|尼日利|1.49 4.09 6.83;20140701040000|世界杯|德　国|阿尔及|1.29 5.16 10.13;20140702000000|世界杯|阿根廷|瑞　士|1.49 4.14 6.73;20140702040000|世界杯|比利时|美　国|1.79 3.45 4.53;20140628170000|日　乙|福　冈|枥　木|2.69 3.10 2.45;20140628170000|日　乙|山　形|磐　田|2.87 3.15 2.30;20140628180000|日　乙|千　叶|松　本|2.37 3.08 2.83;20140628180000|日　乙|京　都|冈　山|2.01 3.19 3.46;20140628180000|日　乙|湘　南|北九州|1.39 4.15 7.21;20140628183000|日　乙|群　马|大　分|2.93 3.11 2.29");
        nextGt.setWinningNumber("3|3|3|3|3|3|3|3|3|3|3|3|3|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        nextGt = new Term();
        gameTermCode = "2014023";
        gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2014-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2014023期");
        nextGt.setNextCode("2014024");
        nextGt.setStatus(TermState.ON_SALE.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setDetailInfo("20140629000000|世界杯|巴　西|智　利|1.57 4.01 5.68;20140629040000|世界杯|哥伦比|乌拉圭|1.96 3.35 3.92;20140630000000|世界杯|荷　兰|墨西哥|2.08 3.31 3.56;20140630040000|世界杯|哥斯达|希　腊|2.46 3.04 3.03;20140701000000|世界杯|法　国|尼日利|1.49 4.09 6.83;20140701040000|世界杯|德　国|阿尔及|1.29 5.16 10.13;20140702000000|世界杯|阿根廷|瑞　士|1.49 4.14 6.73;20140702040000|世界杯|比利时|美　国|1.79 3.45 4.53;20140628170000|日　乙|福　冈|枥　木|2.69 3.10 2.45;20140628170000|日　乙|山　形|磐　田|2.87 3.15 2.30;20140628180000|日　乙|千　叶|松　本|2.37 3.08 2.83;20140628180000|日　乙|京　都|冈　山|2.01 3.19 3.46;20140628180000|日　乙|湘　南|北九州|1.39 4.15 7.21;20140628183000|日　乙|群　马|大　分|2.93 3.11 2.29");
        nextGt.setWinningNumber("3|3|3|3|3|3|3|3|3|3|3|3|3|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        nextGt = new Term();
        gameTermCode = "2014020";
        gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2014-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2014020期");
        nextGt.setNextCode("2014021");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setDetailInfo("20140629000000|世界杯|巴　西|智　利|1.57 4.01 5.68;20140629040000|世界杯|哥伦比|乌拉圭|1.96 3.35 3.92;20140630000000|世界杯|荷　兰|墨西哥|2.08 3.31 3.56;20140630040000|世界杯|哥斯达|希　腊|2.46 3.04 3.03;20140701000000|世界杯|法　国|尼日利|1.49 4.09 6.83;20140701040000|世界杯|德　国|阿尔及|1.29 5.16 10.13;20140702000000|世界杯|阿根廷|瑞　士|1.49 4.14 6.73;20140702040000|世界杯|比利时|美　国|1.79 3.45 4.53;20140628170000|日　乙|福　冈|枥　木|2.69 3.10 2.45;20140628170000|日　乙|山　形|磐　田|2.87 3.15 2.30;20140628180000|日　乙|千　叶|松　本|2.37 3.08 2.83;20140628180000|日　乙|京　都|冈　山|2.01 3.19 3.46;20140628180000|日　乙|湘　南|北九州|1.39 4.15 7.21;20140628183000|日　乙|群　马|大　分|2.93 3.11 2.29");
        nextGt.setWinningNumber("3|3|3|3|3|3|3|3|3|3|3|3|3|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        nextGt = new Term();
        gameTermCode = "2014021";
        gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2014-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2014021期");
        nextGt.setNextCode("2014022");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setDetailInfo("20140629000000|世界杯|巴　西|智　利|1.57 4.01 5.68;20140629040000|世界杯|哥伦比|乌拉圭|1.96 3.35 3.92;20140630000000|世界杯|荷　兰|墨西哥|2.08 3.31 3.56;20140630040000|世界杯|哥斯达|希　腊|2.46 3.04 3.03;20140701000000|世界杯|法　国|尼日利|1.49 4.09 6.83;20140701040000|世界杯|德　国|阿尔及|1.29 5.16 10.13;20140702000000|世界杯|阿根廷|瑞　士|1.49 4.14 6.73;20140702040000|世界杯|比利时|美　国|1.79 3.45 4.53;20140628170000|日　乙|福　冈|枥　木|2.69 3.10 2.45;20140628170000|日　乙|山　形|磐　田|2.87 3.15 2.30;20140628180000|日　乙|千　叶|松　本|2.37 3.08 2.83;20140628180000|日　乙|京　都|冈　山|2.01 3.19 3.46;20140628180000|日　乙|湘　南|北九州|1.39 4.15 7.21;20140628183000|日　乙|群　马|大　分|2.93 3.11 2.29");
        nextGt.setWinningNumber("3|3|3|3|3|3|3|3|3|3|3|3|3|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addT54Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "T54";
		String prizeDesc = addT54PrizeDesc(false);
		
		Term nextGt = new Term();
        String gameTermCode = "2014022";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2014-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2014022期");
        nextGt.setNextCode("2014023");
        nextGt.setStatus(TermState.ON_SALE.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("3|3|3|3|3|3|3|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addF02Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "F02";
		String prizeDesc = addF02PrizeDesc(false);
		
		Term nextGt = new Term();
        String gameTermCode = "2013088";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2013088期");
        nextGt.setNextCode("2013089");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("1|2|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "2013089";
        gameTermId = "9856ecba3eb446f491a319d943e3adca";
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第2013089期");
        gt13089.setNextCode("2013090");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("1|2|3");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addF01Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "F01";
		String prizeDesc = addF01PrizeDesc(false);
		
		Term nextGt = new Term();
        String gameTermCode = "2013088";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2013088期");
        nextGt.setNextCode("2013089");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("09,12,14,30,32,33|02");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "2013089";
        gameTermId = "fde2255dbd904826abde53af0bd6c8c3";
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第2013089期");
        gt13089.setNextCode("2013090");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("09,12,14,30,32,33|02");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addT05Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "T05";
		String prizeDesc = addT05PrizeDesc(false);
	
		Term nextGt = new Term();
        String gameTermCode = "2013101008";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第2013101008期");
        nextGt.setNextCode("2013101009");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("01|02|03|04|05");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "2013101009";
        gameTermId = "4eaf48d763c149e5b86e16e0b46d2ee4";
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第2013101009期");
        gt13089.setNextCode("2013101010");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("01|02|03|04|05");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
        
        Term term = new Term();
        gameTermCode = "2013101010";
        gameTermId = CoreUtil.getUUID();
        term.setId(gameTermId);
        term.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        term.setOpenTime(dateFormat.parse("2014-07-31T22:30:00.000+0800"));
        term.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        term.setCode(gameTermCode);
        term.setName("第2013101010期");
        term.setNextCode("2013101011");
        term.setStatus(TermState.NOT_ON_SALE.getCode());
        term.setPrizePool(-1);
        term.setWinningNumber("01|02|03|04|05");
        term.setGameCode(gameCode);
        term.setPrizeDesc(prizeDesc);
        termService.save(term);
        
        term = new Term();
        gameTermCode = "2013101011";
        gameTermId = CoreUtil.getUUID();
        term.setId(gameTermId);
        term.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        term.setOpenTime(dateFormat.parse("2014-07-31T22:30:00.000+0800"));
        term.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        term.setCode(gameTermCode);
        term.setName("第2013101011期");
        term.setNextCode("2013101012");
        term.setStatus(TermState.NOT_ON_SALE.getCode());
        term.setPrizePool(-1);
        term.setWinningNumber("01|02|03|04|05");
        term.setGameCode(gameCode);
        term.setPrizeDesc(prizeDesc);
        termService.save(term);
        
        term = new Term();
        gameTermCode = "2013101012";
        gameTermId = CoreUtil.getUUID();
        term.setId(gameTermId);
        term.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        term.setOpenTime(dateFormat.parse("2014-07-31T22:30:00.000+0800"));
        term.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        term.setCode(gameTermCode);
        term.setName("第2013101011期");
        term.setNextCode("2013101012");
        term.setStatus(TermState.NOT_ON_SALE.getCode());
        term.setPrizePool(-1);
        term.setWinningNumber("01|02|03|04|05");
        term.setGameCode(gameCode);
        term.setPrizeDesc(prizeDesc);
        termService.save(term);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addT04Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		String gameCode = "T04";
		String prizeDesc = addT04PrizeDesc(false);
		 
		Term nextGt = new Term();
        String gameTermCode = "13088";
        String gameTermId = CoreUtil.getUUID();
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第13088期");
        nextGt.setNextCode("13089");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("1|2|3|4|5");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "13089";
        gameTermId = "76bc0da1760b44e6836c1b1dc912a464";
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第13089期");
        gt13089.setNextCode("13090");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("1|2|3|4|5");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
	}
	
	/**
	 * @throws Exception
	 */
	public static void addT03Term() throws Exception {
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		Term nextGt = new Term();
        String gameTermCode = "13088";
        String gameCode = "T03";
        String gameTermId = CoreUtil.getUUID();
        String prizeDesc = addT03PrizeDesc(false);
        nextGt.setId(gameTermId);
        nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
        nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
        nextGt.setCode(gameTermCode);
        nextGt.setName("第13088期");
        nextGt.setNextCode("13089");
        nextGt.setStatus(TermState.SEAL.getCode());
        nextGt.setPrizePool(-1);
        nextGt.setPrizeDesc(prizeDesc);
        nextGt.setWinningNumber("1|2|3");
        nextGt.setGameCode(gameCode);
        termService.save(nextGt);

        Term gt13089 = new Term();
        gameTermCode = "13089";
        gameTermId = "5695e2fd2078465aa730757edbea265f";
        gt13089.setId(gameTermId);
        gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
        gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
        gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
        gt13089.setCode(gameTermCode);
        gt13089.setName("第13089期");
        gt13089.setNextCode("13090");
        gt13089.setStatus(TermState.ON_SALE.getCode());
        gt13089.setPrizePool(-1);
        gt13089.setWinningNumber("1|2|3");
        gt13089.setGameCode(gameCode);
        gt13089.setPrizeDesc(prizeDesc);
        termService.save(gt13089);
	}

	/**
	 * @throws Exception
	 */
	public static void addT02Term() throws Exception {
		
		TermService termService = DeployContext.getInstance().getBean(
				"termService", TermService.class);
		
		String prizeDesc = addT02PrizeDesc(false);
		String gameCode = "T02";
		
		Term nextGt = new Term();
		String gameTermCode = "13088";
		String gameTermId = CoreUtil.getUUID();
		nextGt.setId(gameTermId);
		nextGt.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		nextGt.setOpenTime(dateFormat.parse("2013-07-29T22:30:00.000+0800"));
		nextGt.setEndTime(dateFormat.parse("2013-07-31T20:30:00.000+0800"));
		nextGt.setCode(gameTermCode);
		nextGt.setName("第13088期");
		nextGt.setNextCode("13089");
		nextGt.setStatus(TermState.SEAL.getCode());
		nextGt.setPrizePool(-1);
		nextGt.setPrizeDesc(prizeDesc);
		nextGt.setWinningNumber("1|2|3|4|5|6|7");
		nextGt.setGameCode(gameCode);
		termService.save(nextGt);

		Term gt13089 = new Term();
		gameTermCode = "13089";
		gameTermId = "272fb079533f4095b154781380123c5d";
		gt13089.setId(gameTermId);
		gt13089.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		gt13089.setOpenTime(dateFormat.parse("2013-07-31T22:30:00.000+0800"));
		gt13089.setEndTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
		gt13089.setCode(gameTermCode);
		gt13089.setName("第13089期");
		gt13089.setNextCode("13090");
		gt13089.setStatus(TermState.ON_SALE.getCode());
		gt13089.setPrizePool(-1);
		gt13089.setWinningNumber("1|2|3|4|5|6|7");
		gt13089.setGameCode(gameCode);
		gt13089.setPrizeDesc(prizeDesc);
		termService.save(gt13089);

		Term gt13090 = new Term();
		gameTermCode = "13090";
		gameTermId = CoreUtil.getUUID();
		gt13090.setId(gameTermId);
		gt13090.setCreateTime(dateFormat.parse("2013-07-27T22:00:00.000+0800"));
		gt13090.setOpenTime(dateFormat.parse("2015-08-30T20:30:00.000+0800"));
		gt13090.setEndTime(dateFormat.parse("2015-08-31T20:30:00.000+0800"));
		gt13090.setCode(gameTermCode);
		gt13090.setName("第13090期");
		gt13090.setNextCode("13091");
		gt13090.setStatus(TermState.NOT_ON_SALE.getCode());
		gt13090.setPrizePool(-1);
		gt13090.setWinningNumber("1|2|3|4|5|6|7");
		gt13090.setGameCode(gameCode);
		gt13090.setPrizeDesc(prizeDesc);
		termService.save(gt13090);
	}
	
	public static String addT53PrizeDesc(boolean saveToDb) throws Exception {
		
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T53";
		
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
        gameGrade3.setBonus(-1);
        gameGrade3.setFixedBonus(false);
        gameGrade3.setStatus(1);
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gameGrade1.setBonus(400000000);
		gameGrade1.setgCount(2);
		gradeList.add(gameGrade1);
		gameGrade2.setBonus(200000000);
		gameGrade2.setgCount(20);
		gradeList.add(gameGrade2);
		gameGrade3.setBonus(100000000);
		gameGrade3.setgCount(30);
		gradeList.add(gameGrade3);
		pd.setGrades(gradeList);
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
		}
		
		return prizeDesc;
	}
	
	public static String addT54PrizeDesc(boolean saveToDb) throws Exception {
		
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T54";
		
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
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gameGrade1.setBonus(400000000);
		gameGrade1.setgCount(2);
		gradeList.add(gameGrade1);
		pd.setGrades(gradeList);
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
		}
		
		return prizeDesc;
	}

    public static String addF04PrizeDesc(boolean saveToDb) throws Exception {
        GameGradeService gameGradeService = DeployContext.getInstance()
                .getBean("gameGradeService", GameGradeService.class);
        String gameCode = "F04";
        PrizeDescription pd = new PrizeDescription();

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
        String prizeDesc = om.writeValueAsString(pd);
        if(saveToDb)
        {
            gameGradeService.save(gameGrade1);
            gameGradeService.save(gameGrade2);
            gameGradeService.save(gameGrade3);
            gameGradeService.save(gameGrade4);
            gameGradeService.save(gameGrade5);
            gameGradeService.save(gameGrade6);
            gameGradeService.save(gameGrade7);

            gameGradeService.save(gameGrade8);
            gameGradeService.save(gameGrade9);
            gameGradeService.save(gameGrade10);
            gameGradeService.save(gameGrade11);
            gameGradeService.save(gameGrade12);
            gameGradeService.save(gameGrade13);
            gameGradeService.save(gameGrade14);
            gameGradeService.save(gameGrade15);
            gameGradeService.save(gameGrade16);
            gameGradeService.save(gameGrade17);
            gameGradeService.save(gameGrade18);
            gameGradeService.save(gameGrade19);
            gameGradeService.save(gameGrade20);
            gameGradeService.save(gameGrade21);
        }
        return prizeDesc;
    }
	
	public static String addF03PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "F03";
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
        gameGrade3.setBonus(-1);
        gameGrade3.setFixedBonus(false);
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
        gameGrade5.setBonus(5000);
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
        gameGrade6.setBonus(1000);
        gameGrade6.setFixedBonus(true);
        gameGrade6.setStatus(1);
        
        /**
         * 七等奖
         */
        GameGrade gameGrade7 = new GameGrade();
        gameGradeId = CoreUtil.getUUID();
        gameGrade7.setId(gameGradeId);
        gameGrade7.setGameCode(gameCode);
        gameGrade7.setCode("LV7");
        gameGrade7.setName("七等奖");
        gameGrade7.setgLevel(Constants.GRADE_LEVEL_SEVENTH);
        gameGrade7.setBonus(500);
        gameGrade7.setFixedBonus(true);
        gameGrade7.setStatus(1);
       
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gameGrade1.setBonus(400000000);
		gameGrade1.setgCount(2);
		gradeList.add(gameGrade1);
		gameGrade2.setBonus(200000000);
		gameGrade2.setgCount(20);
		gradeList.add(gameGrade2);
		gameGrade3.setBonus(100000000);
		gameGrade3.setgCount(30);
		gradeList.add(gameGrade3);
		gradeList.add(gameGrade4);
		gradeList.add(gameGrade5);
		gradeList.add(gameGrade6);
		gradeList.add(gameGrade7);
		pd.setGrades(gradeList);
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
			gameGradeService.save(gameGrade4);
			gameGradeService.save(gameGrade5);
			gameGradeService.save(gameGrade6);
			gameGradeService.save(gameGrade7);
		}
		
		return prizeDesc;
	}
	
	public static String addF02PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "F02";
		
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
        gameGrade1.setBonus(100000);
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
        gameGrade2.setBonus(32000);
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
        gameGrade3.setBonus(16000);
        gameGrade3.setFixedBonus(true);
        gameGrade3.setStatus(1);
        
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		pd.setGrades(gradeList);
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
		}
		
		return prizeDesc;
	}
	
	public static String addF01PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "F01";
		
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
        gameGradeService.save(gameGrade1);

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
        gameGradeService.save(gameGrade2);

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
        gameGradeService.save(gameGrade3);

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
        gameGradeService.save(gameGrade4);

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
        gameGradeService.save(gameGrade5);

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
        
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
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
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
			gameGradeService.save(gameGrade4);
			gameGradeService.save(gameGrade5);
			gameGradeService.save(gameGrade6);
		}
		return prizeDesc;
	}
	
	public static String addT05PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T05";
	   /***
        * 任选一
        */
       GameGrade gameGrade1 = new GameGrade();
       String gameGradeId = CoreUtil.getUUID();
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
       gameGrade2.setId(CoreUtil.getUUID());
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
       gameGrade3.setId(CoreUtil.getUUID());
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
       gameGrade4.setId(CoreUtil.getUUID());
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
       gameGrade5.setId(CoreUtil.getUUID());
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
       gameGrade6.setId(CoreUtil.getUUID());
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
       gameGrade7.setId(CoreUtil.getUUID());
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
       gameGrade8.setId(CoreUtil.getUUID());
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
       gameGrade9.setId(CoreUtil.getUUID());
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
       gameGrade10.setId(CoreUtil.getUUID());
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
       gameGrade11.setId(CoreUtil.getUUID());
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
       gameGrade12.setId(CoreUtil.getUUID());
       gameGrade12.setGameCode(gameCode);
       gameGrade12.setCode("LV12");
       gameGrade12.setName("前三直选");
       gameGrade12.setgLevel(Constants.GRADE_LEVEL_TWELFTH);
       gameGrade12.setBonus(117000);
       gameGrade12.setFixedBonus(true);
       gameGrade12.setStatus(1);
       
       
       String prizeDesc = "";
       ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
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
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
			gameGradeService.save(gameGrade4);
			gameGradeService.save(gameGrade5);
			gameGradeService.save(gameGrade6);
			gameGradeService.save(gameGrade7);
			gameGradeService.save(gameGrade8);
			gameGradeService.save(gameGrade9);
			gameGradeService.save(gameGrade10);
			gameGradeService.save(gameGrade11);
			gameGradeService.save(gameGrade12);
		}
		
		return prizeDesc;
	}
	
	public static String addT04PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T04";
		/**
         * 一等奖
         */
        GameGrade gameGrade1 = new GameGrade();
        String gameGradeId = CoreUtil.getUUID();
        gameGrade1.setId(gameGradeId);
        gameGrade1.setGameCode(gameCode);
        gameGrade1.setCode("LV1");
        gameGrade1.setName("普通");
        gameGrade1.setgLevel(Constants.GRADE_LEVEL_FIRST);
        gameGrade1.setBonus(10000000);
        gameGrade1.setFixedBonus(true);
        gameGrade1.setStatus(1);
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		pd.setGrades(gradeList);
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
		}
		return prizeDesc;
	}
	
	public static String addT03PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T03";
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
        
        
        String prizeDesc = "";
        ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
		List<GameGrade> gradeList = new ArrayList<GameGrade>();
		gradeList.add(gameGrade1);
		gradeList.add(gameGrade2);
		gradeList.add(gameGrade3);
		pd.setGrades(gradeList);
		prizeDesc = om.writeValueAsString(pd);
		
		if(saveToDb)
		{
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
		}
		
		return prizeDesc;
	}

	public static String addT02PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T02";

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

		String prizeDesc = "";
		ObjectMapper om = new ObjectMapper();
		PrizeDescription pd = new PrizeDescription();
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
		prizeDesc = om.writeValueAsString(pd);

		if (saveToDb) {
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
			gameGradeService.save(gameGrade4);
			gameGradeService.save(gameGrade5);
			gameGradeService.save(gameGrade6);
		}

		return prizeDesc;
	}

	public static String addT01PrizeDesc(boolean saveToDb) throws Exception {
		GameGradeService gameGradeService = DeployContext.getInstance()
				.getBean("gameGradeService", GameGradeService.class);
		String gameCode = "T01";

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

        ObjectMapper om = new ObjectMapper();
        PrizeDescription pd = new PrizeDescription();
        pd.setGrades(gradeList);
		String prizeDesc = om.writeValueAsString(pd);

		if (saveToDb) {
			gameGradeService.save(gameGrade1);
			gameGradeService.save(gameGrade2);
			gameGradeService.save(gameGrade3);
			gameGradeService.save(gameGrade4);
			gameGradeService.save(gameGrade5);
			gameGradeService.save(gameGrade6);
			gameGradeService.save(gameGrade9);
			gameGradeService.save(gameGrade10);
			gameGradeService.save(gameGrade11);
			gameGradeService.save(gameGrade12);
			gameGradeService.save(gameGrade13);
		}
		return prizeDesc;
	}
	
	public static void addArea()
	{
		
		AreaService areaService = DeployContext.getInstance().getBean(
				"areaService", AreaService.class);
		//status没有状态传递，父级不可用，不代表子级不可用。父级可用，不代表子级可用。
        Area henan = new Area();
        String areaId = "24376197effc4fc6b6ada5c62b3b92f6";
        henan.setId(areaId);
        henan.setParentId("");
        henan.setName("河南省");
        henan.setaLevel(ConstantValues.Area_Level_Province.getCode());
        henan.setCode("41");
        henan.setType(ConstantValues.Area_Type_TC.getCode());
        henan.setContact("李主任");
        henan.setAddress("郑州市金水区文化街道");
        henan.setTelephone("010-88889999");
        henan.setDescription("河南体彩中心");
        henan.setStatus(ConstantValues.Area_Status_Open.getCode());
        areaService.save(henan);

        Area hebei = new Area();
        areaId = "2737b491f11e4fa0af024dd4145a65fa";
        hebei.setId(areaId);
        hebei.setParentId("");
        hebei.setName("河北省");
        hebei.setaLevel(ConstantValues.Area_Level_Province.getCode());
        hebei.setCode("40");
        hebei.setType(ConstantValues.Area_Type_TC.getCode());
        hebei.setContact("廖主任");
        hebei.setAddress("石家庄市裕华区");
        hebei.setTelephone("010-88889999");
        hebei.setDescription("河北体彩中心");
        hebei.setStatus(ConstantValues.Area_Status_Open.getCode());
        areaService.save(hebei);

        //新增加一个区域，郑州市
        Area zhengzhou = new Area();
        String zhengzhouId = "abf53863427d45e7913ee831b11a6373";
        zhengzhou.setId(zhengzhouId);
        zhengzhou.setParentId(areaId);
        zhengzhou.setName("郑州市");
        zhengzhou.setaLevel(ConstantValues.Area_Level_City.getCode());
        zhengzhou.setCode("4110");
        zhengzhou.setType(ConstantValues.Area_Type_TC.getCode());
        zhengzhou.setContact("张主任");
        zhengzhou.setAddress("郑州市金水区文化北路");
        zhengzhou.setTelephone("0371-88889999");
        zhengzhou.setDescription("郑州体彩");
        zhengzhou.setStatus(ConstantValues.Area_Status_Open.getCode());
        areaService.save(zhengzhou);

        //新增加一个区域，洛阳市
        Area luoyang = new Area();
        String luoyangAreaId = "6f1e97fa4e3247e0a3c6ac6c935cd2dd";
        luoyang.setId(luoyangAreaId);
        luoyang.setParentId(areaId);
        luoyang.setName("洛阳市");
        luoyang.setaLevel(ConstantValues.Area_Level_City.getCode());
        luoyang.setCode("4111");
        luoyang.setType(ConstantValues.Area_Type_TC.getCode());
        luoyang.setContact("赵主任");
        luoyang.setAddress("洛阳市洛龙区");
        luoyang.setTelephone("0373-88889999");
        luoyang.setDescription("洛阳体彩");
        luoyang.setStatus(ConstantValues.Area_Status_Open.getCode());
        areaService.save(luoyang);
	}
	
	public static void addConservator() {
		ConservatorService conservatorService = DeployContext.getInstance().getBean(
				"conservatorService", ConservatorService.class);
        //新增加一个监管员
        Conservator conservator = new Conservator();
        String conservatorId = "89d9a88d6fa647029f85b9bcd2c81291";
        conservator.setId(conservatorId);
        conservator.setAreaId("abf53863427d45e7913ee831b11a6373");
        conservator.setConservatorName("李明明");
        conservator.setUserName("liming");
        conservator.setPassword("123456");
        conservator.setEmail("test@test.com");
        conservator.setPhone("12345678901");
        conservator.setNote("郑州体彩中心市场部专员");
        conservator.setRegDate(new Date());
        conservator.setLastActiveTime(new Date());
        conservator.setLastLoginIp("192.168.11.147");
        conservator.setStatus(ConstantValues.Conservator_Status_Open.getCode());
        conservatorService.save(conservator);
    }
	
	
	/**
	 * 民生银行
	 */
	public static void cmbcStation()
	{
		StationService stationService = DeployContext.getInstance().getBean(
				"stationService", StationService.class);
		
		Station station = new Station();
		station.setId(CoreUtil.getUUID());
		station.setCode("Q0005");
		station.setPassword("123456");
		station.setRelayable(false);
		station.setName("民生银行渠道");
		station.setDescription("民生银行渠道");
		station.setBalance(10000000000L);
		station.setPhoto("photo");
		station.setBuildTime(new Date());
		station.setLastLoginTime(new Date());
		station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
		station.setStationType(ConstantValues.Station_StationType_CHANNEL.getCode());
		station.setSecretKey("123456");
        stationService.save(station);
        
        //建行渠道
      	String sId = station.getId();
      	//建行渠道全部转票到九歌出票中心
      	String relayToId = "9295ac6d81bb4f778c9660a9909aa987";
      	addStationGame(sId, "T01", relayToId, 400, 0);
      	addStationGame(sId, "T02", relayToId, 400, 0);
      	addStationGame(sId, "T03", relayToId, 400, 0);
      	addStationGame(sId, "T04", relayToId, 400, 0);
      	addStationGame(sId, "T05", relayToId, 400, 0);
      	addStationGame(sId, "F03", relayToId, 400, 0);
      	addStationGame(sId, "F02", relayToId, 400, 0);
      	addStationGame(sId, "F01", relayToId, 400, 0);
      	addStationGame(sId, "T51", relayToId, 400, 0);
      	addStationGame(sId, "T53", relayToId, 400, 0);
      	addStationGame(sId, "T54", relayToId, 400, 0);
	}
	
	public static void addStation()
	{
		StationService stationService = DeployContext.getInstance().getBean(
				"stationService", StationService.class);
		
		//新增一个投注站点
        Station station = new Station();
        String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
        station.setId(stationId);
        station.setCode("00001");
        station.setPassword("123456");
        station.setRelayable(false);
        station.setName("1011");
        station.setDescription("健翔投注站");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_Common.getCode());
        station.setSecretKey("123456");
        stationService.save(station);
        
        
        Station jgstation = new Station();
        String jgstationId = "24fb88b47c694ec4880ce36d3293e647";
        jgstation.setId(jgstationId);
        jgstation.setCode("Q0003");
        jgstation.setPassword("CePEYAR/Snc=");
        jgstation.setRelayable(false);
        jgstation.setName("九歌渠道");
        jgstation.setDescription("九歌渠道");
        jgstation.setBalance(10000000000L);
        jgstation.setPhoto("photo");
        jgstation.setBuildTime(new Date());
        jgstation.setLastLoginTime(new Date());
        jgstation.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        jgstation.setStationType(ConstantValues.Station_StationType_CHANNEL.getCode());
        jgstation.setSecretKey("CePEYAR/Snc=");
        stationService.save(jgstation);
        

        //新增一个投注站点
        Station station2 = new Station();
        String station2Id = "91a740694eeb4dd4b3fca7a71a5e610e";
        station2.setId(station2Id);
        station2.setCode("00002");
        station2.setPassword("123456");
        station2.setRelayable(false);
        station2.setName("1011");
        station2.setDescription("富强投注站");
        station2.setBalance(10000000000L);
        station2.setPhoto("photo");
        station2.setBuildTime(new Date());
        station2.setLastLoginTime(new Date());
        station2.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station2.setStationType(ConstantValues.Station_StationType_Common.getCode());
        station2.setSecretKey("123456");
        stationService.save(station2);
        
        //建行渠道
        station = new Station();
        stationId = "f5091cf9e9754a88ad9b304105e86383";
        station.setId(stationId);
        station.setCode("Q0002");
        station.setPassword("123456");
        station.setRelayable(false);
        station.setName("建行渠道");
        station.setDescription("建行渠道");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_CHANNEL.getCode());
        station.setSecretKey("123456");
        stationService.save(station);

        //工行渠道
        station = new Station();
        stationId = "f5091cf9e9754a88ad9b304105e86384";
        station.setId(stationId);
        station.setCode("Q0006");
        station.setPassword("2tMpcljdB0s=");
        station.setRelayable(false);
        station.setName("工行渠道");
        station.setDescription("工行渠道");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_CHANNEL.getCode());
        station.setSecretKey("2tMpcljdB0s=");
        stationService.save(station);
        
        //农行渠道
        station = new Station();
        stationId = "9d2da2c41aea4a3ebb29e516a09b47ad";
        station.setId(stationId);
        station.setCode("Q0004");
        station.setPassword("123456");
        station.setRelayable(false);
        station.setName("ABC");
        station.setDescription("ABC");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_CHANNEL.getCode());
        station.setSecretKey("123456");
        stationService.save(station);
        
        //新增投注站公有渠道
        station = new Station();
        stationId = "28c1446a0f474172a4ec9f7b87246676";
        station.setId(stationId);
        station.setCode("Q0001");
        station.setPassword("123456");
        station.setRelayable(false);
        station.setName("投注站公有渠道");
        station.setDescription("投注站公有渠道");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_DEFAULT.getCode());
        station.setSecretKey("123456");
        stationService.save(station);
        
        //我中啦出票中心
        station = new Station();
        stationId = "9295ac6d81bb4f778c9660a9909aa987";
        station.setId(stationId);
        station.setCode("C0001");
        station.setPassword("123456");
        station.setRelayable(false);
        station.setName("我中啦出票中心");
        station.setDescription("我中啦出票中心");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_Center.getCode());
        station.setQueueIndex(4000);
        station.setSecretKey("GtUuw3yWiQI=");
        stationService.save(station);
        
        //九歌出票中心
        station = new Station();
        stationId = "eb13c5ab030d42e0826c97e02e120043";
        station.setId(stationId);
        station.setCode("C0002");
        station.setPassword("123456");
        station.setRelayable(false);
        station.setName("九歌出票中心");
        station.setDescription("九歌出票中心");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_Center.getCode());
        station.setSecretKey("123456");
        stationService.save(station);

        //九歌出票中心
        station = new Station();
        stationId = CoreUtil.getUUID();
        station.setId(stationId);
        station.setCode("SYSTEM");
        station.setPassword("4iGXSFkOKXA=");
        station.setRelayable(false);
        station.setName("九歌外部系统");
        station.setDescription("九歌外部系统");
        station.setBalance(10000000000L);
        station.setPhoto("photo");
        station.setBuildTime(new Date());
        station.setLastLoginTime(new Date());
        station.setExpiredTime(DateTimeUtil.getYear(station.getBuildTime(), 2));
        station.setStationType(ConstantValues.Station_StationType_SYSTEM.getCode());
        station.setSecretKey("4iGXSFkOKXA=");
        stationService.save(station);
	}
	
	public static void addStationGame(String stationId, String gameCode, String relayToId)
	{
		addStationGame(stationId, gameCode, relayToId, 100, 9000);
		
	}
	
	public static void addStationGame(String stationId, String gameCode, String relayToId, int rFactor, int pFactor)
	{
		StationGameService stationGameService = DeployContext.getInstance().getBean(
				"stationGameService", StationGameService.class);
		
		StationGame stationGame = new StationGame();
        stationGame.setId(CoreUtil.getUUID());
        stationGame.setStationId(stationId);
        stationGame.setGameCode(gameCode);
        stationGame.setEarlyStopBufferSimplex(1);
        stationGame.setEarlyStopBufferDuplex(1);
        stationGame.setStatus(ConstantValues.StationGame_Status_Open.getCode());
        stationGame.setRelayToId(relayToId);
        stationGame.setrFactor(rFactor);
        stationGame.setpFactor(pFactor);
        stationGameService.save(stationGame);
	}
	
	public static void addTerminal()
	{
		TerminalVersionService terminalVersionService = DeployContext.getInstance().getBean(
				"terminalVersionService", TerminalVersionService.class);
		
		TerminalService terminalService = DeployContext.getInstance().getBean(
				"terminalService", TerminalService.class);
		
		TerminalVersion terminalVersion = new TerminalVersion();
        String terminalVersionId = CoreUtil.getUUID();
        terminalVersion.setId(terminalVersionId);
        terminalVersion.setType(ConstantValues.TerminalVersion_Type_Default.getCode());
        terminalVersion.setVendorName("乐得瑞科技有限公司");
        terminalVersion.setName("ldr-TS001");
        terminalVersion.setCode("TS001");
        terminalVersion.setDescription("");
        terminalVersion.setStatus(ConstantValues.TerminalVersion_Status_Open.getCode());
        terminalVersionService.save(terminalVersion);
        
        String glbStationId = "91a730694eeb4dd4b3fca7a71a5e610d";
        Terminal terminal = new Terminal();
        terminal.setId("b9f8c764ec6542ab8c26c3168b63f633");
        terminal.setStationId(glbStationId);
        terminal.setTerminalVersionId(terminalVersionId);
        terminal.setCode("95201");
        terminal.setPassword("123456");
        terminal.setStatus(ConstantValues.Terminal_Status_Open.getCode());
        terminalService.save(terminal);

        Terminal terminal2 = new Terminal();
        terminal2.setId("b9f8c764ec6542ab8a26c3168b63f633");
        terminal2.setStationId(glbStationId);
        terminal2.setTerminalVersionId(terminalVersionId);
        terminal2.setCode("95202");
        terminal2.setPassword("123456");
        terminal2.setStatus(ConstantValues.Terminal_Status_Open.getCode());
        terminalService.save(terminal2);

        Terminal terminal3 = new Terminal();
        terminal3.setId("b9f8c764e16542ab8a26c3168b63f633");
        terminal3.setStationId(glbStationId);
        terminal3.setTerminalVersionId(terminalVersionId);
        terminal3.setCode("95203");
        terminal3.setPassword("123456");
        terminal3.setStatus(ConstantValues.Terminal_Status_Open.getCode());
        terminalService.save(terminal3);

        Terminal terminal4 = new Terminal();
        terminal4.setId("b9f8c764ec6542ab8a26c3178b63f633");
        terminal4.setStationId(glbStationId);
        terminal4.setTerminalVersionId(terminalVersionId);
        terminal4.setCode("95204");
        terminal4.setPassword("123456");
        terminal4.setStatus(ConstantValues.Terminal_Status_Open.getCode());
        terminalService.save(terminal4);
        
        Terminal terminal5 = new Terminal();
        terminal5.setId(CoreUtil.getUUID());
        terminal5.setStationId(glbStationId);
        terminal5.setTerminalVersionId(terminalVersionId);
        terminal5.setCode("01671");
        terminal5.setPassword("123456");
        terminal5.setStatus(ConstantValues.Terminal_Status_Open.getCode());
        terminalService.save(terminal5);
	}
	
	public static void addTerminalGame()
	{
		StationGameTerminalService stationGameTerminalService = DeployContext.getInstance().getBean(
				"stationGameTerminalService", StationGameTerminalService.class);
		
		//投注站游戏与终端的多对多关系  .只用于配置文件生成，不用作出票判断。
        StationGameTerminal stationGameTerminal = new StationGameTerminal();
        stationGameTerminal.setId(CoreUtil.getUUID());
        stationGameTerminal.setStationGameId("29bcf76d7c914a1ab738a6249a7994e4");
        stationGameTerminal.setTerminalId("b9f8c764ec6542ab8c26c3168b63f633");
        stationGameTerminalService.save(stationGameTerminal);
	}
	
	public static void initPlatForm()
	{
		CPlatformService cPlatformService = DeployContext.getInstance().getBean(
				"cPlatformService", CPlatformService.class);
		
		CPlatform platform = new CPlatform();
        platform.setId(CoreUtil.getUUID());
        platform.setFormKey("system:status");
        platform.setFormValue("open");
        platform.setSponsor("cc");
        platform.setRemarks("平台的打开或关闭");
        platform.setStatus(0);//此条配置可用
        cPlatformService.save(platform);
        
        CPlatform platformBalance = new CPlatform();
        platformBalance.setId(AccountConstants.CPLATFORM_BALANCE_ID);
        platformBalance.setFormKey(AccountConstants.CPLATFORM_BALANCE_KEY);
        platformBalance.setFormValue("平台结算账户");
        platformBalance.setFormLongValue(10000000000L);
        platformBalance.setSponsor("cc");
        platformBalance.setRemarks("平台结算账户");
        platformBalance.setStatus(0);//此条配置可用
        cPlatformService.save(platformBalance);

        CPlatform platform2 = new CPlatform();
        platform2.setId(CoreUtil.getUUID());
        platform2.setFormKey("system:prize");//100万奖金储备金，专款专用。
        platform2.setFormValue("10000000000");
        platform2.setSponsor("cc");
        platform2.setRemarks("发放奖金的储备金账户");
        platform2.setStatus(0);//此条配置可用
        cPlatformService.save(platform2);

        CPlatform platform3 = new CPlatform();
        platform3.setId(CoreUtil.getUUID());
        platform3.setFormKey("system:integral");
        platform3.setFormValue("10000000000"); //100万积分，由cc发行。
        platform3.setSponsor("cc");
        platform3.setRemarks("发放积分的储备金账户");
        platform3.setStatus(0);//此条配置可用
        cPlatformService.save(platform3);
	}
	
	public static void init() throws Exception
	{
        addAdmini();

		addT01();
		addT01Term();
		addT01PrizeDesc(true);
		
		addT02();
		addT02Term();
		addT02PrizeDesc(true);
		
		addT03();
		addT03Term();
		addT03PrizeDesc(true);
		
		addT04();
		addT04Term();
		addT04PrizeDesc(true);
		
		addT05();
		addT05Term();
		addT05PrizeDesc(true);
		
		addF01();
		addF01Term();
		addF01PrizeDesc(true);
		
		addF02();
		addF02Term();
		addF02PrizeDesc(true);
		
		addF03();
		addF03Term();
		addF03PrizeDesc(true);

        addF04();
        addF04Term();
        addF04PrizeDesc(true);
		
		addJcGame();
		addJcMatch("T51");
		
		addT53();
		addT53Term();
		addT53PrizeDesc(true);
		
		addT54();
		addT54Term();
		addT54PrizeDesc(true);
		
		
		
		addArea();
		
		addConservator();
		
		addStation();
		
		String sId = "91a730694eeb4dd4b3fca7a71a5e610d";
		addStationGame(sId, "T01", sId);
		addStationGame(sId, "T02", sId);
		addStationGame(sId, "T03", sId);
		addStationGame(sId, "T04", sId);
		addStationGame(sId, "T05", sId);
		addStationGame(sId, "F03", sId);
		addStationGame(sId, "F02", sId);
		addStationGame(sId, "F01", sId);
		addStationGame(sId, "T51", sId);
		addStationGame(sId, "T53", sId);
		addStationGame(sId, "T54", sId);
        addStationGame(sId, "F04", sId);
		
		sId = "91a730694eeb4dd4b3fca7a71a5e610e";
		addStationGame(sId, "T01", sId);
		addStationGame(sId, "T02", sId);
		addStationGame(sId, "T03", sId);
		addStationGame(sId, "T04", sId);
		addStationGame(sId, "T05", sId);
		addStationGame(sId, "F03", sId);
		addStationGame(sId, "F02", sId);
		addStationGame(sId, "F01", sId);
		addStationGame(sId, "T51", sId);
		addStationGame(sId, "T53", sId);
		addStationGame(sId, "T54", sId);
        addStationGame(sId, "F04", sId);
		
		//九歌渠道，转票到我中啦接口
		sId = "24fb88b47c694ec4880ce36d3293e647";
		String relayToId = "9295ac6d81bb4f778c9660a9909aa987";
		addStationGame(sId, "T01", relayToId);
		addStationGame(sId, "T02", relayToId);
		addStationGame(sId, "T03", relayToId);
		addStationGame(sId, "T04", relayToId);
		addStationGame(sId, "T05", relayToId);
		addStationGame(sId, "F03", relayToId);
		addStationGame(sId, "F02", relayToId);
		addStationGame(sId, "F01", relayToId);
		addStationGame(sId, "T51", relayToId);
		addStationGame(sId, "T53", relayToId);
		addStationGame(sId, "T54", relayToId);
        addStationGame(sId, "F04", relayToId);
		
		//我中啦出票中心转到自身
		sId = "9295ac6d81bb4f778c9660a9909aa987";
		relayToId = "9295ac6d81bb4f778c9660a9909aa987";
		addStationGame(sId, "T01", relayToId);
		addStationGame(sId, "T02", relayToId);
		addStationGame(sId, "T03", relayToId);
		addStationGame(sId, "T04", relayToId);
		addStationGame(sId, "T05", relayToId);
		addStationGame(sId, "F03", relayToId);
		addStationGame(sId, "F02", relayToId);
		addStationGame(sId, "F01", relayToId);
		addStationGame(sId, "T51", relayToId);
		addStationGame(sId, "T53", relayToId);
		addStationGame(sId, "T54", relayToId);
        addStationGame(sId, "F04", relayToId);
		
		//建行渠道
		sId = "f5091cf9e9754a88ad9b304105e86383";
		//建行渠道全部转票到九歌出票中心
		relayToId = "eb13c5ab030d42e0826c97e02e120043";
		addStationGame(sId, "T01", relayToId);
		addStationGame(sId, "T02", relayToId);
		addStationGame(sId, "T03", relayToId);
		addStationGame(sId, "T04", relayToId);
		addStationGame(sId, "T05", relayToId);
		addStationGame(sId, "F03", relayToId);
		addStationGame(sId, "F02", relayToId);
		addStationGame(sId, "F01", relayToId);
		addStationGame(sId, "T51", relayToId);
		addStationGame(sId, "T53", relayToId);
		addStationGame(sId, "T54", relayToId);
        addStationGame(sId, "F04", relayToId);

        //工行渠道
        sId = "f5091cf9e9754a88ad9b304105e86384";
        //工行渠道全部转票到九歌出票中心
        relayToId = "9295ac6d81bb4f778c9660a9909aa987";
        addStationGame(sId, "T01", relayToId);
        addStationGame(sId, "T02", relayToId);
        addStationGame(sId, "T03", relayToId);
        addStationGame(sId, "T04", relayToId);
        addStationGame(sId, "T05", relayToId);
        addStationGame(sId, "F03", relayToId);
        addStationGame(sId, "F02", relayToId);
        addStationGame(sId, "F01", relayToId);
        addStationGame(sId, "T51", relayToId);
        addStationGame(sId, "T53", relayToId);
        addStationGame(sId, "T54", relayToId);
        addStationGame(sId, "F04", relayToId);
		
		//公有渠道销售的游戏
		sId = "28c1446a0f474172a4ec9f7b87246676";
		addStationGame(sId, "T01", sId);
		addStationGame(sId, "T02", sId);
		addStationGame(sId, "T03", sId);
		addStationGame(sId, "T04", sId);
		addStationGame(sId, "T05", sId);
		addStationGame(sId, "F03", sId);
		addStationGame(sId, "F02", sId);
		addStationGame(sId, "F01", sId);
		addStationGame(sId, "T51", sId);
		addStationGame(sId, "T53", sId);
		addStationGame(sId, "T54", sId);
        addStationGame(sId, "F04", relayToId);
		
		//九歌出票中心支持的游戏
		sId = "eb13c5ab030d42e0826c97e02e120043";
		addStationGame(sId, "T01", sId);
		addStationGame(sId, "T02", sId);
		addStationGame(sId, "T03", sId);
		addStationGame(sId, "T04", sId);
		addStationGame(sId, "T05", sId);
		addStationGame(sId, "F03", sId);
		addStationGame(sId, "F02", sId);
		addStationGame(sId, "F01", sId);
		addStationGame(sId, "T51", sId);
		addStationGame(sId, "T53", sId);
		addStationGame(sId, "T54", sId);
        addStationGame(sId, "F04", relayToId);
		
		addCustomer();
		
		sId = "24fb88b47c694ec4880ce36d3293e647";
		
		addCustomerAccount("d95e5e0207c548bd9200e9072052bd66", "24fb88b47c694ec4880ce36d3293e647", 10000000000L, 1000000, 0);
		
		//limitee2-Q0003
		addCustomerAccount("04f94fc02f0a4069ba75d44b4097bce2", "24fb88b47c694ec4880ce36d3293e647", 10000000000L, 1000000, 0);
		
		addCustomerAccount("047c9af002174b0692a75320d372f24b", "91a730694eeb4dd4b3fca7a71a5e610d", 10000000000L, 1000000, 0);
		addCustomerAccount("d95e5e0207c548bd9200e9072052bd66", "91a730694eeb4dd4b3fca7a71a5e610d", 10000000000L, 1000000, 0);
		
		addCustomerAccount("047c9af002174b0692a75320d372f24b", "91a730694eeb4dd4b3fca7a71a5e610e", 10000000000L, 1000000, 0);
		addCustomerAccount("d95e5e0207c548bd9200e9072052bd66", "91a730694eeb4dd4b3fca7a71a5e610e", 10000000000L, 1000000, 0);
		
		addCustomerAccount("d95e5e0207c548bd9200e9072052bd66", "f5091cf9e9754a88ad9b304105e86383", 10000000000L, 1000000, 0);
		
		//渠道用户
		addCustomerAccount("cc5b0c0202db405998d219f506533887", "f5091cf9e9754a88ad9b304105e86383", 10000000000L, 1000000, 0);
		
		addTerminal();
		addTerminalGame();
		
		initPlatForm();
		
		cmbcStation();
	}

	public static void main(String[] args) throws Exception {
        long startMatchCode = 201312172001L;
        for(int i = 0; i < 7; i++)
        {
            String oName = "中国赔率"; String oCode = "CN"; String gameCode = "T51";
            String matchCode = String.valueOf(startMatchCode + i);

            String playTypeCode = "01"; String oddsInfo = "4.22|3.12|2.02";
            addJcOdds(oCode, oName, gameCode, matchCode, playTypeCode, oddsInfo);

            playTypeCode = "02"; oddsInfo = "5.22|3.12|2.02"; addJcOdds(oCode,
                oName, gameCode, matchCode, playTypeCode, oddsInfo);

            playTypeCode = "03"; oddsInfo = "5.24,3.12,2.02,4.00,5.00,6.00,7.00,8.00|1.24,2.12,3.02,4.00|2.24,3.12,2.02,4.00,5.00,6.00,7.00,8.00;";
            addJcOdds(oCode, oName, gameCode, matchCode, playTypeCode, oddsInfo);

            playTypeCode = "04"; oddsInfo = "5.24,3.12,2.02,4.00,5.00,6.00,7.00,8.00"; addJcOdds(oCode, oName, gameCode, matchCode, playTypeCode, oddsInfo);

            playTypeCode = "05"; oddsInfo = "5.24,3.12,2.02,4.00,5.00,6.00,7.00,8.00,9.24"; addJcOdds(oCode, oName, gameCode, matchCode, playTypeCode, oddsInfo);
        }
	}

}
