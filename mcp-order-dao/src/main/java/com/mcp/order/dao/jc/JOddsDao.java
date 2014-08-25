package com.mcp.order.dao.jc;

import com.mcp.order.model.jc.JOdds;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JOddsDao extends PagingAndSortingRepository<JOdds, String>, JpaSpecificationExecutor<JOdds> {
	
	
}
