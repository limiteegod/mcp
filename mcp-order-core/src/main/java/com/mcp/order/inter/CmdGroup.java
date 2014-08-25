/**
 * 
 */
package com.mcp.order.inter;

import java.util.List;

/**
 * @author ming.li
 *
 */
public class CmdGroup {
	
	private String code;
	
	private String pkg;
	
	private String rep;
	
	private String req;
	
	private List<Cmd> cmdList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public List<Cmd> getCmdList() {
		return cmdList;
	}

	public void setCmdList(List<Cmd> cmdList) {
		this.cmdList = cmdList;
	}

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}
}
