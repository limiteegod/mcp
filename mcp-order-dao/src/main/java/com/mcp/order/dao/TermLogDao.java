package com.mcp.order.dao;

import com.mcp.order.model.ts.TermLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TermLogDao extends PagingAndSortingRepository<TermLog, String>, JpaSpecificationExecutor<TermLog> {
    public List<TermLog> findAllByTermIdOrderByHTimeAsc(String termId);
}
