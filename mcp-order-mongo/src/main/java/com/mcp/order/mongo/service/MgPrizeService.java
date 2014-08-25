package com.mcp.order.mongo.service;

import com.mcp.core.util.cons.BonusLevelType;
import com.mcp.core.util.cons.TOrderType;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Created by limitee on 2014/7/31.
 */
@Service("mgPrizeService")
public class MgPrizeService {


    private String prize_order = "prize_order";

    @Autowired
    private MongoOperations mongoTemplate;

    /**
     * 订单中奖
     * @param orderId
     * @param gameCode
     * @param termCode
     */
    public void orderPrized(String orderId, String gameCode, String termCode, String channelCode, String customerId, TOrderType type, String stationId, long bonus, long bonusBeforeTax, int schemeType, String outerId, BonusLevelType bonusLevelType)
    {
        Query query = new Query(Criteria.where("_id").is(orderId));
        Update update = new Update().set("customerId", customerId).set("type", type.ordinal()).set("stationId", stationId).set("bonus", bonus).set("bonusBeforeTax", bonusBeforeTax).set("schemeType", schemeType).set("outerId", outerId).set("channelCode", channelCode);
        this.mongoTemplate.upsert(query, update, getOrderColl(gameCode, termCode, bonusLevelType));
    }

    public String getOrderColl(String gameCode, String termCode, BonusLevelType bonusLevelType)
    {
        return String.format("%s_%s_%s_%s", prize_order, bonusLevelType.getFlag(), gameCode, termCode);
    }

    /**
     * 清除算奖的collections
     * @param gameCode
     * @param termCode
     */
    public void clear(String gameCode, String termCode)
    {
        BonusLevelType[] typeArray = BonusLevelType.values();
        for(BonusLevelType type:typeArray)
        {
            String orderColl = getOrderColl(gameCode, termCode, type);
            this.mongoTemplate.dropCollection(orderColl);
        }
    }

    /**
     * 通过名称获得coll
     * @param name
     * @return
     */
    public DBCollection getCollByName(String name)
    {
        return this.mongoTemplate.getCollection(name);
    }
}
