package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.jc.check.DefaultJcCheck;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.RepQ15Body;
import com.mcp.order.inter.query.ReqQ15Body;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ming.li on 2014/6/20.
 */
public class JcCheck {

    private static PrizeDescription pd = new PrizeDescription();

    static {
        pd.putJcDrawNumber("201406135003", "01|1;02|3;03|10;04|1;05|13");
        pd.putJcDrawNumber("201406135004", "01|0;02|0;03|15;04|6;05|10");
        pd.putJcDrawNumber("201406135005", "01|3;02|3;03|31;04|4;05|33");
        pd.putJcDrawNumber("201406146006", "01|3;02|3;03|30;04|3;05|33");
        pd.putJcDrawNumber("201406146007", "01|0;02|0;03|13;04|4;05|30");
        pd.putJcDrawNumber("201406146008", "01|1;02|0;03|12;04|3;05|10");
        pd.putJcDrawNumber("201406146009", "01|1;02|3;03|21;04|3;05|03");
        pd.putJcDrawNumber("201406146061", "01|0;02|0;03|01;04|1;05|00");
        pd.putJcDrawNumber("201406146062", "01|0;02|1;03|11;04|2;05|11");
        pd.putJcDrawNumber("201406146063", "01|3;02|3;03|21;04|3;05|13");
        pd.putJcDrawNumber("201406146064", "01|3;02|3;03|30;04|3;05|33");
        pd.putJcDrawNumber("201406157004", "01|1;02|3;03|21;04|3;05|03");
        pd.putJcDrawNumber("201406157005", "01|3;02|3;03|30;04|3;05|33");
        pd.putJcDrawNumber("201406157006", "01|1;02|3;03|21;04|3;05|33");
        pd.putJcDrawNumber("201406161003", "01|3;02|3;03|40;04|4;05|33");
        pd.putJcDrawNumber("201406161004", "01|3;02|1;03|00;04|0;05|11");
        pd.putJcDrawNumber("201406161005", "01|0;02|0;03|12;04|3;05|00");
        pd.putJcDrawNumber("201406172004", "01|1;02|3;03|21;04|3;05|03");
        pd.putJcDrawNumber("201406172005", "01|0;02|1;03|00;04|0;05|11");
        pd.putJcDrawNumber("201406183002", "01|1;02|0;03|23;04|5;05|10");
        pd.putJcDrawNumber("201406183003", "01|1;02|0;03|23;04|5;05|10");
        pd.putJcDrawNumber("201406183004", "01|0;02|0;03|04;04|4;05|00");
        pd.putJcDrawNumber("201406194002", "01|1;02|3;03|21;04|3;05|13");
        pd.putJcDrawNumber("201406194003", "01|3;02|3;03|21;04|3;05|33");
        pd.putJcDrawNumber("201406194004", "01|0;02|1;03|00;04|0;05|11");
    }

    private static Logger log = Logger.getLogger(JcCheck.class);

    private static Map<String, String> tMap = new HashMap<String, String>();

    private static Map<String, String> oMap = new HashMap<String, String>();

