package com.mcp.order.inter.account;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.model.ts.Payment;

import java.util.List;

public class RepA02Body extends RepBody {
	
	private String customerId;
	
	private String realName;
	
	private String identityId;
	
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private List<CustomerAccount> accounts;
	
	private List<Payment> payments;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public List<CustomerAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<CustomerAccount> accounts) {
		this.accounts = accounts;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
}
