/**
 * 
 */
package com.mcp.notify.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;



/**
 * @author ming.li
 *
 */
public class NotifyContext {
	
	private static NotifyContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private NotifyContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath*:applicationContext-notify.xml"});
	}
	
	public static NotifyContext getInstance()
	{
		if(context == null)
		{
			context = new NotifyContext();
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
