package com.mcp.core.util.cons;

/**
 * Created by hadoop on 2014/6/8.
 */
public enum TOrderType {

    CUSTOMER(0, "customer", "普通用户"),
    CHANNEL(1, "channel", "渠道");

    /**
     * 编码
     */
    private int code;

    /**
     * 标志
     */
    private String flag;

    /**
     * 描述信息
     */
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private TOrderType(int code, String flag, String desc)
    {
        this.code = code;
        this.flag = flag;
        this.desc = desc;
    }
}
