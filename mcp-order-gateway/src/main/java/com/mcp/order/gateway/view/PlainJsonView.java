package com.mcp.order.gateway.view;

import com.mcp.core.util.MD5Util;
import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.RepBody;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public class PlainJsonView extends AbstractView {
	
	// 默认内容类型
	private final static String CONTENT_TYPE = "application/json";
	
	public PlainJsonView() {
		super();
		setContentType(CONTENT_TYPE);
	}
	
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responseCode = "01";
		if(model.containsKey("responseCode"))
		{
			responseCode = (String)model.get("responseCode");
		}
		String filterCode = "01";
		if(model.containsKey("filterCode"))
		{
			filterCode = (String)model.get("filterCode");
		}
		String cmd = (String)request.getAttribute("cmd");
		if(cmd == null)
		{
			cmd = (String)model.get("cmd");
		}
		Head head = (Head)request.getAttribute("head");
		if(head == null)
		{
			head = (Head)model.get("head");
		}
		String secretKey = (String)request.getAttribute("secretKey");;
		if(secretKey == null)
		{
			secretKey = "";
		}
        Date now = new Date();
        String dateStr = DateTimeUtil.getDateString(now);
		head.setTimestamp(dateStr);
		ObjectMapper om = new ObjectMapper();
		FilterProvider fp = CmdContext.getInstance().getFilterProviderByCode(cmd + responseCode + filterCode);
		om.setFilters(fp);
		RepBody repBody = (RepBody)model.get("response");
		String bodyStr = om.writeValueAsString(repBody);
        String decodedBody = DigestFactory.generate(head, bodyStr, secretKey);
        if(!StringUtil.isEmpty(decodedBody))
        {
            bodyStr = decodedBody;
        }
		McpGtMsg msg = new McpGtMsg();
		msg.setHead(head);
		msg.setBody(bodyStr);
		String content = om.writeValueAsString(msg);
		//写入 response 内容
		response.getWriter().write(content);
		response.getWriter().close();
	}

}
