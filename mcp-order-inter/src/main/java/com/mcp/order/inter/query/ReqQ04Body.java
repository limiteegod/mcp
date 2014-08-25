package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

import java.util.Date;
import java.util.List;


public class ReqQ04Body extends ReqBody {
	
	private String accountType;
    
    private List<String> entityIds;
    
    private Date startTime;
    
    private Date endTime;
    
    private int startIndex;
    
    private int size = 10;

    public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getEntityIds() {
		return entityIds;
	}

	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}

	@Override
	public void validate() throws CoreException {
	}
}
