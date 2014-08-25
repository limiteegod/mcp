package com.mcp.order.dao;

import com.mcp.order.model.admin.Terminal;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TerminalDao extends PagingAndSortingRepository<Terminal, String> {
	
	public Terminal findOneByCode(String code);
    public List<Terminal> findAllByStationId(String stationId);
}
