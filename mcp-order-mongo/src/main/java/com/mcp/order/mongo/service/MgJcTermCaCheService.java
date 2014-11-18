package com.mcp.order.mongo.service;

import com.mcp.order.model.mongo.MgJcTermCache;
import com.mcp.order.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by CH on 2014/11/18.
 */
@Service
public class MgJcTermCaCheService {

    private String colName = "mcpJC_termCache";

    @Autowired
    private MongoOperations mongoTemplate;

    public MgJcTermCache findOne(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)) , MgJcTermCache.class, colName);
    }

    public void save(MgJcTermCache mgJcTermCache){
        mongoTemplate.save(mgJcTermCache, colName);
    }

    public void remove(String id){
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), colName);
    }

    public void removeByCreateTime(int mins){
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(DateTimeUtil.getDateBeforeMilliseconds(new Date(), 1000*60*mins))), colName);
    }

}
