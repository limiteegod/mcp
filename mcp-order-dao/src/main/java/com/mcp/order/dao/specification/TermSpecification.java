package com.mcp.order.dao.specification;/*
 * User: yeeson he
 * Date: 13-8-29
 * Time: 下午12:57
 */

import com.mcp.order.model.ts.Term;
import com.mcp.order.status.TermState;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class TermSpecification {
	
    public static Specification<Term> isGameCodeEqual(final String gameCode)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("gameCode"), gameCode);
            }
        };
    }
    
    public static Specification<Term> isStatusEqual(final int status)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }
    
    public static Specification<Term> isStatusNotEqual(final int status)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get("status"), status);
            }
        };
    }
    
    public static Specification<Term> isStatusGreaterThan(final int status)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Integer> path = root.get("status");
            	return cb.greaterThan(path, status);
            }
        };
    }
    
    public static Specification<Term> isStatusLessThan(final int status)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Integer> path = root.get("status");
            	return cb.lessThan(path, status);
            }
        };
    }
    
    public static Specification<Term> isCodeGreaterThanOrEqualTo(final String code)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Path<String> codePath = root.get("code");
            	return cb.greaterThanOrEqualTo(codePath, code);
            }
        };
    }
    
    public static Specification<Term> isCodeEqual(final String code)
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("code"), code);
            }
        };
    }
    public static Specification<Term> isIdNotNull()
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNotNull(root.get("id"));
            }
        };
    }
    
    public static Specification<Term> defaultStatus()
    {
        return new Specification<Term>(){
            @Override
            public Predicate toPredicate(Root<Term> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("status").as(Integer.class), TermState.ON_SALE.getCode(), TermState.PAYOUT.getCode());
            }
        };
    }
 }
