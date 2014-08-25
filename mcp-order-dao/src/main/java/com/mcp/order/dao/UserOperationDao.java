package com.mcp.order.dao;

import com.mcp.order.model.admin.UserOperation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by hadoop on 2014/5/1.
 */
public interface UserOperationDao extends PagingAndSortingRepository<UserOperation, String>, JpaSpecificationExecutor<UserOperation> {

    @Query("from UserOperation u where u.userType=?1")
    public List<UserOperation> findAllByUserType(int userType);

}
