package com.mcp.deploy.cmd.generate;

import com.mcp.core.util.CoreUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kfzx-liming on 2014/7/23.
 */
public class GetTerm {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception
    {
        String gameCode = "F01";
        int curTermCode = 2017078;
        long startDate = new Date().getTime();
        long gap = 120*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"231e4ab82822432b91f55b0391b952d1\",\"gameCode\":\"F01\",\"code\":\"LV1\",\"name\":\"一等奖\",\"gLevel\":1,\"bonus\":400000000,\"status\":1,\"gCount\":2,\"fixedBonus\":false},{\"id\":\"eafd90ba4a9e4357980444afaa166de6\",\"gameCode\":\"F01\",\"code\":\"LV2\",\"name\":\"二等奖\",\"gLevel\":2,\"bonus\":200000000,\"status\":1,\"gCount\":20,\"fixedBonus\":false},{\"id\":\"c01a82a7f17644da983a3602c99869f2\",\"gameCode\":\"F01\",\"code\":\"LV3\",\"name\":\"三等奖\",\"gLevel\":3,\"bonus\":300000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"22d30fa115aa4146ab63178622d82894\",\"gameCode\":\"F01\",\"code\":\"LV4\",\"name\":\"四等奖\",\"gLevel\":4,\"bonus\":20000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f6b152d9ba1b46b6bffff315e67cc844\",\"gameCode\":\"F01\",\"code\":\"LV5\",\"name\":\"五等奖\",\"gLevel\":5,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f1f3ab05a3064987a07780a129d1e5f7\",\"gameCode\":\"F01\",\"code\":\"LV6\",\"name\":\"六等奖\",\"gLevel\":6,\"bonus\":500,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        for(int i = 0; i < 20; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + "01,02,03,04,05,06|01" + "');";
            System.out.println(sql);
            curTermCode++;
        }
    }

}
