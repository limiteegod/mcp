package com.mcp.order.inter.account;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Customer;
import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter("repBody")
public class RepA04Body extends RepBody {
	
	/**
	 * 状态，密码更新成功为true，更新失败则为false
	 */
	private boolean status = false;

    private Customer customer;

    private String st;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
