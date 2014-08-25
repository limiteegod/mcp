package com.mcp.deploy.cmd.common;

import com.mcp.core.util.PropFile;

import java.util.Properties;

public class Station {
	
	private static Properties props = PropFile.getProps("/station.properties");
	
	public static final String CODE = props.getProperty("code");
	
	public static final String KEY = props.getProperty("key");
}
