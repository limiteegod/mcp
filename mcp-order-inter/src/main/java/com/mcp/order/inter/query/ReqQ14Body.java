package com.mcp.order.inter.query;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

import java.util.List;

public class ReqQ14Body extends ReqBody {
	
	private List<Integer> status;
	
	private List<Integer> exStatus;
	
	private String gameCode;
	
	private String matchCode;
	
	/**
	 * 需要显示的赔率组名称
	 */
	private List<String> oddsCode;
	
	/**
	 * 需要显示的玩法的列表
	 */
	private List<String> playTypeCode;

	public List<Integer> getStatus() {
		return status;
	}

	public void setStatus(List<Integer> status) {
		this.status = status;
	}

	public List<Integer> getExStatus() {
		return exStatus;
	}

	public void setExStatus(List<Integer> exStatus) {
		this.exStatus = exStatus;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

	public List<String> getOddsCode() {
		return oddsCode;
	}

	public void setOddsCode(List<String> oddsCode) {
		this.oddsCode = oddsCode;
	}

	public List<String> getPlayTypeCode() {
		return playTypeCode;
	}

	public void setPlayTypeCode(List<String> playTypeCode) {
		this.playTypeCode = playTypeCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mcp.core.inter.define.Body#validate()
	 */
	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(this.gameCode))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
