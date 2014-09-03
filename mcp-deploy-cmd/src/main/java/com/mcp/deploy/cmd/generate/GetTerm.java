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
        String gameCode = "T06";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 120*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"697a344a9f874fcab62d32c5812b9445\",\"gameCode\":\"T06\",\"code\":\"LV1\",\"name\":\"组选24\",\"gLevel\":1,\"bonus\":19700,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f53043a480c8438c9c2c86848bc2ac1f\",\"gameCode\":\"T06\",\"code\":\"LV2\",\"name\":\"组选12\",\"gLevel\":2,\"bonus\":39500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"3e41f88be56243208a333cb5282d7065\",\"gameCode\":\"T06\",\"code\":\"LV3\",\"name\":\"组选6\",\"gLevel\":3,\"bonus\":79100,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"cbe6111ec4a844d987abe79e9b605756\",\"gameCode\":\"T06\",\"code\":\"LV4\",\"name\":\"组选4\",\"gLevel\":4,\"bonus\":118700,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"b3ca412a073440d9b638f32ddeb58dfa\",\"gameCode\":\"T06\",\"code\":\"LV5\",\"name\":\"任选一\",\"gLevel\":5,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"958d7fbc0b5b42808e48361faa0104d0\",\"gameCode\":\"T06\",\"code\":\"LV6\",\"name\":\"任选二\",\"gLevel\":6,\"bonus\":7400,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"023838e456c64bf1b08001765ca1a10f\",\"gameCode\":\"T06\",\"code\":\"LV7\",\"name\":\"任选三\",\"gLevel\":7,\"bonus\":59300,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"0ca4d86841124c6ca88af9d4b186d7a7\",\"gameCode\":\"T06\",\"code\":\"LV8\",\"name\":\"任选四\",\"gLevel\":8,\"bonus\":475100,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        for(int i = 0; i < 4; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + "1,2,3,4" + "');";
            System.out.println(sql);
            curTermCode++;
        }
    }

}
