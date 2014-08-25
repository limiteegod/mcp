package com.mcp.order.service;/*
 * User: yeeson he
 * Date: 13-8-23
 * Time: 下午4:41
 */

import com.mcp.order.dao.AdminiDao;
import com.mcp.order.dao.AreaDao;
import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.admin.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminiService")
public class AdminiService {
	
    @Autowired
    private AdminiDao adminiDao;

    public Admini save(Admini admini)
    {
        return this.adminiDao.save(admini);
    }

    public Admini findOneByNameAndType(String name, int type)
    {
        return this.adminiDao.findOneByNameAndType(name, type);
    }


    public Admini findOneByNameAndPassword(String name, String password)
    {
        return this.adminiDao.findOneByNameAndPassword(name, password);
    }
}
