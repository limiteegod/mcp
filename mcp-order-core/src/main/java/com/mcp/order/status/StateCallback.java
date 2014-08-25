package com.mcp.order.status;/*
 * User: yeeson he
 * Date: 13-9-15
 * Time: 下午5:01
 */

import java.util.Properties;

public interface StateCallback {
    public boolean run();
    public void update(int state,Properties param);
}
