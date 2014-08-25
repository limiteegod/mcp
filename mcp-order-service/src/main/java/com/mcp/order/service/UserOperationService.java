package com.mcp.order.service;

import com.mcp.order.dao.UserOperationDao;
import com.mcp.order.model.admin.UserOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hadoop on 2014/5/1.
 */
@Service("userOperationService")
public class UserOperationService {

    @Autowired
    private UserOperationDao userOperationDao;


    public UserOperation save(UserOperation userOperation)
    {
        return this.userOperationDao.save(userOperation);
    }

    public List<UserOperation> findAllByUserType(int userType)
    {
        return this.userOperationDao.findAllByUserType(userType);
    }


}
