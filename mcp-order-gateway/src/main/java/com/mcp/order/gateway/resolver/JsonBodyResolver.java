package com.mcp.order.gateway.resolver;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.inter.common.ParameterUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class JsonBodyResolver implements HandlerMethodArgumentResolver {

	private static Logger log = Logger.getLogger(JsonBodyResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonBody.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		JsonBody jsonBody = parameter.getParameterAnnotation(JsonBody.class);
        //如果涉及到签名或者加密的话，从attribute取得body
        String body = (String)webRequest.getAttribute(jsonBody.value(), RequestAttributes.SCOPE_REQUEST);
        if(StringUtil.isEmpty(body))
        {
            body = webRequest.getParameter(jsonBody.value());
        }
		String cmd = jsonBody.cmd();
		webRequest.setAttribute("cmd", cmd, RequestAttributes.SCOPE_REQUEST);
		
		log.info("body:" + body);
		String pkg = ParameterUtil.getReqPkg(cmd);
		String className = ParameterUtil.getReqBodyClassNameByCmd(cmd);
		ObjectMapper om = new ObjectMapper();
		ReqBody reqBody = null;
		try {
			reqBody = (ReqBody)om.readValue(body, Class.forName(pkg + "." + className));
		} 
		catch(JsonMappingException e)
		{
			e.printStackTrace();
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
		reqBody.validate();
		return reqBody;
	}
}
