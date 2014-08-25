package com.mcp.order.gateway.control;

import com.jayway.jsonpath.JsonPath;
import com.mcp.core.util.CoreUtil;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.service.CustomerService;
import org.junit.*;
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
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml", "classpath:applicationContext-web.xml"})
public class AccountControlTest {
	
	//private static Logger log = Logger.getLogger(AccountControlTest.class);
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private CustomerService customerService;

	private MockMvc mockMvc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		 this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 用户登录
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception
	{
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee\",\"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cMap = new HashMap<String, Cookie>();
		body = "{\"name\":\"limitee2\",\"password\":\"123456\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
	}
	
	/**
	 * 修改密码
	 * @throws Exception
	 */
	@Test
	public void testModifyPasswod() throws Exception
	{
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		body = "{\"password\":\"123456\", \"newPassword\":\"654321\"}";
		cmd = "A03";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		body = "{\"password\":\"654321\", \"newPassword\":\"123456\"}";
		cmd = "A03";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
	}
	
	/**
	 * 根据用户名查询详细信息
	 * @throws Exception
	 */
	@Test
	public void testQuery() throws Exception
	{
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		body = "{}";
		cmd = "A02";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), ErrCode.E0000);
	}
	
	/**
	 * 新增一个用户
	 * @throws Exception
	 */
	@Test
	public void testAdd() throws Exception
	{
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A01";
		String name = CoreUtil.getUUID();
		String body = "{\"customer\":{\"name\":\"" + name + "\", \"phone\":\"" + UUID.randomUUID().toString().replace("-", "") + "\", \"identityId\":\"" + UUID.randomUUID().toString().replace("-", "") + "\", \"password\":\"123456\"}}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.customer.name"), name);
	}
	
	/**
	 * 新增一个用户
	 * @throws Exception
	 */
	@Test
	public void testAddAndBindStation() throws Exception
	{
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A01";
		String name = UUID.randomUUID().toString().replace("-", "");
		String body = "{\"stationId\":\"24fb88b47c694ec4880ce36d3293e647\", \"customer\":{\"name\":\"" + name + "\", \"phone\":\"" + UUID.randomUUID().toString().replace("-", "") + "\", \"identityId\":\"" + UUID.randomUUID().toString().replace("-", "") + "\", \"password\":\"123456\"}}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.customer.name"), name);
	}

	@Test
	public void testModifyInfo() throws Exception
	{
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		String cmd = "A04";
		String body = "{\"name\":\"limitee2\", \"password\":\"123456\"}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
		
		cmd = "R01";
		body = "{\"phone\":\"12345678901\"}";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "0000");
		
		body = "{\"authCode\":\"123456\",\"needAuthCode\":true,\"nickyName\":\"limiteenick2\", \"phone\":\"12345678901\", \"email\":\"limitee@qq.com\", \"realName\":\"李明\", \"identityId\":\"12345678909999\"}";
		cmd = "A05";
		raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.status"), true);
	}
	
	/**
	 * 测试商户查询自己的信息
	 * @throws Exception
	 */
	@Test
	public void testChannelQuery() throws Exception { 
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0001";
		
		String cmd = "A07";
		String body = "{}";
		String raRepString = TestUtil.cRequest(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.code"), channelCode);
	}
	
	
	/**
	 * 测试商户未登录的情况下查询自己的信息
	 * @throws Exception
	 */
	@Test
	public void testChannelQueryNotLogin() throws Exception {
		Map<String, Cookie> cMap = new HashMap<String, Cookie>();
		String channelCode = "Q0002";
		String cmd = "A07";
		String body = "{}";
		String raRepString = TestUtil.request(mockMvc, channelCode, cmd, body, cMap);
		String repBody = JsonPath.read(raRepString, "$.body");
		assertEquals(JsonPath.read(repBody, "$.repCode"), "1013");
	}
}