    static {
        tMap.put("cffcd055c7984aa4b23a840a903195d8", "02|201406205006|3@1.370;02|201406205007|3@4.550");
        tMap.put("f3a495f09cb1403fb474f130a69a42e4", "02|201406205006|3@1.370;02|201406205007|1@3.500,0@1.620");
        tMap.put("0ac85899183a4379982aac3321e3c7c7", "02|201406205006|3@1.370;02|201406205007|3@4.550");
        tMap.put("4457c77a0f694309af4abe904c9610f8", "02|201406205006|3@1.370;02|201406205007|0@1.620;02|201406205008|0@1.490;02|201406216010|3@1.050;02|201406216011|3@1.170;02|201406227008|3@2.050");
        tMap.put("5c1e59f850fa467ab017c9bcd0193985", "02|201406205006|3@1.370;02|201406205007|0@1.620");
        tMap.put("ebb51fa0118b4e968f2c80a75e10be4a", "02|201406205006|3@1.370;02|201406205007|3@4.550");
        tMap.put("d80a4dbe635d463eb10f237ae82d96b4", "02|201406216010|3@1.050;02|201406216011|3@1.170");
        tMap.put("bec344f8441d46c4ade120969f401895", "02|201406216010|3@1.050;02|201406216011|3@1.170;02|201406231006|3@7.550");
        tMap.put("31e6cd9a59d04ef1abc42144df846947", "02|201406216010|3@1.050;01|201406216011|3@1.690");
        tMap.put("9f7ced7eefec401eb265d05d1fc8a0c1", "02|201406205006|3@1.370;02|201406205007|0@1.620;02|201406205008|0@1.490");
        tMap.put("45cab88d2ceb4889bfc571aa8c83c7bb", "02|201406205006|0@6.800;02|201406205007|1@3.500;01|201406205008|1@3.250");
        tMap.put("fa58e7d3f33c4bf49ba704609dc8cb71", "01|201406205006|1@3.400;02|201406205007|1@3.500;01|201406205008|1@3.250");
        tMap.put("50fefe7d797e495b885b998b6614a6b3", "02|201406194002|0@3.750;02|201406194003|0@1.850;02|201406194004|1@3.250;02|201406205006|3@1.370;02|201406205007|0@1.620");
        tMap.put("1ac6bb4a1d5a4b8e9494974db3e24387", "02|201406205006|3@1.370;02|201406216010|3@1.050");
        tMap.put("03e33b263950436e9e19cd351c0617fa", "02|201406205006|3@1.370;02|201406216010|3@1.050;02|201406216011|3@1.170");
        tMap.put("a240dd5d472044e18bc36bd62ad2ef08", "02|201406194002|3@1.810,1@3.300,0@3.750;02|201406194003|3@3.200,1@3.500,0@1.900");
        tMap.put("d983fc7945944cabbc7d67df611802d0", "01|201406194002|3@3.650;02|201406194003|3@3.200;01|201406194004|3@4.100;01|201406205006|3@2.220;02|201406205007|3@4.550;02|201406205008|3@5.050");
        tMap.put("1fb2733e04374683b8a4545084d1ee44", "02|201406194004|0@3.180;02|201406216010|0@22.00;02|201406216011|3@1.170;02|201406227007|1@3.200;01|201406227008|1@3.550;02|201406227009|3@4.600;02|201406231006|3@8.000;01|201406231008|1@4.200");
        tMap.put("e260be0698be4eb7868759c1bc91be4d", "02|201406216010|3@1.050;02|201406216011|3@1.170");
        tMap.put("541b1d8fbcdd4d74b46bbb375b0ead56", "02|201406194002|1@3.300;02|201406216011|1@5.750");
        tMap.put("024e22bb4c174b038c98750f6277c091", "02|201406194002|3@1.810;02|201406194003|0@1.900");
        tMap.put("2fad34f8f06f4dbba8642fa2a8703682", "02|201406205006|3@1.370;02|201406216011|3@1.170");
        tMap.put("1e21081d988343dfab4cc51ad68c303d", "02|201406194002|3@1.830;02|201406194003|3@3.200");
        tMap.put("7894d409b5784a43b3fae2040e0d006e", "01|201406231008|1@4.200;02|201406231009|3@2.240");
        tMap.put("7991a93d50404182af38a8f6f4c7fd0c", "02|201406194002|3@1.830;02|201406205006|3@1.370");
        tMap.put("6f07fa07e5594ef2870177c87dc1edc5", "01|201406194002|3@3.750,1@3.450,0@1.750;02|201406194003|3@3.150,1@3.400,0@1.950");
        tMap.put("96364f0219134d589f42fbb9326ac93f", "01|201406205006|3@2.220,1@3.400,0@2.600;01|201406205007|3@2.000,1@3.400,0@2.980;01|201406205008|3@2.200,1@3.300,0@2.700");
        tMap.put("26011b403c8c437d9d51ddbd516b91ed", "02|201406194002|3@1.860;02|201406194003|3@3.150,0@1.950");
        tMap.put("4fbd22eef1e44690885c3ab4e1ac561f", "02|201406194002|3@1.860;01|201406194003|1@3.850,0@3.750");
        tMap.put("12f9bc3f4f114c58bf939c02fc85b0bb", "01|201406194002|3@3.850,1@3.500,0@1.720;01|201406194003|3@1.660,1@3.850,0@3.750");
        tMap.put("1772f9e5921f4cfa8dad63041bb7e9bc", "02|201406205006|3@1.370;02|201406205007|0@1.620");
        tMap.put("5e7aad653af3483489cf75d3f4b35fea", "01|201406183003|0@2.580;02|201406205006|0@6.800");
        tMap.put("3382a5440d3147ef9ede66a1fd82b6ff", "02|201406183003|3@1.360;01|201406183004|1@3.450");
        tMap.put("a2df114fb5e7428398a6daa75ada6a4f", "02|201406183003|3@1.360;02|201406183004|0@1.490");
        tMap.put("2caa915fe0fa40439ba02c99aeb255dd", "02|201406183004|0@1.500;01|201406183053|3@1.600,1@3.900");
        tMap.put("007e0d1e42f44398bccd43b9e12c24b1", "02|201406216010|3@1.060;02|201406227009|0@1.560");
        tMap.put("be7923f044cc4b11b2fd1f373be6bb44", "01|201406183002|1@4.050;02|201406183004|3@5.150,1@3.800;02|201406183053|0@11.50;01|201406205006|3@2.220");
        tMap.put("9f48c8e5febd47448011905dda3fcf12", "02|201406183002|3@14.50;02|201406183004|3@5.150,1@3.800;02|201406183053|0@11.50;01|201406205006|3@2.220");
        tMap.put("3025177254ad43c2a3c498c7bbf98a06", "02|201406183002|0@1.120;02|201406183003|3@1.360");
        tMap.put("03574582101c469f990c2fbef1304b28", "01|201406183002|0@1.510;01|201406183003|3@2.190;02|201406183004|1@3.800");
        tMap.put("e507c8877230424eab67eee78e0008ce", "02|201406183002|0@1.120;01|201406183004|3@2.210,1@3.400,0@2.620");
        tMap.put("d606ee32745a47c0824d2f415e6eb429", "01|201406183002|0@1.510;01|201406183003|3@2.190;02|201406183004|3@5.200");
        tMap.put("c5cd5b7bc7754e15bd651f1056756815", "01|201406183002|0@1.510;02|201406183003|3@1.360;02|201406183004|3@5.400");
        tMap.put("0f8e8af2beb5438bb54c62b0dcf89679", "02|201406183002|3@14.50;02|201406183003|3@1.360;02|201406183004|3@5.400");
        tMap.put("06325843362b429f894fe29cc6683f38", "01|201406183002|1@4.050,0@1.510;01|201406183003|3@2.190,1@3.450,0@2.620");
        tMap.put("42f40227181641f293041b68ac6d1814", "02|201406205008|0@1.540;01|201406216011|3@1.690");
        tMap.put("c0b4706bf1384a338f9a350d567eed34", "02|201406183002|0@1.120;02|201406183003|3@1.420");
        tMap.put("95a9a4159efe4fcd829d6b6a8a634dcd", "02|201406172005|3@1.180;02|201406172006|3@1.590;02|201406172051|3@1.180;02|201406172052|1@3.700;02|201406172053|0@8.000;02|201406172054|3@1.500;02|201406172055|3@1.470");
        tMap.put("4dc6af87a58a446a94e9f65f219437e8", "02|201406172004|3@1.200;01|201406172005|3@1.670;02|201406172006|3@1.590");
        tMap.put("e69cb5b6fec64164b27a6a0126c7c770", "01|201406172004|3@1.770;01|201406172005|3@1.670");
        tMap.put("012c4213c14145759ec14f967039fae6", "02|201406172004|0@10.00;02|201406172005|3@1.170;02|201406172006|0@4.550");
        tMap.put("c8d30c43a6f742dc81d69dc60ef49ae2", "02|201406172004|3@1.200;01|201406172005|3@1.670");
        tMap.put("ab25397917794df582bcfac5d7590bbd", "02|201406172005|3@1.170;02|201406183002|0@1.130;02|201406183003|3@1.450");
        tMap.put("d06f669b46a64f168c6120439ef3b367", "01|201406172004|3@1.800;01|201406172005|3@1.670;02|201406172006|3@1.680");
        tMap.put("58360c57a49846d78e6643d97bcfee4b", "01|201406172004|3@1.880;01|201406172005|3@1.670");
        tMap.put("4a2d6224e22c4979a97aae66b1d3a719", "01|201406172005|1@3.700;01|201406183002|1@3.550;01|201406183003|3@2.650;01|201406183004|1@3.350");
        tMap.put("e196887074a549759caa8e215bd2bc85", "01|201406172004|3@1.950;01|201406172005|3@1.700;01|201406172006|3@3.650");
        tMap.put("e6e2d330c9fc4a80ac197f00d2de1ae7", "02|201406172004|3@1.270;02|201406172005|3@1.180");
        tMap.put("fdafbe00443d4ba9b08cf1124445d74f", "02|201406172004|3@1.270;02|201406172005|3@1.180;02|201406172006|3@1.780");
        tMap.put("06f00b13f23545b6b03bf16ec9ca2177", "02|201406172004|0@8.800;01|201406172005|0@3.750");
        tMap.put("47f9d3ddb61c4d97a6cd13df3461c53c", "02|201406161004|0@1.640;02|201406161005|0@2.700;02|201406172004|3@1.320");
        tMap.put("f52375723f36485c9e2d0ba799167601", "02|201406161003|3@1.750;02|201406161004|0@1.680");
        tMap.put("e41b624dbcd046ab99176713cc841806", "02|201406161003|3@1.750;02|201406161004|0@1.680");
        tMap.put("24875acfd15344acbaf0d5c4bb9d6f1c", "02|201406161003|3@1.750,1@3.600,0@3.650;02|201406161004|3@4.100,1@3.500,0@1.680;02|201406161005|3@2.200,1@3.350,0@2.700;02|201406172005|3@1.180;02|201406183002|0@1.160;02|201406183003|3@1.530;02|201406205006|3@1.420");
        tMap.put("528cfa8201524079a00c262f7165442e", "02|201406161003|1@3.600;02|201406172005|3@1.180;02|201406183002|0@1.160;02|201406183003|3@1.570;02|201406205006|3@1.420;02|201406205007|0@1.790");
        tMap.put("0226be9e2156474382d44b4ade4bfcfa", "02|201406172005|3@1.180;02|201406183002|0@1.160");
        tMap.put("635fe3dcc42f419887c9332a9f8a03a3", "02|201406161003|3@1.860;02|201406161004|1@2.900;02|201406161005|1@3.000");
        tMap.put("91f794db816749c6a1a1e31bcfd06b76", "02|201406161003|0@3.450;02|201406161004|3@3.750");
        tMap.put("f909a80dd6f749dcaa4b18cbed024f9c", "02|201406157004|3@2.120;01|201406157005|3@1.690;02|201406157006|3@1.200");
        tMap.put("f26261c857874fb48c23d673cbfc13e2", "02|201406157004|0@3.080;02|201406157005|3@1.170;02|201406157006|1@5.350");
        tMap.put("3e34fdbc518e41c491823c7790fbb20f", "02|201406157004|3@2.120,0@3.080;02|201406157005|3@1.170;02|201406157006|3@1.200,0@9.600");
        tMap.put("a1ed3b2cb88241498dc9cdf65fcc9f7b", "02|201406157004|3@2.120,0@3.080;02|201406157005|3@1.170");
        tMap.put("3a769c8d1bd5414dac1288e6e74283af", "02|201406157004|3@2.120,0@3.080;02|201406157006|3@1.200,0@9.600");
        tMap.put("d93b306829b443e6a38f0856747489ee", "02|201406157005|3@1.170;02|201406157006|3@1.200,0@9.600");
        tMap.put("50d5a3a0c6e542c38f694163fe5d132d", "01|201406157004|0@1.550;01|201406157005|3@1.750");
        tMap.put("d711c75a29524ad99a343043077e7889", "01|201406157004|1@3.800;02|201406157005|3@1.180");
        tMap.put("38e057429aef407697b371a52835a194", "02|201406157004|3@2.120;02|201406157006|3@1.210");
        tMap.put("03132b18f2944333bd157a5701a032c5", "02|201406157004|3@2.140;02|201406157005|3@1.190");
        tMap.put("62d9ce927c0646b8a7f8265379670778", "02|201406157004|1@3.050;02|201406157005|3@1.190;02|201406157006|3@1.220");
        tMap.put("0869cd10e9064c1b9ef7935ee2ba059e", "01|201406157004|0@1.540;02|201406157005|0@12.00;01|201406157006|1@3.700");
        tMap.put("062288ad0da64c68a678b82289c50e8a", "01|201406157005|3@1.770;01|201406157006|3@1.770");
        tMap.put("f89907addf4d425f8746c7fc2b514ecd", "01|201406157005|3@1.770;02|201406157006|3@1.220");
        tMap.put("c4375d8047a3477da27373d79f8b0637", "02|201406157004|3@2.180;02|201406157005|3@1.190;02|201406157006|3@1.230");
        tMap.put("bfd1145e9c254c7e8dce8e0dc6ea1b64", "02|201406157004|1@3.000;01|201406157005|3@1.770;01|201406157006|3@1.810");
        tMap.put("11cbbe8b6d3643bf80c109141428917c", "01|201406161003|0@1.720;02|201406161004|0@2.000");
        tMap.put("0133ef134fd147efa8f2d158029db9be", "02|201406157004|3@2.180;01|201406157005|3@1.800;01|201406157006|0@3.150");
        tMap.put("37379325b27a45c8b7952fb1ead3845d", "02|201406157004|0@3.020;02|201406157005|3@1.190");
        tMap.put("bd26c780951344eca882e7c64ca5fa3b", "02|201406157004|3@2.230;01|201406157005|3@1.800");
        tMap.put("8afa38884de84c75b262cc4d78ec62c0", "02|201406157004|3@2.230;02|201406157005|0@13.00;02|201406157006|3@1.250");
        tMap.put("5406d7893d454340b260bd1c51607ec4", "02|201406157004|3@2.230;01|201406157005|3@1.800;01|201406157006|3@1.920");
        tMap.put("6d5e37f5a2ee4a28b3ea45fb00e6ebc2", "02|201406157005|1@4.850;02|201406157006|3@1.250");
        tMap.put("6ceb15301db44e7fa093966387cc9c6e", "02|201406157004|3@2.230,1@2.900,0@3.020;01|201406157005|3@1.800;01|201406157006|3@1.920");
        tMap.put("4c31d69179ec4384863baa33909fe83e", "01|201406157005|3@1.800;01|201406157006|3@1.920");
        tMap.put("ddec3c990b2d410e8d570b8ae8279e26", "02|201406157004|3@2.230;02|201406157005|3@1.190");
        tMap.put("54b28db4fa1e4436a7452bdfb8eb6e89", "02|201406157004|0@3.020;01|201406157005|3@1.800");
        tMap.put("8a0c0fc07fb9415b925bce5cd3e5bc44", "01|201406157004|3@5.150;02|201406157005|0@13.00;02|201406157006|0@8.600");
        tMap.put("c158fd4265e443fbab8e7e44d36227e4", "02|201406157004|3@2.300;01|201406157005|3@1.830;02|201406157006|3@1.270");
        tMap.put("5a1e637c86754f13a04716f0647efbd3", "01|201406146007|3@2.030;01|201406146008|3@1.400;01|201406146009|0@1.370");
        tMap.put("bb2c49d6d2f84e23aa19b20f6863517b", "01|201406146006|3@3.300;01|201406146007|3@2.030");
        tMap.put("4c20542000694b8b8fe1edda85ef8b51", "02|201406146006|3@1.670;02|201406146007|3@1.280;01|201406146009|0@1.370");
        tMap.put("ea9c296932274f44acd262e9fc2a7b55", "02|201406146006|3@1.670;02|201406146008|0@2.500");
        tMap.put("6b3210af39ed4690823b3d83e7172a57", "02|201406146006|3@1.670;02|201406161003|0@3.650");
        tMap.put("37bbaf83e57a481ab3dbd70a571f8d24", "02|201406146006|3@1.670;01|201406146007|3@2.030;01|201406157005|3@1.830;01|201406157006|3@1.970;02|201406161003|3@1.860;02|201406161004|0@2.000");
        tMap.put("36865b0f4fe24edd82d9d35e0d94676e", "02|201406146006|3@1.670;02|201406146007|3@1.280;01|201406157005|3@1.830;02|201406157006|3@1.290;02|201406172004|3@1.340;02|201406172005|3@1.180;01|201406183002|0@1.820;02|201406183003|3@1.610");
        tMap.put("ad876de316154089bb51a16a64fbc490", "01|201406146007|3@2.030,1@3.300;02|201406146063|0@1.710");
        tMap.put("3ffa11ce4fa04b53ad9547c02f11485d", "02|201406146006|3@1.690;02|201406146007|3@1.280;02|201406146008|0@2.500");
        tMap.put("87d51415dc8340ff816b810eaa829aad", "02|201406157004|3@2.300;01|201406161005|0@1.400");
        tMap.put("14ba031b99aa494dba790260be8eaa42", "01|201406146060|0@1.550;01|201406146061|0@2.000");
        tMap.put("78f9f21c1f6642c585ee2e750c142acd", "02|201406146060|0@2.880;02|201406146061|3@1.620");
        tMap.put("4c37cc170e384a6d9337d8dbf8ab446e", "02|201406146006|3@1.690;02|201406146007|3@1.280;01|201406157005|3@1.830;01|201406157006|3@2.000;02|201406161003|3@1.860;02|201406172004|3@1.340;01|201406172005|3@1.700;01|201406183002|0@1.820");
        tMap.put("cbbe75a75d1944a1b5f9cef33c3a59d7", "02|201406146008|3@2.780;02|201406157006|3@1.290");
        tMap.put("c2c104d841a74649b771a6e101861a84", "01|201406146007|1@3.300;01|201406146009|1@4.000");
        tMap.put("c0377593ec6d49e496850a1ae888e449", "02|201406146008|0@2.500;02|201406146009|0@2.700");
        tMap.put("440fd2f1cf074462a468ab24c45bb52d", "01|201406146007|3@2.070;01|201406146008|1@4.000;02|201406161003|3@1.860");
        tMap.put("992f600549f64367bbdfd2a08cd12fe9", "02|201406146051|0@2.400;02|201406146052|1@3.900,0@1.470;02|201406146053|0@1.990;02|201406146054|1@3.100");
        tMap.put("c0a5ddb9ece24ecc9b2555ce1766c6bf", "01|201406146006|3@3.350,1@3.300;02|201406146007|1@4.250;02|201406146008|3@2.780,1@2.750;02|201406146009|3@2.480,0@2.700");
        tMap.put("e01e9a6c79114bebadb554e80a3a1898", "02|201406146006|3@1.690;02|201406146007|3@1.300;02|201406146008|1@2.750;02|201406146009|3@2.480");
        tMap.put("9477fd7bf1b444edb79da7ce300e623a", "01|201406157004|3@5.650;01|201406157005|3@1.830;01|201406157006|3@2.000");
        tMap.put("fc0ad35640d54dc5a066e3d8f974af94", "01|201406146006|3@3.350;01|201406146007|3@2.100;02|201406146008|3@2.780;01|201406146009|3@6.050");
        tMap.put("3b66c55053b344cc9895d8e4a5e1c171", "02|201406135004|1@3.250;01|201406135005|3@2.170");
        tMap.put("ba8cae352052420e8da26cf025d9dd9b", "02|201406135004|0@4.950;02|201406135005|0@7.600");
        tMap.put("09dd51fdf59d4f818fe35ad8746daf83", "02|201406135004|3@1.700,1@3.150;02|201406135005|3@1.300");
        tMap.put("830ca3c572df435992daeb13c5d40d4b", "02|201406124001|1@5.350;02|201406124052|0@2.350");
        tMap.put("643f45aa53334c7b989202521a2c237a", "01|201406124001|3@1.690;01|201406135004|3@3.500");
        tMap.put("37e520666805400aaeec62385675de22", "01|201406124001|3@1.690;02|201406135004|3@1.740");
        tMap.put("4e89f0df571647ad892b07867b94423e", "02|201406102051|3@1.500;02|201406124001|3@1.180");
        tMap.put("48047af7ea0143d989f130b920c0be12", "02|201405283007|3@1.440;02|201405283008|3@2.170");
        tMap.put("be816f342b5241958fac5ab433c5ea36", "02|201405283007|3@1.440;02|201405283008|3@2.170");
    }

