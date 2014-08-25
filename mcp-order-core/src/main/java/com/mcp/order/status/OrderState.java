package com.mcp.order.status;/*
 * User: yeeson he
 * Date: 13-8-28
 * Time: 下午2:48
 */

import java.util.HashMap;
import java.util.Map;

public enum OrderState {
    INIT("初始状态", 1000) {

    },
    PRESALE("预售", 1001) {
    	
    },
    WAITING_PRINT("等待出票", 1100) {

    },
    SUCCESS("出票成功", 1200) {

    },
    PRINT_FAILURE("出票失败", 1210) {

    },
    REFUNDED("已退款", 1220) {
    	
    },
    PARTIAL_SUCCESS("部分出票成功", 1300) {

    },
    FAILURE("交易失败", 1400) {

    },
    CANCEL("交易取消", 1500) {

    },
    WAITING_PAY("等待支付", 1600) {

    },
    WAITING_CONFIRM("等待确认", 1700) {

    },
    NOT_HIT("未中奖", 1800) {
    	
    },
    HIT("已中奖", 1810) {
    	
    },
    PRIZED("已返奖", 1900) {
    	
    },
    WAITING_BIG_PRIZING("等待返大奖", 2100) {
    	
    };
    private boolean isTransient = false;
    private String desc = "";
    private int code = 0;
    private static Map<Integer, OrderState> valuesMap = new HashMap<Integer, OrderState>();

    public static Map<Integer, OrderState> getValuesMap() {
        for (OrderState s : OrderState.values()) {
            valuesMap.put(s.getCode(), s);
        }
        return valuesMap;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    private OrderState() {
    }

    private OrderState(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    private OrderState(boolean aTransient) {
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
