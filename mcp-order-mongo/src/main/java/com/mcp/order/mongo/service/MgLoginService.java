/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.model.mongo.MgAutoIncrId;
import com.mcp.order.model.mongo.MgLogin;
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
@Service("mgLoginService")
public class MgLoginService {
	
	private String colName = MgConstants.MG_CUS_LOGIN;
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public void save(MgLogin mgLogin)
    {
        this.mongoTemplate.save(mgLogin, colName);
    }

    public MgLogin findById(String id)
    {
        return this.mongoTemplate.findById(id, MgLogin.class, colName);
    }

    /**
     * 用户发生操作，激活用户，普通用户和管理员将在半个小时后过期，
     * 渠道用户不会过期。
     * @param id
     * @param type
     */
    public void active(String id, int type)
    {
        Query q = new Query(Criteria.where("_id").is(id));
        Date now = new Date();
        Update update = new Update().set("lastActiveTime", now);
        if(type == SystemUserType.CUSTOMER.getCode() || type == SystemUserType.ADMINISTRATOR.getCode())
        {
            update = update.set("expiredTime", new Date(now.getTime() + Constants.LOGIN_EXPIRE_MILISECOND));
        }
        this.mongoTemplate.findAndModify(q, update, MgLogin.class, colName);
    }
}
