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
public class ResourceControlTest {

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
	 * 获得验证码
	 * @throws Exception
	 */
	@Test
	public void testGetAuthCode() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "R01";
		body = "{\"phone\":\"12345678901\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 找回密码
	 * @throws Exception
	 */
	@Test
	public void testFindBackPwd() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		
		String cmd = "R02";
		String body = "{\"name\":\"limitee2\", \"phone\":\"12345678901\", \"password\":\"654321\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		body = "{\"name\":\"limitee2\", \"phone\":\"12345678901\", \"password\":\"123456\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 绑定银行卡
	 * @throws Exception
	 */
	@Test
	public void testBindBack() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "R03";
		body = "{\"unique\":true,\"cardNumber\":\"123456\",\"bankNo\":\"12345\",\"provinceNo\":\"12345\",\"cityNo\":\"12345\",\"areaNo\":\"12345\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
	/**
	 * 绑定银行卡
	 * @throws Exception
	 */
	@Test
	public void testBindPhone() throws Exception {
		String channelCode = "Q0001";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "R04";
		String body = "{\"name\":\"limitee2\", \"phone\":\"18612110987\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		body = "{\"name\":\"limitee2\", \"phone\":\"12345678901\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
}
