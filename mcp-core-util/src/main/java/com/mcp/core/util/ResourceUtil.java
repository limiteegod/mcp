/**
 * 
 */
package com.mcp.core.util;

import java.net.URL;

/**
 * @author ming.li
 *
 */
public class ResourceUtil {
	
	/**
	 * 通过相对路径来获得资源的绝对路经。
	 * 比如src/main/resources/xml/accountOperatorType.xml文件，
	 * 则relativePath为xml/accountOperatorType.xml 
	 * @param relativePath
	 * @return
	 */
	public static URL getURL(String relativePath)
	{
		return ResourceUtil.class.getClassLoader().getResource(relativePath);
	}
}
