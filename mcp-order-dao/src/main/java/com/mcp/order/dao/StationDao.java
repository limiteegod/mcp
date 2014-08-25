package com.mcp.order.dao;

import com.mcp.order.model.admin.Station;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StationDao extends PagingAndSortingRepository<Station, String>, JpaSpecificationExecutor<Station> {

   /* @Query("select s from Station s where s.name=? and ABS(?-s.longitude)< ? and abs(?-s.latitude)<?")
    public List<Station> findAllByStationAndGap(double longitudeNTU, double lonNTUGap, double latitudeNTU, double latNTUGap);*/

    public List<Station> findAllByNameLike(String name);

    public List<Station> findAll();

    public Station findOneByCode(String code);
	
	@Modifying
    @Query("update Station cs set cs.balance=cs.balance+?1 where cs.id=?2")
	public int incrBalanceById(long money, String id);
	
	@Modifying
    @Query("update Station cs set cs.balance=cs.balance-?1 where cs.id=?2")
	public int decrBalanceById(long money, String id);

}
