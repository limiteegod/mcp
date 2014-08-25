/**
 * 
 */
package com.mcp.filter.model;

/**
 * @author ming.li
 *
 */
public class ServiceType {
	
	private String cmd;
	
	private String path;
	
	public ServiceType()
	{
		this(null, null);
	}
	
	public ServiceType(String cmd, String path)
	{
		this.cmd = cmd;
		this.path = path;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
