package com.mcp.order.service;/*
 * User: yeeson he
 * Date: 13-8-23
 * Time: 下午4:41
 */

import com.mcp.order.dao.ConservatorDao;
import com.mcp.order.model.admin.Conservator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("conservatorService")
public class ConservatorService {
	
    @Autowired
    private ConservatorDao conservatorDao;
    
    public Conservator save(Conservator conservator)
    {
    	return this.conservatorDao.save(conservator);
    }
}
