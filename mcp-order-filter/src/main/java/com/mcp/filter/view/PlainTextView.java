package com.mcp.filter.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PlainTextView extends AbstractView {
	
	// 默认内容类型
	private final static String CONTENT_TYPE = "text/plain";
	
	public PlainTextView() {
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
		String content = (String)model.get("response");
		//写入 response 内容
		response.getWriter().write(content);
		response.getWriter().close();
	}

}
