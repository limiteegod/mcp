package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ16Body;
import com.mcp.order.inter.query.ReqQ17Body;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 测试渠道查询订单信息
 */
public class TestQ17 {
	
	private static Logger log = Logger.getLogger(TestQ17.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        ReqQ17Body reqQ17Body = new ReqQ17Body();
        reqQ17Body.setMinId(10);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q170101"));
        String bodyStr = om.writeValueAsString(reqQ17Body);
        String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "Q17", "123456");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
	}
}
