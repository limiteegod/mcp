package com.mcp.admin.util;

import com.mcp.order.model.common.LotteryContext;

/**
 * Created by limitee on 2014/8/7.
 */
public class GameUtil {

    public static String getNameByCode(String code)
    {
        return LotteryContext.getInstance().getGameByCode(code).getName();
    }
}
