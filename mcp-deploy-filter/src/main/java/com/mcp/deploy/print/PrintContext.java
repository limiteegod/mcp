package com.mcp.deploy.print;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class PrintContext {
	
	private static PrintContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private PrintContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath*:applicationContext.xml", "classpath*:applicationContext-mq.xml", "classpath*:applicationContext-consumer.xml"});
	}
	
	public static PrintContext getInstance()
	{
		if(context == null)
		{
			context = new PrintContext();
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
