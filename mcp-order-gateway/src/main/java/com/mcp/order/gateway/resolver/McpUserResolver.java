package com.mcp.order.gateway.resolver;

import com.mcp.core.util.MD5Util;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.CookieUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgLogin;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.mongo.service.MgLoginService;
import com.mcp.order.service.CustomerService;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class McpUserResolver implements HandlerMethodArgumentResolver {

	private static Logger log = Logger.getLogger(McpUserResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(McpUser.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		McpUser mcpUser = parameter.getParameterAnnotation(McpUser.class);
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
        Head h = (Head)webRequest.getAttribute("head", RequestAttributes.SCOPE_REQUEST);
        String body = webRequest.getParameter("body");
        String userId = h.getUserId();
        MgLoginService mgLoginService = context.getBean("mgLoginService", MgLoginService.class);
        MgLogin mgLogin = mgLoginService.findById(userId);
        Date now = new Date();
        if(mgLogin == null || now.getTime() - mgLogin.getLastActiveTime().getTime() > Constants.LOGIN_EXPIRE_MILISECOND)
        {
            if(mcpUser.required())
            {
                throw new CoreException(ErrCode.E1013, ErrCode.codeToMsg(ErrCode.E1013));
            }
            else
            {
                return null;
            }
        }
        else
        {
            String decodedBody = DigestFactory.check(h, body, mgLogin.getSt());
            if(decodedBody != null)
            {
                CustomerService customerService = context.getBean("customerService", CustomerService.class);
                webRequest.setAttribute("secretKey", mgLogin.getSt(), RequestAttributes.SCOPE_REQUEST);
                webRequest.setAttribute("body", decodedBody, RequestAttributes.SCOPE_REQUEST);
                return customerService.findOne(userId);
            }
            else if(mcpUser.required())
            {
                throw new CoreException(ErrCode.E1013, ErrCode.codeToMsg(ErrCode.E1013));
            }
            else
            {
                return null;
            }
        }
	}
}
