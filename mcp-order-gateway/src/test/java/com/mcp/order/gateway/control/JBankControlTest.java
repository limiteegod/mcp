/**
 * 
 */
package com.mcp.order.gateway.control;

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
import java.util.Map;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author ming.li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class JBankControlTest {

	private static Logger log = Logger.getLogger(JBankControlTest.class);
	
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
	 * 用户登录
	 * @throws Exception
	 */
	@Test
	public void testJ01() throws Exception {
		/*log.info("建行用户登录...................");
		String channelCode = "Q0002";
		
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String cmd = "P05";
		String stationCode = "Q0002";
		String body = "{\"code\":\"" + stationCode + "\", \"password\":\"" + 123456 + "\", \"type\":" + Constants.STATION_OP_LOGIN + "}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "J01";
		body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "J01";
		body = "{\"name\":\"limitee2\", \"password\":\"123456\", \"prize\":500}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "J01";
		body = "{\"name\":\"limitee222\", \"password\":\"123456\", \"recharge\":10000, \"prize\":500}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);*/
	}
	
	/**
	 * 用户注册
	 * @throws Exception
	 */
	@Test
	public void testJ04() throws Exception {
		/*log.info("建行新用户注册...................");
		String channelCode = "Q0002";
		
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String cmd = "P05";
		String stationCode = "Q000201";
		String body = "{\"code\":\"" + stationCode + "\", \"password\":\"" + 123456 + "\", \"type\":" + Constants.STATION_OP_LOGIN + "}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "J04";
		body = "{\"name\":\"" + CoreUtil.getUUID() + "\", \"password\":\"123456\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	/**
	 * 用户登录之后，查询用户信息
	 * @throws Exception
	 */
	@Test
	public void testJ02() throws Exception {
		/*String channelCode = "Q0002";
		Map<String, Cookie> aMap = getUMap(getPMap());
		String cmd = "J02";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	/**
	 * 用户登录之后，修改用户信息
	 * @throws Exception
	 */
	@Test
	public void testJ03() throws Exception {
		/*String channelCode = "Q0002";
		Map<String, Cookie> aMap = getUMap(getPMap());
		String cmd = "J03";
		String body = "{\"realName\":\"李明明\", \"identityId\":\"123321\", \"cardNumber\":\"0909090909\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	/**
	 * 用户使用第三方支付投注，然后通过第三方完成支付
	 * @throws Exception
	 */
	@Test
	public void testJ05() throws Exception {
		/*String channelCode = "Q0002";
		Map<String, Cookie> pMap = getPMap();
		Map<String, Cookie> aMap = getUMap(pMap);
		Map<String, Cookie> sysMap = getJbSystemMap();
		
		//第三方支付
		String cmd = "T01";
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06|02\"}]";
		String body = "{\"payType\":" + ConstantValues.TOrder_PayType_Company.getCode() + ", \"order\":{\"tickets\":" + ticketsMessage + ", \"gameCode\":\"F01\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		String orderId = JsonPath.read(repBody, "$.order.id");
		
		cmd = "J05";
		body = "{\"orderId\":\"" + orderId + "\", \"stationCode\":\"" + channelCode + "\", \"payType\":6}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, sysMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	/**
	 * 查询游戏的当前期
	 * @throws Exception
	 */
	@Test
	public void testJ06() throws Exception {
		/*String channelCode = "Q0002";
		
		String cmd = "J06";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, null);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	/**
	 * 当期投注记录
	 * @throws Exception
	 */
	@Test
	public void testJ07() throws Exception {
		/*String channelCode = "Q0002";
		Map<String, Cookie> pMap = getPMap();
		Map<String, Cookie> aMap = getUMap(pMap);
		
		String cmd = "J07";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	/**
	 * 上期中奖记录
	 * @throws Exception
	 */
	@Test
	public void testJ08() throws Exception {
		/*String channelCode = "Q0002";
		Map<String, Cookie> pMap = getPMap();
		Map<String, Cookie> aMap = getUMap(pMap);
		
		String cmd = "J08";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);*/
	}
	
	private Map<String, Cookie> getUMap(Map<String, Cookie> pMap) throws Exception
	{
		/*String channelCode = "Q0002";
		
		String cmd = "J01";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		String userId = JsonPath.read(repBody, "$.cookie.userId");
		String userType = String.valueOf(JsonPath.read(repBody, "$.cookie.userType"));
		String userMd5 = JsonPath.read(repBody, "$.cookie.userMd5");
		Cookie cId = new Cookie("userId", userId);
		Cookie cType = new Cookie("userType", userType);
		Cookie cMd5 = new Cookie("userMd5", userMd5);
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		aMap.put("userId", cId);
		aMap.put("userType", cType);
		aMap.put("userMd5", cMd5);
		
		return aMap;*/
		return null;
	}
	
	private Map<String, Cookie> getPMap() throws Exception
	{
		/*String channelCode = "Q0002";
		
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String cmd = "P05";
		String stationCode = "Q0002";
		String body = "{\"code\":\"" + stationCode + "\", \"password\":\"" + 123456 + "\", \"type\":" + Constants.STATION_OP_LOGIN + "}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		return pMap;*/
		return null;
	}
	
	private Map<String, Cookie> getJbSystemMap() throws Exception
	{
		/*String channelCode = "";
		Map<String, Cookie> pMap = new HashMap<String, Cookie>();
		String cmd = "P05";
		String stationCode = "SYS0001";
		String body = "{\"code\":\"" + stationCode + "\", \"password\":\"" + 123456 + "\", \"type\":" + Constants.STATION_OP_LOGIN + "}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, pMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		return pMap;*/
		return null;
	}
}
