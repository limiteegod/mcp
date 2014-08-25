package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.message.CustomerMsg;

import java.util.List;


public class RepQ12Body extends RepBody {
	
	private List<CustomerMsg> messages;

	public List<CustomerMsg> getMessages() {
		return messages;
	}

	public void setMessages(List<CustomerMsg> messages) {
		this.messages = messages;
	}
}
