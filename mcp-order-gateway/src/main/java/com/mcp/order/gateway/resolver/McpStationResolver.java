package com.mcp.order.gateway.resolver;

import com.mcp.core.util.MD5Util;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.model.admin.Station;
import com.mcp.order.service.StationService;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class McpStationResolver implements HandlerMethodArgumentResolver {

    private static Logger log = Logger.getLogger(McpStationResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(McpStation.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		McpStation mcpStation = parameter.getParameterAnnotation(McpStation.class);
		Head h = (Head)webRequest.getAttribute("head", RequestAttributes.SCOPE_REQUEST);
		String body = webRequest.getParameter("body");
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
		StationService stationService = context.getBean("stationService", StationService.class);
		
		Station station = stationService.findOneByCode(h.getChannelCode());
        String decodedBody = DigestFactory.check(h, body, station.getSecretKey());
		if(decodedBody != null)
		{
			webRequest.setAttribute("secretKey", station.getSecretKey(), RequestAttributes.SCOPE_REQUEST);
            webRequest.setAttribute("body", decodedBody, RequestAttributes.SCOPE_REQUEST);
			return station;
		}
		else if(mcpStation.required())
		{
			throw new CoreException(ErrCode.E0001, ErrCode.codeToMsg(ErrCode.E0001));
		}
		else 
		{
			return null;
		}
	}
}
