package com.mcp.order.model.ts;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午11:27
 * To be the best of me!
 */
public class GameAdapter implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8912653142614960059L;
	
	//    游戏适配玩法
    private String id;
    private String gameAdapterCode;//game、betType、playType的组合字符串
    private String gameAdapterName;
    private String gameCode;
    private String verifyRegular; //验证规则
    private String winningRegular; //中奖规则
    private int status;// 当前状态。0 不可用 1 可用
    //TODO 可以删除
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameAdapterCode() {
		return gameAdapterCode;
	}
	public void setGameAdapterCode(String gameAdapterCode) {
		this.gameAdapterCode = gameAdapterCode;
	}
	public String getGameAdapterName() {
		return gameAdapterName;
	}
	public void setGameAdapterName(String gameAdapterName) {
		this.gameAdapterName = gameAdapterName;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getVerifyRegular() {
		return verifyRegular;
	}
	public void setVerifyRegular(String verifyRegular) {
		this.verifyRegular = verifyRegular;
	}
	public String getWinningRegular() {
		return winningRegular;
	}
	public void setWinningRegular(String winningRegular) {
		this.winningRegular = winningRegular;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
}
