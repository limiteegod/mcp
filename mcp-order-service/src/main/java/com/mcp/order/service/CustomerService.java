/**
 *
 */
package com.mcp.order.service;

import com.mcp.core.util.MD5Util;
import com.mcp.core.util.StringUtil;
import com.mcp.order.dao.CustomerDao;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.ts.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ming.li
 */
@Service("customerService")
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer save(Customer customer) {
        return this.customerDao.save(customer);
    }

    @Transactional
    public void delete(String id) {
        this.customerDao.delete(id);
    }

    public Customer findOne(String id) {
        return this.customerDao.findOne(id);
    }

    public Customer findOneByIdentityId(String identityId) {
        return this.customerDao.findOneByIdentityId(identityId);
    }

    public Customer findOneByNameAndChannelCode(String name, String channelCode) {
        return this.customerDao.findOneByNameAndChannelCode(name, channelCode);
    }
    
    public Customer findOneByPhoneAndChannelCode(String phone, String channelCode)
    {
    	return this.customerDao.findOneByPhoneAndChannelCode(phone, channelCode);
    }

    public Customer findOneByEmail(String email) {
        return this.customerDao.findOneByEmail(email);
    }

    /**
     * 修改帐户密码
     *
     * @param id
     * @param password
     * @param newPassword
     */
    @Transactional
    public void modifyPassword(String id, String password, String newPassword) {
        Customer cus = this.customerDao.findOne(id);
        if (cus.getPassword().endsWith(password)) {
            cus.setPassword(newPassword);
        } else {
            throw new CoreException(ErrCode.E1004, ErrCode.codeToMsg(ErrCode.E1004));
        }
    }

    @Transactional
    public void updatePasswordById(String password, String id)
    {
        this.customerDao.updatePasswordById(password, id);
    }

    /**
     * 用户更新自己在基本信息
     *
     * @param nickyName
     * @param phone
     * @param email
     * @param realName
     * @param identityId
     * @param id
     * @return
     */
    @Transactional
    public void updateInfoById(String nickyName, String phone, String email, String realName, String identityId, String id) {
        Customer cus = this.customerDao.findOne(id);
        if(!StringUtil.isEmpty(nickyName))
        {
        	cus.setNickyName(nickyName);
        }
        if(!StringUtil.isEmpty(phone))
        {
        	cus.setPhone(phone);
        }
        if(!StringUtil.isEmpty(email))
        {
        	cus.setEmail(email);
        }
        if(!StringUtil.isEmpty(realName))
        {
        	cus.setRealName(realName);
        }
        if(!StringUtil.isEmpty(identityId))
        {
        	cus.setIdentityId(identityId);
        }
    }

    public List<Customer> findAll() {
        return this.customerDao.findAll();
    }
    
    public Page<Customer> findAll(Specifications<Customer> specs, Pageable p) {
        return this.customerDao.findAll(specs, p);
    }

    public List<Customer> findAllByNameLike(String userName) {
        return this.customerDao.findAllByNameLike("%"+userName+"%");
    }

    public List<Customer> findAllByPhoneLike(String phone) {
        return this.customerDao.findAllByPhoneLike("%"+phone+"%");
    }

    /**
     * 绑定手机号码
     * @param channelCode
     * @param name
     * @param phone
     */
    @Transactional
    public void bindPhone(String channelCode, String name, String phone) {
    	Customer customer = this.customerDao.findOneByNameAndChannelCode(name, channelCode);
    	customer.setPhone(phone);
    }
}
