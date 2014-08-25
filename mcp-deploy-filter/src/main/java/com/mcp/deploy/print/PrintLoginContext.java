/**
 * 
 */
package com.mcp.deploy.print;


import com.deploy.filter.TestUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.print.ReqP05Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 *
 */
public class PrintLoginContext {
	
	private static Logger log = Logger.getLogger(PrintLoginContext.class);
	
	private CookieParam sCp;
	
	private static PrintLoginContext context;
	
	private PrintLoginContext()
	{
		sCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqP05Body body = new ReqP05Body();
		body.setCode("C0001");
		body.setPassword("123456");
		body.setType(Constants.STATION_OP_LOGIN);
		try {
			String bodyStr = om.writeValueAsString(body);
			String message = TestUtil.getReqMessage("", "C0001", bodyStr, "P05");
			String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
			log.info(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sCp.headersToCookies();
		
		/*uCp = new CookieParam();
		
		ReqA04Body ubody = new ReqA04Body();
		ubody.setName("limitee2");
		ubody.setPassword("123456");
		try {
			String bodyStr = om.writeValueAsString(ubody);
			String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "A04");
			String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
			log.info(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		uCp.headersToCookies();*/
	}
	
	public static synchronized PrintLoginContext getInstance(boolean refresh)
	{
		if(refresh || context == null)
		{
			context = new PrintLoginContext();
		}
		return context;
	}
	
	public CookieParam getSCp()
	{
		return this.sCp;
	}
}
