package com.mcp.order.gateway.control;

import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.common.ParameterUtil;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestUtil {
	
	private static Map<String, Integer> ckMap = new HashMap<String, Integer>();
	
	static {
		ckMap.put("userId", 1);
		ckMap.put("userType", 1);
		ckMap.put("userMd5", 1);
	}
	
	private static Logger log = Logger.getLogger(TestUtil.class);
	
	public static String request(MockMvc mockMvc, String channelCode, String cmd, String body, Map<String, Cookie> cMap) throws Exception
	{
		String head = ParameterUtil.getHead(channelCode, cmd);
		MockHttpServletRequestBuilder builder = post("/" + CmdContext.getInstance().getCmdByCode(cmd).getPath())
				.param("head", head).param("body", body);
		builder = addCookie(builder, cMap);
		ResultActions ra = mockMvc.perform(builder);
		String raRepString = ra.andReturn().getResponse().getContentAsString();
		MockHttpServletResponse response = ra.andReturn().getResponse();
		Cookie[] cooks = response.getCookies();
		for(int i = 0; i < cooks.length; i++)
		{
			Cookie cook = cooks[i];
			if(ckMap.containsKey(cook.getName()))
			{
				cMap.put(cook.getName(), cook);
				log.info("cook---name:" + cook.getName() + ",value:" + cook.getValue());
			}
		}
		log.info(raRepString);
		return raRepString;
	}
	
	public static String cRequest(MockMvc mockMvc, String channelCode, String cmd, String body, Map<String, Cookie> cMap) throws Exception
	{
		String time = DateTimeUtil.getDateString(new Date());
		String key = "123456";
		String head = ParameterUtil.getHead(channelCode, cmd, time, body, key);
		MockHttpServletRequestBuilder builder = post("/" + CmdContext.getInstance().getCmdByCode(cmd).getPath())
				.param("head", head).param("body", body);
		builder = addCookie(builder, cMap);
		ResultActions ra = mockMvc.perform(builder);
		String raRepString = ra.andReturn().getResponse().getContentAsString();
		MockHttpServletResponse response = ra.andReturn().getResponse();
		Cookie[] cooks = response.getCookies();
		for(int i = 0; i < cooks.length; i++)
		{
			Cookie cook = cooks[i];
			if(ckMap.containsKey(cook.getName()))
			{
				cMap.put(cook.getName(), cook);
				log.info("cook---name:" + cook.getName() + ",value:" + cook.getValue());
			}
		}
		log.info(raRepString);
		return raRepString;
	}
	
	/**
	 * 添加cookie信息
	 * @param builder
	 * @param cMap
	 * @return
	 */
	public static MockHttpServletRequestBuilder addCookie(MockHttpServletRequestBuilder builder, Map<String, Cookie> cMap)
    {
		if(cMap != null)
		{
			Iterator<String> keyIt = cMap.keySet().iterator();
			while(keyIt.hasNext())
			{
				builder = builder.cookie(cMap.get(keyIt.next()));
			}
		}
		return builder;
    }
}
