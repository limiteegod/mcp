/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.DesUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.common.RedisKey;
import com.mcp.order.dao.redis.RedisHelp;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.gateway.util.CookieUtil;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.account.*;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgLogin;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.CustomerAccount;
import com.mcp.order.mongo.service.MgLoginService;
import com.mcp.order.service.CustomerAccountService;
import com.mcp.order.service.CustomerService;
import com.mcp.order.service.PaymentService;
import com.mcp.order.service.StationService;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/account")
public class AccountControl {
	
	private static Logger log = Logger.getLogger(AccountControl.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private StationService stationService;

    @Autowired
    private MgLoginService mgLoginService;
	
	/**
	 * A01用户注册
	 */
	@RequestMapping(value = "/register.htm")
	public String add(@JsonHead(value="head", checkChannel=true) Head head, @JsonBody(value="body", cmd="A01") ReqA01Body body,
			ModelMap modelMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		Customer cus = body.getCustomer();
		//验证用户名的唯一性
		Customer cusInDb = customerService.findOneByNameAndChannelCode(cus.getName(), head.getChannelCode());
		if(cusInDb != null)
		{   
			throw new CoreException(ErrCode.E1005, ErrCode.codeToMsg(ErrCode.E1005));
		}
		if(!StringUtil.isEmpty(cus.getPhone()))
		{
			cusInDb = customerService.findOneByPhoneAndChannelCode(cus.getPhone(), head.getChannelCode());
			if(cusInDb != null)
			{   
				throw new CoreException(ErrCode.E1005, ErrCode.codeToMsg(ErrCode.E1005));
			}
		}
		cus.setId(CoreUtil.getUUID());
		cus.setEmail("");
		Date now = new Date();
		cus.setRegDate(now);
		String pwd = cus.getPassword();
		cus.setPassword(pwd);
		cus.setLastActiveTime(now);
		cus.setRecharge(0);
		cus.setIntegral(0);
		cus.setLastLoginTime(now);
		cus.setStatus(ConstantValues.Customer_Status_Open.getCode());
		cus.setChannelCode(head.getChannelCode());
		Customer customerAfterSave = customerService.save(cus);
		
		String stationCode = head.getChannelCode();
		Station station = stationService.findOneByCode(stationCode);
		String stationId = null;
		if(station.getStationType() == ConstantValues.Station_StationType_CHANNEL.getCode())
		{
			stationId = station.getId();
		}
		else if(station.getStationType() == ConstantValues.Station_StationType_DEFAULT.getCode())
		{
			stationId = body.getStationId();
		}
		
		//如果stationid不为空，则绑定投注站
		if(!StringUtil.isEmpty(stationId))
		{
			CustomerAccount ca = new CustomerAccount();
			ca.setId(CoreUtil.getUUID());
			ca.setCustomerId(cus.getId());
			ca.setIntegral(0);
			ca.setPrize(0);
			ca.setRecharge(0);
			ca.setStationId(stationId);
			ca.setStatus(ConstantValues.CustomerAccount_Status_Open.getCode());
			customerAccountService.save(ca);
		}
		
		RepA01Body rep = new RepA01Body();
		customerAfterSave.setPassword(pwd);
		rep.setCustomer(customerAfterSave);

        String st = DesUtil.getKey(8);
        MgLogin mgLogin = new MgLogin();
        mgLogin.setId(customerAfterSave.getId());
        mgLogin.setSt(st);
        mgLogin.setLastActiveTime(now);
        this.mgLoginService.save(mgLogin);

        //设置到返回信息
        rep.setSt(st);

		modelMap.put("response", rep);
		return "plainJsonView";
	}
	
	/**
	 * A02用户查询
	 */
	@RequestMapping(value = "/query.htm")
	public String query(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="A02") ReqA02Body body, ModelMap modelMap) throws Exception {
		RepA02Body repBody = new RepA02Body();
		repBody.setCustomerId(customer.getId());
		repBody.setIdentityId(customer.getIdentityId());
		repBody.setRealName(customer.getRealName());
		repBody.setPhone(customer.getPhone());
		List<CustomerAccount> accountList = customerAccountService.findAllByCustomerId(customer.getId());
		for(int i = 0; i < accountList.size(); i++)
		{
			CustomerAccount account = accountList.get(i);
			account.setStation(stationService.findOne(account.getStationId()));
		}
		repBody.setAccounts(accountList);
		repBody.setPayments(paymentService.findAllByCustomerId(customer.getId()));
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	//登录
	@RequestMapping(value = "/login.htm")
	public String login(@JsonHead(value="head",checkChannel=true) Head head, @JsonBody(value="body", cmd="A04") ReqA04Body body, ModelMap modelMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		Customer cus;
		if(StringUtil.isEmpty(body.getName()))
		{
			cus = customerService.findOneByPhoneAndChannelCode(body.getPhone(), head.getChannelCode());
		}
		else
		{
			cus = customerService.findOneByNameAndChannelCode(body.getName(), head.getChannelCode());
		}
		RepA04Body repBody = new RepA04Body();
		if(cus == null)
		{
			repBody.setStatus(false);
			repBody.setRepCode(ErrCode.E1003);
			repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1003));
		}
		else
		{
			String uPwd = body.getPassword();
			String pwd = cus.getPassword();
			log.info("sPwd:" + pwd + ",pwd:" + pwd);
			if(uPwd.endsWith(pwd))
			{
				repBody.setStatus(true);
                Date now = new Date();
                MgLogin lastMgLogin = this.mgLoginService.findById(cus.getId());
                String st = null;
                if(lastMgLogin != null)
                {
                    Date lastActiveTime  =lastMgLogin.getLastActiveTime();
                    if(now.getTime() - lastActiveTime.getTime() < Constants.LOGIN_EXPIRE_MILISECOND)
                    {
                        st = lastMgLogin.getSt();
                    }
                }
                if(StringUtil.isEmpty(st))
                {
                    st = DesUtil.getKey(8);
                    MgLogin mgLogin = new MgLogin();
                    mgLogin.setId(cus.getId());
                    mgLogin.setSt(st);
                    mgLogin.setLastActiveTime(now);
                    this.mgLoginService.save(mgLogin);
                }
                repBody.setSt(st);
                repBody.setCustomer(cus);
			}
			else
			{
				repBody.setStatus(false);
				repBody.setRepCode(ErrCode.E1004);
				repBody.setDescription(ErrCode.codeToMsg(ErrCode.E1004));
			}
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 彩民修改自己的密码
	 * @param head
	 * @param body
	 * @param customer
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyPassword.htm")
	public String modifyPassword(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="A03") ReqA03Body body, ModelMap modelMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		RepA03Body repBody = new RepA03Body();
		
		String uPwd = body.getPassword();
		String sPwd = customer.getPassword();
		if(sPwd.endsWith(uPwd))
		{
			this.customerService.updatePasswordById(body.getNewPassword(), customer.getId());
		}
		else
		{
			throw new CoreException(ErrCode.E1004, ErrCode.codeToMsg(ErrCode.E1004));
		}
		repBody.setStatus(true);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 彩民修改一些自身的基本信息
	 * @param head
	 * @param body
	 * @param customer
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyInfo.htm")
	public String modifyInfo(@JsonHead(value="head",checkChannel=false) Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="A05") ReqA05Body body, ModelMap modelMap) throws Exception {
		RepA05Body repBody = new RepA05Body();
		String nickyName = body.getNickyName();
		String phone = body.getPhone();
		String email = body.getEmail();
		String realName = body.getRealName();
		String identityId = body.getIdentityId();
		if(!StringUtil.isEmpty(phone))
		{
			Customer t = customerService.findOneByPhoneAndChannelCode(phone, customer.getChannelCode());
			if(t != null && !t.getId().equals(customer.getId()))
			{
				throw new CoreException(ErrCode.E1005, ErrCode.codeToMsg(ErrCode.E1005));
			}
			/*if(body.isNeedAuthCode())	//如果需要验证码，则校验验证码是否正确
			{
				String authCode = redisHelp.get(RedisKey.getPhoneAuthCode(phone));
				if(authCode == null || body.getAuthCode() == null || !authCode.equals(body.getAuthCode()))
				{
					throw new CoreException(ErrCode.E1012, ErrCode.codeToMsg(ErrCode.E1012));
				}
			}*/
		}
		if(!StringUtil.isEmpty(identityId))
		{
			Customer t = customerService.findOneByIdentityId(identityId);
			if(t != null && !t.getId().equals(customer.getId()))
			{
				throw new CoreException(ErrCode.E1019, ErrCode.codeToMsg(ErrCode.E1019));
			}
		}
		customerService.updateInfoById(nickyName, phone, email, realName, identityId, customer.getId());
		
		//填了银行卡，则绑定银行卡
		String cardNumber = body.getCardNumber();
		if(!StringUtil.isEmpty(cardNumber))
		{
			this.paymentService.updateFirstCardNoByCustomerId(customer.getId(), cardNumber);
		}
		
		repBody.setStatus(true);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 渠道查询接口
	 * @param head
	 * @param body
	 * @param channel
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/channelQuery.htm")
	public String channelQuery(@JsonHead(value="head",checkChannel=false) Head head, @McpStation Station channel,
                               @JsonBody(value="body", cmd="A07") ReqA07Body body,
			 ModelMap modelMap) throws Exception {
		RepA07Body repBody = new RepA07Body();
		repBody.setCode(channel.getCode());
		repBody.setName(channel.getName());
		repBody.setStatus(channel.getStatus());
		repBody.setBalance(channel.getBalance());
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
}
