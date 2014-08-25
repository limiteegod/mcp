package com.mcp.filter.model;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import org.codehaus.jackson.map.ObjectMapper;

public class FilterMsg {
	
	private String cmd;
	
	private String body;
	
	private String gateWayHead;
	
	/**
	 * @param message
	 */
	public FilterMsg(String message)
	{
		if(StringUtil.isEmpty(message))
		{
			throw new RuntimeException("json字符串不能为空");
		}
		ObjectMapper om = new ObjectMapper();
		try {
			McpGtMsg req = om.readValue(message, McpGtMsg.class);
			Head filterHead = req.getHead();
			cmd = filterHead.getCmd();
			body = req.getBody();
			gateWayHead = om.writeValueAsString(filterHead);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getGateWayHead() {
		return gateWayHead;
	}
}
