package com.mcp.order.inter.admin;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.mongo.MgSort;

import java.util.List;

public class ReqAD05Body extends ReqBody {

    private String gameCode;

    private String termCode;

    private List<Integer> statusList;

    private List<Integer> exStatusList;

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

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public List<Integer> getExStatusList() {
        return exStatusList;
    }

    public void setExStatusList(List<Integer> exStatusList) {
        this.exStatusList = exStatusList;
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

    @Override
	public void validate() throws CoreException {

	}
}