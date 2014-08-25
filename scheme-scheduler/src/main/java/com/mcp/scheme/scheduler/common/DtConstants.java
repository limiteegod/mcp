/**
 * 
 */
package com.mcp.scheme.scheduler.common;

import com.mcp.core.util.PropFile;
import com.mcp.order.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * @author ming.li
 *
 */
public class DtConstants {
	
	
	private static Properties props = PropFile.getProps("/dt.properties");
	
	public static final String DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * dt地址
	 */
	public static final String DT_HOST = props.getProperty("dt.host");
	
	/**
	 * dt所在服务器的用户名
	 */
	public static final String DT_HOST_USER = props.getProperty("dt.host.user");
	
	/**
	 * dt所在服务器的密码
	 */
	public static final String DT_HOST_PASSWORD = props.getProperty("dt.host.password");
	
	
	/**
	 * dt所在服务器的输入文件夹
	 */
	public static final String DT_FOLDER = props.getProperty("dt.folder");
	
	/**
	 * dt所在服务器的输出文件夹
	 */
	public static final String DT_FOLDER_OUT = props.getProperty("dt.folder.out");
	
	/**
	 * dt修改资料文件所在文件夹
	 */
	public static final String DT_XG_FOLDER = props.getProperty("dt.xg.folder");
	
	/**
	 * dt算奖之后，生成追号订单所在的文件夹
	 */
	public static final String DT_BET_FOLDER = props.getProperty("dt.bet.folder");
	
	/**
	 * dt修改资料文件结果文件所在文件夹
	 */
	public static final String DT_XG_FOLDER_OUT = props.getProperty("dt.xg.folder.out");
	
	/**
	 * 续投文件所在文件夹
	 */
	public static final String DT_XT_FOLDER = props.getProperty("dt.xt.folder");
	
	/**
	 * 定投到期方案所在文件夹
	 */
	public static final String DT_END_FOLDER = props.getProperty("dt.end.folder");
	
	/**
	 * 定投返奖文件所在文件夹
	 */
	public static final String DT_FJ_FOLDER = props.getProperty("dt.fj.folder");
	
	/**
	 * 续投文件扣款结果所在文件夹
	 */
	public static final String DT_XT_FOLDER_OUT = props.getProperty("dt.xt.folder.out");
	
	/**
	 * 参与定投业务的游戏 
	 */
	public static final String DT_GAMES = props.getProperty("dt.games");
	
	/**
	 * 渠道编码
	 */
	public static final String DT_CHANNELCODE = props.getProperty("dt.channelcode");
	
	/**
	 * 定投的出票的投注站
	 */
	public static final String DT_STATIONID = "91a730694eeb4dd4b3fca7a71a5e610d";
	
	/**
	 * 获得定投的文件名称（当天）
	 * @return
	 */
	public static String getDtFileName()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String date = sDateFormat.format(DateTimeUtil.getLastDate());
		
		StringBuffer sb = new StringBuffer();
		sb.append("DT306");
		sb.append(date);
		sb.append(".txt");
		return sb.toString();
	}
	
	/**
	 * 修改文件名称
	 * @return
	 */
	public static String getXGFileName()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String date = sDateFormat.format(DateTimeUtil.getLastDate());
		
		StringBuffer sb = new StringBuffer();
		sb.append("XG306");
		sb.append(date);
		sb.append(".txt");
		return sb.toString();
	}
	
	/**
	 * 修改文件名称
	 * @return
	 */
	public static String getXtFileName()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String date = sDateFormat.format(DateTimeUtil.getLastDate());
		
		StringBuffer sb = new StringBuffer();
		sb.append("XTKK306");
		sb.append(date);
		sb.append(".txt");
		return sb.toString();
	}
	
	/**
	 * 合约到期文件名称
	 * @return
	 */
	public static String getEndFileName()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String date = sDateFormat.format(DateTimeUtil.getLastDate());
		
		StringBuffer sb = new StringBuffer();
		sb.append("END306");
		sb.append(date);
		sb.append(".txt");
		return sb.toString();
	}
	
	/**
	 * 合约到期文件名称
	 * @return
	 */
	public static String getFjFileName()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String date = sDateFormat.format(DateTimeUtil.getLastDate());
		
		StringBuffer sb = new StringBuffer();
		sb.append("FJ306");
		sb.append(date);
		sb.append(".txt");
		return sb.toString();
	}
	
	/**
	 * 获得参与定投业务的游戏的编码
	 * @return
	 */
	public static List<String> getGameList()
	{
		List<String> gameList = new ArrayList<String>();
		String games = DT_GAMES;
		String[] gameArray = games.split(",");
		for(int i = 0; i < gameArray.length; i++)
		{
			gameList.add(gameArray[i]);
		}
		return gameList;
	}
	
	/**
	 * 获得前一天的日期字符串
	 * @return
	 */
	public static String getLastDateString()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return sDateFormat.format(DateTimeUtil.getLastDate());
	}
	
	/**
	 * 获得当天的日期字符串
	 * @return
	 */
	public static String getCurDateString()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return sDateFormat.format(new Date());
	}
	
	/**
	 * 获得追号自动生成订单的文件夹
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getDtBetFolder(String gameCode, String termCode)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(new Date());
		String year = dateString.substring(0, 4);
		String month = dateString.substring(5, 7);
		String day = dateString.substring(8, 10);
		
		StringBuffer sb = new StringBuffer();
		sb.append(DT_BET_FOLDER);
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
