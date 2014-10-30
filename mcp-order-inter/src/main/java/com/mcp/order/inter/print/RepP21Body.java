package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;

import java.util.List;

public class RepP21Body extends RepBody {
	
	private  List<RepP02Body> repP02BodyList;

    public List<RepP02Body> getRepP02BodyList() {
        return repP02BodyList;
    }

    public void setRepP02BodyList(List<RepP02Body> repP02BodyList) {
        this.repP02BodyList = repP02BodyList;
    }
}
