package com.mcp.order.service;/*
 * User: yeeson he
 * Date: 13-8-23
 * Time: 下午4:41
 */

import com.mcp.order.dao.AreaDao;
import com.mcp.order.model.admin.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("areaService")
public class AreaService {
	
    @Autowired
    private AreaDao areaDao;
    
    public Area save(Area area)
    {
    	return this.areaDao.save(area);
    }
    
    public List<Area> findAllByALevelAndStatus(int level,int status){
        return this.areaDao.findAllByALevelAndStatus(level,status);
    }

    public List<Area> findAllByParentId(String parentId){
        return this.areaDao.findAllByParentId(parentId);
    }
}