    static {
        oMap.put("7e31315163df42dd9f429c36d2b62306", "");
        oMap.put("9131c8307cf64150ac8b397241cc9f50", "");
        oMap.put("a14a26f2461c4f6dadb4868f21671c82", "");
        oMap.put("b2a17ae25d084ea8ba24c470e643f2ac", "");
        oMap.put("f727d605794c48b7a1f59b4951a783e5", "");
        oMap.put("7e19565915be41ad8a38b5d68f35f60a", "");
        oMap.put("ca0de6b7f6a5497998e3eda7aad268db", "");
        oMap.put("c73c5748b5c24718a13828e49819418c", "");
        oMap.put("1d5bcaf8ac5d49868e61783870d57255", "");
        oMap.put("2de7c21d32564d78b3e84bd5a0751bb9", "");
        oMap.put("f358416187204e38b9926e5221648716", "");
        oMap.put("3f4d880952e54e42851bfa159e1dd3e1", "");
        oMap.put("9ff00fa69c2a46c08f58124bbc1bb5bc", "");
        oMap.put("cdff5db7a7944407b20a98e1bfcc6eeb", "");
        oMap.put("00918b11c1f74f9e9b9ecff629c67bad", "");
        oMap.put("5af7269fb76745afa55ce59ff7cfecbd", "");
        oMap.put("21dec7f740b94687982d83f220f37d3d", "");
        oMap.put("97601bce1d7f4588a495f53aa058f003", "");
        oMap.put("54ba7de29e4c46fab67783cecf28399a", "");
        oMap.put("7c0b0326b65e42cda62931c781018a0d", "");
        oMap.put("57c407c5a4a94ca3b425e923fa6a205b", "");
        oMap.put("224a863a7aff45a69f0dde622a39a211", "");
        oMap.put("1fb4fa19119c47559d82447092fe45ff", "");
        oMap.put("c20526aa9d09466fb7b4d0da1cd7440f", "");
        oMap.put("4af344923cf048f5a794e361044d9f84", "");
        oMap.put("c8ed05693cf34eda83e535540b1313fd", "");
        oMap.put("3a78bf6658b64d1f9e01c3a77a722e34", "");
        oMap.put("352909c4b045426283926604cb2b4f74", "");
        oMap.put("866645ba2e4b4d86b71abc6fc55ea14b", "");
    }

