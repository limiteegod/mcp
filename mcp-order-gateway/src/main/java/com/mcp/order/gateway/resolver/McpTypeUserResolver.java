package com.mcp.order.gateway.resolver;

import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.McpTypeUser;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.UserUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.model.mongo.MgLogin;
import com.mcp.order.mongo.service.MgLoginService;
import com.mcp.order.service.CustomerService;
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
import java.util.Date;

public class McpTypeUserResolver implements HandlerMethodArgumentResolver {

	private static Logger log = Logger.getLogger(McpTypeUserResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(McpTypeUser.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
        log.info("McpTypeUser-------------------");
        McpTypeUser mcpTypeUser = parameter.getParameterAnnotation(McpTypeUser.class);
        Head head = (Head)webRequest.getAttribute("head", RequestAttributes.SCOPE_REQUEST);
        String body = webRequest.getParameter("body");
        String userId = head.getUserId();
        int userType = head.getUserType();
        String typeStr = mcpTypeUser.value();
        boolean hit = false;
        if(typeStr.equals(Constants.SEP_STAR))  //所有人都可以用
        {
            hit = true;
        }
        else
        {
            String[] typeArray = typeStr.split(Constants.SEP_COMMA);
            for(String type:typeArray)
            {
                if(userType == Integer.parseInt(type))
                {
                    hit = true;
                    break;
                }
            }
        }
        if(!hit)
        {
            throw new CoreException(ErrCode.E1024, ErrCode.codeToMsg(ErrCode.E1024));
        }
        if(userType == SystemUserType.GUEST.getCode())
        {
            //游客没有加密信息，直接返回空的用户
            return null;
        }
        Object user = null;
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
        MgLoginService mgLoginService = context.getBean("mgLoginService", MgLoginService.class);
        String idInMg = userType + "-" + userId;
        MgLogin mgLogin = mgLoginService.findById(idInMg);
        Date now = new Date();
        if(mgLogin != null && now.getTime() < mgLogin.getExpiredTime().getTime())
        {
            String st = mgLogin.getSt();
            String decodedBody = DigestFactory.check(head, body, st);
            if(decodedBody != null)
            {
                webRequest.setAttribute("secretKey", mgLogin.getSt(), RequestAttributes.SCOPE_REQUEST);
                webRequest.setAttribute("body", decodedBody, RequestAttributes.SCOPE_REQUEST);
                user = UserUtil.getUser(context, userType, head.getUserId());
                if(user != null)
                {
                    mgLoginService.active(idInMg, userType);
                    return user;
                }
            }
        }
        if(mcpTypeUser.required())
        {
            throw new CoreException(ErrCode.E1024);
        }
        else
        {
            return null;
        }
	}
}
