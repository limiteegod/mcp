/**
 * 
 */
package com.mcp.jbank;

import com.deploy.filter.TestUtil;
import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.jbank.*;
import com.mcp.order.inter.print.ReqP05Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;

/**
 * @author ming.li
 *
 */
public class JBankQuery {
	
	private static Logger log = Logger.getLogger(JBankQuery.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CookieParam sCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqP05Body body = new ReqP05Body();
		body.setCode("Q000201");
		body.setPassword("123456");
		body.setType(Constants.STATION_OP_LOGIN);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P050101"));
		String bodyStr = om.writeValueAsString(body);
		String message = TestUtil.getReqMessage("", "Q0002", bodyStr, "P05");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
		sCp.headersToCookies();
		
		om = new ObjectMapper();
		ReqJ01Body j01body = new ReqJ01Body();
		j01body.setName("limitee2");
		j01body.setPassword("123456");
		j01body.setRealName("李明");
		j01body.setCreateWhenNotExist(false);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J010101"));
		bodyStr = om.writeValueAsString(j01body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J01");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
		
		om = new ObjectMapper();
		McpGtMsg rep = om.readValue(content, McpGtMsg.class);
		String repBodyStr = rep.getBody();
		RepJ01Body repJ01body = om.readValue(repBodyStr, RepJ01Body.class);
		RepJ01Cookie cookie = repJ01body.getCookie();
		Cookie[] cookList = new Cookie[3];
		Cookie c0 = new Cookie("userId", cookie.getUserId());
		c0.setPath("/");
		Cookie c1 = new Cookie("userType", String.valueOf(cookie.getUserType()));
		c1.setPath("/");
		Cookie c2 = new Cookie("userMd5", cookie.getUserMd5());
		c2.setPath("/");
		cookList[0] = c0;
		cookList[1] = c1;
		cookList[2] = c2;
		CookieParam uCp = new CookieParam();
		uCp.setCookies(cookList);
		
		om = new ObjectMapper();
		ReqJ04Body j04body = new ReqJ04Body();
		j04body.setName(CoreUtil.getUUID());
		j04body.setPassword("123456");
		j04body.setRealName("李明");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J040101"));
		bodyStr = om.writeValueAsString(j04body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J04");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
		
		om = new ObjectMapper();
		ReqJ02Body j02body = new ReqJ02Body();
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J020101"));
		bodyStr = om.writeValueAsString(j02body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J02");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		
		om = new ObjectMapper();
		ReqJ03Body j03body = new ReqJ03Body();
		j03body.setRealName("李明");
		j03body.setIdentityId("123456");
		j03body.setCardNumber("999000000098");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J030101"));
		bodyStr = om.writeValueAsString(j03body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J03");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}

}
