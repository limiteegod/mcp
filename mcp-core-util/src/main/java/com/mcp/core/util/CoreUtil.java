/**
 * 
 */
package com.mcp.core.util;

import java.util.UUID;

/**
 * @author ming.li
 *
 */
public class CoreUtil {
	
	/**
	 * 获得32位唯一id
	 * @return
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
}
