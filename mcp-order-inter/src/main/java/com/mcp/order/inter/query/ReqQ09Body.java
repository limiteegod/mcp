package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;


public class ReqQ09Body extends ReqBody {
	
	private String clientCode;

	private String fileTypeCode;

	private String fileVersionCode;

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	public String getFileVersionCode() {
		return fileVersionCode;
	}

	public void setFileVersionCode(String fileVersionCode) {
		this.fileVersionCode = fileVersionCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mcp.core.inter.define.Body#validate()
	 */
	@Override
	public void validate() throws CoreException {
		// if(this.gameCode == null || this.gameCode.length() == 0)
		// {
		// throw new CoreException(ErrCode.E0003, "gameCode的格式不正确");
		// }
		// if(this.termCode == null || this.termCode.length() == 0)
		// {
		// throw new CoreException(ErrCode.E0003, "termCode的格式不正确");
		// }
	}
}
