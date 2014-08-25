/**
 * 安全级别：保密
 * 版权所有 2012 中福在线（北京）网络科技有限公司
 * 官方网站：http://www.zolcorp.com
 * 
 * 版权所有 2011-2012 上海慧行信息科技有限公司
 * 官方网站：http://www.hxtek.net
 */
package com.mcp.core.util;

public class HexUtil {
	
	//private static final String HEX_CHARS = "0123456789abcdef";

	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append("0123456789abcdef".charAt(b[i] >>> 4 & 0xF));
			sb.append("0123456789abcdef".charAt(b[i] & 0xF));
		}
		return sb.toString();
	}

	public static byte[] toByteArray(String s) {
		byte[] buf = new byte[s.length() / 2];
		int j = 0;
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) (Character.digit(s.charAt(j++), 16) << 4 | Character
					.digit(s.charAt(j++), 16));
		}
		return buf;
	}
}
