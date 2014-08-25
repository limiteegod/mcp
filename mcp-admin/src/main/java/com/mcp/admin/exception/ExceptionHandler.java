/**
 * 
 */
package com.mcp.admin.exception;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.RepE01Body;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
		String cmd = (String)request.getAttribute("cmd");
        Head head = new Head();

        map.put("cmd", "E01");
        head.setId(CoreUtil.getUUID());
        head.setVer(Constants.GATEWAY_VERSION);
        head.setChannelCode("000");
        head.setCmd("E01");
        map.put("head", head);

		RepE01Body body = new RepE01Body();
		if(ex instanceof CoreException)
		{	
			CoreException ce = (CoreException)ex;
			body.setRepCode(ce.getErrorCode());
			body.setDescription(ce.getMessage());
		}
		else
		{
			body.setRepCode(ErrCode.E0999);
			body.setDescription(ErrCode.codeToMsg(ErrCode.E0999));
			ex.printStackTrace();
		}
        ObjectMapper om = new ObjectMapper();
        McpGtMsg mcpGtMsg = new McpGtMsg();
        mcpGtMsg.setHead(head);
        try {
            mcpGtMsg.setBody(om.writeValueAsString(body));
            map.put("response", om.writeValueAsString(mcpGtMsg));
        } catch (IOException e) {
            map.put("response", "{}");
            e.printStackTrace();
        }
		ModelAndView mav = new ModelAndView("plainJsonView", map);
		return mav;
	}

}
