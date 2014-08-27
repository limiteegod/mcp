/**
 *
 */
package com.mcp.order.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.TermDao;
import com.mcp.order.dao.TermLogDao;
import com.mcp.order.dao.specification.TermSpecification;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.ts.Term;
import com.mcp.order.status.TermState;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author ming.li
 */
@Service("termService")
public class TermService {
	
	private static final Logger log = Logger.getLogger(TermService.class);

    @Autowired
    private TermDao termDao;
    
    @Autowired
    private TermLogDao termLogDao;
    
    /**
     * 通过游戏码和期号查询奖期
     *
     * @param gameCode
     * @param code
     * @return
     */
    public Term findOneByGameCodeAndCode(String gameCode, String code) {
        return this.termDao.findOneByGameCodeAndCode(gameCode, code);
    }

    public Term findOne(String id) {
        return termDao.findOne(id);
    }

    public List<Term> findAllByStatusAndGameCode(int status, String gameCode) {
        return this.termDao.findAllByStatusAndGameCode(status, gameCode);
    }

    public Page<Term> findAll(Specification<Term> spec, Pageable p) {
        return this.termDao.findAll(spec, p);
    }
    
    public List<Term> findAll(Specification<Term> spec) {
        return this.termDao.findAll(spec);
    }

    public Term save(Term gameTerm) {
        return this.termDao.save(gameTerm);
    }
    
    @Transactional
    public void updateStatusById(int status, String id)
    {
    	this.termDao.updateStatus(status, id);
    }


    @Transactional
    public void updateEndTimeById(Date date, String id) {
        this.termDao.updateEndTime(date,id);
    }


    @Transactional
    public void updateDrawInfo(String prizeDesc, String drawNumber, String id, int gameType)
    {
    	Term t = this.termDao.findOne(id);
    	//高频或者竞彩，不需要详细的开奖详情，直接算奖，普通游戏则等待录入开奖号码
    	if(gameType == ConstantValues.Game_Type_Gaopin.getCode() || gameType == ConstantValues.Game_Type_Jingcai.getCode())
    	{
    		t.setStatus(TermState.DRAW.getCode());
    	}
    	t.setWinningNumber(drawNumber);
    	if(!StringUtil.isEmpty(prizeDesc) && gameType == ConstantValues.Game_Type_Normal.getCode())
    	{
    		t.setPrizeDesc(prizeDesc);
    	}
    }

    /**
     * 获得需要开售的奖期
     *
     * @return
     */
    @Transactional
    public List<Term> findToOpen(String gameCode, int nextStatus, Pageable p) {
        int status = TermState.NOT_ON_SALE.getCode();
        List<Term> termList = this.termDao.findAllByGameCodeAndStatusAndOpenTimeBefore(gameCode, status, new Date(), p);
        for(int i = 0; i < termList.size(); i++)
        {
        	Term t = termList.get(i);
        	t.setStatus(nextStatus);
        }
        return termList;
    }

    /**
     * 获得需要停售的奖期，每个彩种都有自己的提前停售时间（单位为秒）
     * 对所有的彩种进行扫描，对当前时间+提前停售时间>官方停售时间的期次，则满足条件
     *
     * @return List<Term> 需要停售的期次列表
     */
    @Transactional
    public List<Term> findToEnd(String gameCode, long offset, int nextStatus, Pageable p) {
        List<Term> termList = new ArrayList<Term>();
        Date date = DateTimeUtil.getDateAfterMilliseconds(offset);
        termList = termDao.findAllByEndTimeBeforeAndStatusLessThanAndGameCode(date, TermState.PREEND.getCode(), gameCode, p);
        for(int i = 0; i < termList.size(); i++)
        {
        	Term t = termList.get(i);
        	if(t.getStatus() != TermState.ON_SALE.getCode() && t.getStatus() != TermState.PAUSE.getCode())
        	{
        		log.error("游戏：" + t.getGameCode() + "，期次：" + t.getCode() + "，发生状态跳跃");
        	}
        	t.setStatus(nextStatus);
        }
        return termList;
    }
    
