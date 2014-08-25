/**
 * 
 */
package com.mcp.order.util;

import com.mcp.order.common.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ming.li
 *
 */
public class DateTimeUtil {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	private static final SimpleDateFormat alipayDateFormat = new SimpleDateFormat(Constants.ALIPAY_DATE_FORMAT);
	
	/**
	 * 获得n年后的时间
	 * @param date
	 * @return
	 */
	public static Date getYear(Date date, int n)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); 
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + n);//让日期加1  
		return calendar.getTime();
	}
    /**
     * 获得今天的开始时间点
     * @param
     * @return
     */
    public static Date getToday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }
    
    /**
     * 获得上一天的日期
     * @return
     */
    public static Date getLastDate()
    {
    	return new Date(System.currentTimeMillis() - 24*60*60*1000);
    }
    
    /**
     * 获得milliseconds毫秒之后的时间
     * @param
     * @return
     */
    public static Date getDateAfterMilliseconds(long milliseconds)
    {
       return new Date(System.currentTimeMillis() + milliseconds);
    }

    /**
     * 获得milliseconds毫秒之前的时间
     * @param milliseconds
     * @return
     */
    public static Date getDateBeforeMilliseconds(long milliseconds)
    {
        return new Date(System.currentTimeMillis() - milliseconds);
    }
    
    /**
     * 获得milliseconds毫秒之后的时间
     * @param
     * @return
     */
    public static Date getDateAfterMilliseconds(Date date, long milliseconds)
    {
       return new Date(date.getTime() + milliseconds);
    }

    /**
     * 获得milliseconds毫秒之前的时间
     * @param
     * @return
     */
    public static Date getDateBeforeMilliseconds(Date date, long milliseconds)
    {
        return new Date(date.getTime() - milliseconds);
    }
    
	/**
	 * 获得当前时间
	 * @return
	 */
	public static String getCurrentTime()
	{
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获得对应时间的标准字符串格式
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date)
	{
		return dateFormat.format(date);
	}
	
	/**
	 * 获得支付宝通知时间
	 * @param date
	 * @return
	 */
	public static String alipayGetDateString(Date date)
	{
		return alipayDateFormat.format(date);
	}
	
	public static Date getDateFromString(String dateString) throws ParseException
	{
		return dateFormat.parse(dateString);
	}
	
	public static Date alipayGetDateFromString(String dateString) throws ParseException
	{
		return alipayDateFormat.parse(dateString);
	}

    public static void main(String[] args) {

        System.out.println(getToday());
    }


    /**
     * 时间格式进行转换
     * @param dateStr
     * @param fromFormat
     * @param toFormat
     * @return
     * @throws ParseException
     */
    public static String convert(String dateStr, String fromFormat, String toFormat) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(fromFormat);
        SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat);
        Date date = dateFormat.parse(dateStr);
        return toDateFormat.format(date);
    }
}
