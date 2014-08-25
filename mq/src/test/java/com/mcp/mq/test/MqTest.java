/**
 * 
 */
package com.mcp.mq.test;

import org.junit.Test;
/**
 * @author ming.li
 * 
 */
public class MqTest {

	@Test
	public void testSend() {
		/*Destination des = MqContext.getInstance().getBean(
				"matchQueue", Destination.class);
		JmsTemplate jmsTemplate = MqContext.getInstance().getBean(
				"jmsTemplate", JmsTemplate.class);
		jmsTemplate.send(des, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("这是一个测试");
			}
		});*/
	}

	@Test
	public void testReceive() throws Exception
	{
		/*Destination des = MqContext.getInstance().getBean(
				"matchQueue", Destination.class);
		JmsTemplate jmsTemplate = MqContext.getInstance().getBean(
				"jmsTemplate", JmsTemplate.class);
		TextMessage message  = (TextMessage)jmsTemplate.receive(des);  
        System.out.println("reviced msg is:" + message.getText());*/
	}
}
