package com.mcp.order.model.ts;

import javax.persistence.*;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午10:33
 * 彩种奖项等级
 */
@Entity
@Table(name="gamegrade")
public class GameGrade implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2355990784739897673L;

	@Id
    @Column(length=32)
    private String id;
	
	@Basic
	@Column(length = 10)
    private String gameCode;
	
	@Basic
	@Column(length = 40)
    private String code;
	
	@Basic
	@Column(length = 40)
    private String name;
	
	@Basic
    private int gLevel;//奖项等级
	
	@Basic
    private long bonus;
	
	@Basic
    private boolean isFixedBonus;//是否固定奖金
	
	@Basic
    private int status;   // 当前状态。d0 可用 1 不可用
	
	/**
	 * 此奖级的中奖注数
	 */
	@Basic
	private int gCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public boolean isFixedBonus() {
		return isFixedBonus;
	}

	public void setFixedBonus(boolean isFixedBonus) {
		this.isFixedBonus = isFixedBonus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getgLevel() {
		return gLevel;
	}

	public void setgLevel(int gLevel) {
		this.gLevel = gLevel;
	}

	public int getgCount() {
		return gCount;
	}

	public void setgCount(int gCount) {
		this.gCount = gCount;
	}

	
}
