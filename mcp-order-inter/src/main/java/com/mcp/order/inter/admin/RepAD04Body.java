package com.mcp.order.inter.admin;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.model.ts.Term;

import java.util.List;

public class RepAD04Body extends RepBody {

    private List<Term> rst;

    public List<Term> getRst() {
        return rst;
    }

    public void setRst(List<Term> rst) {
        this.rst = rst;
    }

    private PageInfo pi;

    public PageInfo getPi() {
        return pi;
    }

    public void setPi(PageInfo pi) {
        this.pi = pi;
    }
}
