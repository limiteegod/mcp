/**
 * 
 */
package com.mcp.deploy.lot;

import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.scheme.ReqS01Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.scheme.model.SchemeZh;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 */
public class SchemeLotFromJqToWzl {
	
	private static Logger log = Logger.getLogger(SchemeLotFromJqToWzl.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
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
		
		ReqS01Body reqS01Body = new ReqS01Body();
		SchemeZh scheme = new SchemeZh();
		scheme.setWinStop(false);
		scheme.setPayType(ConstantValues.TOrder_PayType_Cash.getCode());
		scheme.setPlatform("IOS");
		scheme.setAmount(1200);
		scheme.setOrderCount(3);
		scheme.setGameCode("T05");
		scheme.setNumList("23~00~01,02,03~400~2");
		scheme.setStartTermCode("2013101009");
		reqS01Body.setScheme(scheme);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("S010101"));
		bodyStr = om.writeValueAsString(reqS01Body);
		message = TestUtil.getReqMessage("", "Q0003", bodyStr, "S01");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		uCp.headersToCookies();
	}

}
