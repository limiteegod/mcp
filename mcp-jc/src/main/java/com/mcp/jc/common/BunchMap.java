package com.mcp.jc.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeeson on 13-12-17.
 */
public class BunchMap {
    public static Map<String, String> bunchMap = new HashMap<String, String>();

    static {
        //todo 需要完整列出所有的串关场次特征码。
        bunchMap.put("11", "10000000");
        bunchMap.put("21", "01000000");
        bunchMap.put("31", "00100000");
        bunchMap.put("41", "00010000");
        bunchMap.put("51", "00001000");
        bunchMap.put("61", "00000100");
        bunchMap.put("71", "00000010");
        bunchMap.put("81", "00000001");
        bunchMap.put("23", "11000000");
        bunchMap.put("36", "11000000");
        bunchMap.put("37", "11100000");
        bunchMap.put("410", "11000000");
        bunchMap.put("414", "11100000");
        bunchMap.put("415", "11110000");
        bunchMap.put("515", "11000000");
        bunchMap.put("525", "11100000");
        bunchMap.put("530", "11110000");
        bunchMap.put("531", "11111000");
        bunchMap.put("621", "11000000");
        bunchMap.put("641", "11100000");
        bunchMap.put("656", "11110000");
        bunchMap.put("662", "11111000");
        bunchMap.put("663", "11111100");
        bunchMap.put("7127", "11111110");
        bunchMap.put("8255", "11111111");
        bunchMap.put("33", "01000000");
        bunchMap.put("34", "01100000");
        bunchMap.put("46", "01000000");
        bunchMap.put("411", "01110000");
        bunchMap.put("510", "01000000");
        bunchMap.put("520", "01100000");
        bunchMap.put("526", "01111000");
        bunchMap.put("615", "01000000");
        bunchMap.put("635", "01100000");
        bunchMap.put("650", "01110000");
        bunchMap.put("657", "01111100");
        bunchMap.put("7120", "01111110");
        bunchMap.put("8247", "01111111");
        bunchMap.put("44", "00100000");
        bunchMap.put("45", "00110000");
        bunchMap.put("516", "00111000");
        bunchMap.put("620", "00100000");
        bunchMap.put("642", "00111100");
        bunchMap.put("55", "00010000");
        bunchMap.put("56", "00011000");
        bunchMap.put("622", "00011100");
        bunchMap.put("735", "00010000");
        bunchMap.put("870", "00010000");
        bunchMap.put("66", "00001000");
        bunchMap.put("67", "00001100");
        bunchMap.put("721", "00001000");
        bunchMap.put("856", "00001000");
        bunchMap.put("77", "00000100");
        bunchMap.put("78", "00000110");
        bunchMap.put("828", "00000100");
        bunchMap.put("88", "00000010");
        bunchMap.put("89", "00000011");
    }
}
