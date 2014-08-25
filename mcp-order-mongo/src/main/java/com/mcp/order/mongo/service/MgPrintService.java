/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.model.mongo.MgNotifyMsg;
import com.mcp.order.model.mongo.MgPrint;
import com.mcp.order.mongo.common.MgConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author ming.li
 *
 */
@Service("mgPrintService")
public class MgPrintService {

    public static Logger log = Logger.getLogger(MgPrintService.class);
	
	@Autowired
	private MongoOperations mongoTemplate;

    /**
     * 保存出票信息
     * @param code
     * @param mgPrint
     */
    public void save(String code, MgPrint mgPrint)
    {
        this.mongoTemplate.save(mgPrint, getColName(code));
    }

    /**
     * 删除code对应的队列中，小于id的所有记录
     * @param code
     * @param id
     */
    public void deleteAllByIdLessThanOrEqualTo(String code, long id)
    {
        Criteria c = Criteria.where("_id").lte(id);
        Query query = new Query(c);
        this.mongoTemplate.remove(query, getColName(code));
    }

    /**
     * 分页查找的消息
     * @param code
     * @param page
     * @return
     */
    public Page<MgPrint> find(String code, Pageable page)
    {
        String colName = getColName(code);
        Query query = new Query().with(page);
        List<MgPrint> oList = mongoTemplate.find(query, MgPrint.class, colName);
        return new PageImpl<MgPrint>(oList, page, this.mongoTemplate.count(query, colName));
    }

    /**
     * 获得集合的名称
     * @param code
     * @return
     */
    private String getColName(String code)
    {
        return MgConstants.PRINT_QUEEN + code;
    }

}
