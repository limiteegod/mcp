package com.mcp.deploy.util;

import com.mcp.core.util.PropFile;

import java.util.Properties;

public class LotConfig {
	
	private static Properties props = PropFile.getProps("/lot.properties");
	
	public static final String CHANNEL_CODE = props.getProperty("channel.code");
	
	public static final String CUSTOMER_NAME = props.getProperty("customer.name");
	
	public static final String CUSTOMER_PASSWORD = props.getProperty("customer.password");
	
	public static final long LOT_COUNT = Long.parseLong(props.getProperty("lot.count"));
	
	public static final String TERM_GAMECODE = props.getProperty("term.gameCode");
	
	public static final String TERM_CODE = props.getProperty("term.code");
	
	public static final int ORDER_AMOUNT = Integer.parseInt(props.getProperty("order.amount"));
	
	public static final int TICKET_AMOUNT = Integer.parseInt(props.getProperty("ticket.amount"));
	
	public static final String TICKET_PTTYPE = props.getProperty("ticket.ptType");
	
	public static final String TICKET_BTTYPE = props.getProperty("ticket.btType");
	
	public static final String TICKET_NUMBERS = props.getProperty("ticket.numbers");
}
