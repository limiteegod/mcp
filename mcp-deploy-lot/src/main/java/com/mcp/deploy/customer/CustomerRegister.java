/**
 * 
 */
package com.mcp.deploy.customer;

import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.inter.account.ReqA01Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.ts.Customer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ming.li
 *
 */
public class CustomerRegister {
	
	private static Logger log = Logger.getLogger(CustomerRegister.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqA01Body reqA01Body = new ReqA01Body();
		Customer cus = new Customer();
		cus.setName("18612110987");
		cus.setPassword("123456");
		reqA01Body.setCustomer(cus);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A010101"));
		String bodyStr = om.writeValueAsString(reqA01Body);
		String message = TestUtil.getCReqMessage("", "Q0010", bodyStr, "A01");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
	}

}
