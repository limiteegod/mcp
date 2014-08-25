package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ03Body extends ReqBody {
	/**
     * 游戏的编号
     */
    private String gameCode = "";
    
    /**
     * 订单状态    ：奖金流程与订单流程是分离的流程。
     */
    private String orderStatus = "";
    
    /**
     * 排除的订单状态
     */
    private String exOrderStatus;
    
    /**
     * 记录起始地址
     */
    private int startIndex = 0;
    /**
     * 返回的分页大小
     */
    private int size = 10;
    
    /**
     * 投注站id
     */
    private String stationId = "";
    
    /**
     * 方案类型
     */
    private int schemeType = -1;
    
    /**
     * 订单号
     */
    private String orderId;
    
    /**
     * 方案号
     */
    private String schemeId;
    
    /**
     * 是否显示票的信息
     */
    private boolean showTickets = false;

    public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getGameCode() {
        return gameCode;
    }

    public int getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}

	public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getExOrderStatus() {
		return exOrderStatus;
	}

	public void setExOrderStatus(String exOrderStatus) {
		this.exOrderStatus = exOrderStatus;
	}

	public boolean isShowTickets() {
		return showTickets;
	}

	public void setShowTickets(boolean showTickets) {
		this.showTickets = showTickets;
	}

	/* (non-Javadoc)
     * @see com.mcp.core.inter.define.Body#validate()
     */
    @Override
    public void validate() throws CoreException {
    }

}
