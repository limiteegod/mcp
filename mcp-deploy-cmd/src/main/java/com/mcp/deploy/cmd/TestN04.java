package com.mcp.deploy.cmd;

import com.mcp.core.util.PropFile;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.notify.ReqN04Body;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.ts.Term;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TestN04 {
	
	private static Logger log = Logger.getLogger(TestN04.class);
	
	private static Properties props = PropFile.getProps("/term.properties");
	
	public static final String code = props.getProperty("code");
	
	public static final String nextCode = props.getProperty("nextCode");
	
	public static final String name = props.getProperty("name");
	
	public static final int status = Integer.parseInt(props.getProperty("status"));

	public static void main(String[] args) throws Exception {
		/*
		ReqN04Body reqN04Body = new ReqN04Body();
		List<Term> tList = new ArrayList<Term>();
		Term t = new Term();
		t.setGameCode("T05");
		t.setOpenTime(new Date());
		t.setEndTime(DateTimeUtil.getDateAfterMilliseconds(30*60*1000));
		t.setCode(code);
		t.setName(name);
		t.setNextCode(nextCode);
		t.setStatus(status);
		tList.add(t);
		reqN04Body.setTerms(tList);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("N040101"));
		String bodyStr = om.writeValueAsString(reqN04Body);
		String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "N04", "123456");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);*/

        ObjectMapper om = new ObjectMapper();
        String bodyStr = "";
        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "A02", key);
        String message = TestUtil.getDesCReqMessage("C0002", "C0002", bodyStr, "N04", "wzl0okmnhy6");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), "wzl0okmnhy6"));



	}
}
