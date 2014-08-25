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
public class JcTradeControlTest {

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
	 * 投注站公有渠道投注
	 * @throws Exception
	 */
	@Test
	public void testJcFootballLot() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "T05";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		body = "{\"payType\":1, \"order\":{\"multiple\":1,\"number\":\"01|201312172001|1@1.0,2@3.2;01|201312172002|1@1.1,2@2.1;01|201312172003|1@1.2,3@1.8;01|201312172005|1@1.3,2@2.4,3@3.5;01|201312172006|1@1.4,2@2.1,3@2.78;01|201312172007|1@1.5\", \"betType\":\"516\", \"playType\":\"01\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T51\", \"type\":0, \"amount\":276200, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		body = "{\"payType\":1, \"order\":{\"multiple\":1,\"number\":\"01|201312172001|1@1.0,2@3.2;01|201312172002|1@1.1,2@2.1;01|201312172003|1@1.2,3@1.8;01|201312172005|1@1.3,2@2.4,3@3.5;01|201312172006|1@1.4,2@2.1,3@2.78;01|201312172007|1@1.5\", \"betType\":\"516\", \"playType\":\"01\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T51\", \"type\":0, \"amount\":276200, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		String number = "$01|201312172001|1@1.0,2&02|201312172001|1@1.0;21|201312172002|1@1.1,2;21|201312172003|1@1.2,3,0&22|201312172003|1@1.2,3;21|201312172005|1@1.3,2,3;21|201312172006|1@1.4,2,3;21|201312172006|1@1.5";
		body = "{\"payType\":1, \"order\":{\"multiple\":1,\"number\":\"" + number + "\", \"betType\":\"31,21\", \"playType\":\"06\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T51\", \"type\":0, \"amount\":52800, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
}
