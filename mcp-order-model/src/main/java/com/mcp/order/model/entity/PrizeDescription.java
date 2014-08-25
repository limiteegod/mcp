/**
 * 
 */
package com.mcp.order.model.entity;

import com.mcp.order.model.ts.GameGrade;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ming.li
 *
 */
public class PrizeDescription implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7213922014650916681L;

	/**
	 * 浮动奖，奖级的奖金描述
	 */
	private List<GameGrade> grades;
	
	@JsonIgnore
	private Map<String, GameGrade> gradeMap = new HashMap<String, GameGrade>();
	
	@JsonIgnore
	private Map<Integer, GameGrade> gradeLevelMap = new HashMap<Integer, GameGrade>();
	
	/**
	 * 存储了，场次玩法的开奖号码
	 */
	@JsonIgnore
	private Map<String, String> drawMap = new HashMap<String, String>();
	
	/**
	 * 存储了，场次的开奖号码
	 */
	@JsonIgnore
	private Map<String, String> matchMap = new HashMap<String, String>();

	public List<GameGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<GameGrade> grades) {
		this.grades = grades;
		for(int i = 0; i < grades.size(); i++)
		{
			GameGrade gg = grades.get(i);
			gradeMap.put(gg.getCode(), gg);
			gradeLevelMap.put(gg.getgLevel(), gg);
		}
	}
	
	public GameGrade getGradeByCode(String code)
	{
		return gradeMap.get(code);
	}
	
	public GameGrade getGradeByLevel(int level)
	{
		return gradeLevelMap.get(level);
	}
	
	/**
	 * 放置竞彩开奖号码
	 * @param matchCode 场次号
	 * @param drawNumber 开奖号码
	 */
	public void putJcDrawNumber(String matchCode, String drawNumber)
	{
		this.matchMap.put(matchCode, drawNumber);
		
		String[] dnArray = drawNumber.split(";");
		for(int p = 0; p < dnArray.length; p++)
		{
			String[] detailDnArray = dnArray[p].split("\\|");
			this.drawMap.put(matchCode + detailDnArray[0], detailDnArray[1]);
		}
	}
	
	/**
	 * 获得竞彩的开奖号码
	 * @param key matchCode+playTypeCode
	 * @return
	 */
	public String getJcDrawNumber(String key)
	{
		return this.drawMap.get(key);
	}
	
	/**
	 * 是否有竞彩开奖号码
	 * @param matchCode 检查是否有开奖号码的场次号
	 * @return
	 */
	public boolean hasJcDrawNumber(String matchCode)
	{
		return this.matchMap.containsKey(matchCode);
	}
}
