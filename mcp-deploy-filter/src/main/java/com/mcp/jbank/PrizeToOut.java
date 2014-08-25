/**
 * 
 */
package com.mcp.jbank;

import com.deploy.filter.TestUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.cash.ReqC01Body;
import com.mcp.order.inter.cash.ReqC03Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.jbank.RepJ01Body;
import com.mcp.order.inter.jbank.RepJ01Cookie;
import com.mcp.order.inter.jbank.ReqJ01Body;
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
public class PrizeToOut {
	
	private static Logger log = Logger.getLogger(PrizeToOut.class);

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
		ReqC01Body c01body = new ReqC01Body();
		c01body.setAmount(100);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C010101"));
		bodyStr = om.writeValueAsString(c01body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "C01");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		
		om = new ObjectMapper();
		ReqC03Body c03body = new ReqC03Body();
		c03body.setAmount(100);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C030101"));
		bodyStr = om.writeValueAsString(c03body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "C03");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}

}