    /**
     * 获得需要停售的奖期，每个彩种都有自己的提前停售时间（单位为秒）
     * 对所有的彩种进行扫描，对当前时间+提前停售时间>官方停售时间的期次，则满足条件
     *
     * @return List<Term> 需要停售的期次列表
     */
    @Transactional
    public List<Term> findToSEnd(int gameType, String gameCode, int nextStatus, Pageable p) {
        List<Term> termList = new ArrayList<Term>();
        long milliSeconds = 0L;
        if(gameType == ConstantValues.Game_Type_Normal.getCode() || gameType == ConstantValues.Game_Type_Jingcai.getCode())
        {
            milliSeconds = 20*60*1000;
            //milliSeconds = 60*1000;
        }
        else if(gameType == ConstantValues.Game_Type_Gaopin.getCode())
        {
            milliSeconds = 20*1000;
        }
        Date date = DateTimeUtil.getDateBeforeMilliseconds(milliSeconds);
        termList = termDao.findAllByEndTimeBeforeAndStatusAndGameCode(date, TermState.END.getCode(), gameCode, p);
        for(int i = 0; i < termList.size(); i++)
        {
        	Term t = termList.get(i);
        	t.setStatus(nextStatus);
        }
        return termList;
    }

    public List<Term> findToDisDisplay() {
        //默认只列出从在售到结算状态之间的期次，其余状态不列出。
        return this.termDao.findAllByStatusBetween(TermState.ON_SALE.getCode(), TermState.SETTLEMENT.getCode());
//        return this.gameTermDao.findAll();
    }

    public List<Term> findAllByGameCodeAndStatusGreaterThanOrderByCodeDesc(String gameCode, int status, Pageable p) {
        return this.termDao.findAllByGameCodeAndStatusGreaterThanOrderByCodeDesc(gameCode, status, p);
    }

    public ArrayList<Object[]> findMaxGameCodeGroupByGame(int status) {
        return this.termDao.findMaxGameCodeGroupByGame(status);
    }

    public List<Term> findAllByStatus(int status) {
        Specifications<Term> specs = where(TermSpecification.isStatusEqual(status));
        return this.termDao.findAll(specs);
    }
    
    /**
     * 查找指定游戏销售期次中最小的一期
     * @param gameCode
     * @return
     */
    public Term findFirstSaleTerm(String gameCode) {
    	Sort sort = new Sort(Direction.ASC, "code");
    	PageRequest pr = new PageRequest(0, 1, sort);
    	Specifications<Term> specs = where(TermSpecification.isGameCodeEqual(gameCode));
        Specifications<Term> statusSpecs = where(TermSpecification.isStatusEqual(TermState.ON_SALE.getCode()));
        statusSpecs = statusSpecs.or(TermSpecification.isStatusEqual(TermState.NOT_ON_SALE.getCode()));
        specs = specs.and(statusSpecs);
        List<Term> termList = this.termDao.findAll(specs, pr).getContent();
        if(termList.size() == 0)
        {
        	return null;
        }
        return termList.get(0);
    }
    
    /**
     * 当很多期同时进行状态跟新时，为了保证事务尽可能的成功，要让事务执行的时间尽可能的短，
     * 所以，此方法每次取得数据不应该很多
     * @param nextStatus 要更新的状态
     * @return
     */
    @Transactional
    public Page<Term> findAllByStatusAndUpdate(Specifications<Term> specs, int nextStatus, Pageable p) {
        Page<Term> termPage = this.termDao.findAll(specs, p);
        Iterator<Term> termIt = termPage.iterator();
        while(termIt.hasNext())
        {
        	Term t = termIt.next();
        	t.setStatus(nextStatus);
        }
        return termPage;
    }

    /**
     * 查询预销售的期次列表
     *
     * @param gameCode
     * @param termCode
     * @return
     */
    public Page<Term> findToSaleList(String gameCode, String termCode, Pageable p) {
        Specifications<Term> specs = where(TermSpecification.isCodeGreaterThanOrEqualTo(termCode));
        specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
        return this.termDao.findAll(specs, p);
    }


    /**
     * 动态条件查询
     */
    public List<Term> specificQuery(String gameCode, int status, String termCode) {

        Specifications<Term> specs = where(TermSpecification.isIdNotNull());

        if (!"".equals(gameCode)) {
            specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
        }

        if (-1 != status) {
            specs = specs.and(TermSpecification.isStatusEqual(status));
        } else {
            specs = specs.and(TermSpecification.defaultStatus());
        }

        if (!"".equals(termCode)) {
            specs = specs.and(TermSpecification.isCodeEqual(termCode));
        }
        return this.termDao.findAll(specs, new Sort(Sort.Direction.DESC,"gameCode","code"));
    }
    
