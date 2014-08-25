/**
 * 
 */
package com.mcp.order.service;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.dao.PaymentDao;
import com.mcp.order.model.ts.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author ming.li
 *
 */
@Service("paymentService")
public class PaymentService {
	
	@Autowired
	private PaymentDao paymentDao;
	
	
	public Payment save(Payment payment)
	{
		return this.paymentDao.save(payment);
	}
	
	public List<Payment> findAllByCustomerId(String customerId)
	{
		return this.paymentDao.findAllByCustomerId(customerId);
	}
	
	public Payment findOneByCustomerId(String customerId)
	{
		return this.paymentDao.findOneByCustomerId(customerId);
	}
	
	
	/**
	 * 更新绑定的银行卡
	 * @return
	 */
	@Transactional
	public void updateByCustomerId(String customerId, String cardNumber, String bankNo, 
			String provinceNo, String cityNo, String areaNo)
	{
		List<Payment> payList = paymentDao.findAllByCustomerId(customerId);
		for(Payment payment:payList)
		{
			payment.setBankNo(bankNo);
			payment.setProvinceNo(provinceNo);
			payment.setCityNo(cityNo);
			payment.setAreaNo(areaNo);
			payment.setCardNumber(cardNumber);
		}
	}
	
	@Transactional
	public void updateFirstCardNoByCustomerId(String customerId, String cardNumber)
	{
		List<Payment> payList = paymentDao.findAllByCustomerId(customerId);
		if(payList.size() == 0)
		{
			Payment payment = new Payment();
			payment.setId(CoreUtil.getUUID());
			payment.setCustomerId(customerId);
			payment.setCardNumber(cardNumber);
			
			paymentDao.save(payment);
		}
		else
		{
			Payment payment = payList.get(0);
			payment.setCardNumber(cardNumber);
		}
	}
}
