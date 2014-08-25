/**
 * 
 */
package com.mcp.mq.service;

import com.mcp.mq.common.McpMessageContext;
import com.mcp.mq.model.McpMessage;
import com.mcp.mq.model.MessageConfig;
import org.apache.activemq.command.ActiveMQQueue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author ming.li
 *
 */
@Service("mcpMsgSender")
public class McpMsgSender {
	
	@Autowired
	private McpProducer mcpProducer;
	
	public void sendMsg(ApplicationContext context, int code, Object obj) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		McpMessage msg = new McpMessage();
		msg.setCode(code);
		FilterProvider fp = McpMessageContext.getInstance().getDefaultFilterProviderByCode(String.valueOf(code));
		om.setFilters(fp);
		msg.setContent(om.writeValueAsString(obj));
		
		MessageConfig mc = McpMessageContext.getInstance().getMcByCode(code);
		String queueName = mc.getQueue();
		
		ActiveMQQueue queue = context.getBean(queueName, ActiveMQQueue.class);
		mcpProducer.send(queue, msg);
	}
	
}
