package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Term;

import java.util.List;

public class RepQ16Body extends RepBody {

    /**
     * 期次信息
     */
	private Term term;

    /**
     * 附加信息
     */
    private String tip;

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
