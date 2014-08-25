/**
 * 
 */
package com.mcp.order.dao.specification;

import com.mcp.order.model.jc.JOdds;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * @author ming.li
 *
 */
public class JOddsSpecification {
	
	/**
	 * 游戏编码相等
	 * @param terminalId
	 * @return
	 */
	public static Specification<JOdds> isGameCodeEqual(final String gameCode)
	{
		return new Specification<JOdds>(){
			@Override
			public Predicate toPredicate(Root<JOdds> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("gameCode"), gameCode);
			}
		};
	}
	
	/**
	 * 场次编码相等
	 * @param terminalId
	 * @return
	 */
	public static Specification<JOdds> isMatchCodeEqual(final String matchCode)
	{
		return new Specification<JOdds>(){
			@Override
			public Predicate toPredicate(Root<JOdds> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("matchCode"), matchCode);
			}
		};
	}
	
	/**
	 * 场次编码相等
	 * @param terminalId
	 * @return
	 */
	public static Specification<JOdds> isPlayTypeCodeEqual(final String playTypeCode)
	{
		return new Specification<JOdds>(){
			@Override
			public Predicate toPredicate(Root<JOdds> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("playTypeCode"), playTypeCode);
			}
		};
	}
	
	/**
	 * 赔率的分组
	 * @param terminalId
	 * @return
	 */
	public static Specification<JOdds> isOddsCodeEqual(final String oddsCode)
	{
		return new Specification<JOdds>(){
			@Override
			public Predicate toPredicate(Root<JOdds> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("oddsCode"), oddsCode);
			}
		};
	}
}
