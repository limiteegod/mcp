package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ02Body extends ReqBody {
	
	/**
	 * 页号，从0开始
	 */
	private int startIndex = 0;

	/**
	 * 每页记录条数
	 */
	private int size = 10;
	
	/**
	 * 投注站id
	 */
	private String stationId;
	
	/**
	 * 彩票编码
	 */
	private String gameCode;
	
	/**
	 * 方案的类型
	 */
	private int schemeType;
	
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

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}
	
	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mcp.core.inter.define.Body#validate()
	 */
	@Override
	public void validate() throws CoreException {
		
	}
}
