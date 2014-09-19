package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;

/**
 * Created by ming.li on 2014/6/16.
 */
public class GetId {

    public static void main(String[] args) throws Exception
    {
        /*System.out.println(CoreUtil.getUUID());

        String a = "abc";
        if(a instanceof String)
        {

        }*/
        System.out.println(MD5Util.MD5("abc可以"));
    }

}
