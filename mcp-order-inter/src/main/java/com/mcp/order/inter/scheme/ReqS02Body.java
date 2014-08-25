package com.mcp.order.inter.scheme;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqS02Body extends ReqBody {

	/**
	 * 页号，从0开始
	 */
	private int startIndex = 0;

	/**
	 * 每页记录条数
	 */
	private int size = 10;

	/**
	 * 彩票编码
	 */
	private String gameCode;

	/**
	 * 方案状态
	 */
	private int status;
	
	/**
	 * 方案的类型
	 */
	private String schemeId;

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

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	@Override
	public void validate() throws CoreException {

	}
}
