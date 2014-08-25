/**
 *
 */
package com.mcp.order.dao;

import com.mcp.order.model.report.Coupon;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author ming.li
 */
public interface CouponDao extends PagingAndSortingRepository<Coupon, String>, JpaSpecificationExecutor<Coupon> {

}
