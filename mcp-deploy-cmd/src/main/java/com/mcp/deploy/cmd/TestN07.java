package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.notify.ReqN07Body;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.jc.JOdds;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestN07 {
	
	private static Logger log = Logger.getLogger(TestN07.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqN07Body reqN07Body = new ReqN07Body();
		List<JOdds> oddsList = new ArrayList<JOdds>();
		JOdds odds = new JOdds();
		odds.setGameCode("T51");
		odds.setMatchCode("201312172001");
		odds.setOddsCode("CN");
		odds.setOddsName("中国赔率");
		odds.setPlayTypeCode("01");
		odds.setOddsInfo("4.22|3.12|2.02");
		oddsList.add(odds);
		reqN07Body.setOddsList(oddsList);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("N070101"));
		String bodyStr = om.writeValueAsString(reqN07Body);
		String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "N07", "123456");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
	}
}
