/**
 *
 */
package com.mcp.order.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.dao.StationDao;
import com.mcp.order.dao.StationGameDao;
import com.mcp.order.dao.StationGameTerminalDao;
import com.mcp.order.dao.TerminalDao;
import com.mcp.order.dao.specification.StationSpecification;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.admin.StationGame;
import com.mcp.order.model.admin.StationGameTerminal;
import com.mcp.order.model.admin.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ming.li
 */
@Service("stationService")
public class StationService {
	
	//private static Logger log = Logger.getLogger(StationService.class);
	
    @Autowired
    private StationDao stationDao;
    
    @Autowired
    private TerminalDao terminalDao;
    
    @Autowired
    private StationGameDao stationGameDao;
    
    @Autowired
    private StationGameTerminalDao stationGameTerminalDao;

    public Station findOne(String id) {
        return this.stationDao.findOne(id);
    }

    public Station save(Station station) {
        return this.stationDao.save(station);
    }
    
    public Page<Station> findAll(Specification<Station> spec, Pageable p) {
        return this.stationDao.findAll(spec, p);
    }
    
    public List<Station> findAll(Specification<Station> spec) {
        return this.stationDao.findAll(spec);
    }

    /*public List<Station> findAllByStationAndGap(double longitudeNTU, double lonNTUGap, double latitudeNTU, double latNTUGap) {
        return this.stationDao.findAllByStationAndGap(longitudeNTU, lonNTUGap, latitudeNTU, latNTUGap);
    }*/

    public List<Station> findAllByStationNameLike(String stationName) {
        return this.stationDao.findAllByNameLike(stationName);
    }
    
    public Station findOneByCode(String code)
    {
    	return this.stationDao.findOneByCode(code);
    }

    @Transactional
    public Station saveAllStationInfos(Station station, Map<String, Terminal> terminalMap, Map<String, StationGame> stationGameMap, Map<String, StationGameTerminal> stationGameTerminalMap) {
        Station stationForReturn = new Station();
        terminalDao.save(terminalMap.values());
        stationGameDao.save(stationGameMap.values());
        stationGameTerminalDao.save(stationGameTerminalMap.values());
        stationDao.save(station);
        return stationForReturn;
    }
    
    public Page<Station> customerQuery(double longitude, double lonGap, double latitude, double latGap, int startIndex, int size)
    {
    	Sort sort = new Sort(new Order(Direction.DESC, "code"));
		PageRequest pr = new PageRequest(startIndex, size, sort);
    	return query(null, longitude, lonGap, latitude, latGap, null, pr);
    }
    
    public Page<Station> customerQuery(String pattern, int startIndex, int size)
    {
    	Sort sort = new Sort(new Order(Direction.DESC, "code"));
		PageRequest pr = new PageRequest(startIndex, size, sort);
    	return query(null, 0, 0, 0, 0, pattern, pr);
    }
    
    public Page<Station> query(String id, double longitude, double lonGap, double latitude, double latGap, String pattern, Pageable p)
    {
    	Specifications<Station> specs;
    	if(!StringUtil.isEmpty(id))
    	{
    		specs = Specifications.where(StationSpecification.isIdEqual(id));
    	}
    	else if(!StringUtil.isEmpty(pattern))
    	{
    		specs = Specifications.where(StationSpecification.isNameLike(pattern));
    		specs = specs.or(StationSpecification.isDescriptionLike(pattern));
    	}
    	else
    	{
    		specs = Specifications.where(StationSpecification.isLongitudeNear(longitude, lonGap));
    		specs = specs.and(StationSpecification.isLatitudeNear(latitude, latGap));
    	}
    	return stationDao.findAll(specs, p);
    }
    
    public List<Station> findAll() {
        return this.stationDao.findAll();
    }
}
