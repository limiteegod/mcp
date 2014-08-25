package com.mcp.deploy.lot;

import com.mcp.core.util.PropFile;

import java.util.Properties;

public class JcConfig {
	
	private static Properties props = PropFile.getProps("/jcNum.properties");
	
	public static final int LOT_COUNT = Integer.parseInt(props.getProperty("jc.lotCount"));
	
	public static final String NUMBER = props.getProperty("jc.number");
	
	public static final String PLAY_TYPE = props.getProperty("jc.playType");
	
	public static final String BET_TYPE = props.getProperty("jc.betType");
	
	public static final int AMOUNT = Integer.parseInt(props.getProperty("jc.amount"));
}
