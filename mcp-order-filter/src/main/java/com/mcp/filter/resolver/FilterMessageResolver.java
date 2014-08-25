package com.mcp.filter.resolver;

import com.mcp.filter.annotation.FilterMessage;
import com.mcp.filter.model.FilterMsg;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FilterMessageResolver implements HandlerMethodArgumentResolver {

	//private static Logger log = Logger.getLogger(FilterMessageResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(FilterMessage.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		FilterMessage afm = parameter.getParameterAnnotation(FilterMessage.class);
		String value = webRequest.getParameter(afm.value());
		return new FilterMsg(value);
	}
}
