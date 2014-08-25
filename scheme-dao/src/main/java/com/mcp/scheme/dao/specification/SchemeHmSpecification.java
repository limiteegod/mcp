package com.mcp.scheme.dao.specification;

import com.mcp.scheme.model.SchemeHm;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class SchemeHmSpecification {

    /**
     * 合买方案的截止时间在指定的时间之后
     * @param endTime
     * @return
     */
    public static Specification<SchemeHm> isEndTimeAfter(final Date endTime) {
        return new Specification<SchemeHm>() {
            @Override
            public Predicate toPredicate(Root<SchemeHm> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.get("endTime").as(java.util.Date.class), endTime);
            }
        };
    }

    /**
     * 合买方案的截止时间在指定的时间之前
     * @param endTime
     * @return
     */
    public static Specification<SchemeHm> isEndTimeBefore(final Date endTime) {
        return new Specification<SchemeHm>() {
            @Override
            public Predicate toPredicate(Root<SchemeHm> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(root.get("endTime").as(java.util.Date.class), endTime);
            }
        };
    }


    /**
     * 方案的状态相等
     * @param status
     * @return
     */
    public static Specification<SchemeHm> isStatusEqual(final int status) {
        return new Specification<SchemeHm>() {
            @Override
            public Predicate toPredicate(Root<SchemeHm> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }
}
