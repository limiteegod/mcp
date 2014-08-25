package com.mcp.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工具类，用于读取properties文件
 */
public class PropFile {

	public static Properties getProps(String propFile) {
		System.out.println("读取properties文件：" + propFile);
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = PropFile.class.getResourceAsStream(propFile);
			props.load(in);
			return props;
		} catch (IOException e) {
			System.out.println("读取配置文件失败");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
