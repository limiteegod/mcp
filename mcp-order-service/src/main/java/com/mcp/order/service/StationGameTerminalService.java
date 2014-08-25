/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.StationGameTerminalDao;
import com.mcp.order.model.admin.StationGameTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author ming.li
 *
 */
@Service("stationGameTerminalService")
public class StationGameTerminalService {
	
	@Autowired
	private StationGameTerminalDao stationGameTerminalDao;
	
	public StationGameTerminal save(StationGameTerminal stationTerminalGame)
	{
		return this.stationGameTerminalDao.save(stationTerminalGame);
	}
}
