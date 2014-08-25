package com.mcp.deploy.cmd.common;

import com.mcp.core.util.PropFile;

import java.util.Properties;

public class User {
	
	private static Properties props = PropFile.getProps("/user.properties");
	
	public static final String NAME = props.getProperty("name");
	
	public static final String PASSWORD = props.getProperty("password");
}
