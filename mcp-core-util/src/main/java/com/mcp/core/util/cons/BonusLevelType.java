package com.mcp.core.util.cons;

/**
 * Created by hadoop on 2014/6/8.
 */
public enum BonusLevelType {

    NOT_HIT(0, "notHit", "未中奖"),
    LITTLE_HIT(1, "prized", "中小奖"),
    BIG_HIT(2, "big", "中大奖");

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

    private BonusLevelType(int code, String flag, String desc)
    {
        this.code = code;
        this.flag = flag;
        this.desc = desc;
    }
}
