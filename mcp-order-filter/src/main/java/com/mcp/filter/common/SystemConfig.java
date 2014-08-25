/**
 * 
 */
package com.mcp.filter.common;

import com.mcp.core.util.PropFile;

import java.util.Properties;

/**
 * @author ming.li
 *
 */
public class SystemConfig {
	
	private static Properties props = PropFile.getProps("/filter.properties");
	
	/**
	 * 网关地址
	 */
	public static final String GATEWAY_SITE = props.getProperty("gateway_site");
	
	/**
	 * 网关端口
	 */
	public static final int GATEWAY_PORT = Integer.parseInt(props.getProperty("gateway_port"));
}
