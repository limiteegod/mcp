/**
 * 
 */
package com.mcp.mq.converter;

import com.mcp.mq.model.McpMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author ming.li
 *
 */
public class McpMessageConverter implements MessageConverter {

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
	 */
	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		McpMessage msg = (McpMessage)object;
		TextMessage tm = new ActiveMQTextMessage();
		ObjectMapper om = new ObjectMapper();
		try {
			tm.setText(om.writeValueAsString(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tm;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		TextMessage tm = (TextMessage)message;
		String s = tm.getText();
		ObjectMapper om = new ObjectMapper();
		McpMessage msg = null;
		try {
			msg = om.readValue(s, McpMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

}
