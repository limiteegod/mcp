package com.mcp.order.inter.admin;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.ts.Customer;
import org.codehaus.jackson.map.annotate.JsonFilter;

public class RepAD01Body extends RepBody {
	
	private Admini user;

    /**
     * 用户的密钥串
     */
    private String st;

    public Admini getUser() {
        return user;
    }

    public void setUser(Admini user) {
        this.user = user;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}
