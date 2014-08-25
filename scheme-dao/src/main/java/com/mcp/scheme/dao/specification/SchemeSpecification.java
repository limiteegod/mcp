package com.mcp.scheme.dao.specification;

import com.mcp.scheme.model.SchemeZh;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SchemeSpecification {

    public static Specification<SchemeZh> isCustomerIdEqual(final String customerId) {
        return new Specification<SchemeZh>() {
            @Override
            public Predicate toPredicate(Root<SchemeZh> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerId"), customerId);
            }
        };
    }
    
    public static Specification<SchemeZh> isGameCodeEqual(final String gameCode) {
        return new Specification<SchemeZh>() {
            @Override
            public Predicate toPredicate(Root<SchemeZh> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("gameCode"), gameCode);
            }
        };
    }
    
    public static Specification<SchemeZh> isStatusEqual(final int status) {
        return new Specification<SchemeZh>() {
            @Override
            public Predicate toPredicate(Root<SchemeZh> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }
    
}
