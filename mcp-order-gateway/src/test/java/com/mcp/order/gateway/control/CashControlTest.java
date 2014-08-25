/**
 * 
 */
package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
import com.mcp.order.common.ConstantValues;
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

/**
 * @author ming.li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class CashControlTest {

	//private static Logger log = Logger.getLogger(JcTradeControlTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	/**
	 * 奖金转投注金
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "C01";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		body = "{\"stationId\":\"" + stationId + "\", \"amount\":100}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		cmd = "C03";
		body = "{\"stationId\":\"" + stationId + "\", \"amount\":100}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 在投注站充值
	 * @throws Exception
	 */
	@Test
	public void testRechargeAtStation() throws Exception {
		String channelCode = "Q0003";
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		
		String cmd = "C02";
		String name = "limitee2";
		String body = "{\"name\":\"" + name + "\", \"amount\":100, \"fromType\":" + ConstantValues.Recharge_Channel_Alipay.getCode() + ", \"orderId\":\"123456\"}";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 渠道用户提现
	 * @throws Exception
	 */
	@Test
	public void testPrizeToOut() throws Exception {
		String channelCode = "Q0002";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "C01";
		body = "{\"amount\":100}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		cmd = "C03";
		body = "{\"amount\":100}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
}
