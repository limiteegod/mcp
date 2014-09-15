package com.mcp.order.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.CustomerAccountDao;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.finance.AccountConstants;
import com.mcp.order.dao.finance.AccountOperator;
import com.mcp.order.dao.finance.AccountOperatorType;
import com.mcp.order.dao.finance.MoneyHandler;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.model.ts.MoneyLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 扣款在一个事务里发生的时候，扣款顺序，彩民，投注站，渠道，销售机构，出票中心,平台
 * 转票所产生的station之间账户记录的操作，请按以下原则:先处理销售方的账户记录，再处理出票方的账户记录
 * @author ming.li
 */
@Service("moneyService")
public class MoneyService {
	
	private static Logger log = Logger.getLogger(MoneyService.class);
	
	@Autowired
	private CustomerAccountDao customerAccountDao;

    @Autowired
    private MoneyLogDao moneyLogDao;
	
	/**
	 * 代理商投注，代理商扣款
	 */
	/*@Autowired
    private MoneyHandler moneyHandlerRC00102;*/
	
	/**
	 * 代理商投注，投注站收款
	 */
	/*@Autowired
    private MoneyHandler moneyHandlerRS00003;*/
	
	/**
	 * 彩民给现金账户充值
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU00000;
	
	/**
	 * 彩民账户提现
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU00101;
	
	/**
	 * 彩民在投注站购买彩票
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU00100;
	
	/**
	 * 彩民收入，投注站退款
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU00001;
	
	/**
	 * 投注站奖金收入
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00005;
	
	/**
	 * 投注站结算账户支出，提款
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00100;
	
	/**
	 * 投注站结算账户收入，充值
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00001;
	
	/**
	 * 彩民收入，投注站返奖 
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU00002;
	
	
	/**
	 * 渠道商预冲额度
	 */
	/*@Autowired
    private MoneyHandler moneyHandlerRC00000;*/
	
	/**
	 * 渠道商提款
	 */
	/*@Autowired
    private MoneyHandler moneyHandlerRC00100;*/
	
	/**
	 * 平台结算账户，借款 
	 */
	@Autowired
    private MoneyHandler moneyHandlerRP00000;
	
	/**
	 * 平台结算账户，提款
	 */
	@Autowired
    private MoneyHandler moneyHandlerRP00100;
	
	/**
	 * 平台结算账户收入，代理商支付业务费用 
	 */
	@Autowired
    private MoneyHandler moneyHandlerRP00001;
	
	/**
	 * 活动支出
	 */
	@Autowired
    private MoneyHandler moneyHandlerRP00104;
	
	/**
	 * 平台结算账户收入，投注站支付业务费用 
	 */
	@Autowired
    private MoneyHandler moneyHandlerRP00002;
	
	/**
	 * 投注站结算账户支出，支付业务费用
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00101;
	
	/**
	 * 平台结算账户支出，返点代理商
	 */
	@Autowired
    private MoneyHandler moneyHandlerRP00101;
	
	/**
	 * 代理商结算账户收入，返点
	 */
	/*@Autowired
    private MoneyHandler moneyHandlerRC00001;*/
	
	/**
	 *  投注站结算账户支出，退款给代理商，返奖给代理商
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00102;
	
	/**
	 * 代理商结算账户收入，投注站退款，投注站返奖
	 */
	/*@Autowired
    private MoneyHandler moneyHandlerRC00002;*/
	
	/**
	 * 投注站结算账户收入，平台返点
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00002;
	
	/**
	 * 出票中心出票收入
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00003;
	
	/**
	 * 彩民充值，通过网银、支付宝等第三方渠道
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU01000;
	
	/**
	 * 彩民取款
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU01101;
	
	/**
	 * 投注站结算账户，退款收入
	 */
	@Autowired
    private MoneyHandler moneyHandlerRS00000;
	
	/**
	 * 彩民平台业务账户，第三方投注支出
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU01100;
	
	/**
	 * 投注站返奖到彩民的奖金账户
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU02000;
	
	/**
	 * 彩民奖金账户支出（现金提款、转投注金）
	 */
	@Autowired
    private MoneyHandler moneyHandlerRU02100;
	
	/**
     * 彩民取款（退款给彩民）
     */
    @Transactional
    public void platFormRefundToCustomer(String customerId, String orderId, long amount) {
    	String op = "RU0110100";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	try {
			moneyHandlerRU01101.handle(null, customerId, "", "", "", amount, orderId, type);
		} catch (Exception e) {
			throw new CoreException(ErrCode.E1022, ErrCode.codeToMsg(ErrCode.E1022));
		}
    }
    
