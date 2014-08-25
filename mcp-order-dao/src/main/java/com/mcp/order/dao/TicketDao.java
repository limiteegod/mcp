/**
 *
 */
package com.mcp.order.dao;

import com.mcp.order.model.ts.TTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 */
public interface TicketDao extends PagingAndSortingRepository<TTicket, String>, JpaSpecificationExecutor<TTicket> {

    @Query("select count(*) from TTicket t where t.orderId=?1 and t.status=?2")
    public long getCountByOrderIdAndStatus(String orderId, int status);

    @Modifying
    @Query("delete from TTicket t where t.orderId = ?")
    public void deleteByOrderId(String orderId);

    @Modifying
    @Query("delete from TTicket t where t.customerId=?1")
    public int deleteByCustomerId(String customerId);

    @Modifying
    @Query("delete from TTicket t where t.stationId=?1")
    public int deleteByStationId(String stationId);

    @Modifying
    @Query("update TTicket tt set tt.bonus=?1, tt.receiptStatus=?2 where tt.id=?3")
    public int updateCheckInfo(long bonus, int receiptStatus, String id);

    @Modifying
    @Query("update TTicket tt set tt.id=?1 where tt.id=?2")
    public int updateIdById(String newId, String oldId);

    @Modifying
    @Query("update TTicket tt set tt.printerStationId=?1 where tt.id=?2")
    public int updatePrintStationIdById(String printerStationId, String id);

    @Query("update TTicket tt set tt.bonus=?1 where tt.id=?2")
    public int updateCheckInfo(int bonus, String id);

    @Modifying
    @Query("update TTicket tt set tt.status=?1 where tt.id=?2")
    public int updateStatusById(int status, String id);
    
    @Modifying
    @Query("update TTicket tt set tt.status=?1 where tt.orderId=?2")
    public int updateStatusByOrderId(int status, String orderId);
    
    @Modifying
    @Query("update TTicket tt set tt.status=?1 where tt.orderId=?2 and tt.seq=?3")
    public int updateStatusByOrderIdAndSeq(int status, String orderId, int seq);
    
    public TTicket findOneByOrderIdAndSeq(String orderId, int seq);
    
    @Modifying
    @Query("update TTicket tt set tt.status=?1 where tt.status=?2 and tt.gameCode=?3 and tt.termCode like ?4")
    public int updateStatusByStatusAndGameCodeAndTermCode(int status, int istatus, String gameCode, String termCode);
    
    @Modifying
    @Query("update TTicket tt set tt.status=?1 where tt.gameCode=?2 and tt.termCode=?3")
    public int updateStatusByGameCodeAndTermCode(int status, String gameCode, String termCode);

    @Modifying
    @Query("update TTicket tt set tt.receiptStatus=?1 where tt.id=?2")
    public int updateReceiptStatusById(int receiptStatus, String id);
    
    @Modifying
    @Query("update TTicket tt set tt.status=?1, tt.receiptStatus=?2 where tt.id=?3")
    public int updateStatusAndReceiptStatusById(int status, int receiptStatus, String id);

    @Modifying
    @Query("update TTicket tt set tt.status=?1 where tt.status=?2 and tt.printerStationId=?3")
    public int updateStatusByStatusAndPrinterStationId(int statusToSet, int status, String printerStationId);

    @Modifying
    @Query("update TTicket tt set tt.receiptStatus=?1 where tt.receiptStatus=?2 and tt.printerStationId=?3")
    public int updateReceiptStatusByReceiptStatusAndPrinterStationId(int receiptStatusToSet, int receiptStatus, String printerStationId);
    
    public List<TTicket> findAllByOrderId(String orderId);

    public List<TTicket> findAllByStationIdAndStatusOrderByAcceptTimeAsc(String stationId, int status, Pageable p);

    public Page<TTicket> findAllByGameCode(String gameCode, Pageable pageable);

    public Page<TTicket> findAllByGameCodeAndTermCode(String gameCode, String termCode, Pageable pageable);

    public Page<TTicket> findAllByReceiptStatus(int receiptStatus, Pageable pageable);

    public Page<TTicket> findAllByGameCodeAndReceiptStatus(String gameCode, int receiptStatus, Pageable pageable);

    public Page<TTicket> findAllByGameCodeAndTermCodeAndReceiptStatus(String gameCode, String termCode, int receiptStatus, Pageable pageable);

    public Page<TTicket> findAllByStationIdAndReceiptStatus(String stationId, int receiptStatus, Pageable pageable);

    public Page<TTicket> findAllByStationIdAndGameCodeAndReceiptStatus(String stationId, String gameCode, int receiptStatus, Pageable pageable);

    public Page<TTicket> findAllByStationIdAndGameCodeAndTermCodeAndReceiptStatus(String stationId, String gameCode, String termCode, int receiptStatus, Pageable pageable);

    public Page<TTicket> findAllByStationIdAndStatus(String stationId, int status, Pageable page);

    public Page<TTicket> findAllByStationIdAndStatusAndPrintTimeAfter(String stationId, int status, Date today, Pageable page);

    @Query("select sum(t.amount) from TTicket t where t.stationId=?1 and t.status=?2 and t.printTime is not null and t.printTime>?3")
    public Object getTodayCountByStationIdAndStatus(String stationId, int status, Date today);

    public List<TTicket> findAllByTerminalIdAndGameCodeAndTermCodeAndReceiptStatusAndBigBonusOrderByAcceptTimeAsc(
            String terminalId, String gameCode, String termCode, int receiptStatus, boolean bigBonus, Pageable pageable);
}
