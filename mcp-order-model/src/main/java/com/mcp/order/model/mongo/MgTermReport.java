/**
 * 
 */
package com.mcp.order.model.mongo;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.core.util.cons.TermReportType;

/**
 * 期次统计表，统计期次的各种数据
 * @author ming.li
 */
public class MgTermReport {
	
	private String id;
	
	private String gameCode;
	
	private String termCode;
	
	private String channelCode;

    private int type = TOrderType.CUSTOMER.ordinal();   //订单类型，用户投注或者渠道投注，TOrderType
	
	/**
	 * 订单返奖数
	 */
	private long orderPrizeCount;
	
	/**
	 * 返奖订单销售金额
	 */
	private long orderPrizeAmount;
	
	/**
	 * 订单返奖金额
	 */
	private long orderPrizeBonus;
	
	/**
	 * 票据退款数量
	 */
	private long ticketRefundCount;
	
	/**
	 * 票据退款金额
	 */
	private long ticketRefundAmount;

    /**
     * 订单退款数量
     */
    private long orderRefundCount;

    /**
     * 订单退款金额
     */
    private long orderRefundAmount;
	
	/**
	 * 中奖数目
	 */
	private long ticketHitCount;
	
	/**
	 * 中奖票的销售额
	 */
	private long ticketHitAmount;
	
	/**
	 * 中奖票的中奖额
	 */
	private long ticketHitBonus;
	
	/**
	 * 未中奖票数
	 */
	private long ticketNotHitCount;
	
	/**
	 * 未中奖票金额
	 */
	private long ticketNotHitAmount;

    /**
     * 票据税前中奖金额
     */
    private long ticketHitBonusBeforeTax;

    /**
     * 中奖订单数目
     */
    private long orderHitCount;

    /**
     * 中奖订单的销售额
     */
    private long orderHitAmount;

    /**
     * 中奖订单的中奖额
     */
    private long orderHitBonus;

    /**
     * 未中奖订单数
     */
    private long orderNotHitCount;

    /**
     * 未中奖订单金额
     */
    private long orderNotHitAmount;

    /**
     * 订单税前中奖金额
     */
    private long orderHitBonusBeforeTax;

    /**
     * 期次报表的统计口径
     */
    private int rptType = TermReportType.SALE.ordinal();

    public int getRptType() {
        return rptType;
    }

    public void setRptType(int rptType) {
        this.rptType = rptType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTicketHitBonusBeforeTax() {
        return ticketHitBonusBeforeTax;
    }

    public void setTicketHitBonusBeforeTax(long ticketHitBonusBeforeTax) {
        this.ticketHitBonusBeforeTax = ticketHitBonusBeforeTax;
    }

    public long getOrderPrizeCount() {
		return orderPrizeCount;
	}

	public void setOrderPrizeCount(long orderPrizeCount) {
		this.orderPrizeCount = orderPrizeCount;
	}

	public long getOrderPrizeAmount() {
		return orderPrizeAmount;
	}

	public void setOrderPrizeAmount(long orderPrizeAmount) {
		this.orderPrizeAmount = orderPrizeAmount;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public long getTicketRefundCount() {
		return ticketRefundCount;
	}

	public void setTicketRefundCount(long ticketRefundCount) {
		this.ticketRefundCount = ticketRefundCount;
	}

	public long getTicketRefundAmount() {
		return ticketRefundAmount;
	}

	public void setTicketRefundAmount(long ticketRefundAmount) {
		this.ticketRefundAmount = ticketRefundAmount;
	}

	public long getTicketHitCount() {
		return ticketHitCount;
	}

	public void setTicketHitCount(long ticketHitCount) {
		this.ticketHitCount = ticketHitCount;
	}

	public long getTicketHitAmount() {
		return ticketHitAmount;
	}

	public void setTicketHitAmount(long ticketHitAmount) {
		this.ticketHitAmount = ticketHitAmount;
	}

	public long getTicketHitBonus() {
		return ticketHitBonus;
	}

	public void setTicketHitBonus(long ticketHitBonus) {
		this.ticketHitBonus = ticketHitBonus;
	}

	public long getTicketNotHitCount() {
		return ticketNotHitCount;
	}

	public void setTicketNotHitCount(long ticketNotHitCount) {
		this.ticketNotHitCount = ticketNotHitCount;
	}

	public long getTicketNotHitAmount() {
		return ticketNotHitAmount;
	}

	public void setTicketNotHitAmount(long ticketNotHitAmount) {
		this.ticketNotHitAmount = ticketNotHitAmount;
	}

	public long getOrderPrizeBonus() {
		return orderPrizeBonus;
	}

	public void setOrderPrizeBonus(long orderPrizeBonus) {
		this.orderPrizeBonus = orderPrizeBonus;
	}

    public long getOrderRefundCount() {
        return orderRefundCount;
    }

    public void setOrderRefundCount(long orderRefundCount) {
        this.orderRefundCount = orderRefundCount;
    }

    public long getOrderRefundAmount() {
        return orderRefundAmount;
    }

    public void setOrderRefundAmount(long orderRefundAmount) {
        this.orderRefundAmount = orderRefundAmount;
    }

    public long getOrderHitCount() {
        return orderHitCount;
    }

    public void setOrderHitCount(long orderHitCount) {
        this.orderHitCount = orderHitCount;
    }

    public long getOrderHitAmount() {
        return orderHitAmount;
    }

    public void setOrderHitAmount(long orderHitAmount) {
        this.orderHitAmount = orderHitAmount;
    }

    public long getOrderHitBonus() {
        return orderHitBonus;
    }

    public void setOrderHitBonus(long orderHitBonus) {
        this.orderHitBonus = orderHitBonus;
    }

    public long getOrderNotHitCount() {
        return orderNotHitCount;
    }

    public void setOrderNotHitCount(long orderNotHitCount) {
        this.orderNotHitCount = orderNotHitCount;
    }

    public long getOrderNotHitAmount() {
        return orderNotHitAmount;
    }

    public void setOrderNotHitAmount(long orderNotHitAmount) {
        this.orderNotHitAmount = orderNotHitAmount;
    }

    public long getOrderHitBonusBeforeTax() {
        return orderHitBonusBeforeTax;
    }

    public void setOrderHitBonusBeforeTax(long orderHitBonusBeforeTax) {
        this.orderHitBonusBeforeTax = orderHitBonusBeforeTax;
    }
}
