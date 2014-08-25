package com.mcp.order.status;/*
 * User: yeeson he
 * Date: 13-8-28
 * Time: 下午2:49
 */


import java.util.HashMap;
import java.util.Map;

public enum TicketState {
    INIT("初始状态", 1000) {

    },
    WAITING_PAY("等待支付", 1080) {
    	
    },
    PRESALE("预售", 1090) {
    	
    },
    WAITING_PRINT("等待打印", 1100) {

    },
    TAKE_AWAY("程序取走", 1200) {

    },
    PRINT_SUCCESS("打印成功", 1300) {

    },
    PRINT_ERROR("打印错误", 1400) {

    },
    PRINT_FAILURE("打印失败", 1500) {

    },
    CANCELED("打印取消", 1600) {

    },
    NEED_UPDATE("需要更新", 1700) {

    },
    REFUNDED("已经退款", 1800) {
    
    };
    private boolean isTransient = false;
    private String desc = "";
    private int code = 0;
    private static Map<Integer, TicketState> valuesMap = new HashMap<Integer, TicketState>();

    public static Map<Integer, TicketState> getValuesMap() {
        for (TicketState s : TicketState.values()) {
            valuesMap.put(s.getCode(), s);
        }
        return valuesMap;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    private TicketState() {
    }

    private TicketState(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    private TicketState(boolean aTransient) {
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
