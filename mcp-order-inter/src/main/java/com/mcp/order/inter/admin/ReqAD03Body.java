package com.mcp.order.inter.admin;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.mongo.MgSort;

import java.util.List;

public class ReqAD03Body extends ReqBody {

    private String gameCode;

    private String termCode;

    private String stationCode;

    private int orderType = -1;

    private int rptType = -1;

    private int page = 0;

    private int size = 10;

    private List<MgSort> sortList;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<MgSort> getSortList() {
        return sortList;
    }

    public void setSortList(List<MgSort> sortList) {
        this.sortList = sortList;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getRptType() {
        return rptType;
    }

    public void setRptType(int rptType) {
        this.rptType = rptType;
    }

    @Override
	public void validate() throws CoreException {
        if(StringUtil.isEmpty(this.gameCode) || StringUtil.isEmpty(this.termCode))
        {
            throw new CoreException(ErrCode.E0003);
        }
	}
}
