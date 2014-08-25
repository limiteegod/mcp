package com.mcp.admin.util;

import com.mcp.order.status.TermState;

/**
 * Created by limitee on 2014/8/7.
 */
public class StatusUtil {

    public static String getTermStatusDes(int status)
    {
        return TermState.getValuesMap().get(status).getDesc();
    }

}
