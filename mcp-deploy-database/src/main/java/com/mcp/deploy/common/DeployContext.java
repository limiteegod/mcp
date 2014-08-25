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
public class DeployContext {
	
	private static DeployContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private DeployContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath*:applicationContext.xml"});
	}
	
	public static DeployContext getInstance()
	{
		if(context == null)
		{
			context = new DeployContext();
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
