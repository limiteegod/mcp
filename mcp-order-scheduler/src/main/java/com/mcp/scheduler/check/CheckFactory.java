/**
 * 
 */
package com.mcp.scheduler.check;

import com.mcp.order.batch.check.Check;
import com.mcp.scheduler.common.SchedulerContext;

/**
 * @author ming.li
 *
 */
public class CheckFactory {
	
	public static Check getCheckByName(String name)
	{
		return SchedulerContext.getInstance().getBean(name, Check.class);
	}
}
