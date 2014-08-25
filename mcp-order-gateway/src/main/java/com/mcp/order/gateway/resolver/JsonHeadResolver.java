package com.mcp.order.gateway.resolver;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.inter.Head;
import com.mcp.order.model.admin.Station;
import com.mcp.order.service.StationService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class JsonHeadResolver implements HandlerMethodArgumentResolver {

	private static Logger log = Logger.getLogger(JsonHeadResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonHead.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		JsonHead head = parameter.getParameterAnnotation(JsonHead.class);
		String value = webRequest.getParameter(head.value());
		log.info("head:" + value);
		ObjectMapper om = new ObjectMapper();
		Head h = om.readValue(value, Head.class);
		if(!h.getVer().equals(Constants.GATEWAY_VERSION))
		{
			throw new CoreException(ErrCode.E1023, ErrCode.codeToMsg(ErrCode.E1023));
		}
		if(head.checkChannel())	//校验渠道的可用性
		{
			HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
			StationService stationService = context.getBean("stationService", StationService.class);
			Station channel = stationService.findOneByCode(h.getChannelCode());
			if(channel == null || channel.getStatus() == ConstantValues.Channel_Status_Stop.getCode())
			{
				throw new CoreException(ErrCode.E2034, ErrCode.codeToMsg(ErrCode.E2034));
			}
		}
		webRequest.setAttribute("head", h, RequestAttributes.SCOPE_REQUEST);
		return h;
	}
}
