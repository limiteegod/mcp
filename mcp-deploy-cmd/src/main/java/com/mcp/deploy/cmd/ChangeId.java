package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ming.li on 2014/6/20.
 */
public class ChangeId {

    private static Map<String, String> tMap = new HashMap<String, String>();

    static {
        tMap.put("c77cd06157214a048ca09381e6e582e4", "");
        tMap.put("148e1f83d20d47d680984c0b32e0191c", "");

        tMap.put("5646f6b37ddb4d62bf9d824fbe14acde", "");
        tMap.put("af2a2393dd704789aebfb6ddcdb0d96a", "");
        tMap.put("30d3963d544f48f0afb993cb58f221af", "");
        tMap.put("7a3afaecbfad4c679481e896c4f5e7a4", "");

        tMap.put("b6cb87b0b0b74973b88c28a47c34f5f2", "");
        tMap.put("bdcf4f36c9bf4aeb9573c9fc35c386ca", "");
        tMap.put("be167748e5c142f19ffe82d64bc4e7ef", "");

        tMap.put("4244bb2aebae4c3d861c65e1134b4b17", "");
    }

    public static void main(String[] args)
    {
        Set<String> keys = tMap.keySet();
        StringBuffer ids = new StringBuffer();
        int count = 0;
        for(String key:keys)
        {
            System.out.println("update tticket set id='" + CoreUtil.getUUID() + "' where id='" + key + "';");
        }
    }
}
