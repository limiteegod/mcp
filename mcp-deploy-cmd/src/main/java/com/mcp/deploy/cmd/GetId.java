package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;

/**
 * Created by ming.li on 2014/6/16.
 */
public class GetId {

    public static void main(String[] args) throws Exception
    {
        System.out.println(CoreUtil.getUUID());
    }

}
