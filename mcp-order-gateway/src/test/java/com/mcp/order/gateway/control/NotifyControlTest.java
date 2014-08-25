/**
 * 
 */
package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
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
public class NotifyControlTest {

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
	 * 七乐彩投注
	 * @throws Exception
	 */
	@Test
	public void testMatchNotify() throws Exception {
		String channelCode = "C0001";
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "N03";
		String body = "{\"gameCode\":\"T51\",\"matchCode\":\"201312172001\",\"drawNumber\":\"0:0|0:2|1|1|0:2|2|01\"}";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 期次通知
	 * @throws Exception
	 */
	@Test
	public void testTermNotify() throws Exception {
		/*String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "N04";
		String body = "{\"terms\":[{\"matchTime\":\"2014-02-04T15:41:53.132+0800\", \"openTime\":\"2014-02-04T15:41:53.132+0800\", \"endTime\":\"2014-02-05T15:41:53.132+0800\", \"gameCode\":\"T51\", \"code\":\"20130104001\", \"status\":1100}]}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");*/
	}
	
	/**
	 * 出票通知
	 * @throws Exception
	 */
	@Test
	public void testPrintNotify() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "N05";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 兑奖通知
	 * @throws Exception
	 */
	@Test
	public void testReceiptNotify() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "N06";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 赔率通知
	 * @throws Exception
	 */
	@Test
	public void testJoddsNotify() throws Exception {
		String channelCode = "C0001";
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "N07";
		String body = "{\"oddsList\":[{\"oddsCode\":\"CN\", \"oddsInfo\":\"5.22|3.12|2.02\", \"gameCode\":\"T51\", \"matchCode\":\"201312172001\", \"oddsName\":\"中国赔率\", \"playTypeCode\":\"02\"}]}";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
}
