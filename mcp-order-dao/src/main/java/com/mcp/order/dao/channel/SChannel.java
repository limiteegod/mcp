/**
 * 
 */
package com.mcp.order.dao.channel;

/**
 * @author ming.li
 */
public class SChannel {
	
	private String code;
	
	private long bigBonus;
	
	/**
	 * 算奖的参数类
	 */
	private String drawParam;
	
	/**
	 * 算奖的任务类
	 */
	private String drawJob;
	
	/**
	 * 返奖所用的bean的名称
	 */
	private String prizeBean;
	
	/**
	 * 返奖所用的任务的名称
	 */
	private String prizeJob;
	
	/**
	 * 投注时，是否检查余额足够支付
	 */
	private boolean checkBalanceAtBet;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getBigBonus() {
		return bigBonus;
	}

	public void setBigBonus(long bigBonus) {
		this.bigBonus = bigBonus;
	}

	public String getPrizeBean() {
		return prizeBean;
	}

	public void setPrizeBean(String prizeBean) {
		this.prizeBean = prizeBean;
	}

	public boolean isCheckBalanceAtBet() {
		return checkBalanceAtBet;
	}

	public void setCheckBalanceAtBet(boolean checkBalanceAtBet) {
		this.checkBalanceAtBet = checkBalanceAtBet;
	}

	public String getPrizeJob() {
		return prizeJob;
	}

	public void setPrizeJob(String prizeJob) {
		this.prizeJob = prizeJob;
	}

	public String getDrawParam() {
		return drawParam;
	}

	public void setDrawParam(String drawParam) {
		this.drawParam = drawParam;
	}

	public String getDrawJob() {
		return drawJob;
	}

	public void setDrawJob(String drawJob) {
		this.drawJob = drawJob;
	}
}
