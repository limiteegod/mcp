package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.mongo.MgNotifyMsg;
import com.mcp.order.model.ts.Term;

import java.util.List;

public class RepQ17Body extends RepBody {

    private List<MgNotifyMsg> msgList;

    public List<MgNotifyMsg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<MgNotifyMsg> msgList) {
        this.msgList = msgList;
    }
}
