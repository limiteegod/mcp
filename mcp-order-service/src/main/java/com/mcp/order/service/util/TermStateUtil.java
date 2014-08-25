package com.mcp.order.service.util;

/*
 * User: yeeson he
 * Date: 13-9-16
 * Time: 上午11:51
 */

import java.util.Properties;

public class TermStateUtil {
    public static Properties getProperties(String user, String desc, String term, String opType,int handleCode) {
        return getProperties(user, desc, term, opType, handleCode, true);
    }
    
    public static Properties getProperties(String user, String desc, String term, String opType,int handleCode, boolean updateDb) {
        Properties param = new Properties();
        param.setProperty("USER", user);   //TODO 需要从session中获取登录人员的姓名。
        param.setProperty("DESC", desc); //TODO 需要记录信息。
        param.setProperty("TERM", term);   //写入term的id
        param.setProperty("OPType", opType);
        param.setProperty("HANDLER",handleCode+"");
        param.setProperty("updateDb",String.valueOf(updateDb));	//是否更新数据库状态
        return param;
    }
}
