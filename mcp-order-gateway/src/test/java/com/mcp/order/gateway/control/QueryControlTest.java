package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
import com.mcp.order.inter.common.ParameterUtil;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.StationService;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.junit.After;
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
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml", "classpath:applicationContext-web.xml"})
public class QueryControlTest {

    private static Logger log = Logger.getLogger(QueryControlTest.class);

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private MoneyService moneyService;
    
    @Autowired
    private StationService stationService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testQ01T0() throws Exception {
    	String channelCode = "Q0001";
    	String body = "{\"showGrade\":true, \"type\":0, \"terms\": [{\"gameCode\": \"T01\", \"termCode\": \"13087\"},{\"gameCode\": \"F01\",\"termCode\": \"2013088\"},{\"gameCode\": \"T02\",\"termCode\": \"13088\"}]}";
    	String cmd = "Q01";
		String head = ParameterUtil.getHead(channelCode, cmd);
		ResultActions ra = this.mockMvc.perform(post("/query/getGameTerm.htm").param("head", head).param("body", body));
		String repString = ra.andReturn().getResponse().getContentAsString();
		String repBody = JsonPath.read(repString, "$.body");
        log.info("返回的json字符串为：" + repString);
        ra.andExpect(status().isOk());
        assertEquals(JsonPath.read(repBody, "$.rst[0].gameCode"), "T01");
        assertEquals(JsonPath.read(repBody, "$.rst[0].code"), "13087");
    }

