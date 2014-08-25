/**
 * 
 */
package com.mcp.rmi.common;

import com.mcp.core.util.PropFile;

import java.util.Properties;

/**
 * @author ming.li
 *
 */
public class RmiConstants {
	
	
	private static Properties props = PropFile.getProps("/rmi.properties");
	
	/**
	 * 订单引擎ip
	 */
	public static final String RMI_ORDER_HOST = props.getProperty("rmi.order.host");
	
	/**
	 * 订单引擎port
	 */
	public static final int RMI_ORDER_PORT = Integer.parseInt(props.getProperty("rmi.order.port"));
	
	/**
	 * 订单引擎name
	 */
	public static final String RMI_ORDER_NAME = props.getProperty("rmi.order.name");
	
	/**
	 * 方案引擎ip
	 */
	public static final String RMI_SCHEME_HOST = props.getProperty("rmi.scheme.host");
	
	/**
	 * 方案引擎port
	 */
	public static final int RMI_SCHEME_PORT = Integer.parseInt(props.getProperty("rmi.scheme.port"));
	
	/**
	 * 方案引擎name
	 */
	public static final String RMI_SCHEME_NAME = props.getProperty("rmi.scheme.name");
	
	/**
	 * rmi数据传输端口
	 */
	public static final String RMI_DATA_PORT = props.getProperty("rmi.data.port");
	
	/**
	 * 获得订单引擎地址
	 * @return
	 */
	public static String getOrderEngineUri()
	{
		return "//" + RMI_ORDER_HOST + ":" + RMI_ORDER_PORT + "/" + RMI_ORDER_NAME;
	}
	
	
	/**
	 * 获得方案引擎地址
	 * @return
	 */
	public static String getSchemeEngineUri()
	{
		return "//" + RMI_SCHEME_HOST + ":" + RMI_SCHEME_PORT + "/" + RMI_SCHEME_NAME;
	}
}
