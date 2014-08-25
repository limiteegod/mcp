package com.mcp.order.inter.print;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.mongo.MgPrint;

import java.util.List;

public class RepP12Body extends RepBody {

    private PageInfo pi;

    private List<MgPrint> rst;

    public PageInfo getPi() {
        return pi;
    }

    public void setPi(PageInfo pi) {
        this.pi = pi;
    }

    public List<MgPrint> getRst() {
        return rst;
    }

    public void setRst(List<MgPrint> rst) {
        this.rst = rst;
    }
}
