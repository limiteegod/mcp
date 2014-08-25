package com.mcp.order.inter.operation;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.admin.UserOperation;

import java.util.List;

public class RepAP01Body extends RepBody {

    private List<UserOperation> rst;

    public List<UserOperation> getRst() {
        return rst;
    }

    public void setRst(List<UserOperation> rst) {
        this.rst = rst;
    }

}
