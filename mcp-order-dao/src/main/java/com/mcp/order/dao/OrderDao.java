package com.mcp.order.dao;

import com.mcp.order.model.ts.TOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface OrderDao extends PagingAndSortingRepository<TOrder, String>, JpaSpecificationExecutor<TOrder> {
    /*
        根据投注站的id查询所有的订单。
     */
    public List<TOrder> findAllByStationIdOrderByAcceptTimeAsc(String stationId);

    /*
        根据彩民Id、游戏id、奖金状态、订单状态等分页查询投注订单。
   */
    @Query("from TOrder t where t.customerId=? and t.gameCode=? and t.status=? order by t.acceptTime desc")
    public List<TOrder> findAllByStatusAndGame(String customerId, String gameCode, int status, Pageable p);

    /*
        根据彩民Id、奖金状态、订单状态等分页查询投注订单。
   */
    @Query("from TOrder t where t.customerId=? and t.status=? order by t.acceptTime desc")
    public List<TOrder> findAllByStatus(String customerId, int status, Pageable p);


    @Query("from TOrder t where t.schemeId=?1 and t.termCode=?2")
    public TOrder findOneBySchemeIdAndTermCode(String schemeId, String termCode);
    
//    public List<TOrder> findAllByCustomerIdOrderByCreateTimeDesc(String customerId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndStatusOrderByCreateTimeDesc(String customerId, int status, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndStatusAndPrizeStatusOrderByCreateTimeDesc(String customerId, int status, int prizeStatus, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndPrizeStatusOrderByCreateTimeDesc(String customerId, int prizeStatus, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeOrderByCreateTimeDesc(String customerId ,String gameCode, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndStatusOrderByCreateTimeDesc(String customerId, String gameCode, int status, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndStatusAndPrizeStatusOrderByCreateTimeDesc(String customerId, String gameCode, int status, int prizeStatus, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndPrizeStatusOrderByCreateTimeDesc(String customerId, String gameCode, int prizeStatus, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndStationIdOrderByCreateTimeDesc(String customerId, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndStatusAndStationIdOrderByCreateTimeDesc(String customerId, int status, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndStatusAndPrizeStatusAndStationIdOrderByCreateTimeDesc(String customerId, int status, int prizeStatus, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndPrizeStatusAndStationIdOrderByCreateTimeDesc(String customerId, int prizeStatus, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndStationIdOrderByCreateTimeDesc(String customerId, String gameCode, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndStatusAndStationIdOrderByCreateTimeDesc(String customerId, String gameCode, int status, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndStatusAndPrizeStatusAndStationIdOrderByCreateTimeDesc(String customerId, String gameCode, int status, int prizeStatus, String stationId, Pageable p);
//
//    public List<TOrder> findAllByCustomerIdAndGameCodeAndPrizeStatusAndStationIdOrderByCreateTimeDesc(String customerId, String gameCode, int prizeStatus, String stationId, Pageable p);
//
    @Modifying
    @Query("update TOrder t set t.status=?1 where t.id=?2")
    public int updateStatusById(int status, String id);

    @Modifying
    @Query("update TOrder t set t.printCount=?1,t.version=t.version+1 where t.id=?3 and t.version=?2")
    public int updatePrintCountById(int printCount, int version, String id);

    @Modifying
    @Query("update TOrder t set t.status=?1,t.printCount=?2,t.printTime=?3 where t.id=?5 and t.version=?4")
    public int updateStatusAndPrintCountById(int status, int printCount, Date pTime, int version, String id);
    
    @Modifying
    @Query("update TOrder t set t.status=?1 where t.status=?2 and t.gameCode=?3 and t.termCode=?4")
    public int updateStatusByStatusAndGameCodeAndTermCode(int status, int istatus, String gameCode, String termCode);
    
    @Modifying
    @Query("update TOrder t set t.status=?1 where t.gameCode=?2 and t.termCode=?3")
    public int updateStatusByGameCodeAndTermCode(int status, String gameCode, String termCode);
    
    @Modifying
    @Query("delete from TOrder t where t.customerId=?1")
    public int deleteByCustomerId(String customerId);
    
    @Modifying
    @Query("delete from TOrder t where t.stationId=?1")
    public int deleteByStationId(String stationId);
    
    @Modifying
    @Query("update TOrder t set t.status=?1, t.acceptTime=?2 where t.schemeId=?3")
    public int updateStatusAndAcceptTimeBySchemeId(int status, Date acceptTime, String schemeId);
    
    @Modifying
    @Query("update TOrder t set t.status=?1, t.printTime=?2 where t.id=?3")
    public int updateStatusAndPrintTimeById(int status, Date printTime, String id);
    
    @Modifying
    @Query("update TOrder t set t.status=?1, t.acceptTime=?2 where t.id=?3")
    public int updateStatusAndAcceptTimeById(int status, Date acceptTime, String id);
    
    @Modifying
    @Query("update TOrder t set t.bonus=t.bonus+?1 where t.id=?2")
    public int incrBonusById(long bonus, String id);
    
    @Modifying
    @Query("update TOrder t set t.bonus=?1 where t.id=?2")
    public int updateBonusById(long bonus, String id);
    
    @Modifying
    @Query("update TOrder t set t.bonus=t.bonus-?1 where t.id=?2")
    public int decrBonusById(long bonus, String id);
    
    @Query("select t.gameCode, t.termCode, sum(t.amount) as amount from TOrder t  where t.acceptTime >= ?1 and acceptTime <= ?2 group by t.gameCode, t.termCode")
    public List<Object[]> getDailyClearData(Date begin, Date end);
    /*
      分页查询所有投注订单。
   */



}
