package com.mcp.order.inter.admin;

import com.mcp.order.inter.RepBody;

public class RepAD02Body extends RepBody {

    private String ticketId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
