package com.mcp.order.model.mongo;

/**
 * Created by limitee on 2014/8/1.
 */
public class MgSort {

    private int direction = 1;

    private String property;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
