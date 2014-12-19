/**
 * 
 */
package com.mcp.scheme.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.status.SchemeState;
import com.mcp.scheme.dao.SchemeZhDao;
import com.mcp.scheme.dao.specification.SchemeSpecification;
import com.mcp.scheme.model.SchemeZh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ming.li
 *
 */
@Service("schemeZhService")
public class SchemeZhService {
	
	@Autowired
	private SchemeZhDao schemeZhDao;
	
	public SchemeZh findOne(String id)
	{
		return this.schemeZhDao.findOne(id);
	}
	
	@Transactional
	public void save(SchemeZh scheme)
	{
        this.schemeZhDao.save(scheme);
	}
	
	@Transactional
	public int updateStatusById(int status, String id)
	{
		return this.schemeZhDao.updateStatusById(status, id);
	}
	
	/**
	 * 更新方案当前期次，如果所有订单都处理完成，则更新方案状态为完成
	 * @param id
	 * @param nextTermCode
	 */
	@Transactional
	public SchemeZh incrNewOrder(String id, long bonus, long bonusBeforeTax, String nextTermCode)
	{
		SchemeZh scheme = this.schemeZhDao.findOne(id);
        if(scheme == null)
        {
            return null;
        }
        scheme.setBonus(scheme.getBonus() + bonus);
        scheme.setBonusBeforeTax(scheme.getBonusBeforeTax() + bonusBeforeTax);
        if(scheme.getFinishedOrderCount() + scheme.getCancelOrderCount() >= scheme.getOrderCount())
        {
            scheme.setStatus(SchemeState.FINISHED.getCode());
        }
        else
        {
            scheme.setCurTermCode(nextTermCode);
            scheme.setFinishedOrderCount(scheme.getFinishedOrderCount() + 1);
        }
        return scheme;
	}
	
	/**
	 * 增加方案中奖金额，如果中奖，且方案设置了中奖后停追，则设置方案状态为取消
	 * @param id
	 * @param bonus
	 */
	@Transactional
	public SchemeZh incrBonus(String id, long bonus, long bonusBeforeTax)
	{
		SchemeZh scheme = this.schemeZhDao.findOne(id);
		scheme.setBonus(scheme.getBonus() + bonus);
        scheme.setBonusBeforeTax(scheme.getBonusBeforeTax() + bonusBeforeTax);
		if(scheme.isWinStop() && scheme.getFinishedOrderCount() < scheme.getOrderCount())
		{
			scheme.setStatus(SchemeState.CANCELED.getCode());
		}
		return scheme;
	}
	
	/**
	 * 取消未支付的方案
	 */
	@Transactional
	public void cancelUnAfforded(String gameCode, String startTermCode)
	{
		this.schemeZhDao.updateStatusByGameCodeAndStartTermCodeAndStatus(SchemeState.CANCELED.getCode(), 
				gameCode, startTermCode, SchemeState.NOT_AVAILABLE.getCode());
	}
	
	/**
	 * 支付一个未支付的方案
	 * @param id
	 */
	@Transactional
	public SchemeZh afford(String id)
	{
		SchemeZh scheme = this.schemeZhDao.findOne(id);
		scheme.setStatus(SchemeState.RUNNING.getCode());
		return scheme;
	}
	
	/**
	 * 取消方案
	 * @param id
	 */
	@Transactional
	public SchemeZh cancel(String id)
	{
		SchemeZh scheme = this.schemeZhDao.findOne(id);
		scheme.setStatus(SchemeState.CANCELED.getCode());
		return scheme;
	}
	
	/**
	 * 用户方案查询
	 * @param customerId
	 * @param gameCode
	 * @param status
	 * @return
	 */
	public Page<SchemeZh> query(String customerId, String gameCode, int status, Pageable page)
	{
		Specifications<SchemeZh> specs = Specifications.where(SchemeSpecification.isCustomerIdEqual(customerId));
		if(!StringUtil.isEmpty(gameCode))
		{
			specs = specs.and(SchemeSpecification.isGameCodeEqual(gameCode));
		}
		if(status > 0)
		{
			specs = specs.and(SchemeSpecification.isStatusEqual(status));
		}
		return this.schemeZhDao.findAll(specs, page);
	}

    public Page<SchemeZh> findAll(Specification<SchemeZh> spec, Pageable pageable)
    {
        return this.schemeZhDao.findAll(spec, pageable);
    }

}
