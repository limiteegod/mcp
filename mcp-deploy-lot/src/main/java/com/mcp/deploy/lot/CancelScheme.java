/**
 * 
 */
package com.mcp.deploy.lot;

import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.scheme.ReqS03Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 */
public class CancelScheme {
	
	private static Logger log = Logger.getLogger(CancelScheme.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if(args.length == 0)
		{
			log.info("请输入要取消的方案的id......");
			return;
		}
		CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName("limitee2");
		reqA04Body.setPassword("123456");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "A04");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		uCp.headersToCookies();
		
		ReqS03Body reqS03Body = new ReqS03Body();
		reqS03Body.setSchemeId(args[0]);
		reqS03Body.setSchemeType(ConstantValues.TScheme_Type_Follow.getCode());
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("S030101"));
		bodyStr = om.writeValueAsString(reqS03Body);
		message = TestUtil.getReqMessage("", "Q0003", bodyStr, "S03");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}

}
