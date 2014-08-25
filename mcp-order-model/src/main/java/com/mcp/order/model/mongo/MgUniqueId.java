package com.mcp.order.model.mongo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ming.li on 2014/6/10.
 */
public class MgUniqueId implements Serializable {

    private String id;

    private Date createTime;

    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
