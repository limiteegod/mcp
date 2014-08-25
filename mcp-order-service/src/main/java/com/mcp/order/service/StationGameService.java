/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.StationGameDao;
import com.mcp.order.model.admin.StationGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("stationGameService")
public class StationGameService {
	
	@Autowired
	private StationGameDao stationGameDao;
	
	public StationGame save(StationGame stationGame)
	{
		return this.stationGameDao.save(stationGame);
	}
	
	/**
     * 根据条件分页查询
     * @param specs
     * @param p
     * @return
     */
    public Page<StationGame> findAll(Specifications<StationGame> specs, Pageable p) {
        return this.stationGameDao.findAll(specs, p);
    }
    
    
    /**
     * 根据条件查询
     * @param specs
     * @return
     */
    public List<StationGame> findAll(Specifications<StationGame> specs) {
        return this.stationGameDao.findAll(specs);
    }
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public StationGame find(String id)
	{
		return this.stationGameDao.findOne(id);
	}
	
	/**
	 * 根据id删除一条记录
	 * @param id
	 */
	@Transactional
    public void delete(String id) {
        this.stationGameDao.delete(id);
    }
	
	/**
	 * 删除机构的所有游戏
	 * @param stationId
	 */
	@Transactional
    public void deleteByStationId(String stationId) {
        this.stationGameDao.deleteByStationId(stationId);
    }
	
	public StationGame findOneByStationIdAndGameCodeAndStatus(String stationId, String gameCode, int status)
	{
		return this.stationGameDao.findOneByStationIdAndGameCodeAndStatus(stationId, gameCode, status);
	}

    public List<StationGame> findAllByStationIdAndStatus(String stationId,int status){
        return this.stationGameDao.findAllByStationIdAndStatus(stationId,status);
    }
}
