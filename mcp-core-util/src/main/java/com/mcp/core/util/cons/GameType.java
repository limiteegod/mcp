package com.mcp.core.util.cons;

/**
 * Created by hadoop on 2014/6/8.
 */
public enum GameType {

    NONE(0, "none", "保留"),
    NORMAL(1, "normal", "普通"),
    GAOPIN(2, "gaopin", "高频"),
    JINGCAI(3, "jingcai", "竞彩");

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

    private GameType(int code, String flag, String desc)
    {
        this.code = code;
        this.flag = flag;
        this.desc = desc;
    }
}
