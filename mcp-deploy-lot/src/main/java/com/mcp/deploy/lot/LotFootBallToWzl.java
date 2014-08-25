/**
 * 
 */
package com.mcp.deploy.lot;

import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT05Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 */
public class LotFootBallToWzl {
	
	private static Logger log = Logger.getLogger(LotFootBallToWzl.class);
	
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
		System.out.println(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		System.out.println(content);
		uCp.headersToCookies();
		
		for(int i = 0; i < JcConfig.LOT_COUNT; i++)
		{
			om = new ObjectMapper();
			ReqT05Body t05body = new ReqT05Body();
			t05body.setPayType(ConstantValues.TOrder_PayType_Cash.getCode());
			ReqOrder reqOrder = new ReqOrder();
			reqOrder.setGameCode("T51");
			reqOrder.setAmount(JcConfig.AMOUNT);
			reqOrder.setPlatform("ANDROID");
			reqOrder.setPlayType(JcConfig.PLAY_TYPE);
			reqOrder.setBetType(JcConfig.BET_TYPE);
			reqOrder.setMultiple(1);
			reqOrder.setNumber(JcConfig.NUMBER);
			t05body.setOrder(reqOrder);
			om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T050101"));
			bodyStr = om.writeValueAsString(t05body);
			message = TestUtil.getReqMessage("", "Q0003", bodyStr, "T05");
			log.info(message);
			content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
			log.info(content);
		}
	}

}
