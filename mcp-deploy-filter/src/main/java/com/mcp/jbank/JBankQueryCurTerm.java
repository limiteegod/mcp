/**
 * 
 */
package com.mcp.jbank;

import com.deploy.filter.TestUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.jbank.ReqJ06Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 *
 */
public class JBankQueryCurTerm {
	
	private static Logger log = Logger.getLogger(JBankQueryCurTerm.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CookieParam sCp = new CookieParam();
		ObjectMapper om = new ObjectMapper();
		ReqJ06Body reqJ06Body = new ReqJ06Body();
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J060101"));
		String bodyStr = om.writeValueAsString(reqJ06Body);
		String message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J06");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
	}

}
