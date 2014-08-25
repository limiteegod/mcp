/**
 * 
 */
package com.mcp.order.dao.finance;

/**
 * @author ming.li
 *
 */
public class AccountOperatorType {

	private String type;
	
	private String name;
	
	private String eventCode;
	
	private String operator;
	
	public AccountOperatorType(String type, String name, String eventCode)
	{
		this(type, name, eventCode, null);
	}
    public AccountOperatorType(String type, String name)
    {
        this(type, name, "", null);
    }
	
	public AccountOperatorType(String type, String name, String eventCode, String operator)
	{
		this.type = type;
		this.name = name;
		this.eventCode = eventCode;
		this.operator = operator;
	}
	
	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
