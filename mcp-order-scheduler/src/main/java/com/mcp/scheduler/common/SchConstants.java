/**
 * 
 */
package com.mcp.scheduler.common;

import com.mcp.core.util.PropFile;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author ming.li
 *
 */
public class SchConstants {
	
	private static Properties props = PropFile.getProps("/scheduler.properties");
	
	/**
	 * 核心引擎所在服务器的奖期的文件夹
	 */
	public static final String ISSUE_FOLDER = props.getProperty("issue.folder");
	
	/**
	 * 核心引擎日结的文件夹
	 */
	public static final String ISSUE_DAILY_CLEAR_FOLDER = props.getProperty("issue.daily.clear.folder");
	
	/**
	 * 记录奖期的封存时间文件夹
	 */
	public static final String SEAL_TIME_FOLDER = ISSUE_DAILY_CLEAR_FOLDER + "/" + props.getProperty("seal.time.folder");
	
	/**
	 * ftp服务器地址
	 */
	public static final String FTP_HOST = props.getProperty("ftp.host");
	
	/**
	 * ftp服务器地址的端口
	 */
	public static final String FTP_PORT = props.getProperty("ftp.port");
	
	/**
	 * ftp服务器ssh所用的用户名
	 */
	public static final String FTP_USER = props.getProperty("ftp.user");
	
	/**
	 * ftp服务器ssh所用的密码
	 */
	public static final String FTP_PASSWORD = props.getProperty("ftp.password");
	
	/**
	 * ftp服务器ftp服务所在的路径
	 */
	public static final String FTP_PATH = props.getProperty("ftp.path");
	
	/**
	 * 获得当前奖期处理的文件夹
	 * @return
	 */
	public static String getIssueFolder(String gameCode, String termCode, int gameType, Date endTime)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(endTime);
		String year = dateString.substring(0, 4);
		String month = dateString.substring(5, 7);
		String day = dateString.substring(8, 10);
		StringBuffer sb = new StringBuffer();
		sb.append(SchConstants.ISSUE_FOLDER);
		sb.append("/");
		if(gameType == ConstantValues.Game_Type_Normal.getCode())
		{
			sb.append("normal/");
			sb.append(gameCode);
			sb.append("/");
			sb.append(year);
			sb.append("/");
			sb.append(termCode);
		}
		if(gameType == ConstantValues.Game_Type_Gaopin.getCode())
		{
			sb.append("gaopin/");
			sb.append(gameCode);
			sb.append("/");
			sb.append(year);
			sb.append("/");
			sb.append(month);
			sb.append("/");
			sb.append(day);
			sb.append("/");
			sb.append(termCode);
		}
		if(gameType == ConstantValues.Game_Type_Jingcai.getCode())
		{
			sb.append("jingcai/");
			sb.append(gameCode);
			sb.append("/");
			sb.append(year);
			sb.append("/");
			sb.append(month);
			sb.append("/");
			sb.append(day);
			sb.append("/");
			sb.append(termCode);
		}
		return sb.toString();
	}
	
	/**
	 * 记录封存文件文件夹
	 * @param gameCode
	 * @return
	 */
	public static String getSealTimeFolder(Date date, String gameCode)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(date);
		String year = dateString.substring(0, 4);
		String month = dateString.substring(5, 7);
		String day = dateString.substring(8, 10);
		StringBuffer sb = new StringBuffer();
		sb.append(SEAL_TIME_FOLDER);
		sb.append("/");
		sb.append(year);
		sb.append("/");
		sb.append(month);
		sb.append("/");
		sb.append(day);
		if(!StringUtil.isEmpty(gameCode))
		{
			sb.append("/");
			sb.append(gameCode);
		}
		return sb.toString();
	}
}
