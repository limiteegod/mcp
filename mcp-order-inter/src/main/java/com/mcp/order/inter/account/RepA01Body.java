package com.mcp.order.inter.account;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Customer;
import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter("repBody")
public class RepA01Body extends RepBody {
	
	private Customer customer;

    /**
     * 用户的密钥串
     */
    private String st;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
