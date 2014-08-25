package com.mcp.core.util.cons;

/**
 * Created by hadoop on 2014/6/8.
 */
public enum SchemeType {

    POS0(0, "pos", "占位"),
    NONE(1, "default", "无方案"),
    SEQ_FOLLOW(2, "follow", "连期追号"),
    DT(3, "dt", "定投"),
    HEMAI(4, "hemai", "合买");

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

    private SchemeType(int code, String flag, String desc)
    {
        this.code = code;
        this.flag = flag;
        this.desc = desc;
    }
}
