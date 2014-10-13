package com.mcp.deploy.cmd.generate;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kfzx-liming on 2014/7/23.
 */
public class GetTerm {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception
    {
        getT04();
    }

    public static void getF04()
    {
        String gameCode = "F04";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 15*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"4e34b3b2fced442e8c6914de68a3a997\",\"gameCode\":\"F04\",\"code\":\"LV1\",\"name\":\"和值4\",\"gLevel\":1,\"bonus\":8000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"dfe94251e1af4f9f882ec6edc1e2a117\",\"gameCode\":\"F04\",\"code\":\"LV2\",\"name\":\"和值5\",\"gLevel\":2,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"018c1ff35465450a98f450da8685a584\",\"gameCode\":\"F04\",\"code\":\"LV3\",\"name\":\"和值6\",\"gLevel\":3,\"bonus\":2500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"ec1f52f562ca4d92b8828062fc29d309\",\"gameCode\":\"F04\",\"code\":\"LV4\",\"name\":\"和值7\",\"gLevel\":4,\"bonus\":1600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"1ad4ccf24c394e5e9159ad14644db441\",\"gameCode\":\"F04\",\"code\":\"LV5\",\"name\":\"和值8\",\"gLevel\":5,\"bonus\":1200,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"c3c78763c55f4a189720334d9f7258af\",\"gameCode\":\"F04\",\"code\":\"LV6\",\"name\":\"和值9\",\"gLevel\":6,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"1ff6dbe0071c423188b92f9c9c5682b1\",\"gameCode\":\"F04\",\"code\":\"LV7\",\"name\":\"和值10\",\"gLevel\":7,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"078defbb39a74f43902707dd3918319d\",\"gameCode\":\"F04\",\"code\":\"LV8\",\"name\":\"和值11\",\"gLevel\":8,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"4830429e3f774785abb6fa9369b67358\",\"gameCode\":\"F04\",\"code\":\"LV9\",\"name\":\"和值12\",\"gLevel\":9,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"348b113c335f424cb5702ba727a23d73\",\"gameCode\":\"F04\",\"code\":\"LV10\",\"name\":\"和值13\",\"gLevel\":10,\"bonus\":1200,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"fa586dc9096a4f299f043197be305f6a\",\"gameCode\":\"F04\",\"code\":\"LV11\",\"name\":\"和值14\",\"gLevel\":11,\"bonus\":1600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f6889339841446baaa2a1b43be01639b\",\"gameCode\":\"F04\",\"code\":\"LV12\",\"name\":\"和值15\",\"gLevel\":12,\"bonus\":2500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f171cbe2890c493db9e22170e61a5c7f\",\"gameCode\":\"F04\",\"code\":\"LV13\",\"name\":\"和值16\",\"gLevel\":13,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"da2ed64e589444eabfb963f05bce7e5a\",\"gameCode\":\"F04\",\"code\":\"LV14\",\"name\":\"和值17\",\"gLevel\":14,\"bonus\":8000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"81728eda95f744349bd1362ca56a61c0\",\"gameCode\":\"F04\",\"code\":\"LV15\",\"name\":\"三同号通选\",\"gLevel\":15,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"abd6dadd2d534d51b7e53cd626c65bb1\",\"gameCode\":\"F04\",\"code\":\"LV16\",\"name\":\"三同号单选\",\"gLevel\":16,\"bonus\":24000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"77a39e2d9fbd4505a24778b769857f36\",\"gameCode\":\"F04\",\"code\":\"LV17\",\"name\":\"三同号复选\",\"gLevel\":17,\"bonus\":1500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"361125b75a634f66a5efbde44bf842c2\",\"gameCode\":\"F04\",\"code\":\"LV18\",\"name\":\"二同号单选\",\"gLevel\":18,\"bonus\":8000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"df3f8022899242c98138b84c0345a15f\",\"gameCode\":\"F04\",\"code\":\"LV19\",\"name\":\"三不同号单选\",\"gLevel\":19,\"bonus\":4000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"ad3faccee19e41d5ba9202deb1e76508\",\"gameCode\":\"F04\",\"code\":\"LV20\",\"name\":\"二不同号复选\",\"gLevel\":20,\"bonus\":800,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"e546412fb8924f229c361e8646c1e99a\",\"gameCode\":\"F04\",\"code\":\"LV21\",\"name\":\"三连号通选\",\"gLevel\":21,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        String wNum = "1,2,3";
        for(int i = 0; i < 20; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }


    public static void getT01()
    {
        String gameCode = "T01";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 180*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"48d7ad96c2c04c8db66f819be96b805c\",\"gameCode\":\"T01\",\"code\":\"LV1\",\"name\":\"一等奖\",\"gLevel\":1,\"bonus\":400000000,\"status\":1,\"gCount\":0,\"fixedBonus\":false},{\"id\":\"5ff778526aa146a885979789ffeae64a\",\"gameCode\":\"T01\",\"code\":\"LV2\",\"name\":\"二等奖\",\"gLevel\":2,\"bonus\":200000000,\"status\":1,\"gCount\":0,\"fixedBonus\":false},{\"id\":\"2c3c4acfdda54ad58a7a2018ef88b921\",\"gameCode\":\"T01\",\"code\":\"LV3\",\"name\":\"三等奖\",\"gLevel\":3,\"bonus\":100000000,\"status\":1,\"gCount\":0,\"fixedBonus\":false},{\"id\":\"d26e90853fad4c1381bb109ad20ac773\",\"gameCode\":\"T01\",\"code\":\"LV4\",\"name\":\"四等奖\",\"gLevel\":4,\"bonus\":20000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"08e957d15df34145940024908a10cec9\",\"gameCode\":\"T01\",\"code\":\"LV5\",\"name\":\"五等奖\",\"gLevel\":5,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"8bf25c08a5eb498dbeffe83b8d00ead4\",\"gameCode\":\"T01\",\"code\":\"LV6\",\"name\":\"六等奖\",\"gLevel\":6,\"bonus\":500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"5cbdd29cded94a7da01f1997e7452523\",\"gameCode\":\"T01\",\"code\":\"LV7\",\"name\":\"一等奖追加\",\"gLevel\":7,\"bonus\":240000000,\"status\":1,\"gCount\":0,\"fixedBonus\":false},{\"id\":\"a007f339ce3841159d6420ad1b86d288\",\"gameCode\":\"T01\",\"code\":\"LV8\",\"name\":\"二等奖追加\",\"gLevel\":8,\"bonus\":120000000,\"status\":1,\"gCount\":0,\"fixedBonus\":false},{\"id\":\"abb64ff7bbce40deaecfb4cd58d32943\",\"gameCode\":\"T01\",\"code\":\"LV9\",\"name\":\"三等奖追加\",\"gLevel\":9,\"bonus\":60000000,\"status\":1,\"gCount\":0,\"fixedBonus\":false},{\"id\":\"3d3a66cc55c04d27961e14fe1fcc58f6\",\"gameCode\":\"T01\",\"code\":\"LV10\",\"name\":\"四等奖追加\",\"gLevel\":10,\"bonus\":10000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"9b423e5cf0c84e1d91983e74d66ca939\",\"gameCode\":\"T01\",\"code\":\"LV11\",\"name\":\"五等奖追加\",\"gLevel\":11,\"bonus\":500,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "11,13,20,28,35|01,05";
        for(int i = 0; i < 2; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getT02()
    {
        String gameCode = "T02";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"f5efb6e9c5cd4961b99c92c36e2882c0\",\"gameCode\":\"T02\",\"code\":\"LV1\",\"name\":\"一等奖\",\"gLevel\":1,\"bonus\":400000000,\"status\":1,\"gCount\":2,\"fixedBonus\":false},{\"id\":\"e9555c9f51b641fda3ba8317791bba8c\",\"gameCode\":\"T02\",\"code\":\"LV2\",\"name\":\"二等奖\",\"gLevel\":2,\"bonus\":200000000,\"status\":1,\"gCount\":20,\"fixedBonus\":false},{\"id\":\"4805d88d2df345daabf27371dd143b37\",\"gameCode\":\"T02\",\"code\":\"LV3\",\"name\":\"三等奖\",\"gLevel\":3,\"bonus\":180000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"af3fb0e4a1ba4161a4dfb69853f043ce\",\"gameCode\":\"T02\",\"code\":\"LV4\",\"name\":\"四等奖\",\"gLevel\":4,\"bonus\":30000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"3f260c81a75f42f4b29bfee63bdfb29d\",\"gameCode\":\"T02\",\"code\":\"LV5\",\"name\":\"五等奖\",\"gLevel\":5,\"bonus\":2000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"ce5d0bf1fcfc4124aec20d42467589ab\",\"gameCode\":\"T02\",\"code\":\"LV6\",\"name\":\"六等奖\",\"gLevel\":6,\"bonus\":500,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "4|9|5|0|4|6|3";
        for(int i = 0; i < 200; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getT03()
    {
        String gameCode = "T03";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"3a7ca9ab1ecf45eebee7849d164eb6b2\",\"gameCode\":\"T03\",\"code\":\"LV1\",\"name\":\"直选\",\"gLevel\":1,\"bonus\":104000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"d9c6ab027f114bedb023c6d3f1b4f80b\",\"gameCode\":\"T03\",\"code\":\"LV2\",\"name\":\"组选3\",\"gLevel\":2,\"bonus\":34600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"0ea7a769c60844e89c165654cdf41647\",\"gameCode\":\"T03\",\"code\":\"LV3\",\"name\":\"组选6\",\"gLevel\":3,\"bonus\":17300,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "1|7|7";
        for(int i = 0; i < 200; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static String format(int code)
    {
        //return String.format("%08d", code);
        return String.format("%d", code);
    }

    public static void getT04() throws Exception
    {
        String gameCode = "T04";
        int curTermCode = 5005;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"eddb13ad81c444f0abddb2b93e4026a2\",\"gameCode\":\"T04\",\"code\":\"LV1\",\"name\":\"普通\",\"gLevel\":1,\"bonus\":10000000,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "1|2|3|4|5";
        for(int i = 0; i < 1; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += format(curTermCode) + ",'" + format(curTermCode) + "'," + format(nextTermCode) + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getT05()
    {
        String gameCode = "T05";
        int curTermCode = 5005;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"dbe4d6fbb33e4376a5ad4713630e685c\",\"gameCode\":\"T05\",\"code\":\"LV1\",\"name\":\"任选一\",\"gLevel\":1,\"bonus\":1300,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"1131d5a6b5f540148eaf1a29d89a3405\",\"gameCode\":\"T05\",\"code\":\"LV2\",\"name\":\"任选二\",\"gLevel\":2,\"bonus\":600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"9b85cd1489e2474e993531132b9c9f97\",\"gameCode\":\"T05\",\"code\":\"LV3\",\"name\":\"任选三\",\"gLevel\":3,\"bonus\":1900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"96f95afa0c4048448002769e7b3f3632\",\"gameCode\":\"T05\",\"code\":\"LV4\",\"name\":\"任选四\",\"gLevel\":4,\"bonus\":7800,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"264ba97e59a749c8a19969370593391e\",\"gameCode\":\"T05\",\"code\":\"LV5\",\"name\":\"任选五\",\"gLevel\":5,\"bonus\":54000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"6b9128fd9a884915976ef92e7e426eae\",\"gameCode\":\"T05\",\"code\":\"LV6\",\"name\":\"任选六\",\"gLevel\":6,\"bonus\":9000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"0604a1862f0b4334be376670e97e4ad6\",\"gameCode\":\"T05\",\"code\":\"LV7\",\"name\":\"任选七\",\"gLevel\":7,\"bonus\":2600,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"8c8f67171e7f430080f23b1083c7f57e\",\"gameCode\":\"T05\",\"code\":\"LV8\",\"name\":\"任选八\",\"gLevel\":8,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"93c5345b3f6843a6b44103ec043c955f\",\"gameCode\":\"T05\",\"code\":\"LV9\",\"name\":\"前二组选\",\"gLevel\":9,\"bonus\":6500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"a940edb5a624494e92c83549fe9b6fc6\",\"gameCode\":\"T05\",\"code\":\"LV10\",\"name\":\"前二直选\",\"gLevel\":10,\"bonus\":13000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"276a7a641bff4069b293318448651665\",\"gameCode\":\"T05\",\"code\":\"LV11\",\"name\":\"前三组选\",\"gLevel\":11,\"bonus\":19500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"79baed4a6a9e43d9aefdb0676ab199c8\",\"gameCode\":\"T05\",\"code\":\"LV12\",\"name\":\"前三直选\",\"gLevel\":12,\"bonus\":117000,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "01|02|03|04|05";
        for(int i = 0; i < 20; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += format(curTermCode) + ",'" + format(curTermCode) + "'," + format(nextTermCode) + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getT06()
    {
        String gameCode = "T06";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"697a344a9f874fcab62d32c5812b9445\",\"gameCode\":\"T06\",\"code\":\"LV1\",\"name\":\"组选24\",\"gLevel\":1,\"bonus\":19700,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f53043a480c8438c9c2c86848bc2ac1f\",\"gameCode\":\"T06\",\"code\":\"LV2\",\"name\":\"组选12\",\"gLevel\":2,\"bonus\":39500,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"3e41f88be56243208a333cb5282d7065\",\"gameCode\":\"T06\",\"code\":\"LV3\",\"name\":\"组选6\",\"gLevel\":3,\"bonus\":79100,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"cbe6111ec4a844d987abe79e9b605756\",\"gameCode\":\"T06\",\"code\":\"LV4\",\"name\":\"组选4\",\"gLevel\":4,\"bonus\":118700,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"b3ca412a073440d9b638f32ddeb58dfa\",\"gameCode\":\"T06\",\"code\":\"LV5\",\"name\":\"任选一\",\"gLevel\":5,\"bonus\":900,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"958d7fbc0b5b42808e48361faa0104d0\",\"gameCode\":\"T06\",\"code\":\"LV6\",\"name\":\"任选二\",\"gLevel\":6,\"bonus\":7400,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"023838e456c64bf1b08001765ca1a10f\",\"gameCode\":\"T06\",\"code\":\"LV7\",\"name\":\"任选三\",\"gLevel\":7,\"bonus\":59300,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"0ca4d86841124c6ca88af9d4b186d7a7\",\"gameCode\":\"T06\",\"code\":\"LV8\",\"name\":\"任选四\",\"gLevel\":8,\"bonus\":475100,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        String wNum = "1,2,3,4";
        for(int i = 0; i < 20; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getT53()
    {
        String gameCode = "T53";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"9aede9cee381406c8f62acc4c32bf0b6\",\"gameCode\":\"T53\",\"code\":\"LV1\",\"name\":\"14场一等奖\",\"gLevel\":1,\"bonus\":4000000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f4fffacbdac7492599cd51185b72d264\",\"gameCode\":\"T53\",\"code\":\"LV2\",\"name\":\"14场二等奖\",\"gLevel\":2,\"bonus\":40000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"178180447bf84754b22f632679e35b4f\",\"gameCode\":\"T53\",\"code\":\"LV3\",\"name\":\"任9场一等奖\",\"gLevel\":3,\"bonus\":400,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "3|1|0|3|1|0|0|1|3|1|3|3|1|1";
        for(int i = 0; i < 200; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getT54()
    {
        String gameCode = "T54";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"def0107659a94bb6a617565c18470d30\",\"gameCode\":\"T54\",\"code\":\"LV1\",\"name\":\"一等奖\",\"gLevel\":1,\"bonus\":4000000,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}\n";
        String wNum = "3|1|0|3|1|0|0|1";
        for(int i = 0; i < 200; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getF01()
    {
        String gameCode = "F01";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"40623ae3da4945a2b8482435f0578d37\",\"gameCode\":\"F01\",\"code\":\"LV1\",\"name\":\"一等奖\",\"gLevel\":1,\"bonus\":400000000,\"status\":1,\"gCount\":2,\"fixedBonus\":false},{\"id\":\"b44a2be235d74f158326635506aea278\",\"gameCode\":\"F01\",\"code\":\"LV2\",\"name\":\"二等奖\",\"gLevel\":2,\"bonus\":200000000,\"status\":1,\"gCount\":20,\"fixedBonus\":false},{\"id\":\"5c1221410f564bbf9648a9744e92e5f0\",\"gameCode\":\"F01\",\"code\":\"LV3\",\"name\":\"三等奖\",\"gLevel\":3,\"bonus\":300000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"86eef4a5a927450e80fb805f536b9d59\",\"gameCode\":\"F01\",\"code\":\"LV4\",\"name\":\"四等奖\",\"gLevel\":4,\"bonus\":20000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"4da3a2e9599f410aafbab45ee2cadd8f\",\"gameCode\":\"F01\",\"code\":\"LV5\",\"name\":\"五等奖\",\"gLevel\":5,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"57cbe76695b049858340d0363f2bfd3e\",\"gameCode\":\"F01\",\"code\":\"LV6\",\"name\":\"六等奖\",\"gLevel\":6,\"bonus\":500,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        String wNum = "09,12,14,30,32,33|02";
        for(int i = 0; i < 200; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getF02()
    {
        String gameCode = "F02";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 60*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"e4f1f29132dc45a7bdc73c72457aab8b\",\"gameCode\":\"F02\",\"code\":\"LV1\",\"name\":\"直选\",\"gLevel\":1,\"bonus\":100000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"25bde95065bd428795c6d64dbf087927\",\"gameCode\":\"F02\",\"code\":\"LV2\",\"name\":\"组选3\",\"gLevel\":2,\"bonus\":32000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"b172d4ca2a624f33a50ab43286c28f36\",\"gameCode\":\"F02\",\"code\":\"LV3\",\"name\":\"组选6\",\"gLevel\":3,\"bonus\":16000,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        String wNum = "1|2|3";
        for(int i = 0; i < 200; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }

    public static void getF03()
    {
        String gameCode = "F03";
        int curTermCode = 2014001;
        long startDate = new Date().getTime();
        long gap = 120*60*1000;
        String pDes = "{\"grades\":[{\"id\":\"3f72fdc663344871b2c27ef4a467e3f8\",\"gameCode\":\"F03\",\"code\":\"LV1\",\"name\":\"一等奖\",\"gLevel\":1,\"bonus\":400000000,\"status\":1,\"gCount\":2,\"fixedBonus\":false},{\"id\":\"e851c06f5450493aa4c53148cce46df8\",\"gameCode\":\"F03\",\"code\":\"LV2\",\"name\":\"二等奖\",\"gLevel\":2,\"bonus\":200000000,\"status\":1,\"gCount\":20,\"fixedBonus\":false},{\"id\":\"047fef21a81b4409aba497b094f50180\",\"gameCode\":\"F03\",\"code\":\"LV3\",\"name\":\"三等奖\",\"gLevel\":3,\"bonus\":100000000,\"status\":1,\"gCount\":30,\"fixedBonus\":false},{\"id\":\"fc343411559c42bebcaa043690de31c8\",\"gameCode\":\"F03\",\"code\":\"LV4\",\"name\":\"四等奖\",\"gLevel\":4,\"bonus\":20000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"f7d6cc4f862d4b929150f52b14c4974e\",\"gameCode\":\"F03\",\"code\":\"LV5\",\"name\":\"五等奖\",\"gLevel\":5,\"bonus\":5000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"caaa3057a78d4ca99069a2b2401fb480\",\"gameCode\":\"F03\",\"code\":\"LV6\",\"name\":\"六等奖\",\"gLevel\":6,\"bonus\":1000,\"status\":1,\"gCount\":0,\"fixedBonus\":true},{\"id\":\"cdb1527f3f2f48d6a2077569843dc2c0\",\"gameCode\":\"F03\",\"code\":\"LV7\",\"name\":\"七等奖\",\"gLevel\":7,\"bonus\":500,\"status\":1,\"gCount\":0,\"fixedBonus\":true}]}";
        String wNum = "01,02,09,12,14,19,30|04";
        for(int i = 0; i < 20; i++)
        {
            String sql = "insert into term(id, code, name, nextCode, gameCode, openTime, endTime, prizeDesc, prizePool, status, concedePoints, version, winningNumber) values('" + CoreUtil.getUUID() + "',";
            int nextTermCode = curTermCode + 1;
            sql += curTermCode + ",'" + curTermCode + "'," + nextTermCode + ",'" + gameCode + "','" + dateFormat.format(new Date(startDate)) + "',";
            startDate += gap;
            sql += "'" + dateFormat.format(new Date(startDate)) + "','" + pDes + "',0," + 1100 + ",0,0,'" + wNum + "');";
            System.out.print(sql);
            curTermCode++;
        }
    }
}
