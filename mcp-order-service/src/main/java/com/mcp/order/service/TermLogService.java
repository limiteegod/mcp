/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.TermLogDao;
import com.mcp.order.model.ts.TermLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("termLogService")
public class TermLogService {
	
	@Autowired
	private TermLogDao termLogDao;
	
	public TermLog save(TermLog termLog)
	{
		return this.termLogDao.save(termLog);
	}

    public List<TermLog> findAllByTermId(String termId){
         return this.termLogDao.findAllByTermIdOrderByHTimeAsc(termId);
    }
}
