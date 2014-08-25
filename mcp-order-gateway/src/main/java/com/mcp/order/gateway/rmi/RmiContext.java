/**
 * 
 */
package com.mcp.order.gateway.rmi;

import com.mcp.rmi.common.RmiConstants;
import com.mcp.rmi.inter.SchemeInter;
import org.apache.log4j.Logger;

import java.rmi.Naming;

/**
 * @author ming.li
 *
 */
public class RmiContext {
	
	public static Logger log = Logger.getLogger(RmiContext.class);

	private static RmiContext context;
	
	private SchemeInter schemeInter;
	
	private RmiContext()
	{
		
	}
	
	public static RmiContext getInstance()
	{
		if(context == null)
		{
			context = new RmiContext();
		}
		return context;
	}
	
	public SchemeInter getSchemeInter()
	{
		if(schemeInter == null)
		{
			log.info("开始连接方案引擎rmi接口....");
			try {
				String url = RmiConstants.getSchemeEngineUri();
				schemeInter = (SchemeInter)Naming.lookup(url);
			} catch (Exception e) {
				throw new RuntimeException("请检查方案引擎rmi接口是否开启...");
			}
			log.info("成功连接方案引擎rmi接口....");
		}
		return schemeInter;
	}
}
