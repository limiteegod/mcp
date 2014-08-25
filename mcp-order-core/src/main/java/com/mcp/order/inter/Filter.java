package com.mcp.order.inter;

import java.util.List;

public class Filter {
	
	private String code;
	
	List<FilterName> nameList;

	public List<FilterName> getNameList() {
		return nameList;
	}

	public void setNameList(List<FilterName> nameList) {
		this.nameList = nameList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
