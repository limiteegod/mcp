/**
 * 
 */
package com.mcp.order.gateway.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author ming.li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class SchemeTradeControlTest {

	//private static Logger log = Logger.getLogger(JcTradeControlTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	//private MockMvc mockMvc;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	/**
	 * 七乐彩投注
	 * @throws Exception
	 */
	@Test
	public void testSchemeZhLot() throws Exception {
		/*String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "S01";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		body = "{\"scheme\":{\"winStop\":false,\"payType\":1,\"platform\":\"IOS\",\"amount\":1200,\"multiplier\":2,\"orderCount\":3,\"stationId\":\"" + stationId + "\",\"gameCode\":\"T01\",\"numList\":\"00~00~01,02,03,04,06|02,04~400\",\"startTermCode\":\"13089\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		body = "{\"scheme\":{\"winStop\":true,\"payType\":1,\"platform\":\"IOS\",\"amount\":2400,\"multiplier\":2,\"orderCount\":3,\"stationId\":\"" + stationId + "\",\"gameCode\":\"T01\",\"numList\":\"00~00~01,02,03,04,06|02,04~400!00~00~01,02,03,04,06|02,04~400\",\"startTermCode\":\"13089\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");*/
	}
}
