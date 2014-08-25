package com.mcp.order.dao;

import com.mcp.order.model.admin.TerminalVersion;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TerminalVersionDao extends PagingAndSortingRepository<TerminalVersion, String> {

}
