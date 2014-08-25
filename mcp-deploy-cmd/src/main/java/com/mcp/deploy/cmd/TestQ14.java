package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ14Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestQ14 {
	
	private static Logger log = Logger.getLogger(TestQ14.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		ObjectMapper om = new ObjectMapper();
		ReqQ14Body reqQ14Body = new ReqQ14Body();
		reqQ14Body.setGameCode("T51");
		List<Integer> statusList = new ArrayList<Integer>();
		List<String> oddsCodeList = new ArrayList<String>();
		List<String> playTypeCodeList = new ArrayList<String>();
		oddsCodeList.add("CN");
		playTypeCodeList.add("01");
		statusList.add(1200);
		reqQ14Body.setStatus(statusList);
		reqQ14Body.setOddsCode(oddsCodeList);
		reqQ14Body.setPlayTypeCode(playTypeCodeList);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q140101"));
		String bodyStr = om.writeValueAsString(reqQ14Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q14");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}
}
