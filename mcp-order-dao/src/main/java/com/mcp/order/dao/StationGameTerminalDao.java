package com.mcp.order.dao;

import com.mcp.order.model.admin.StationGameTerminal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StationGameTerminalDao extends
		PagingAndSortingRepository<StationGameTerminal, String> {

}
