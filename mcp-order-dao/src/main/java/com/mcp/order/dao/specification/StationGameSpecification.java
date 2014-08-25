/**
 * 
 */
package com.mcp.order.dao.specification;

import com.mcp.order.model.admin.StationGame;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author ming.li
 *
 */
public class StationGameSpecification {
	
	
	/**
	 * id相等
	 * @param terminalId
	 * @return
	 */
	public static Specification<StationGame> isIdEqual(final String id)
	{
		return new Specification<StationGame>(){
			@Override
			public Predicate toPredicate(Root<StationGame> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("id"), id);
			}
		};
	}
	
}
