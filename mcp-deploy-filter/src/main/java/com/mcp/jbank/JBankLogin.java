/**
 * 
 */
package com.mcp.jbank;

import com.deploy.filter.TestUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.jbank.ReqJ01Body;
import com.mcp.order.inter.print.ReqP05Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 *
 */
public class JBankLogin {
	
	private static Logger log = Logger.getLogger(JBankLogin.class);

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
	}

}
