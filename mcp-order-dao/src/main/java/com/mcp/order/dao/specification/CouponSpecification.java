package com.mcp.order.dao.specification;

import com.mcp.order.model.report.Coupon;
import com.mcp.order.model.ts.TOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

public class CouponSpecification {

    public static Specification<Coupon> isIdEqual(final String id) {
        return new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("id"), id);
            }
        };
    }

    public static Specification<Coupon> isStatusNotEqual(final int status) {
        return new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get("status"), status);
            }
        };
    }

    public static Specification<Coupon> isStatusEqual(final int status) {
        return new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }

    public static Specification<Coupon> isCustomerIdEqual(final String customerId) {
        return new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerId"), customerId);
            }
        };
    }

    public static Specification<Coupon> isIdNotNull() {
        return new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNotNull(root.get("id"));
            }
        };
    }

}
