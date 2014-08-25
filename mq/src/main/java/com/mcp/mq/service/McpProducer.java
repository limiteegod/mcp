/**
 * 
 */
package com.mcp.mq.service;

import com.mcp.mq.model.McpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


/**
 * @author ming.li
 *
 */
@Service("mcpProducer")
public class McpProducer {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(Destination dest, McpMessage msg) {
		jmsTemplate.convertAndSend(dest, msg);
	}
}
