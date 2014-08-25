/**
 * 
 */
package com.mcp.scheme.scheduler.common;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ming.li
 *
 */
public class SchemeContext {
	
	public static Logger log = Logger.getLogger(SchemeContext.class);
	
	private static SchemeContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private SchemeContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath:applicationContext-scheme.xml"});
	}
	
	public static SchemeContext getInstance()
	{
		if(context == null)
		{
			context = new SchemeContext();
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
