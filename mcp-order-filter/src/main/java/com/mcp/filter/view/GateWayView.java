package com.mcp.filter.view;

import com.mcp.filter.common.AdapterContext;
import com.mcp.filter.common.SystemConfig;
import com.mcp.filter.model.FilterMsg;
import com.mcp.filter.model.ServiceType;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class GateWayView extends AbstractView {
	
	private static Logger log = Logger.getLogger(GateWayView.class);
	
	// 默认内容类型
	private final static String CONTENT_TYPE = "application/json";
	
	public GateWayView() {
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
		FilterMsg fm = (FilterMsg)model.get("filterMsg");
		ServiceType serviceType = AdapterContext.getInstance().getServiceType(fm.getCmd());
		if(serviceType == null)
		{
			throw new CoreException(ErrCode.E0004, ErrCode.codeToMsg(ErrCode.E0004));
		}
		String path = serviceType.getPath();
		String content = HttpClientUtil.request(SystemConfig.GATEWAY_SITE, SystemConfig.GATEWAY_PORT, path, fm.getGateWayHead(), fm.getBody(), HttpClientUtil.POST, null);
		log.info(content);
		//写入 response 内容
		response.getWriter().write(content);
		response.getWriter().close();
	}
}