    /**
     * 根据条件查询场次信息
     * @param gameCode
     * @param matchCode
     * @param status
     * @param exStatus
     * @return
     */
    public List<Term> query(String gameCode, String matchCode, List<Integer> status, List<Integer> exStatus, Sort sort)
    {
    	Specifications<Term> specs = Specifications.where(TermSpecification.isGameCodeEqual(gameCode));
    	if(!StringUtil.isEmpty(matchCode))
    	{
    		specs = specs.and(TermSpecification.isCodeEqual(matchCode));
    	}
    	if(status != null && status.size() > 0)
    	{
    		Specifications<Term> statusEqualSpecs = Specifications.where(TermSpecification.isStatusEqual(status.get(0)));
    		for(int i = 1; i < status.size(); i++)
    		{
    			statusEqualSpecs = statusEqualSpecs.or(TermSpecification.isStatusEqual(status.get(i)));
    		}
    		specs = specs.and(statusEqualSpecs);
    	}
    	if(exStatus != null && exStatus.size() > 0)
    	{
    		for(int i = 0; i < exStatus.size(); i++)
    		{
    			specs = specs.and(TermSpecification.isStatusNotEqual(exStatus.get(i)));
    		}
    	}
    	return this.termDao.findAll(specs, sort);
    }


    /**
     * 根据条件查询场次信息
     * @param gameCode
     * @param termCode
     * @param status
     * @param exStatus
     * @return
     */
    public Page<Term> query(String gameCode, String termCode, List<Integer> status, List<Integer> exStatus, Pageable page)
    {
        Specifications<Term> specs = Specifications.where(TermSpecification.isGameCodeEqual(gameCode));
        if(!StringUtil.isEmpty(termCode))
        {
            specs = specs.and(TermSpecification.isCodeEqual(termCode));
        }
        if(status != null && status.size() > 0)
        {
            Specifications<Term> statusEqualSpecs = Specifications.where(TermSpecification.isStatusEqual(status.get(0)));
            for(int i = 1; i < status.size(); i++)
            {
                statusEqualSpecs = statusEqualSpecs.or(TermSpecification.isStatusEqual(status.get(i)));
            }
            specs = specs.and(statusEqualSpecs);
        }
        if(exStatus != null && exStatus.size() > 0)
        {
            for(int i = 0; i < exStatus.size(); i++)
            {
                specs = specs.and(TermSpecification.isStatusNotEqual(exStatus.get(i)));
            }
        }
        return this.termDao.findAll(specs, page);
    }

    /**
     * 根据条件查询期次信息
     * @param gameCodeList  必须有一个gameCode以供查询
     * @param status
     * @param exStatus
     * @param page
     * @return
     */
    public Page<Term> query(List<String> gameCodeList, List<Integer> status, List<Integer> exStatus, Pageable page)
    {
        if(gameCodeList == null || gameCodeList.size() == 0)
        {
            throw new CoreException(ErrCode.E0003);
        }
        Specifications<Term> specs = Specifications.where(TermSpecification.isGameCodeEqual(gameCodeList.get(0)));
        for(int i = 1; i < gameCodeList.size(); i++)
        {
            specs = specs.or(TermSpecification.isGameCodeEqual(gameCodeList.get(i)));
        }
        if(status != null && status.size() > 0)
        {
            Specifications<Term> statusEqualSpecs = Specifications.where(TermSpecification.isStatusEqual(status.get(0)));
            for(int i = 1; i < status.size(); i++)
            {
                statusEqualSpecs = statusEqualSpecs.or(TermSpecification.isStatusEqual(status.get(i)));
            }
            specs = specs.and(statusEqualSpecs);
        }
        if(exStatus != null && exStatus.size() > 0)
        {
            for(int i = 0; i < exStatus.size(); i++)
            {
                specs = specs.and(TermSpecification.isStatusNotEqual(exStatus.get(i)));
            }
        }
        return this.termDao.findAll(specs, page);
    }
}
