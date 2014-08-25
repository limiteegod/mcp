/**
 * 
 */
package com.mcp.scheme.scheduler.common;

import com.mcp.core.util.PropFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 * @author ming.li
 *
 */
public class ZhConstants {
	
	
	private static Properties props = PropFile.getProps("/zh.properties");
	
	/**
	 * dt地址
	 */
	public static final String ZH_FOLDER = props.getProperty("zh.folder");
	
	/**
	 * 获得追号自动生成订单的文件夹
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getZhFolder(String gameCode, String termCode)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(new Date());
		String year = dateString.substring(0, 4);
		String month = dateString.substring(5, 7);
		String day = dateString.substring(8, 10);
		
		StringBuffer sb = new StringBuffer();
		sb.append(ZH_FOLDER);
		sb.append("/");
		sb.append(gameCode);
		sb.append("/");
		sb.append(year);
		sb.append("/");
		sb.append(month);
		sb.append("/");
		sb.append(day);
		sb.append("/");
		sb.append(termCode);
		
		return sb.toString();
	}
}
