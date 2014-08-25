/**
 * 
 */
package com.mcp.deploy.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ming.li
 *
 */
public class JdbcContext {
	
	private static JdbcContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private JdbcContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath*:applicationContext-jdbc.xml"});
	}
	
	public static JdbcContext getInstance()
	{
		if(context == null)
		{
			context = new JdbcContext();
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
