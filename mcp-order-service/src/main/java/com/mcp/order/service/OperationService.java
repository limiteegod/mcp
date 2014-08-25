package com.mcp.order.service;

import com.mcp.order.dao.OperationDao;
import com.mcp.order.model.admin.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hadoop on 2014/5/1.
 */
@Service("operationService")
public class OperationService {

    @Autowired
    private OperationDao operationDao;

    public Operation save(Operation operation)
    {
        return this.operationDao.save(operation);
    }

    public Operation findOneById(String id)
    {
        return this.operationDao.findOne(id);
    }

    public Iterable<Operation> findAll()
    {
        return this.operationDao.findAll();
    }
}
