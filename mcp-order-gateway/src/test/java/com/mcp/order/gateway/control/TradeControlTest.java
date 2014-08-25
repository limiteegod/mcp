/**
 * 
 */
package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.common.ParameterUtil;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.service.*;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author ming.li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class TradeControlTest {

	private static Logger log = Logger.getLogger(TradeControlTest.class);
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private StationService stationService;

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
	public void testQlcLot() throws Exception {
		
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		cmd = "T01";
		for(int i = 0; i < 1; i++)
		{
			//单式投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,09,12,14,19,30\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F03\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
			repBody = JsonPath.read(raRepString, "$.body");
			assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
			
			//复式投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"00\", \"amount\":1600, \"multiple\":1, \"numbers\":\"01,02,04,09,12,14,19,30\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F03\", \"type\":0, \"amount\":1600, \"platform\":\"ANDROID\"}}";
			raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
			repBody = JsonPath.read(raRepString, "$.body");
			assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
			
			//胆拖投注
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":2000, \"multiple\":1, \"numbers\":\"01,02,04,09$12,14,19,29,30\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F03\", \"type\":0, \"amount\":2000, \"platform\":\"ANDROID\"}}";
			raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
			repBody = JsonPath.read(raRepString, "$.body");
			assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		}
	}
	
	/**
	 * 双色球投注
	 * @throws Exception
	 */
	@Test
	public void testSsqLot() throws Exception {
		String userId = "047c9af002174b0692a75320d372f24b";
		String channelCode = "Q0001";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "T01";
		for(int i = 0; i < 1; i++)
		{
			//单式投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06|02\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F01\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
			repBody = JsonPath.read(raRepString, "$.body");
			assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
			
			//复式投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"00\", \"amount\":1400, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06,07|02\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F01\", \"type\":0, \"amount\":1400, \"platform\":\"ANDROID\"}}";
			raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
			repBody = JsonPath.read(raRepString, "$.body");
			assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
			
			//胆拖投注
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":2400, \"multiple\":1, \"numbers\":\"05,13,21,29,30$20,27,28,33|05,06,07\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F01\", \"type\":0, \"amount\":2400, \"platform\":\"ANDROID\"}}";
			raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
			repBody = JsonPath.read(raRepString, "$.body");
			assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		}
	}
	
	/**
	 * plw投注
	 * @throws Exception
	 */
	@Test
	public void testPlwLot() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		for(int i = 0; i < 10; i++)
		{
			//单式投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"1|2|3|4|5\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T04\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//复式投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"00\", \"amount\":6400, \"multiple\":1, \"numbers\":\"1,2|2,3|3,4|4,6|5,6\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T04\", \"type\":0, \"amount\":6400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
		}
	}
	
	/**
	 * esf投注
	 * @throws Exception
	 */
	@Test
	public void testEsfLot() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		for(int i = 0; i < 1; i++)
		{
			//任一复式投注
			String ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"21\", \"amount\":400, \"multiple\":1, \"numbers\":\"01,02\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":400, \"platform\":\"ANDROID\"}}";
			ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任二单式
			ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"22\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任二复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"22\", \"amount\":600, \"multiple\":1, \"numbers\":\"01,02,03\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任二胆拖
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"22\", \"amount\":400, \"multiple\":1, \"numbers\":\"01$02,03\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任三单式
			ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"23\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任三复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"23\", \"amount\":800, \"multiple\":1, \"numbers\":\"01,02,03,04\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":800, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任三胆拖
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"23\", \"amount\":600, \"multiple\":1, \"numbers\":\"01$02,03,04\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任四单式
			ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"24\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任四复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"24\", \"amount\":1000, \"multiple\":1, \"numbers\":\"01,02,03,04,05\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":1000, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任四胆拖
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"24\", \"amount\":600, \"multiple\":1, \"numbers\":\"01,02,03$04,05,06\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任五单式
			ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"25\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04,05\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任五复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"25\", \"amount\":1200, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":1200, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//任五胆拖
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"25\", \"amount\":600, \"multiple\":1, \"numbers\":\"01,02,03,04$05,06,07\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//前二直选定位
			ticketsMessage = "[{\"betTypeCode\":\"07\", \"playTypeCode\":\"30\", \"amount\":400, \"multiple\":1, \"numbers\":\"01,02|01,02\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013101009\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T05\", \"type\":0, \"amount\":400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
		}
	}
	
	/**
	 * fsd投注
	 * @throws Exception
	 */
	@Test
	public void testFsdLot() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		for(int i = 0; i < 1; i++)
		{
			//直选单式投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"01\", \"amount\":200, \"multiple\":1, \"numbers\":\"1|2|3\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F02\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选复式投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"01\", \"amount\":1600, \"multiple\":1, \"numbers\":\"1,2|2,3|3,4\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F02\", \"type\":0, \"amount\":1600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选和值投注
			ticketsMessage = "[{\"betTypeCode\":\"03\", \"playTypeCode\":\"01\", \"amount\":7600, \"multiple\":1, \"numbers\":\"03,21\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F02\", \"type\":0, \"amount\":7600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组三复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"02\", \"amount\":400, \"multiple\":1, \"numbers\":\"1,9\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F02\", \"type\":0, \"amount\":400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组六复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"03\", \"amount\":800, \"multiple\":1, \"numbers\":\"1,2,3,4\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F02\", \"type\":0, \"amount\":800, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组选和值
			ticketsMessage = "[{\"betTypeCode\":\"03\", \"playTypeCode\":\"04\", \"amount\":1400, \"multiple\":1, \"numbers\":\"05,25\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F02\", \"type\":0, \"amount\":1400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
		}
	}
	
	/**
	 * pls投注
	 * @throws Exception
	 */
	@Test
	public void testPlsLot() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		for(int i = 0; i < 1; i++)
		{
			//直选单式投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"01\", \"amount\":200, \"multiple\":1, \"numbers\":\"1|2|3\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选复式投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"01\", \"amount\":1600, \"multiple\":1, \"numbers\":\"1,2|2,3|3,4\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":1600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选和值投注
			ticketsMessage = "[{\"betTypeCode\":\"03\", \"playTypeCode\":\"01\", \"amount\":7600, \"multiple\":1, \"numbers\":\"03,21\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":7600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选组合复式投注
			ticketsMessage = "[{\"betTypeCode\":\"04\", \"playTypeCode\":\"01\", \"amount\":4800, \"multiple\":1, \"numbers\":\"4,5,6,7\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":4800, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选组合胆拖投注
			ticketsMessage = "[{\"betTypeCode\":\"05\", \"playTypeCode\":\"01\", \"amount\":2400, \"multiple\":1, \"numbers\":\"4,5$6,7\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":2400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//直选跨度复式
			ticketsMessage = "[{\"betTypeCode\":\"06\", \"playTypeCode\":\"01\", \"amount\":21600, \"multiple\":1, \"numbers\":\"1,9\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":21600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组三复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"02\", \"amount\":400, \"multiple\":1, \"numbers\":\"1,9\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组三胆拖
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"02\", \"amount\":800, \"multiple\":1, \"numbers\":\"1$2,9\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":800, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组三跨度
			ticketsMessage = "[{\"betTypeCode\":\"06\", \"playTypeCode\":\"02\", \"amount\":4000, \"multiple\":1, \"numbers\":\"1,9\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":4000, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组六复式
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"03\", \"amount\":800, \"multiple\":1, \"numbers\":\"1,2,3,4\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":800, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组六胆拖
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"03\", \"amount\":600, \"multiple\":1, \"numbers\":\"1$2,3,4\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组六跨度
			ticketsMessage = "[{\"betTypeCode\":\"06\", \"playTypeCode\":\"03\", \"amount\":4400, \"multiple\":1, \"numbers\":\"2,3\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":4400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组选单式
			ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"04\", \"amount\":400, \"multiple\":1, \"numbers\":\"1,2,2;4,5,6\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//组选和值
			ticketsMessage = "[{\"betTypeCode\":\"03\", \"playTypeCode\":\"04\", \"amount\":1400, \"multiple\":1, \"numbers\":\"05,25\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T03\", \"type\":0, \"amount\":1400, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
		}
	}
	
	/**
	 * 七星彩投注
	 * @throws Exception
	 */
	@Test
	public void testQxcLot() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		for(int i = 0; i < 5; i++)
		{
			//单式投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"1|2|3|4|5|6|2\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T02\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
			ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
			
			//复式投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"00\", \"amount\":1600, \"multiple\":1, \"numbers\":\"1|2|3|4,6|5,9|6|2,7\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T02\", \"type\":0, \"amount\":1600, \"platform\":\"ANDROID\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			tradeRa.andExpect(status().isOk());
			log.info(tradeRepString);
		}
	}

	/**
	 * 投注
	 * @throws Exception
	 */
	@Test
	public void testDltLot() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		customerAccountService.incrRechageByCustomerIdAndStationId(100000000, userId, stationId);
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		for(int i = 0; i < 1; i++)
		{
			//投注
			String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":1000, \"multiple\":1, \"numbers\":\"01,02,03,04,06|02,05;01,02,03,04,06|02,05;01,02,03,04,06|02,05;01,02,03,04,06|02,05;01,02,03,04,06|02,05\"}]";
			//String schemeMsg = "{\"type\":0, \"totalIssue\":3, \"winStop\":0}";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":1000, \"platform\":\"IOS\"}}";
			ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			log.info(tradeRepString);
			tradeRa.andExpect(status().isOk());
			
			//投注
			ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":400, \"multiple\":1, \"numbers\":\"01,02,03,04,06|01,02;01,02,03,04,06|01,03\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":400, \"platform\":\"IOS\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			log.info(tradeRepString);
			tradeRa.andExpect(status().isOk());
			
			//投注
			ticketsMessage = "[{\"betTypeCode\":\"01\", \"playTypeCode\":\"00\", \"amount\":1200, \"multiple\":1, \"numbers\":\"01,02,03,04,06,09|01,03\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":1200, \"platform\":\"IOS\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			log.info(tradeRepString);
			tradeRa.andExpect(status().isOk());
			
			//前驱胆拖投注
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":1200, \"multiple\":1, \"numbers\":\"01,02,03,04$06,09|02,03,05\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":1200, \"platform\":\"IOS\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			log.info(tradeRepString);
			tradeRa.andExpect(status().isOk());
			
			//前后驱胆拖投注
			ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":800, \"multiple\":1, \"numbers\":\"01,02,03,04$06,09|01$02,05\"}]";
			body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":800, \"platform\":\"IOS\"}}";
			tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
			tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
			log.info(tradeRepString);
			tradeRa.andExpect(status().isOk());
		}
	}
	
	/**
	 * 测试彩民在投注站购买彩票
	 * @throws Exception
	 */
	@Test
	public void testStationLotByOrder() throws Exception {
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		//用户的余额设置为0
		customerAccountService.setRechargeByCustomerIdAndStationId(0, userId, stationId);
		
		//投注
		String ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":800, \"multiple\":1, \"numbers\":\"01,02,03,04$06,09|01$02,05\"}]";
		body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"schemeId\":\"\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":800, \"platform\":\"IOS\"}}";
		ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
		String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
		log.info(tradeRepString);
		tradeRa.andExpect(status().isOk());
		
		String repBody = JsonPath.read(tradeRepString, "$.body");
		String orderId = JsonPath.read(repBody, "$.order.id");
		cmd = "T02";
		body = "{\"orderId\":\"" + orderId + "\"}";
		tradeRa = this.mockMvc.perform(addCookie(post("/trade/afford.htm").param("head", head).param("body", body), cookieList));
		tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
		log.info(tradeRepString);
		tradeRa.andExpect(status().isOk());
		
		//用户的余额设置为10000
		customerAccountService.setRechargeByCustomerIdAndStationId(10000, userId, stationId);
		cmd = "T02";
		body = "{\"orderId\":\"" + orderId + "\"}";
		tradeRa = this.mockMvc.perform(addCookie(post("/trade/afford.htm").param("head", head).param("body", body), cookieList));
		tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
		log.info(tradeRepString);
		tradeRa.andExpect(status().isOk());
	}
	
	/**
	 * 测试第三方支付
	 * @throws Exception
	 */
	@Test
	public void testThirdPay() throws Exception { 
		String channelCode = "Q0001";
		String userId = "047c9af002174b0692a75320d372f24b";
		List<Cookie> cookieList = userLogin();
		
		String cmd = "T01", body, head;
		head = ParameterUtil.getHead(channelCode, cmd);
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		//投注
		String ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":800, \"multiple\":1, \"numbers\":\"01,02,03,04$06,09|01$02,05\"}]";
		body = "{\"payType\":0, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":800, \"platform\":\"IOS\"}}";
		ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
		String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
		log.info(tradeRepString);
		tradeRa.andExpect(status().isOk());
		
		Customer user = customerService.findOne(userId);
		
		String repBody = JsonPath.read(tradeRepString, "$.body");
		String orderId = JsonPath.read(repBody, "$.order.id");
		
		//orderService.payForAnOrderByBank(orderId, user, 800L, AccountConstants.THIRD_PARTY_TYPE_BANK, "1234567890");
		
		//TOrder order = orderService.findOne(orderId);
		//assertEquals(order.getStatus(), OrderState.WAITING_PRINT.getCode());
	}
	
	private MockHttpServletRequestBuilder addCookie(MockHttpServletRequestBuilder builder, List<Cookie> cookieList)
    {
    	for(int i = 0; i < cookieList.size(); i++)
    	{
    		builder = builder.cookie(cookieList.get(i));
    	}
    	return builder;
    }
	
	/**
	 * 彩民登录
	 * @return
	 * @throws Exception
	 */
	private List<Cookie> userLogin() throws Exception
    {
		Cookie userIdCookie = null, userTypeCookie = null, userMd5Cookie = null;
		String channelCode = "Q0001";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String cmd = "A04";
		String head = ParameterUtil.getHead(channelCode, cmd);
		ResultActions ra = this.mockMvc.perform(post("/account/login.htm").param("head", head).param("body", body));
		String raRepString = ra.andReturn().getResponse().getContentAsString();
		log.info(raRepString);
		
		ra.andExpect(status().isOk());
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		MockHttpServletResponse response = ra.andReturn().getResponse();
		Cookie[] cooks = response.getCookies();
		List<Cookie> cookieList = new ArrayList<Cookie>();
		for(int i = 0; i < cooks.length; i++)
		{
			Cookie cook = cooks[i];
			if(cook.getName().equals("userId"))
			{
				userIdCookie = cook;
			}
			else if(cook.getName().equals("userType"))
			{
				userTypeCookie = cook;
			}
			else if(cook.getName().equals("userMd5"))
			{
				userMd5Cookie = cook;
			}
		}
		cookieList.add(userIdCookie);
		cookieList.add(userTypeCookie);
		cookieList.add(userMd5Cookie);
		return cookieList;
    }
	
	/**
	 * 渠道用户进行投注
	 * @throws Exception
	 */
	@Test
	public void testSsqLotByUserFromChannel() throws Exception {
		String channelCode = "Q0002";
		
		Map<String, Cookie> aMap = new HashMap<String, Cookie>();
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "T01";
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06|02\"}]";
		body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"gameCode\":\"F01\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		//第三方支付
		cmd = "T01";
		ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06|02\"}]";
		body = "{\"payType\":" + ConstantValues.TOrder_PayType_Company.getCode() + ", \"order\":{\"tickets\":" + ticketsMessage + ", \"gameCode\":\"F01\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, aMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
	}
	
}
