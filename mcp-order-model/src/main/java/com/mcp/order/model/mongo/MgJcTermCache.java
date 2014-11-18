package com.mcp.order.model.mongo;

import java.util.Date;

/**
 * Created by CH on 2014/11/18.
 */
public class MgJcTermCache {

    private String id;

    private String value;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
