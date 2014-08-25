/**
 *
 */
package com.mcp.order.service;

import com.mcp.order.dao.CouponDao;
import com.mcp.order.dao.WithDrawDao;
import com.mcp.order.dao.specification.CouponSpecification;
import com.mcp.order.dao.specification.TicketSpecification;
import com.mcp.order.model.report.Coupon;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.model.ts.WithDraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming.li
 */
@Service("couponService")
public class CouponService {

	@Autowired
	private CouponDao couponDao;
	
	public void save(Coupon coupon)
	{
        this.couponDao.save(coupon);
	}

    public Page<Coupon> findByCustomerAndStatus(Customer customer, int status, Pageable page)
    {
        Specifications<Coupon> specs = Specifications.where(CouponSpecification.isCustomerIdEqual(customer.getId()));
        if(status > -1)
        {
            specs = specs.and(CouponSpecification.isStatusEqual(status));
        }
        return this.couponDao.findAll(specs, page);
    }

}
