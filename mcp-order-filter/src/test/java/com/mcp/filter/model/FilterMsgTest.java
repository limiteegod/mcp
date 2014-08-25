/**
 * 
 */
package com.mcp.filter.model;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author ming.li
 *
 */
public class FilterMsgTest {
	
	private static Logger log = Logger.getLogger(FilterMsgTest.class);
	
	@Test
	public void test() throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		SimpleFilterProvider provider = new SimpleFilterProvider();
		HashSet<String> set = new HashSet<String>();
		provider.addFilter("filterhead", SimpleBeanPropertyFilter.serializeAllExcept(set));
		om.setFilters(provider);
		
		String body = "{\"a\":\"b\"}";
		
		Head fh = new Head();
		fh.setChannelCode("Q1000");
		fh.setCmd("T01");
		fh.setDigestType("md5");
		fh.setDigest(MD5Util.MD5(body));
		fh.setId(CoreUtil.getUUID());
		fh.setTimestamp(DateTimeUtil.getDateString(new Date()));
		fh.setVer(Constants.GATEWAY_VERSION);
		
		McpGtMsg req = new McpGtMsg();
		req.setHead(fh);
		req.setBody(body);
		
		String message = om.writeValueAsString(req);
		log.info(message);
		
		FilterMsg fm = new FilterMsg(message);
		assertEquals(fm.getBody(), body);
		log.info(fm.getGateWayHead());
	}
}
