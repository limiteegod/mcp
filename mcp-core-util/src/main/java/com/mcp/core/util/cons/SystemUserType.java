package com.mcp.core.util.cons;

/**
 * Created by hadoop on 2014/6/8.
 */
public enum SystemUserType {

    GUEST(0, "guest", "游客"),
    CUSTOMER(1, "customer", "普通用户"),
    CHANNEL(2, "channel", "渠道用户"),
    ADMINISTRATOR(3, "admin", "系统管理员");

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

    private SystemUserType(int code, String flag, String desc)
    {
        this.code = code;
        this.flag = flag;
        this.desc = desc;
    }
}
