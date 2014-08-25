package com.mcp.order.dao;

import com.mcp.order.model.admin.Conservator;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConservatorDao extends PagingAndSortingRepository<Conservator, String> {
}