	/**
     * 彩民充值，通过网银
     */
    /*@Transactional
    public void customerRechargeByBank(String customerId, String fromFlag, String orderId, long amount) {
    	String op = "RU0100000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	try {
			moneyHandlerRU01000.handle(null, "", fromFlag, customerId, "", amount, orderId, type);
		} catch (Exception e) {
			throw new CoreException(ErrCode.E1022, ErrCode.codeToMsg(ErrCode.E1022));
		}
    }*/
    
    /**
     * 彩民充值，通过支付宝
     */
    @Transactional
    public void customerRechargeByAlipay(String customerId, String fromFlag, String orderId, long amount) {
    	String op = "RU0100001";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	try {
			moneyHandlerRU01000.handle(null, "", fromFlag, customerId, "", amount, orderId, type);
		} catch (Exception e) {
			throw new CoreException(ErrCode.E1022, ErrCode.codeToMsg(ErrCode.E1022));
		}
    }
    
    /**
     * 彩民给现金账户充值
     */
    @Transactional
    public MoneyLog customerRecharge(String stationId, String customerId, String orderId, long amount, int fromType) {
    	String op;
    	if(fromType == ConstantValues.Recharge_Channel_Hand.getCode())
    	{
    		op = "RU0000000";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_Prize.getCode())
    	{
    		op = "RU0000001";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_Alipay.getCode())
    	{
    		op = "RU0000002";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_UnionPay.getCode())
    	{
    		op = "RU0000003";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_BOC.getCode())
    	{
    		op = "RU0000004";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_CCB.getCode())
    	{
    		op = "RU0000005";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_CMBC.getCode())
    	{
    		op = "RU0000006";
    	}
    	else if(fromType == ConstantValues.Recharge_Channel_CEB.getCode())
    	{
    		op = "RU0000007";
    	}
        else if(fromType == ConstantValues.Recharge_Channel_XB.getCode())
        {
            op = "RU0000008";
        }
    	else 
    	{
    		throw new CoreException(ErrCode.E1022, ErrCode.codeToMsg(ErrCode.E1022));
    	}
        MoneyLog moneyLog = this.moneyLogDao.findOneByOperationCodeAndOrderId(op, orderId);
        if(moneyLog != null)
        {
            throw new CoreException(ErrCode.E1030, ErrCode.codeToMsg(ErrCode.E1030));
        }
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
    	try {
			 return moneyHandlerRU00000.handle(null, customerId, "", ca.getId(), "", amount, orderId, type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException(ErrCode.E1022, ErrCode.codeToMsg(ErrCode.E1022));
		}
    }
    
    /**
     * 彩民在投注站提现
     */
    @Transactional
    public void customerWithdrawAtStation(Station station, String customerId, String orderId, long amount) {
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType("RU0010100");
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, station.getId());
    	try {
			moneyHandlerRU00101.handle(station, ca.getId(), "", customerId, "", amount, orderId, type);
		} catch (Exception e) {
			throw new CoreException(ErrCode.E1022, ErrCode.codeToMsg(ErrCode.E1022));
		}
    }

    /**
     * 用户使用现金账户购买彩票，对彩民的现金账户直接扣款即可
     */
    @Transactional
    public List<MoneyLog> customerBuyLot(Customer user, CustomerAccount ca, String orderId, long amount, String fromFlag, String toFlag) {
    	List<MoneyLog> logList = new ArrayList<MoneyLog>();
    	String op = "RU0010000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	log.info("投注站购买彩票：" + type.getType() + "，name：" + type.getName());
    	logList.add(moneyHandlerRU00100.handle(user, ca.getId(), fromFlag, ca.getStationId(), toFlag, amount, orderId, type));
    	return logList;
    }
    
    /**
     * 投注站因出票失败，退款到彩民的现金账户
     * @param station
     * @param customerId
     * @param orderId
     * @param amount
     */
    @Transactional
    public void refundToCustomer(String stationId, String customerId, String orderId, String fromFlag, long amount)
    {
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
    	String op = "RU0000100";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRU00001.handle(null, stationId, fromFlag, ca.getId(), "", amount, orderId, type);
    }

    /**
     * 合买撤销，退款到彩民的现金账户
     * @param stationId
     * @param customerId
     * @param orderId
     * @param fromFlag
     * @param amount
     */
    @Transactional
    public void refundToCustomerWhenCancelHm(String stationId, String customerId, String orderId, String fromFlag, long amount)
    {
        CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
        String op = "RU0000102";
        AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
        moneyHandlerRU00001.handle(null, stationId, fromFlag, ca.getId(), "", amount, orderId, type);
    }
    
    /**
     * 投注站因出票失败，退款到彩民的现金账户
     * @param station
     * @param customerId
     * @param orderId
     * @param amount
     */
    @Transactional
    public void customerCancelSchemeZh(String stationId, String customerId, String orderId, long amount)
    {
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
    	String op = "RU0000101";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRU00001.handle(null, stationId, "", ca.getId(), "", amount, orderId, type);
    }
    
