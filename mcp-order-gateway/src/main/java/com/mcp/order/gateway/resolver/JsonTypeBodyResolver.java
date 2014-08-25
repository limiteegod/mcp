package com.mcp.order.gateway.resolver;

import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonTypeBody;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.inter.common.ParameterUtil;
import com.mcp.order.model.mongo.MgUniqueId;
import com.mcp.order.mongo.service.MgLoginService;
import com.mcp.order.mongo.service.MgUniqueIdService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;
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
import java.util.Date;


public class JsonTypeBodyResolver implements HandlerMethodArgumentResolver {

	private static Logger log = Logger.getLogger(JsonTypeBodyResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonTypeBody.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
        JsonTypeBody jsonTypeBody = parameter.getParameterAnnotation(JsonTypeBody.class);
        //如果涉及到签名或者加密的话，从attribute取得body
        String body = (String)webRequest.getAttribute(jsonTypeBody.value(), RequestAttributes.SCOPE_REQUEST);
        if(StringUtil.isEmpty(body))
        {
            body = webRequest.getParameter(jsonTypeBody.value());
        }
		log.info("body:" + body);
		String pkg = ParameterUtil.getReqPkgByGroupCode(jsonTypeBody.group());
        String cmd = jsonTypeBody.group() + jsonTypeBody.cmd();
		String className = ParameterUtil.getReqBodyClassNameByCmd(cmd);
        webRequest.setAttribute("cmd", cmd, RequestAttributes.SCOPE_REQUEST);
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
        Head head = (Head)webRequest.getAttribute("head", RequestAttributes.SCOPE_REQUEST);
        if(head.getUserType() != SystemUserType.GUEST.getCode())
        {
            if(StringUtil.isEmpty(reqBody.getUniqueId()))
            {
                throw new CoreException(ErrCode.E2055);
            }
            HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
            MgUniqueIdService mgUniqueIdService = context.getBean("mgUniqueIdService", MgUniqueIdService.class);
            //唯一id由用户类型，用户id，uniqueid共同决定
            String uniqueId = head.getUserType() + head.getUserId() + reqBody.getUniqueId();
            MgUniqueId mgUniqueId = mgUniqueIdService.findById(uniqueId);
            if(mgUniqueId != null)
            {
                throw new CoreException(ErrCode.E2055);
            }
            else
            {
                mgUniqueId = new MgUniqueId();
                mgUniqueId.setId(uniqueId);
                mgUniqueId.setBody(body);
                mgUniqueId.setCreateTime(new Date());
                mgUniqueIdService.save(mgUniqueId);
            }
        }
		reqBody.validate();
		return reqBody;
	}
}
