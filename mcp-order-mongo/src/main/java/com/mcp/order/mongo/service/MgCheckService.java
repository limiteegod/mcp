package com.mcp.order.mongo.service;

import com.mcp.core.util.cons.SchemeType;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.mongo.common.MgConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
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
@Service("mgCheckService")
public class MgCheckService {

    private String check_ticket = "check_ticket";

    private String check_order = "check_order";

    private String check_scheme = "check_scheme";

    @Autowired
    private MongoOperations mongoTemplate;

    /**
     * 票据算奖完成
     *
     * @param ticketId
     * @param gameCode
     * @param termCode
     * @return
     */
    public void ticketChecked(String ticketId, String gameCode, String termCode, String channelCode,
                              String dNumber, long bonus, long bonusBeforeTax, long amount, TOrderType type, String printStationId) {
        Query query = new Query(Criteria.where("_id").is(ticketId));
        Update update = new Update().inc("bonus", Long.valueOf(bonus)).inc("bonusBeforeTax", Long.valueOf(bonusBeforeTax)).set("dNumber", dNumber).set("channelCode", channelCode).set("amount", amount).set("type", type.ordinal()).set("printStationId", printStationId);
        this.mongoTemplate.upsert(query, update, getTicketColl(gameCode, termCode));
    }

    /**
     * 订单中奖
     * @param orderId
     * @param gameCode
     * @param termCode
     * @param dNumber
     * @param bonus
     * @param bonusBeforeTax
     * @param finishedTicketCountInDb   数据库已经完成的订单数目
     * @param ticketCountInDb   数据库中订单的总票数
     */
    public void orderChecked(String orderId, String gameCode, String termCode, String channelCode,
                              String dNumber, long bonus, long bonusBeforeTax, int finishedTicketCountInDb,
                              int ticketCountInDb, String customerId, TOrderType type, String stationId, SchemeType schemeType,
                              long amount, String outerId)
    {
        Query query = new Query(Criteria.where("_id").is(orderId));
        Update update = new Update().inc("ticketCount", 1).inc("bonus", Long.valueOf(bonus)).inc("bonusBeforeTax", Long.valueOf(bonusBeforeTax)).set("dNumber", dNumber).set("channelCode", channelCode).set("finishedTicketCountInDb", finishedTicketCountInDb).set("ticketCountInDb", ticketCountInDb).set("customerId", customerId).set("type", type.ordinal()).set("stationId", stationId).set("schemeType", schemeType.getCode()).set("amount", amount).set("outerId", outerId);
        this.mongoTemplate.upsert(query, update, getOrderColl(gameCode, termCode));
    }

    /**
     * 方案算奖
     * @param schemeId
     * @param gameCode
     * @param bonus
     * @param bonusBeforeTax
     */
    public void schemeChecked(String schemeId, String gameCode, String termCode, String channelCode, long bonus, long bonusBeforeTax, SchemeType schemeType)
    {
        Query query = new Query(Criteria.where("_id").is(schemeId));
        Update update = new Update().inc("orderCount", Long.valueOf(1)).inc("bonus", Long.valueOf(bonus)).inc("bonusBeforeTax", Long.valueOf(bonusBeforeTax)).set("schemeType", schemeType.getCode()).set("channelCode", channelCode);
        this.mongoTemplate.upsert(query, update, getSchemeColl(gameCode, termCode, schemeType));
    }

