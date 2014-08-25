package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;

public class RepQ09Body extends RepBody {
	
	private boolean latest;
    
    private String fileUrl;

	public boolean isLatest() {
		return latest;
	}

	public void setLatest(boolean latest) {
		this.latest = latest;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
