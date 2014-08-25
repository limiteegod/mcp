/**
 * 
 */
package com.mcp.print;

import com.deploy.filter.TestUtil;
import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.PropFile;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.print.RepP01Body;
import com.mcp.order.inter.print.RepP01Ticket;
import com.mcp.order.inter.print.ReqP01Body;
import com.mcp.order.inter.print.ReqP02Body;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;
import java.util.Properties;

/**
 * @author ming.li
 *
 */
public class StationPrint {
	
	private static Logger log = Logger.getLogger(StationPrint.class);
	
	private static Properties props = PropFile.getProps("/sprint.properties");
	
	public static final String stationCode = props.getProperty("station.code");
	
	public static final String stationKey = props.getProperty("station.key");

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		while(true)
		{
			try {
				log.info("get tickets....................");
				ReqP01Body p01Body = new ReqP01Body();
				p01Body.setSize(10);
				ObjectMapper om = new ObjectMapper();
				om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P010101"));
				String p01BodyStr = om.writeValueAsString(p01Body);
				String p01message = TestUtil.getCReqMessage("", stationCode, p01BodyStr, "P01", stationKey);
				String p01content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, p01message, HttpClientUtil.POST, null);
				log.info(p01content);
				
				McpGtMsg rep = om.readValue(p01content, McpGtMsg.class);
				String repBodyStr = rep.getBody();
				RepP01Body repbody = om.readValue(repBodyStr, RepP01Body.class);
				List<RepP01Ticket> tList = repbody.getTickets();
				for(RepP01Ticket t:tList)
				{
					ReqP02Body p02Body = new ReqP02Body();
					p02Body.setCode(Constants.TICKET_PRINT_RECEIPT_SUCCESS);
					p02Body.setPaper(false);
					p02Body.setTicketId(t.getTicketId());
					om = new ObjectMapper();
					om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P020101"));
					String p02BodyStr = om.writeValueAsString(p02Body);
					String p02message = TestUtil.getCReqMessage("", stationCode, p02BodyStr, "P02", stationKey);
					log.info(p02message);
					String p02content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, p02message, HttpClientUtil.POST, null);
					log.info(p02content);
				}

                if(tList.size() == 0)
                {
                    Thread.sleep(10000);
                }
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
