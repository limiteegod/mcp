/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.TerminalDao;
import com.mcp.order.model.admin.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("terminalService")
public class TerminalService {
	
	@Autowired
	private TerminalDao terminalDao;
	
	public Terminal save(Terminal terminal)
	{
		return this.terminalDao.save(terminal);
	}
	
	public Terminal findOneByCode(String code)
    {
        return this.terminalDao.findOneByCode(code);
    }
    public List<Terminal> findAllByStationId(String StationId)
    {
        return this.terminalDao.findAllByStationId(StationId);
    }
}
