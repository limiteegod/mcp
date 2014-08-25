/**
 * 
 */
package com.mcp.order.service.util;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.model.ts.TermLog;
import com.mcp.order.service.TermLogService;

import java.util.Date;

/**
 * @author ming.li
 *
 */
public class TermLogUtil {
	
	/**
	 * 记录期次变化的日志
	 * @param termLogService
	 * @param termId	期次的id
	 * @param description	期次的描述
	 */
	public static void log(TermLogService termLogService, String termId, String description)
	{
		//写期次操作信息
		TermLog tl = new TermLog();
		tl.setId(CoreUtil.getUUID());
		tl.setTermId(termId);
		tl.sethTime(new Date());
		tl.setUserId("SYSTEM");
		tl.setDescription(description);
		termLogService.save(tl);
	}
}
