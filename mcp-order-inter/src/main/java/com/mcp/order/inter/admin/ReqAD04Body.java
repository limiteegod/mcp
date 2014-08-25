package com.mcp.order.inter.admin;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.mongo.MgSort;

import java.util.List;

public class ReqAD04Body extends ReqBody {

    private String gameCode;

    private String termCode;

    private int gameType = -1;

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

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
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

    @Override
	public void validate() throws CoreException {

	}
}
