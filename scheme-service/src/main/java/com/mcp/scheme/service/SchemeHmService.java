/**
 * 
 */
package com.mcp.scheme.service;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.status.SchemeState;
import com.mcp.order.util.DateTimeUtil;
import com.mcp.scheme.dao.SchemeHmDao;
import com.mcp.scheme.dao.specification.SchemeHmSpecification;
import com.mcp.scheme.model.SchemeHm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 */
@Service("schemeHmService")
public class SchemeHmService {
	
	@Autowired
	private SchemeHmDao schemeHmDao;
	
	public void save(SchemeHm schemeHm)
	{
		this.schemeHmDao.save(schemeHm);
	}


    public SchemeHm findOne(String id)
    {
        return this.schemeHmDao.findOne(id);
    }

    /**
     * 增加合买方案的完成份额
     * @param amount
     * @param id
     * @return
     */
    @Transactional
    public SchemeHm incrfAmount(long amount, String id)
    {
        SchemeHm scheme = this.schemeHmDao.findOne(id);
        long newfAmount = scheme.getfAmount() + amount;
        scheme.setfAmount(newfAmount);
        if(newfAmount == scheme.getAmount())
        {
            scheme.setStatus(SchemeState.FINISHED.getCode());
        }
        scheme.setJoinCount(scheme.getJoinCount() + 1);
        return scheme;
    }

    /**
     * 分页取消过期的合买方案，并返回
     * @return
     */
    @Transactional
    public List<SchemeHm> findToCancel(Pageable p) {
        int status = SchemeState.RUNNING.getCode();
        Date endTime = new Date();
        Specifications<SchemeHm> specs = Specifications.where(SchemeHmSpecification.isEndTimeBefore(endTime));
        specs = specs.and(SchemeHmSpecification.isStatusEqual(status));
        List<SchemeHm> schemeList = this.schemeHmDao.findAll(specs, p).getContent();
        for(SchemeHm scheme:schemeList)
        {
            scheme.setStatus(SchemeState.CANCELING.getCode());
        }
        return schemeList;
    }

    /**
     * 订单完成时，更新合买方案信息，当所有的订单都完成时，更新状态
     * @param schemeId
     * @param bonus
     */
    @Transactional
    public void incrFinishedCount(String schemeId, long bonus, long bonusBeforeTax)
    {
        SchemeHm scheme = this.schemeHmDao.findOne(schemeId);
        int count = scheme.getFinishedOrderCount() + 1;
        scheme.setFinishedOrderCount(count);
        scheme.setBonus(bonus);
        scheme.setBonusBeforeTax(bonusBeforeTax);
        if(count == scheme.getOrderCount())
        {
            scheme.setStatus(SchemeState.WAIT_AUDIT.getCode());
        }
    }

    /**
     * 分页查找需要结算的方案
     * @param p
     * @return
     */
    @Transactional
    public List<SchemeHm> findToAudit(Pageable p)
    {
        int status = SchemeState.WAIT_AUDIT.getCode();
        Specifications<SchemeHm> specs = Specifications.where(SchemeHmSpecification.isStatusEqual(status));
        List<SchemeHm> schemeList = this.schemeHmDao.findAll(specs, p).getContent();
        for(SchemeHm scheme:schemeList)
        {
            scheme.setStatus(SchemeState.AUDITING.getCode());
        }
        return schemeList;
    }

    @Transactional
    public void updateStatusById(int status, String id)
    {
        this.schemeHmDao.updateStatusById(status, id);
    }
}
