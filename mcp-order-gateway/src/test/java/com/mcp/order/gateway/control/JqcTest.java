/**
 * 
 */
package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
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

/**
 * @author ming.li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class JqcTest {

	private static Logger log = Logger.getLogger(JqcTest.class);
	
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
	 * 投注
	 * @throws Exception
	 */
	@Test
	public void testLot() throws Exception {
		log.info("测试进球彩投注...................");
		String channelCode = "Q0001";
		
		//用户登录
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		cmd = "T01";
		
		//单式投注
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"01\", \"amount\":200, \"multiple\":1, \"numbers\":\"3|3|3|3|3|3|3|3\"}]";
		body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2014022\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T54\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		//复式投注
		ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"01\", \"amount\":1800, \"multiple\":1, \"numbers\":\"3,2,1|3,1,0|3|3|3|3|3|3\"}]";
		body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2014022\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T54\", \"type\":0, \"amount\":1800, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
}
