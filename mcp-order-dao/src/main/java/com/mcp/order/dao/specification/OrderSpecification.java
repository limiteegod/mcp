package com.mcp.order.dao.specification;

import com.mcp.order.model.ts.TOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

public class OrderSpecification {

    public static Specification<TOrder> isIdEqual(final String id) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("id"), id);
            }
        };
    }

    /**
     * 外部订单号相等
     * @param outerId
     * @return
     */
    public static Specification<TOrder> isOuterIdEqual(final String outerId) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("outerId"), outerId);
            }
        };
    }

    public static Specification<TOrder> isSchemeTypeEqual(final int schemeType) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("schemeType"), schemeType);
            }
        };
    }

    public static Specification<TOrder> isSchemeIdEqual(final String schemeId) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("schemeId"), schemeId);
            }
        };
    }

    public static Specification<TOrder> isStatusNotEqual(final int status) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get("status"), status);
            }
        };
    }

    public static Specification<TOrder> isCustomerIdEqual(final String customerId) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerId"), customerId);
            }
        };
    }
    
    public static Specification<TOrder> isChannelCodeEqual(final String channelCode) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("channelCode"), channelCode);
            }
        };
    }

    public static Specification<TOrder> isGameCodeEqual(final String gameCode) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("gameCode"), gameCode);
            }
        };
    }

    public static Specification<TOrder> isBonusEqual(final long bonus) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bonus"), bonus);
            }
        };
    }

    public static Specification<TOrder> isBonusGreaterThan(final long bonus) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Long> path = root.get("bonus");
                return cb.greaterThan(path, bonus);
            }
        };
    }

    public static Specification<TOrder> isStationIdEqual(final String stationId) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stationId"), stationId);
            }
        };
    }

    public static Specification<TOrder> isStatusEqual(final int status) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }

    public static Specification<TOrder> isAcceptTimeAfter(final Date start) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.get("acceptTime").as(java.util.Date.class), start);
            }
        };
    }

    public static Specification<TOrder> isAcceptTimeBefore(final Date end) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(root.get("acceptTime").as(java.util.Date.class), end);
            }
        };
    }

    public static Specification<TOrder> isSalesChannelEqual(final String salesChannel) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("salesChannel"), salesChannel);
            }
        };
    }

    public static Specification<TOrder> isPlatformEqual(final String platform) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("platform"), platform);
            }
        };
    }

    public static Specification<TOrder> isPayTypeEqual(final int payType) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("payType"), payType);
            }
        };
    }

    public static Specification<TOrder> isIdNotNull() {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNotNull(root.get("id"));
            }
        };
    }

    public static Specification<TOrder> isTermCodeEqual(final String termCode) {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("termCode"), termCode);
            }
        };
    }
    public static Specification<TOrder> orderByCreateTimeDesc() {
        return new Specification<TOrder>() {
            @Override
            public Predicate toPredicate(Root<TOrder> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return query.orderBy(cb.desc(root.get("createTime"))).getRestriction();
            }
        };
    }
}
