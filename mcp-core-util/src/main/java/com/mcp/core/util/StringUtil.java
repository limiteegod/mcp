/**
 *
 */
package com.mcp.core.util;

/**
 * @author ming.li
 */
public class StringUtil {
	
    /**
     * 判断字符串是否为空，null值，或长度为0，则返回true
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }
    
}
