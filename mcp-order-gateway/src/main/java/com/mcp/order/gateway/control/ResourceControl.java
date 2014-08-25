/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.RedisKey;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.resource.*;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.Payment;
import com.mcp.order.service.CustomerService;
import com.mcp.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * 
 * @author ming.li
 */
@Controller
@RequestMapping("/resource")
public class ResourceControl {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PaymentService paymentService;
	
	/**
	 * 用户绑定手机号时，给用户手机发送验证码
	 * @param head
	 * @param body
	 * @param customer
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendShortMsg.htm")
	public String sendShortMsg(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="R01") ReqR01Body body, ModelMap modelMap) throws Exception {
		RepR01Body repBody = new RepR01Body();
		String phone = body.getPhone();
		
		String code = "123456";
		/*
		Random r = new Random();
		int code = r.nextInt(1000000);
		String.format("%1$06d", code);
		*/
		
		//TODO 发送验证码
		
		//redisHelp.set(RedisKey.getPhoneAuthCode(phone), code);
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 找回密码
	 * @param head
	 * @param body
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findBackPsd.htm")
	public String findBackPsd(@JsonHead("head") Head head, @JsonBody(value="body", cmd="R02") ReqR02Body body, ModelMap modelMap) throws Exception {
		RepR02Body repBody = new RepR02Body();
		
		String name = body.getName();
		String phone = body.getPhone();
		
		Customer customer = customerService.findOneByNameAndChannelCode(name, head.getChannelCode());
		if(customer == null || customer.getPhone() == null || !customer.getPhone().equals(phone))
		{
			throw new CoreException(ErrCode.E1003, ErrCode.codeToMsg(ErrCode.E1003));
		}
		customerService.updatePasswordById(body.getPassword(), customer.getId());
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	@RequestMapping(value = "/bindBank.htm")
	public String bindBank(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="R03") ReqR03Body body, ModelMap modelMap) throws Exception {
		RepR03Body repBody = new RepR03Body();
		
		if(body.isUnique())
		{
			List<Payment> payList = paymentService.findAllByCustomerId(customer.getId());
			if(payList.size() == 0)
			{
				Payment payment = new Payment();
				payment.setId(CoreUtil.getUUID());
				payment.setCustomerId(customer.getId());
				payment.setBankNo(body.getBankNo());
				payment.setProvinceNo(body.getProvinceNo());
				payment.setCityNo(body.getCityNo());
				payment.setAreaNo(body.getAreaNo());
				payment.setCardNumber(body.getCardNumber());
				paymentService.save(payment);
			}
			else
			{
				paymentService.updateByCustomerId(customer.getId(), body.getCardNumber(), body.getBankNo(), body.getProvinceNo(), body.getCityNo(), body.getAreaNo());
			}
		}
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 由九歌系统调用，绑定电话号码
	 * @param head
	 * @param body
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindPhone.htm")
	public String bindPhone(@JsonHead("head") Head head, @JsonBody(value="body", cmd="R04") ReqR04Body body, ModelMap modelMap) throws Exception {
		RepR04Body repBody = new RepR04Body();
		
		customerService.bindPhone(head.getChannelCode(), body.getName(), body.getPhone());
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
}
