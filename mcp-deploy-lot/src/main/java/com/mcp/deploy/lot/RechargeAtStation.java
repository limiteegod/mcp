package com.mcp.deploy.lot;

import com.mcp.core.util.PropFile;
import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.cash.ReqC02Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Properties;

public class RechargeAtStation {
	
	private static Properties props = PropFile.getProps("/recharge.properties");
	
	public static final String CHANNEL_CODE = props.getProperty("channel.code");
	
	public static final String CUSTOMER_NAME = props.getProperty("customer.name");
	
	public static final long AMOUNT = Long.parseLong(props.getProperty("customer.amount"));
	
	private static Logger log = Logger.getLogger(RechargeAtStation.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqC02Body reqC02body = new ReqC02Body();
		reqC02body.setAmount(AMOUNT);
		reqC02body.setName(CUSTOMER_NAME);
		reqC02body.setFromType(ConstantValues.Recharge_Channel_Alipay.getCode());
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C020101"));
		String bodyStr = om.writeValueAsString(reqC02body);
		String message = TestUtil.getCReqMessage("", CHANNEL_CODE, bodyStr, "C02");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
	}

}
