package com.mcp.order.inter.cash;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

import java.util.Date;

/**
 * 用户取现
 * @author ming.li
 */
public class ReqC04Body extends ReqBody {

    private String name;

    private long amount = 0;

    private Date expiredTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
	public void validate() throws CoreException {
        if(StringUtil.isEmpty(name))
        {
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }
        if(amount <= 0)
        {
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }
        if(expiredTime == null)
        {
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }
	}

}
