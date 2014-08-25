package com.mcp.order.dao.specification;

import com.mcp.order.model.report.SaleReport;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SaleReportSpecification {

    public static Specification<SaleReport> isGameCodeEqual(final String gameCode) {
        return new Specification<SaleReport>() {
            @Override
            public Predicate toPredicate(Root<SaleReport> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("gameCode"), gameCode);
            }
        };
    }

    public static Specification<SaleReport> isTermCodeEqual(final String termCode) {
        return new Specification<SaleReport>() {
            @Override
            public Predicate toPredicate(Root<SaleReport> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("termCode"), termCode);
            }
        };
    }
    
    public static Specification<SaleReport> isChannelCodeEqual(final String channelCode) {
        return new Specification<SaleReport>() {
            @Override
            public Predicate toPredicate(Root<SaleReport> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("channelCode"), channelCode);
            }
        };
    }
    
    public static Specification<SaleReport> isScopeEqual(final int scope) {
        return new Specification<SaleReport>() {
            @Override
            public Predicate toPredicate(Root<SaleReport> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("scope"), scope);
            }
        };
    }
}
