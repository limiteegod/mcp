package com.mcp.order.batch.check;

import com.mcp.order.common.Constants;
import org.apache.log4j.Logger;

public class CheckParam {

    public static Logger log = Logger.getLogger(CheckParam.class);

    /**
     * 税后奖金
     */
    private long bonus;

    /**
     * 税前奖金
     */
    private long bonusBeforeTax;

    /**
     * 中奖明细
     */
    private int detail;

    /**
     * 增加单注单倍中奖金额
     * @param level 奖级
     * @param count 中level奖级的注数
     * @param bonus 中奖金额
     */
    public void incrBonus(int level, int count, long bonus)
    {
        this.bonusBeforeTax += bonus*count;
        if(bonus >= Constants.TAX_SEP)  //交税
        {
            this.bonus += (bonus*(10000 - Constants.TAX_PERCENTAGE)/10000)*count;
        }
        else
        {
            this.bonus += bonus*count;
        }
        log.info("奖级:" + level + ",注数:" + count + ",金额:" + bonus + ".");
        //TODO 生成中奖明细
    }

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public long getBonusBeforeTax() {
        return bonusBeforeTax;
    }

    public void setBonusBeforeTax(long bonusBeforeTax) {
        this.bonusBeforeTax = bonusBeforeTax;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }
}
