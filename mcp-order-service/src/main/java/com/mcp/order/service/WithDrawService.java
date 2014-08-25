/**
 *
 */
package com.mcp.order.service;

import com.mcp.order.dao.WithDrawDao;
import com.mcp.order.model.ts.WithDraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

/**
 * @author ming.li
 */
@Service("withDrawService")
public class WithDrawService {

	@Autowired
	private WithDrawDao withDrawDao;
	
	public void save(WithDraw withDraw)
	{
		this.withDrawDao.save(withDraw);
	}
	
	public Page<WithDraw> findAll(Pageable p) {
		return this.withDrawDao.findAll(p);
	}
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param specs
	 * @param p
	 * @return
	 */
	public Page<WithDraw> findAll(Specifications<WithDraw> specs, Pageable p) {
		return this.withDrawDao.findAll(specs, p);
	}
}
