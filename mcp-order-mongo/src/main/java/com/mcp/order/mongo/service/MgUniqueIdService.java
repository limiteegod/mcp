/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.model.mongo.MgLogin;
import com.mcp.order.model.mongo.MgUniqueId;
import com.mcp.order.mongo.common.MgConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ming.li
 *
 */
@Service("mgUniqueIdService")
public class MgUniqueIdService {
	
	private String colName = MgConstants.MG_CUS_UNIQUEID;
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public void save(MgUniqueId mgUniqueId)
    {
        this.mongoTemplate.save(mgUniqueId, colName);
    }

    public MgUniqueId findById(String id)
    {
        return this.mongoTemplate.findById(id, MgUniqueId.class, colName);
    }
}
