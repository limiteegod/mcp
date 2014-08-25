/**
 * 
 */
package com.mcp.order.dao.finance;

/**
 * @author ming.li
 *
 */
public class AccountConstants {
	/**
	 * 系统现金账户暂时使用的id
	 */
	public static final String SYSTEM_ACCOUNT_ID = "505104caff6145eb92d23522e4f850ed";
	
	/**
	 * 系统业务账户暂时使用的id
	 */
	public static final String SYSTEM_BUSSINESS_ACCOUNT_ID = "6f152f4a082441369f58ce62649e2fe8";
	
	/**
	 * 系统（虚拟的用户）id
	 */
	public static final String SYSTEM_USER_ID = "505104caff6145eb92d23522e4f850ed";
	
	
	/**
	 * 第三方支付类型，银行
	 */
	public static final int THIRD_PARTY_TYPE_BANK = 1;
	
	/**
	 * 第三方支付类型，支付宝
	 */
	public static final int THIRD_PARTY_TYPE_ALIPAY = 2;
	
	/**
	 * 平台结算账户的key
	 */
	public static final String CPLATFORM_BALANCE_KEY = "system:balance";
	
	/**
	 * 平台结算账户的id
	 */
	public static final String CPLATFORM_BALANCE_ID = "a72e300189124cb287d0ec637fbe5ac0";
}