    public static void main(String[] args) throws Exception
    {
        /*Set<String> keys = tMap.keySet();
        StringBuffer ids = new StringBuffer();
        int count = 0;
        for(String key:keys)
        {
            if(count > 0)
            {
                ids.append(",");
            }
            ids.append("'");
            ids.append(key);
            ids.append("'");
            count++;
        }
        String sql = "select tod.id,tod.outerid,tt.rnumber from tticket tt,torder tod where tt.orderid=tod.id and tt.id in (" + ids.toString() + ");";
        System.out.println(sql);*/

        Set<String> keys = tMap.keySet();
        for(String key:keys)
        {
            String sql = "update tticket set rnumber='" + tMap.get(key) + "' where id='" + key + "';";
            System.out.println(sql);
        }
        /*DefaultJcCheck check = new DefaultJcCheck();
        ObjectMapper om = new ObjectMapper();
        Set<String> keys = oMap.keySet();
        for(String key:keys)
        {
            ReqQ15Body reqQ15Body = new ReqQ15Body();
            reqQ15Body.setOrderId(key);
            reqQ15Body.setShowTickets(true);

            om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q150101"));
            String bodyStr = om.writeValueAsString(reqQ15Body);
            String message = TestUtil.getCReqMessage("", "BJJG", bodyStr, "Q15", "0okmnhy6123");
            //log.info(message);
            String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
            //log.info(content);

            McpGtMsg mcpGtMsg = om.readValue(content, McpGtMsg.class);
            RepQ15Body repQ15Body = om.readValue(mcpGtMsg.getBody(), RepQ15Body.class);
            TTicket t = repQ15Body.getOrder().getTickets().get(0);
            CheckParam cp = check.check(t, null, pd);
            long bonus = cp.getBonus();
            if(bonus > 0)
            {
                System.out.println(key + "," + repQ15Body.getOrder().getOuterId() + "," + cp.getBonus()*t.getMultiple());
            }
        }*/
    }


}
