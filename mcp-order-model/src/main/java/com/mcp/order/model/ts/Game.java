package com.mcp.order.model.ts;

import com.mcp.order.common.ConstantValues;
import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;
import java.util.Map;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:39
 * To be the best of me!
 */
@Entity
@Table(name="game")
@JsonFilter("game")
public class Game implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4980510706125572613L;

	//    游戏名称，比如双色球、大乐透等。不直接绑定期次状态，可以灵活扩展同时销售的期次。
	@Id
    @Column(length=32)
    private String id;
	
	@Basic
	@Column(length = 40)
    private String code;
	
	@Basic
	@Column(length = 40)
    private String name;
	
	@Basic
	@Column(length = 40)
    private String publishDesc;
	
	@Basic
	@Column(length = 40)
    private String period;//销售周期，用cron表达式表示。
	
	/**
	 * 游戏当前状态的描述信息，如今日开奖等
	 */
	@Basic
	@Column(length = 40)
	private String description;
	
	/**
	 * 当前状态。0 不可用 1 可用
	 */
	@Basic
    private int status;

    /**
     * 是否同步。同步的彩票，一旦下单成功就认为是出票成功。非同步的彩票，下单成功仅代表受理成功。
     */
	@Basic
    private boolean isSynchro;
	
	/**
	 * 单位为秒，后台用提前停售时间
	 */
	@Basic
	private int offset;
	
	/**
	 * 游戏类型，普通、高频、竞彩
	 */
	@Basic
	private int type = ConstantValues.Game_Type_Normal.getCode();
	
	@Transient
	private Map<Integer, GameGrade> gradeMap;
	
	@Transient
	private Term curTerm;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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

	public boolean isSynchro() {
		return isSynchro;
	}

	public void setSynchro(boolean isSynchro) {
		this.isSynchro = isSynchro;
	}

	public String getPublishDesc() {
		return publishDesc;
	}
	
	public void setPublishDesc(String publishDesc) {
		this.publishDesc = publishDesc;
	}
	
	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period = period;
	}
	
	/**
	 * 当前状态。0 不可用 1 可用
	 */
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<Integer, GameGrade> getGradeMap() {
		return gradeMap;
	}

	public void setGradeMap(Map<Integer, GameGrade> gradeMap) {
		this.gradeMap = gradeMap;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Term getCurTerm() {
		return curTerm;
	}

	public void setCurTerm(Term curTerm) {
		this.curTerm = curTerm;
	}
}
