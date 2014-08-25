package com.mcp.deploy.cmd.common;

import com.mcp.core.util.PropFile;

import java.util.Properties;

public class Term {
	
	private static Properties props = PropFile.getProps("/term.properties");
	
	public static final String CODE = props.getProperty("code");
}
