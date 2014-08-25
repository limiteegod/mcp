/**
 * 
 */
package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
import com.mcp.order.exception.ErrCode;
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
public class ChannelControlTest {

	private static Logger log = Logger.getLogger(ChannelControlTest.class);
	
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
	 * @throws Exception
	 */
	@Test
	public void tesCLot() throws Exception {
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0003";
		String cmd = "T03";
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,09,12,14,19,30\"}]";
		String body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"gameCode\":\"F03\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);
	}
	
}
