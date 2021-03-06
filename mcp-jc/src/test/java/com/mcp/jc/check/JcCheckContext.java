/**
 * 
 */
package com.mcp.jc.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ming.li
 *
 */
public class JcCheckContext {
	
	private static JcCheckContext context;
	
	/**
	 * Spring的上下文
	 */
	private ApplicationContext springContext;
	
	private JcCheckContext()
	{
		springContext = new FileSystemXmlApplicationContext(new String[]{"classpath:applicationContext-jc.xml"});
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
	
	public static JcCheckContext getInstance()
	{
		if(context == null)
		{
			context = new JcCheckContext();
		}
		return context;
	}
	
	/**
	 * 根据代码获得算奖的类
	 * @param code
	 * @return
	 */
	public Check getCheckByCode(String code)
	{
		BetType bt = LotteryContext.getInstance().getBetTypeByCode(code);
		return this.getBean(bt.getCheck(), Check.class);
	}
}
