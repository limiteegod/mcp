/**
 * 
 */
package com.mcp.jc;

import com.deploy.filter.TestUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT05Body;
import com.mcp.order.inter.util.HttpClientUtil;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 *
 */
public class JcTrade {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqT05Body body = new ReqT05Body();
		
		ReqOrder order = new ReqOrder();
		order.setAmount(800);
		order.setBetType("21");
		//order.setAmount(276200);
		//order.setBetType("516");
		order.setGameCode("T51");
		order.setMultiple(1);
		order.setNumber("01|201312172001|1@1.4,2@3.2;01|201312172003|1@1.3,2@2.1");
		//order.setNumber("01|201312172001|1@1.0,2@3.2;01|201312172002|1@1.1,2@2.1;01|201312172003|1@1.2,3@1.8;01|201312172005|1@1.3,2@2.4,3@3.5;01|201312172006|1@1.4,2@2.1,3@2.78;01|201312172007|1@1.5");
		order.setPlatform("IOS");
		order.setPlayType("01");
		order.setStationId("91a730694eeb4dd4b3fca7a71a5e610d");
		body.setOrder(order);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T050101"));
		String bodyStr = om.writeValueAsString(body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "T05");
		System.out.println(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, null);
		System.out.println(content);
	}
}
