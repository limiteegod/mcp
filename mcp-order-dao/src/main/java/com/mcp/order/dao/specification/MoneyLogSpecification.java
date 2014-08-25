package com.mcp.order.dao.specification;

import com.mcp.order.model.ts.MoneyLog;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class MoneyLogSpecification {
	
	/**
	 * 来源的id是否相等
	 * @param entityId
	 * @return
	 */
	public static Specification<MoneyLog> isFromEntityIdEqual(final String fromEntityId)
	{
		return new Specification<MoneyLog>(){
			@Override
			public Predicate toPredicate(Root<MoneyLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("fromEntityId"), fromEntityId);
			}
		};
	}
	
	/**
	 * 去向的id是否相等
	 * @param entityId
	 * @return
	 */
	public static Specification<MoneyLog> isToEntityIdEqual(final String toEntityId)
	{
		return new Specification<MoneyLog>(){
			@Override
			public Predicate toPredicate(Root<MoneyLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("toEntityId"), toEntityId);
			}
		};
	}
	
	/**
	 * 操作码是否相等
	 * @param handlerCode
	 * @return
	 */
	public static Specification<MoneyLog> isHandlerCodeEqual(final String handlerCode)
	{
		return new Specification<MoneyLog>(){
			@Override
			public Predicate toPredicate(Root<MoneyLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("handlerCode"), handlerCode);
			}
		};
	}
	
	/**
	 * 操作码是否相似
	 * @param handlerCode
	 * @return
	 */
	public static Specification<MoneyLog> isHandlerCodeLike(final String handlerCode)
	{
		return new Specification<MoneyLog>(){
			@Override
			public Predicate toPredicate(Root<MoneyLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> path = root.get("handlerCode");
				return cb.like(path, handlerCode + "%");
			}
		};
	}
	
	/**
	 * 操作码是否相似
	 * @param handlerCode
	 * @return
	 */
	public static Specification<MoneyLog> isOperationCodeLike(final String operationCode)
	{
		return new Specification<MoneyLog>(){
			@Override
			public Predicate toPredicate(Root<MoneyLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> path = root.get("operationCode");
				return cb.like(path, operationCode + "%");
			}
		};
	}
	
	public static Specification<MoneyLog> isCreateTimeStampAfter(final Long start)
    {
        return new Specification<MoneyLog>(){
            @Override
            public Predicate toPredicate(Root<MoneyLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Path<Long> path = root.get("createTimeStamp");
            	return cb.ge(path, start);
            }
        };
    }
	
    public static Specification<MoneyLog> isCreateTimeStampBefore(final Long end)
    {
        return new Specification<MoneyLog>(){
            @Override
            public Predicate toPredicate(Root<MoneyLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Long> path = root.get("createTimeStamp");
            	return cb.le(path, end);
            }
        };
    }
    
	public static Specification<MoneyLog> isAcceptTimeStampAfter(final Long start)
    {
        return new Specification<MoneyLog>(){
            @Override
            public Predicate toPredicate(Root<MoneyLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Path<Long> path = root.get("acceptTimeStamp");
            	return cb.ge(path, start);
            }
        };
    }
	
    public static Specification<MoneyLog> isAcceptTimeStampBefore(final Long end)
    {
        return new Specification<MoneyLog>(){
            @Override
            public Predicate toPredicate(Root<MoneyLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Path<Long> path = root.get("acceptTimeStamp");
            	return cb.le(path, end);
            }
        };
    }
}
