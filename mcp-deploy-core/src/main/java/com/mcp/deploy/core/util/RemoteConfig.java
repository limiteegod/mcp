package com.mcp.deploy.core.util;

import com.mcp.core.util.PropFile;

import java.util.Properties;

public class RemoteConfig {
	
	private static Properties props = PropFile.getProps("/remote.properties");
	
	public static final String IP = props.getProperty("test.remote.host");
	
	public static final int PORT = Integer.parseInt(props.getProperty("test.remote.port"));
	
	public static final String PATH = "/mcp-filter/main/interface.htm";
}
