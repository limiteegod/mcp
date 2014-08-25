package com.mcp.order.status;/*
 * User: yeeson he
 * Date: 13-8-28
 * Time: 下午2:49
 */


import java.util.HashMap;
import java.util.Map;

public enum ReceiptState {
    NOT_TAKE_AWAY("未领取", 1110) {

    },
    TAKE_AWAY("已领取", 1210) {
    	
    },
    NOT_CLAIM_PRIZE("未兑奖", 1310) {

    },
    NOT_HIT("未中奖", 1410) {

    },
    SYSTEM_TAKEWAY("程序取走", 1510) {

    },
    CLAIM_SUCCESS("兑奖成功", 1610) {

    },
    CLAIM_FAILURE("兑奖失败", 1710) {

    };
    private boolean isTransient = false;
    private String desc = "";
    private int code = 0;
    private static Map<Integer, ReceiptState> valuesMap = new HashMap<Integer, ReceiptState>();

    public static Map<Integer, ReceiptState> getValuesMap() {
        for (ReceiptState s : ReceiptState.values()) {
            valuesMap.put(s.getCode(), s);
        }
        return valuesMap;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    private ReceiptState() {
    }

    private ReceiptState(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    private ReceiptState(boolean aTransient) {
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
