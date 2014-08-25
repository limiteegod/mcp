package com.mcp.order.model.mongo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ming.li on 2014/6/10.
 */
public class MgLogin implements Serializable {

    private String id;

    private String st;

    private Date lastActiveTime;

    /**
     * 失效时间
     */
    private Date expiredTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
