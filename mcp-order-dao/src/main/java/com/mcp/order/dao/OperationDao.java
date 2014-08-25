package com.mcp.order.dao;

import com.mcp.order.model.admin.Operation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by hadoop on 2014/5/1.
 */
public interface OperationDao extends PagingAndSortingRepository<Operation, String>, JpaSpecificationExecutor<Operation> {

}
