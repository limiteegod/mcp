package com.mcp.admin.util;

import com.mcp.order.common.Constants;
import com.mcp.order.util.DateTimeUtil;

import java.text.ParseException;

/**
 * Created by limitee on 2014/8/7.
 */
public class DateConvert {

    public static String getCommonDate(String dateStr) throws ParseException
    {
        return DateTimeUtil.convert(dateStr, Constants.DATE_FORMAT, "yyyy-MM-dd HH:mm:ss");
    }
}
