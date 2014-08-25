package com.mcp.order.dao;


import com.mcp.order.model.admin.StationGame;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StationGameDao extends PagingAndSortingRepository<StationGame, String>, JpaSpecificationExecutor<StationGame> {

    public StationGame findOneByStationIdAndGameCodeAndStatus(String stationId, String gameCode, int status);

    public List<StationGame> findAllByStationIdAndStatus(String stationId, int status);
    
    @Modifying
    @Query("delete from StationGame sg where sg.stationId=?1")
    public int deleteByStationId(String stationId);
}
