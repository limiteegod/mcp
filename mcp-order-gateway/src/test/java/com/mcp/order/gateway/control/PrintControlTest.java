package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
import com.mcp.core.util.CoreUtil;
import com.mcp.order.service.CustomerAccountService;
import com.mcp.order.service.CustomerService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TicketService;
import com.mcp.order.status.ReceiptState;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class PrintControlTest {

	private static Logger log = Logger.getLogger(PrintControlTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	/**
	 * 一键注册功能，如果用户已经存在，则修改用户密码，返回用户信息，否则注册新用户
	 * @throws Exception
	 */
	@Test
	public void testRegUser() throws Exception {
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String channelCode = "Q0003";
		String cmd = "P07";
		String name = "limitee21";
		String body = "{\"customer\":{\"name\":\"" + name + "\", \"phone\":\"" + CoreUtil.getUUID() + "\", \"identityId\":\"" + CoreUtil.getUUID() + "\", \"password\":\"123456\"}}";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.customer.name"), name);
	}
	
	@Test
	public void testAll() throws Exception {
		log.info("测试出票...........");
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "T01";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		orderService.deleteByStationId(stationId);

		//投注（投注站账户支付）
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":400, \"multiple\":2, \"numbers\":\"09,12,22,32,34|02,04\"},{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":400, \"multiple\":2, \"numbers\":\"09,12,22,32,34|02,04\"}]";
		body = "{\"payType\":1, \"order\":{\"multiple\":2, \"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"schemeId\":\"\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":800, \"platform\":\"IOS\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.order.status"), 1100);
		String orderId = JsonPath.read(repBody, "$.order.id");
	
		String stationCode = "00001";
		body = "{\"size\":10,\"gameCodes\":[\"T01\",\"T02\"]}";
		cmd = "P01";
		raRepString = TestUtil.cRequest(mockMvc, stationCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		String firstTicketId = JsonPath.read(repBody, "$.tickets[0].ticketId");
		String secondTicketId = JsonPath.read(repBody, "$.tickets[1].ticketId");
		
		int code = 0;
		String stubInfo = "5555566666\\n123456 EFJEKSWF 9999\\n2222";
		cmd = "P02";
		String terminalCode = "01671";
		body = "{\"ticketId\":\"" + firstTicketId + "\", \"terminalCode\":\"" + terminalCode + "\", \"code\":" + code + ", \"stubInfo\":\"" + stubInfo + "\"}";
		raRepString = TestUtil.cRequest(mockMvc, stationCode, cmd, body, pMap);
		
		body = "{\"ticketId\":\"" + secondTicketId + "\", \"terminalCode\":\"" + terminalCode + "\", \"code\":" + code + ", \"stubInfo\":\"" + stubInfo + "\", \"paper\":\"true\"}";
		raRepString = TestUtil.cRequest(mockMvc, stationCode, cmd, body, pMap);
		
		//用户查询订单状态
		body = "{\"orderId\":\"" + orderId + "\"}";
        cmd = "Q03";
        raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
        repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.rst[0].status"), 1200);
		
		//更新为等待刷票
		ticketService.updateReceiptStatusById(ReceiptState.NOT_CLAIM_PRIZE.getCode(), firstTicketId);
		ticketService.updateCheckInfo("", 1000, firstTicketId);
		
		ticketService.updateReceiptStatusById(ReceiptState.NOT_CLAIM_PRIZE.getCode(), secondTicketId);
		ticketService.updateCheckInfo("", 1000, secondTicketId);
		
		cmd = "P03";
		String gameCode = "T01";
		String gameTermCode = "13089";
		int size = 10;
		body = "{\"terminalCode\":\"" + terminalCode + "\", \"gameCode\":\"" + gameCode + "\", \"gameTermCode\":\"" + gameTermCode + "\", \"size\":" + size + "}";
		raRepString = TestUtil.cRequest(mockMvc, stationCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		String receipFirstId = JsonPath.read(repBody, "$.tickets[0].ticketId");
		String receipSecondId = JsonPath.read(repBody, "$.tickets[1].ticketId");
		
		code = 0;
		cmd = "P04";
		body = "{\"ticketId\":\"" + receipFirstId + "\", \"code\":" + code + "}";
		raRepString = TestUtil.cRequest(mockMvc, stationCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		body = "{\"ticketId\":\"" + receipSecondId + "\", \"code\":" + code + "}";
		raRepString = TestUtil.cRequest(mockMvc, stationCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	@Test
	public void testPrintById() throws Exception {
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "T01";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";

		//投注（投注站账户支付）
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":400, \"multiple\":2, \"numbers\":\"09,12,22,32,34|02,04\"},{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":400, \"multiple\":2, \"numbers\":\"09,12,22,32,34|02,04\"}]";
		body = "{\"payType\":1, \"order\":{\"multiple\":2, \"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"schemeId\":\"\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":800, \"platform\":\"IOS\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.order.status"), 1100);
		
		String cCode = "C0001";
		String orderId = JsonPath.read(repBody, "$.order.id");
		body = "{\"orderId\":\"" + orderId + "\"}";
		cmd = "P06";
		raRepString = TestUtil.cRequest(mockMvc, cCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		String firstTicketId = JsonPath.read(repBody, "$.tickets[0].id");
		String secondTicketId = JsonPath.read(repBody, "$.tickets[1].id");
		
		int code = 0;
		cmd = "P02";
		body = "{\"ticketId\":\"" + firstTicketId + "\", \"code\":" + code + "}";
		raRepString = TestUtil.request(mockMvc, cCode, cmd, body, pMap);
		
		body = "{\"ticketId\":\"" + secondTicketId + "\", \"code\":" + code + "}";
		raRepString = TestUtil.request(mockMvc, cCode, cmd, body, pMap);
	}
	
	/**
	 * 订单出票成功
	 * @throws Exception
	 */
	@Test
	public void testPrintPaper() throws Exception {
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String channelCode = "C0002";
		String customerId = "047c9af002174b0692a75320d372f24b";
		String body = "{\"customerId\":\"" + customerId + "\"}";
		String cmd = "P09";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
}
