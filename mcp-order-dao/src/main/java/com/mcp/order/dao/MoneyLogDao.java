package com.mcp.order.dao;

import com.mcp.order.model.ts.MoneyLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MoneyLogDao extends PagingAndSortingRepository<MoneyLog, String>,JpaSpecificationExecutor<MoneyLog> {
    @Query("select sum(c.amount) from MoneyLog c where c.userId= ?1 and c.handlerCode = ?2 and c.acceptTimeStamp>?3")
    public Object getStationTodayRecharge(String stationId,String handlerCode,long acceptTimeStamp);
}
