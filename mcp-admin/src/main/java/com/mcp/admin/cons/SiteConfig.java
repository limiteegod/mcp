/**
 * 
 */
package com.mcp.admin.cons;

import com.mcp.core.util.PropFile;

import java.util.Properties;

/**
 * @author ming.li
 *
 */
public class SiteConfig {
	
	private static Properties props = PropFile.getProps("/site.properties");
	
	/**
	 * 网关地址
	 */
	public static final String SITE_FILTER = props.getProperty("site.filter");
	
	/**
	 * 网关端口
	 */
	public static final int SITE_FILTER_PORT = Integer.parseInt(props.getProperty("site.filter.port"));

    /**
     * 地址
     */
    public static final String SITE_FILTER_PATH = props.getProperty("site.filter.path");
}