    @Test
    public void testQ01T1() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"type\":1}";
        String cmd = "Q01";
        String head = ParameterUtil.getHead(channelCode, cmd);
		ResultActions ra = this.mockMvc.perform(post("/query/getGameTerm.htm").param("head", head).param("body", body));
		String repString = ra.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        ra.andExpect(status().isOk());
    }

    @Test
    public void testQ01T2() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"type\":\"2\"}";
        String cmd = "Q01";
        String head = ParameterUtil.getHead(channelCode, cmd);
		ResultActions ra = this.mockMvc.perform(post("/query/getGameTerm.htm").param("head", head).param("body", body));
        String repString = ra.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        ra.andExpect(status().isOk());
    }


    @Test
    public void testQ01T3() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"type\":3, \"gameCode\": \"T01\", \"startIndex\": \"0\", \"size\": \"10\"}";
        String cmd = "Q01";
        String head = ParameterUtil.getHead(channelCode, cmd);
		ResultActions ra = this.mockMvc.perform(post("/query/getGameTerm.htm").param("head", head).param("body", body));
        String repString = ra.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        ra.andExpect(status().isOk());
    }
    
    @Test
    public void testQ01T4() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"type\":4, \"terms\":[{\"gameCode\":\"T04\"}]}";
        String cmd = "Q01";
        String head = ParameterUtil.getHead(channelCode, cmd);
		ResultActions ra = this.mockMvc.perform(post("/query/getGameTerm.htm").param("head", head).param("body", body));
        String repString = ra.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        ra.andExpect(status().isOk());
    }
    

    @Test
    public void testQ03() throws Exception {
    	List<Cookie> cookieList = userLogin();
    	String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		
		String cmd = "T01";
		String channelCode = "Q0001";
		String head = ParameterUtil.getHead(channelCode, cmd);
		//投注
		String ticketsMessage = "[{\"betTypeCode\":\"02\", \"playTypeCode\":\"00\", \"amount\":800, \"multiple\":1, \"numbers\":\"01,02,03,04$06,09|01$02,05\"}]";
		String body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":800, \"platform\":\"IOS\"}}";
		ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
		String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
		log.info(tradeRepString);
		tradeRa.andExpect(status().isOk());
		
		String repBody = JsonPath.read(tradeRepString, "$.body");
		String orderId = JsonPath.read(repBody, "$.order.id");
		
        body = "{\"orderStatus\":\"1100,1200\",\"exOrderStatus\":\"1001\",\"startIndex\":0,\"size\":10,\"schemeType\":-1,\"schemeId\":\"\",\"gameCode\":\"\",\"stationId\":\"\"}";
        cmd = "Q03";
        head = ParameterUtil.getHead(channelCode, cmd);
        ResultActions addRa = this.mockMvc.perform(addCookie(post("/query/getBetHistory.htm").param("head", head).param("body", body), cookieList));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
        
        body = "{\"orderId\":\"" + orderId + "\",\"showTickets\":true}";
        cmd = "Q03";
        head = ParameterUtil.getHead(channelCode, cmd);
        addRa = this.mockMvc.perform(addCookie(post("/query/getBetHistory.htm").param("head", head).param("body", body), cookieList));
        repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
    }

    @Test
    public void testQ04() throws Exception {
    	String channelCode = "Q0001";
		List<Cookie> cookieList = userLogin();
        String body = "{\"entityIds\":[\"047c9af002174b0692a75320d372f24b\"],\"accountType\":\"RU00\",\"startTime\":\"2012-08-05T17:05:10.536+0800\",\"endTime\":\"2014-08-31T17:05:10.536+0800\",\"startIndex\":\"0\",\"size\":\"10\"}";
        String cmd = "Q04";
        String head = ParameterUtil.getHead(channelCode, cmd);
        ResultActions addRa = this.mockMvc.perform(addCookie(post("/query/getBetAccountLog.htm").param("head", head).param("body", body), cookieList));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
    }

    /**
     * 投注站查询
     * @throws Exception
     */
    @Test
    public void testQ05() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"stationId\":\"91a730694eeb4dd4b3fca7a71a5e610d\"}";
        String cmd = "Q05";
        String head = ParameterUtil.getHead(channelCode, cmd);
        ResultActions addRa = this.mockMvc.perform(post("/query/getStation.htm").param("head", head).param("body", body));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
        
        body = "{\"keyword\":\"九歌\"}";
        addRa = this.mockMvc.perform(post("/query/getStation.htm").param("head", head).param("body", body));
        repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
        
        body = "{\"longitude\":116.497997,\"longitudeGap\":1,\"latitude\":39.91886,\"latitudeGap\":1}";
        addRa = this.mockMvc.perform(post("/query/getStation.htm").param("head", head).param("body", body));
        repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
    }
    
    /**
     * 投注站查询
     * @throws Exception
     */
    @Test
    public void testQ0502() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"stationId\":\"\",\"longitude\":\"3920567\",\"latitude\":\"11633532\",\"longitudeGap\":\"100\",\"latitudeGap\":\"100\"}";
        String cmd = "Q05";
        String head = ParameterUtil.getHead(channelCode, cmd);
        ResultActions addRa = this.mockMvc.perform(post("/query/getStation.htm").param("head", head).param("body", body));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
    }

    /**
     * 系统时间查询
     * @throws Exception
     */
    @Test
    public void testQ07() throws Exception {
        String channelCode = "Q0001";
        String body = "{}";
        String cmd = "Q07";
        String head = ParameterUtil.getHead(channelCode, cmd);
        ResultActions addRa = this.mockMvc.perform(post("/query/getSystemTime.htm").param("head", head).param("body", body));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的字符串：" + repString);
        addRa.andExpect(status().isOk());
    }
    
    
    @Test
    public void testQ08() throws Exception {
    	/**
		 * 彩民登录
		 */
		String channelCode = "Q0001";
		List<Cookie> cookieList = userLogin();
		String cmd = "T01";
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		String head = ParameterUtil.getHead(channelCode, cmd);
		
		int amount = 200;
		//投注
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":" + amount + ", \"multiple\":1, \"numbers\":\"09,12,22,32,34|02,04\"}]";
		String body = "{\"payType\":0, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"13089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"T01\", \"type\":0, \"amount\":" + amount + ", \"platform\":\"IOS\"}}";
		ResultActions tradeRa = this.mockMvc.perform(addCookie(post("/trade/lot.htm").param("head", head).param("body", body), cookieList));
		String tradeRepString = tradeRa.andReturn().getResponse().getContentAsString();
		log.info(tradeRepString);
		tradeRa.andExpect(status().isOk());
		String repBody = JsonPath.read(tradeRepString, "$.body");
		String firstId = JsonPath.read(repBody, "$.order.id");
		log.info(firstId);
        /*//信息采集接口
        body = "{\"payment\":\"支付宝\", \"paymentType\":1, \"amount\":" + amount + ", \"orderId\":\"" + firstId + "\", \"outerId\":\"1111111111111111111\"}";
        cmd = "Q08";
        ResultActions addRa = this.mockMvc.perform(post("/query/infoCollect.htm").param("head", head).param("body", body));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的字符串：" + repString);
        addRa.andExpect(status().isOk());*/
    }
    
    /**
     * 文件版本查询
     * @throws Exception
     */
    @Test
    public void testQ09() throws Exception {
        String channelCode = "Q0001";
        String body = "{\"clientCode\":\"1.00\", \"fileTypeCode\":\"000\", \"fileVersionCode\":\"1.00\"}";
        String cmd = "Q09";
        String head = ParameterUtil.getHead(channelCode, cmd);
        ResultActions addRa = this.mockMvc.perform(post("/query/getFiles.htm").param("head", head).param("body", body));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的json字符串为：" + repString);
        addRa.andExpect(status().isOk());
    }
    
    @Test
    public void testQ10() throws Exception {
    	Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"phone\":\"01099998888\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		String stationId = "91a730694eeb4dd4b3fca7a71a5e610d";
		
    	//单式投注
    	cmd = "T01";
		String ticketsMessage = "[{\"betTypeCode\":\"00\", \"playTypeCode\":\"00\", \"amount\":200, \"multiple\":1, \"numbers\":\"01,02,03,04,05,06|02\"}]";
		body = "{\"payType\":1, \"order\":{\"tickets\":" + ticketsMessage + ", \"termCode\":\"2013089\", \"stationId\":\"" + stationId + "\", \"gameCode\":\"F01\", \"type\":0, \"amount\":200, \"platform\":\"ANDROID\"}}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		
		repBody = JsonPath.read(raRepString, "$.body");
		String orderId = JsonPath.read(repBody, "$.order.id");
		
		//彩票查询
        body = "{\"orderId\":\"" + orderId + "\"}";
        cmd = "Q10";
        raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
        repBody = JsonPath.read(raRepString, "$.body");
        String ticketId = JsonPath.read(repBody, "$.rst[0].id");
        
        body = "{\"ticketId\":\"" + ticketId + "\"}";
        cmd = "Q10";
        raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
    }

    /**
     * 根据状态查询当前游戏
     */
    @Test
    public void testQ11() throws Exception {
    	Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "Q11";
		String body = "{}";
		TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
    }
    
    
    @Test
    public void testQ12() throws Exception {
		
    	/**
		 * 彩民登录
		 */
		String channelCode = "Q0001";
		List<Cookie> cookieList = userLogin();
        //信息采集接口
        String body = "{\"size\":10,\"time\":\"" + DateTimeUtil.getDateString(DateTimeUtil.getYear(new Date(), 2)) + "\",\"type\":1}";
        String cmd = "Q12";
        String head = ParameterUtil.getHead(channelCode, cmd);

        ResultActions addRa = this.mockMvc.perform(addCookie(post("/query/getMessages.htm").param("head", head).param("body", body), cookieList));
        String repString = addRa.andReturn().getResponse().getContentAsString();
        log.info("返回的字符串：" + repString);
        addRa.andExpect(status().isOk());
    }
    
    
    @Test
    public void testQ13() throws Exception {
    	Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "Q13";
		String body = "{}";
		TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
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
     * 查询竞彩场次信息
     * @throws Exception
     */
    @Test
    public void testQ14() throws Exception {
    	Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		body = "{\"gameCode\":\"T51\",\"status\":[1200],\"oddsCode\":[\"CN\"],\"playTypeCode\":[\"01\",\"02\"]}";
        cmd = "Q14";
        raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
        repBody = JsonPath.read(raRepString, "$.body");
        log.info(repBody);
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
}
