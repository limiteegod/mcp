package com.mcp.mq.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MqContext {
	
	private static MqContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private MqContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath:applicationContext-mq.xml"});
	}
	
	public static MqContext getInstance()
	{
		if(context == null)
		{
			context = new MqContext();
		}
		return context;
	}
	
	/**
	 * 获得Spring的上下文
	 * @return
	 */
	public ApplicationContext getSpringContext()
	{
		return this.springContext;
	}
	
	/**
	 * 获得Spring的bean
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(String beanName, Class<T> clazz) {
	    return springContext.getBean(beanName, clazz);
	}
}
