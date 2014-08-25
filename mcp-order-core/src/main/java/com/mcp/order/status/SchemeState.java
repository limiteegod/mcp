package com.mcp.order.status;/*
 * User: yeeson he
 * Date: 13-8-28
 * Time: 下午2:48
 */

import java.util.HashMap;
import java.util.Map;

public enum SchemeState {
    NOT_AVAILABLE("未生效", 1010) {
    	
    },
    RUNNING("进行中", 1110) {

    },
    CANCELING("取消中", 1200) {

    },
    CANCELED("已取消", 1210) {

    },
    REFUNDED("已退款", 1310) {

    },
    FINISHED("已完成", 1410) {

    },
    WAIT_AUDIT("等待结算", 1405) {

    },
    AUDITING("结算中", 1500) {

    },
    AUDITED("已结算", 1510) {

    },
    OVER("已完结", 1610) {

    };
    private boolean isTransient = false;
    private String desc = "";
    private int code = 0;
    private static Map<Integer, SchemeState> valuesMap = new HashMap<Integer, SchemeState>();

    public static Map<Integer, SchemeState> getValuesMap() {
        for (SchemeState s : SchemeState.values()) {
            valuesMap.put(s.getCode(), s);
        }
        return valuesMap;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    private SchemeState() {
    }

    private SchemeState(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    private SchemeState(boolean aTransient) {
        isTransient = aTransient;
    }

    void next() {
        throw new RuntimeException("前一个操作执行中，不能进行新的操作！");
    }



    String getOperation() {
        return "";
    }

    //状态机结束时，再进行调用得到的方法
    public void output() {
        throw new RuntimeException("当前期次已经封存，不能做任何操作！");
    }

    public String getDesc() {
        return desc;
    }


    public int getCode() {
        return code;
    }
}