    /**
     * 支付中奖票的奖金
     * @param stationId
     * @param orderId
     * @param amount
     */
    @Transactional
    public void stationReward(String stationId, String orderId, long amount)
    {
    	String op = "RS0010200";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00102.handle(null, stationId, "", "", "", amount, orderId, type);
    }
    
    /**
     * 渠道投注
     * @param stationId
     * @param orderId
     * @param amount
     */
    @Transactional
    public MoneyLog stationLot(String stationId, String orderId, long amount)
    {
    	String op = "RS0010202";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	return moneyHandlerRS00102.handle(null, stationId, "", "", "", amount, orderId, type);
    }
    
    /**
     * 投注站因出票失败，退款到代理商
     * @param station
     * @param customerId
     * @param orderId
     * @param amount
     */
   /* @Transactional
    public void stationRewardToChannel(String stationId, String channelId, String orderId, long amount)
    {
    	String op = "RS0010200";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00102.handle(null, stationId, "", channelId, "", amount, orderId, type);
    	
    	op = "RS0110001";
    	type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS01100.handle(null, stationId, "", channelId, "", amount, orderId, type);
    	
    	op = "RC0000200";
    	type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRC00002.handle(null, stationId, "", channelId, "", amount, orderId, type);
    }*/
    
    /**
     * 给彩民返奖
     * @param stationId
     * @param customerId
     * @param orderId
     * @param amount
     */
    @Transactional
    public void rewardToCustomer(String stationId, String customerId, String orderId, long amount, String fromFlag)
    {
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
    	String op = "RU0200000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRU02000.handle(null, fromFlag, "", ca.getId(), "", amount, orderId, type);
    }
    
    /**
     * 给机构返奖
     * @param stationId
     * @param orderId
     * @param amount
     */
    @Transactional
    public void rewardToStation(String stationId,  String orderId, long amount, String fromFlag)
    {
    	String op = "RS0000500";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00005.handle(null, fromFlag, "", stationId, "", amount, orderId, type);
    }
    
    /**
     * 彩民奖金转投注金
     * @param stationId
     * @param customerId
     * @param orderId
     * @param amount
     */
    @Transactional
    public List<MoneyLog> customerPrizeToRecharge(Customer customer, String stationId, String customerId, String orderId, long amount)
    {
    	List<MoneyLog> logList = new ArrayList<MoneyLog>();
    	
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
    	String op = "RU0000001";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	logList.add(moneyHandlerRU00000.handle(customer, ca.getId(), "", ca.getId(), "", amount, orderId, type));
    	
    	op = "RU0210001";
    	type = AccountOperator.getInstance().getAccountOperatorType(op);
    	logList.add(moneyHandlerRU02100.handle(customer, ca.getId(), "", ca.getId(), "", amount, orderId, type));
    	
    	return logList;
    }
    
    /**
     * 彩民从奖金账户提现
     * @param stationId
     * @param customerId
     * @param orderId
     * @param amount
     */
    @Transactional
    public List<MoneyLog> customerPrizeToOut(Customer customer, String stationId, String customerId, String orderId, long amount)
    {
    	List<MoneyLog> logList = new ArrayList<MoneyLog>();
    	
    	CustomerAccount ca = customerAccountDao.findOneByCustomerIdAndStationId(customerId, stationId);
    	String op = "RU0210000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	logList.add(moneyHandlerRU02100.handle(customer, ca.getId(), "", ca.getId(), "", amount, orderId, type));
    	
    	return logList;
    }
    
    
    /**
     * 渠道商预冲额度
     * @param station
     * @param orderId
     * @param amount
     */
    /*@Transactional
    public void channelRecharge(Channel channel, String orderId, long amount)
    {
    	String op = "RC0000000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRC00000.handle(channel, "", "", channel.getId(), "", amount, orderId, type);
    }*/
    
    /**
     * 渠道商提款
     * @param station
     * @param orderId
     * @param amount
     */
    /*@Transactional
    public void channelWithdraw(Channel channel, String orderId, long amount)
    {
    	String op = "RC0010000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRC00100.handle(channel, channel.getId(), "", "", "", amount, orderId, type);
    }*/
    
    /**
     * 平台业务账户，借款
     * @param orderId
     * @param amount
     */
    @Transactional
    public void platFormRecharge(String orderId, long amount)
    {
    	String op = "RP0000000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRP00000.handle(null, "", "", AccountConstants.CPLATFORM_BALANCE_ID, "", amount, orderId, type);
    }
    
    /**
     * 投注站结算账户收入，充值
     * @param orderId
     * @param amount
     */
    @Transactional
    public void stationRecharge(String stationId, String orderId, long amount)
    {
    	String op = "RS0000100";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00001.handle(null, "", "", stationId, "", amount, orderId, type);
    }
    
