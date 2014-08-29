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
        String gameCode = "F04";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 120*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"4e34b3b2fced442e8c6914de68a3a997\",\"gameCode\":\"F04\",\"code\":\"LV1\",\"name\":\"和值4\",\"gLevel\":1,\"bonus\":8000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"dfe94251e1af4f9f882ec6edc1e2a117\",\"gameCode\":\"F04\",\"code\":\"LV2\",\"name\":\"和值5\",\"gLevel\":2,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"018c1ff35465450a98f450da8685a584\",\"gameCode\":\"F04\",\"code\":\"LV3\",\"name\":\"和值6\",\"gLevel\":3,\"bonus\":2500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"ec1f52f562ca4d92b8828062fc29d309\",\"gameCode\":\"F04\",\"code\":\"LV4\",\"name\":\"和值7\",\"gLevel\":4,\"bonus\":1600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"1ad4ccf24c394e5e9159ad14644db441\",\"gameCode\":\"F04\",\"code\":\"LV5\",\"name\":\"和值8\",\"gLevel\":5,\"bonus\":1200,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"c3c78763c55f4a189720334d9f7258af\",\"gameCode\":\"F04\",\"code\":\"LV6\",\"name\":\"和值9\",\"gLevel\":6,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"1ff6dbe0071c423188b92f9c9c5682b1\",\"gameCode\":\"F04\",\"code\":\"LV7\",\"name\":\"和值10\",\"gLevel\":7,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"078defbb39a74f43902707dd3918319d\",\"gameCode\":\"F04\",\"code\":\"LV8\",\"name\":\"和值11\",\"gLevel\":8,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"4830429e3f774785abb6fa9369b67358\",\"gameCode\":\"F04\",\"code\":\"LV9\",\"name\":\"和值12\",\"gLevel\":9,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"348b113c335f424cb5702ba727a23d73\",\"gameCode\":\"F04\",\"code\":\"LV10\",\"name\":\"和值13\",\"gLevel\":10,\"bonus\":1200,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"fa586dc9096a4f299f043197be305f6a\",\"gameCode\":\"F04\",\"code\":\"LV11\",\"name\":\"和值14\",\"gLevel\":11,\"bonus\":1600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f6889339841446baaa2a1b43be01639b\",\"gameCode\":\"F04\",\"code\":\"LV12\",\"name\":\"和值15\",\"gLevel\":12,\"bonus\":2500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f171cbe2890c493db9e22170e61a5c7f\",\"gameCode\":\"F04\",\"code\":\"LV13\",\"name\":\"和值16\",\"gLevel\":13,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"da2ed64e589444eabfb963f05bce7e5a\",\"gameCode\":\"F04\",\"code\":\"LV14\",\"name\":\"和值17\",\"gLevel\":14,\"bonus\":8000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"81728eda95f744349bd1362ca56a61c0\",\"gameCode\":\"F04\",\"code\":\"LV15\",\"name\":\"三同号通选\",\"gLevel\":15,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"abd6dadd2d534d51b7e53cd626c65bb1\",\"gameCode\":\"F04\",\"code\":\"LV16\",\"name\":\"三同号单选\",\"gLevel\":16,\"bonus\":24000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"77a39e2d9fbd4505a24778b769857f36\",\"gameCode\":\"F04\",\"code\":\"LV17\",\"name\":\"三同号复选\",\"gLevel\":17,\"bonus\":1500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"361125b75a634f66a5efbde44bf842c2\",\"gameCode\":\"F04\",\"code\":\"LV18\",\"name\":\"二同号单选\",\"gLevel\":18,\"bonus\":8000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"df3f8022899242c98138b84c0345a15f\",\"gameCode\":\"F04\",\"code\":\"LV19\",\"name\":\"三不同号单选\",\"gLevel\":19,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"ad3faccee19e41d5ba9202deb1e76508\",\"gameCode\":\"F04\",\"code\":\"LV20\",\"name\":\"二不同号复选\",\"gLevel\":20,\"bonus\":800,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"e546412fb8924f229c361e8646c1e99a\",\"gameCode\":\"F04\",\"code\":\"LV21\",\"name\":\"三连号通选\",\"gLevel\":21,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        for(int i = 0; i < 5; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + "1,2,3" + "');";
            System.out.println(sql);
            curTermCode++;
        }
    }

}
