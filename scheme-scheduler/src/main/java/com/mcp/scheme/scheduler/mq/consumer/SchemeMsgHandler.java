/**
 * 
 */
package com.mcp.scheme.scheduler.mq.consumer;

/**
 * @author ming.li
 *
 */
public class SchemeMsgHandler implements MsgHandler {

	/*public static Logger log = Logger.getLogger(SchemeMsgHandler.class);
	
	
	@Autowired
	private SchemeZhService schemeZhService;
	
	@Autowired
	private OrderInter orderInter;
	
	*//* (non-Javadoc)
	 * @see com.mcp.mq.handler.MsgHandler#handler(com.mcp.mq.model.McpMessage)
	 *//*
	@Override
	public void handler(McpMessage msg) throws Exception {
		ObjectMapper om = new ObjectMapper();
		String ct = msg.getContent();
		log.info(ct);
		if(msg.getCode() == 1010)
		{
			TermChange termChange = om.readValue(ct, TermChange.class);
			String gameCode = termChange.getGameCode();
			String termCode = termChange.getTermCode();
			int status = termChange.getStatus();
			if(status == TermState.CALCULATE.getCode())
			{
				//先处理追号方案
				ZhModel zh = new ZhModel();
				zh.termDrawed(gameCode, termCode, termChange.getNextTermCode());
			}
		}
	}*/
}
