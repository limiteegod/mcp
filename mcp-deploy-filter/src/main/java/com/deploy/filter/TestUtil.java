package com.deploy.filter;


import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Date;

public class TestUtil {
	
	public static String getCReqMessage(String userId, String channelCode, String body, String cmd, String key) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		Date now = new Date();
        String dateStr = DateTimeUtil.getDateString(now);
		String digestType = "md5";
		String digest = MD5Util.MD5(body + dateStr + key);
		
		Head h = new Head();
		h.setId(CoreUtil.getUUID());
		h.setChannelCode(channelCode);
		h.setCmd(cmd);
		h.setTimestamp(dateStr);
		h.setVer(Constants.GATEWAY_VERSION);
		
		h.setDigest(digest);
		h.setDigestType(digestType);
		
		McpGtMsg req = new McpGtMsg();
		req.setHead(h);
		req.setBody(body);
		
		String message = om.writeValueAsString(req);
		return message;
	}
	
	public static String getCReqMessage(String userId, String channelCode, String body, String cmd) throws Exception
	{
		return getCReqMessage(userId, channelCode, body, cmd, "123456");
	}
	
	public static String getReqMessage(String userId, String channelCode, String body, String cmd) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		Date now = new Date();
        String dateStr = DateTimeUtil.getDateString(now);
		String digestType = "md5";
		String digest = MD5Util.MD5(body);
		
		Head h = new Head();
		h.setId(CoreUtil.getUUID());
		h.setChannelCode(channelCode);
		h.setCmd(cmd);
		h.setTimestamp(dateStr);
		h.setVer(Constants.GATEWAY_VERSION);
		
		h.setDigest(digest);
		h.setDigestType(digestType);
		
		McpGtMsg req = new McpGtMsg();
		req.setHead(h);
		req.setBody(body);
		
		String message = om.writeValueAsString(req);
		return message;
	}
}
