package com.mcp.order.inter.admin;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.mongo.MgTermReport;

import java.util.List;

public class RepAD03Body extends RepBody {

    private List<MgTermReport> reportList;

    public List<MgTermReport> getReportList() {
        return reportList;
    }

    public void setReportList(List<MgTermReport> reportList) {
        this.reportList = reportList;
    }

    private PageInfo pi;

    public PageInfo getPi() {
        return pi;
    }

    public void setPi(PageInfo pi) {
        this.pi = pi;
    }
}
