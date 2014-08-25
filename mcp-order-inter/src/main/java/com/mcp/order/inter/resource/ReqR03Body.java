package com.mcp.order.inter.resource;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqR03Body extends ReqBody {

	/**
	 * 卡号
	 */
	private String cardNumber;

	/**
	 * 银行代码
	 */
	private String bankNo;

	/**
	 * 省级代码
	 */
	private String provinceNo;

	/**
	 * 市级代码
	 */
	private String cityNo;

	/**
	 * 区县级代码
	 */
	private String areaNo;
	
	/**
	 * 绑定银卡是否唯一
	 */
	private boolean unique;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
