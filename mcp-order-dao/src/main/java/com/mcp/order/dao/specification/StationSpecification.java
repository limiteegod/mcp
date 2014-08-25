/**
 * 
 */
package com.mcp.order.dao.specification;

import com.mcp.order.model.admin.Station;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;

/**
 * @author ming.li
 *
 */
public class StationSpecification {
	
	/**
	 * 类型是否一致
	 * @param entityId
	 * @return
	 */
	public static Specification<Station> isStationTypeEqual(final int stationType)
	{
		return new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("stationType"), stationType);
			}
		};
	}
	
	/**
	 * id是否相等
	 * @param entityId
	 * @return
	 */
	public static Specification<Station> isIdEqual(final String id)
	{
		return new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("id"), id);
			}
		};
	}
	
	/**
	 * 名称是否相似
	 * @param name
	 * @return
	 */
	public static Specification<Station> isNameLike(final String name)
	{
		return new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> path = root.get("name");
				return cb.like(path, "%" + name + "%");
			}
		};
	}
	
	/**
	 * 描述是否相似
	 * @param description
	 * @return
	 */
	public static Specification<Station> isDescriptionLike(final String description)
	{
		return new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> path = root.get("description");
				return cb.like(path, "%" + description + "%");
			}
		};
	}
	
	/**
	 * 描述是否相似
	 * @param address
	 * @return
	 */
	public static Specification<Station> isAddressLike(final String address)
	{
		return new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> path = root.get("address");
				return cb.like(path, "%" + address + "%");
			}
		};
	}
	
	/**
	 * 纬度差距在范围以内
	 * @param entityId
	 * @return
	 */
	public static Specification<Station> isLatitudeNear(final double latitude, final double gap)
	{
		Specification<Station> geSpec = new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Double> path = root.get("latitude");
				return cb.ge(path, latitude - gap);
			}
		};
		Specification<Station> leSpec = new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Double> path = root.get("latitude");
				return cb.le(path, latitude + gap);
			}
		};
		return Specifications.where(geSpec).and(leSpec);
	}
	
	/**
	 * 经度差距在范围以内
	 * @param entityId
	 * @return
	 */
	public static Specification<Station> isLongitudeNear(final double longitude, final double gap)
	{
		Specification<Station> geSpec = new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Double> path = root.get("longitude");
				return cb.ge(path, longitude - gap);
			}
		};
		Specification<Station> leSpec = new Specification<Station>(){
			@Override
			public Predicate toPredicate(Root<Station> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Double> path = root.get("longitude");
				return cb.le(path, longitude + gap);
			}
		};
		return Specifications.where(geSpec).and(leSpec);
	}

	/**
	  * com.mcp.order.dao.specification.StationSpecification
	  * 根据投注站编号查询的条件
	  * @param code
	  * @return
	  */
	 public static Specification<Station> isCodeEqual(final String code) {
	     return new Specification<Station>() {
	         @Override
	         public Predicate toPredicate(Root<Station> root,
	                                      CriteriaQuery<?> query, CriteriaBuilder cb) {
	             return cb.equal(root.get("code"), code);
	         }
	     };
	 }

	 /**
	  * com.mcp.order.dao.specification.StationSpecification
	  * 根据投注站状态查询的条件
	  * @param status
	  * @return
	  */
	 public static Specification<Station> isStatusEqual(final int status) {
	     return new Specification<Station>() {
	         @Override
	         public Predicate toPredicate(Root<Station> root,
	                                      CriteriaQuery<?> query, CriteriaBuilder cb) {
	             return cb.equal(root.get("status"), status);
	         }
	     };
	 }

	 /**
	  * com.mcp.order.dao.specification.StationSpecification
	  * 初始化查询方法
	  * @return
	  */
	 public static Specification<Station> isIdNotNull() {
	     return new Specification<Station>() {
	         @Override
	         public Predicate toPredicate(Root<Station> root,
	                                      CriteriaQuery<?> query, CriteriaBuilder cb) {
	             return cb.isNotNull(root.get("id"));
	         }
	     };
	 }

}
