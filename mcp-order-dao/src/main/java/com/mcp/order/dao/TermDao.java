package com.mcp.order.dao;

import com.mcp.order.model.ts.Term;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TermDao extends PagingAndSortingRepository<Term, String>,JpaSpecificationExecutor<Term> {

    /**
     * 通过游戏码和期号查询奖期
     *
     * @param gameCode
     * @param code
     * @return
     */
    public Term findOneByGameCodeAndCode(String gameCode, String code);

    public List<Term> findAllByStatus(int status);
    
    public List<Term> findAllByStatusAndGameCode(int status, String gameCode);

    public List<Term> findAllByGameCode(String gameCode,Pageable p);
    
    public List<Term> findAllByGameCodeAndStatusGreaterThanOrderByCodeDesc(String gameCode, int status, Pageable p);
    
    @Query("select g.gameCode,MAX(g.code) from Term g where g.status>=? and g.status<=1901 group by g.gameCode")
    public ArrayList<Object[]> findMaxGameCodeGroupByGame(int status);
    
    @Modifying
    @Query("update Term gt set gt.status=?1 where gt.id=?2")
    public int updateStatus(int status, String id);

    @Modifying
    @Query("update Term gt set gt.endTime=?1 where gt.id=?2")
    public int updateEndTime(Date date, String id);
    
    //修改其为可售的条件是：1 当前时间等于或迟于期次定义的开售时间。2 当前状态不是暂停状态。3 当前时间在停售时间之前。
    public List<Term> findAllByGameCodeAndStatusAndOpenTimeBefore(String gameCode, int status, Date openTime, Pageable p);
    
    public List<Term> findAllByEndTimeBeforeAndStatusLessThanAndGameCode(Date openTime, int status, String gameCode, Pageable p);
    
    public List<Term> findAllByEndTimeBeforeAndStatusAndGameCode(Date openTime, int status, String gameCode, Pageable p);
    
    public List<Term> findAllByStatusLessThan(int status);
    
    @Modifying
    @Query("select t from Term t where t.status between ?1 and ?2 order by t.gameCode desc,t.code desc")
    public List<Term> findAllByStatusBetween(int start,int end);
}
