package com.mcp.order.inter.scheme;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.scheme.model.SchemeHm;
import org.apache.log4j.Logger;

public class ReqS05Body extends ReqBody {
	
	private static Logger log = Logger.getLogger(ReqS05Body.class);
	
	private String schemeId;

    private long amount;

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override

	public void validate() throws CoreException {

	}
}
