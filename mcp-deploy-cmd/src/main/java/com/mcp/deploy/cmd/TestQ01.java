package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ01Body;
import com.mcp.order.inter.query.ReqQ01Term;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestQ01 {
	
	private static Logger log = Logger.getLogger(TestQ01.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();

		ReqQ01Body reqQ01Body = new ReqQ01Body();
		reqQ01Body.setType(0);
		ReqQ01Term term = new ReqQ01Term();
		term.setGameCode("T05");
		term.setTermCode("2013101009");
		List<ReqQ01Term> tList = new ArrayList<ReqQ01Term>();
		tList.add(term);
		reqQ01Body.setTerms(tList);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q010101"));
		String bodyStr = om.writeValueAsString(reqQ01Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q01");
		log.info(bodyStr);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(om.readValue(content, McpGtMsg.class).getBody());

        reqQ01Body = new ReqQ01Body();
        reqQ01Body.setType(2);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q010101"));
        bodyStr = om.writeValueAsString(reqQ01Body);
        message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q01");
        log.info(bodyStr);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(om.readValue(content, McpGtMsg.class).getBody());

        reqQ01Body = new ReqQ01Body();
        reqQ01Body.setType(3);
        reqQ01Body.setGameCode("T05");
        reqQ01Body.setStartIndex(0);
        reqQ01Body.setSize(10);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q010101"));
        bodyStr = om.writeValueAsString(reqQ01Body);
        message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q01");
        log.info(bodyStr);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(om.readValue(content, McpGtMsg.class).getBody());

        reqQ01Body = new ReqQ01Body();
        reqQ01Body.setType(4);
        term = new ReqQ01Term();
        term.setGameCode("T05");
        tList = new ArrayList<ReqQ01Term>();
        tList.add(term);
        reqQ01Body.setTerms(tList);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q010101"));
        bodyStr = om.writeValueAsString(reqQ01Body);
        message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q01");
        log.info(bodyStr);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(om.readValue(content, McpGtMsg.class).getBody());
	}
}