    /**
     * 获取票据算奖信息
     * @return
     */
    public CommandResult getTicketInfo(String gameCode, String termCode)
    {
        String colName = getTicketColl(gameCode, termCode);
        BasicDBObject dbObject = new BasicDBObject();
        BasicDBObject group = new BasicDBObject("ns", colName);
        group.append("key", new BasicDBObject("channelCode", 1).append("type", 1));
        group.append("cond", new BasicDBObject());
        group.append("$reduce", "function ( curr, result ) {result.bonus += curr.bonus;result.bonusBeforeTax += curr.bonusBeforeTax;if(curr.bonus > 0){result.hitCount++;result.hitAmount += curr.amount;}else{result.notHitCount++;result.notHitAmount += curr.amount;}}");
        group.append("initial", new BasicDBObject("hitCount", 0).append("hitAmount", 0).append("notHitCount", 0).append("notHitAmount", 0).append("bonus", 0).append("bonusBeforeTax", 0));
        dbObject.append("group", group);
        CommandResult cr = mongoTemplate.executeCommand(dbObject);
        return cr;
    }

    /**
     * 获取票据算奖信息
     * @return
     */
    public CommandResult getPrintInfo(String gameCode, String termCode)
    {
        String colName = getTicketColl(gameCode, termCode);
        BasicDBObject dbObject = new BasicDBObject();
        BasicDBObject group = new BasicDBObject("ns", colName);
        group.append("key", new BasicDBObject("printStationId", 1));
        group.append("cond", new BasicDBObject());
        group.append("$reduce", "function ( curr, result ) {result.bonus += curr.bonus;result.bonusBeforeTax += curr.bonusBeforeTax;if(curr.bonus > 0){result.hitCount++;result.hitAmount += curr.amount;}else{result.notHitCount++;result.notHitAmount += curr.amount;}}");
        group.append("initial", new BasicDBObject("hitCount", 0).append("hitAmount", 0).append("notHitCount", 0).append("notHitAmount", 0).append("bonus", 0).append("bonusBeforeTax", 0));
        dbObject.append("group", group);
        CommandResult cr = mongoTemplate.executeCommand(dbObject);
        return cr;
    }

    /**
     * 获取订单算奖信息
     * @return
     */
    public CommandResult getOrderInfo(String gameCode, String termCode)
    {
        String colName = getOrderColl(gameCode, termCode);
        BasicDBObject dbObject = new BasicDBObject();
        BasicDBObject group = new BasicDBObject("ns", colName);
        group.append("key", new BasicDBObject("channelCode", 1).append("type", 1));
        group.append("cond", new BasicDBObject());
        group.append("$reduce", "function ( curr, result ) {result.bonus += curr.bonus;result.bonusBeforeTax += curr.bonusBeforeTax;if(curr.bonus > 0){result.hitCount++;result.hitAmount += curr.amount;}else{result.notHitCount++;result.notHitAmount += curr.amount;}}");
        group.append("initial", new BasicDBObject("hitCount", 0).append("hitAmount", 0).append("notHitCount", 0).append("notHitAmount", 0).append("bonus", 0).append("bonusBeforeTax", 0));
        dbObject.append("group", group);
        CommandResult cr = mongoTemplate.executeCommand(dbObject);
        return cr;
    }

    public String getTicketColl(String gameCode, String termCode)
    {
        return String.format("%s_%s_%s", check_ticket, gameCode, termCode);
    }

    public String getOrderColl(String gameCode, String termCode)
    {
        return String.format("%s_%s_%s", check_order, gameCode, termCode);
    }

    public String getSchemeColl(String gameCode, String termCode, SchemeType schemeType)
    {
        return String.format("%s_%s_%s_%s", check_scheme, schemeType.getFlag(), gameCode, termCode);
    }

    /**
     * 清除算奖的collections
     * @param gameCode
     * @param termCode
     */
    public void clear(String gameCode, String termCode)
    {
        String ticketColl = getTicketColl(gameCode, termCode);
        String orderColl = getOrderColl(gameCode, termCode);
        this.mongoTemplate.dropCollection(ticketColl);
        this.mongoTemplate.dropCollection(orderColl);
        SchemeType[] sTypes = SchemeType.values();
        for(SchemeType st:sTypes)
        {
            String schemeColl = getSchemeColl(gameCode, termCode, st);
            this.mongoTemplate.dropCollection(schemeColl);
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