    /**
     * 投注站结算账户支出，提款
     * @param orderId
     * @param amount
     */
    @Transactional
    public void stationWithdraw(String stationId, String orderId, long amount)
    {
    	String op = "RS0010000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00100.handle(null, stationId, "", "", "", amount, orderId, type);
    }
    
    /**
     * 平台业务账户，提款
     * @param orderId
     * @param amount
     */
    @Transactional
    public void platFormWithdraw(String orderId, long amount)
    {
    	String op = "RP0010000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRP00100.handle(null, AccountConstants.CPLATFORM_BALANCE_ID, "", "", "", amount, orderId, type);
    }
    
    /**
     * 平台在销售彩票中的提成
     * @param orderId
     * @param amount
     */
    @Transactional
    public void platFormSalePercentage(String orderId, long amount)
    {
    	String op = "RP0000104";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRP00001.handle(null, "", "", AccountConstants.CPLATFORM_BALANCE_ID, "", amount, orderId, type);
    }

    /**
     * 平台支出推广费用
     * @param orderId
     * @param amount
     * @param flag
     */
    @Transactional
    public void platFormPayPromotion(String orderId, long amount, String flag)
    {
        String op = "RP0010400";
        AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
        moneyHandlerRP00104.handle(null, AccountConstants.CPLATFORM_BALANCE_ID, "", "", flag, amount, orderId, type);
    }
    
    /**
     * 投注站支付业务费用给平台结算账户
     * @param channel
     * @param orderId
     * @param amount
     */
    @Transactional
    public void stationPayCommission(Station station, String orderId, long amount)
    {
    	String op = "RS0010100";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00101.handle(null, station.getId(), "", AccountConstants.CPLATFORM_BALANCE_ID, "", amount, orderId, type);
    	
    	op = "RP0000200";
    	type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRP00002.handle(null, station.getId(), "", AccountConstants.CPLATFORM_BALANCE_ID, "", amount, orderId, type);
    }
    
    /**
     * 平台结算账户返点给代理商
     * @param channel
     * @param orderId
     * @param amount
     */
    /*@Transactional
    public void platFormPayCommissionToChannel(Channel channel, String orderId, long amount)
    {
    	String op = "RC0000100";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRC00001.handle(null, AccountConstants.CPLATFORM_BALANCE_ID, "", channel.getId(), "", amount, orderId, type);
    	
    	op = "RP0010100";
    	type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRP00101.handle(null, AccountConstants.CPLATFORM_BALANCE_ID, "", channel.getId(), "", amount, orderId, type);
    }*/
    
    /**
     * 出票成功，出票中心收取出票费
     * @param printStationId 出票成功的出票中心的id
     * @param orderId 订单的id
     * @param amount 出票收入的金额
     */
    @Transactional
    public void orderPrintSuccess(String printStationId, String orderId, long amount)
    {
    	String op = "RS0000300";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00003.handle(null, "", "", printStationId, "", amount, orderId, type);
    }
    
    /**
     * 出票成功，销售方收取提成费用
     * @param stationId 出票成功的出票中心的id
     * @param orderId 订单的id
     * @param amount 出票收入的金额
     */
    @Transactional
    public void orderPrintSuccessSalePercentage(String stationId, String orderId, long amount)
    {
    	String op = "RS0000301";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
    	moneyHandlerRS00003.handle(null, "", "", stationId, "", amount, orderId, type);
    }
    
    /**
     * 彩民通过第三方支付购买彩票之后，出票完成时调用此方法，完成交易。
     * @param user
     * @param station
     * @param orderId
     * @param amount
     * @param schemeType
     */
    @Transactional
    public void refundToStation(String stationId, String orderId, String ticketId, long amount)
    {
    	String op = "RS0000000";
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType(op);
		moneyHandlerRS00000.handle(null, "", "", stationId, ticketId, amount, orderId, type);
    }
    
    /**
     * 代销商在投注站购买彩票，需记录两笔日志记录，代销商扣款，投注站收款
     * @param cha
     * @param stationId
     * @param orderId
     * @param amount
     * @param schemeType
     * @return
     * @throws CoreException
     */
    /*@Transactional
    public List<MoneyLog> channelBuyLot(Channel cha, String stationId, String orderId, long amount, int schemeType) throws CoreException
    {
    	List<MoneyLog> logList = new ArrayList<MoneyLog>();
    	
    	AccountOperatorType type = AccountOperator.getInstance().getAccountOperatorType("RS0000300");
		logList.add(moneyHandlerRS00003.handle(cha, cha.getId(), "", stationId, "", amount, orderId, type));
		
    	String op = "RC0010200";
    	type = AccountOperator.getInstance().getAccountOperatorType(op);
    	logList.add(moneyHandlerRC00102.handle(cha, cha.getId(), "", stationId, "", amount, orderId, type));
		
    	return logList;
    }*/
}
