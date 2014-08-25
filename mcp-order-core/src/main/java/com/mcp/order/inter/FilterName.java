package com.mcp.order.inter;

import java.util.List;

public class FilterName {
	
	private String value;
	
	List<FilterProperty> propertyList;

	public List<FilterProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<FilterProperty> propertyList) {
		this.propertyList = propertyList;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
