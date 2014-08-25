/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.status.OrderState;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("mgOrderService")
public class MgOrderService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	/**
	 * 保存大奖订单
	 * @param order
     * @param termCode
	 */
	public void saveBig(TOrder order, String termCode)
	{
		String colName = MgConstants.TERM_BIG_HIT_ORDER + order.getGameCode() + "_" + termCode;
		mongoTemplate.save(order, colName);
	}
	
	/**
	 * 保存小奖订单
	 * @param order
	 * @param termCode
	 */
	public void saveLittle(TOrder order, String termCode)
	{
		String colName = MgConstants.TERM_LITTLE_HIT_ORDER + order.getGameCode() + "_" + termCode;
		mongoTemplate.save(order, colName);
	}
	
	/**
	 * 分页查找中小奖的订单
	 * @param gameCode
	 * @param termCode
	 * @param p
	 * @return
	 */
	public List<TOrder> findLittle(String gameCode, String termCode, Pageable p)
	{
		String colName = MgConstants.TERM_LITTLE_HIT_ORDER + gameCode + "_" + termCode;
		List<TOrder> oList = mongoTemplate.find(new Query().with(p), TOrder.class, colName);
		return oList;
	}
	
	/**
	 * 返奖之后，删除已经返奖的订单
	 * @param gameCode
	 * @param termCode
	 * @param id
	 */
	public void deleteLittle(String gameCode, String termCode, String id)
	{
		String colName = MgConstants.TERM_LITTLE_HIT_ORDER + gameCode + "_" + termCode;
		this.mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), colName);
	}
	
	/**
	 * 已经返奖的订单
	 * @param order
	 * @param termCode
	 * @return
	 */
	public void savePrized(TOrder order, String termCode)
	{
		String colName = MgConstants.TERM_PRIZEd_ORDER + order.getGameCode() + "_" + termCode;
		this.mongoTemplate.save(order, colName);
	}
	
	
	/**
	 * 获得已经返奖的collection
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public DBCollection getPrizedCollection(String gameCode, String termCode)
	{
		String colName = MgConstants.TERM_PRIZEd_ORDER + gameCode + "_" + termCode;
		return this.mongoTemplate.getCollection(colName);
	}

    /**
     * 根据游戏和期次获取当期等待返大奖的订单collection
     * @param gameCode 游戏代码
     * @param termCode 期次代码
     * @return
     */
    public DBCollection getBigOrderCollection(String gameCode, String termCode)
    {
        String colName = MgConstants.TERM_BIG_HIT_ORDER + gameCode + "_" + termCode;
        return this.mongoTemplate.getCollection(colName);
    }
    /**
     * 根据游戏和期次获取当期未中奖的订单collection
     * @param gameCode 游戏代码
     * @param termCode 期次代码
     * @return
     */
    public DBCollection getNotHitOrderCollection(String gameCode, String termCode)
    {
        String colName = MgConstants.TERM_NOT_HIT_ORDER + gameCode + "_" + termCode;
        return this.mongoTemplate.getCollection(colName);
    }

	
	/**
	 * 未中奖订单
	 * @param order
	 * @param termCode
	 */
	public void saveNotHit(TOrder order, String termCode)
	{
		String colName = MgConstants.TERM_NOT_HIT_ORDER + order.getGameCode() + "_" + termCode;
		mongoTemplate.save(order, colName);
	}
	
	/**
	 * 保存期次流程中退款的订单
	 * @param orderId
	 * @param termCode
	 */
	public void saveRefundedOrder(String orderId, String gameCode, String termCode, String channelCode, TOrderType type,
                                  long amount)
	{
		String colName = MgConstants.TERM_REFUNDED_ORDER + gameCode + "_" + termCode;
        Query query = new Query(Criteria.where("_id").is(orderId));
        Update update = new Update().set("channelCode", channelCode).set("amount", amount).set("type", type.ordinal());
        this.mongoTemplate.upsert(query, update, colName);
	}

    /**
     * 获取订单失败信息
     * @return
     */
    public CommandResult getRefundInfo(String gameCode, String termCode)
    {
        String colName = MgConstants.TERM_REFUNDED_ORDER + gameCode + "_" + termCode;

        BasicDBObject dbObject = new BasicDBObject();
        BasicDBObject group = new BasicDBObject("ns", colName);
        group.append("key", new BasicDBObject("channelCode", 1).append("type", 1));
        group.append("cond", new BasicDBObject());
        group.append("$reduce", "function ( curr, result ) {result.amount += curr.amount;result.count++;}");
        group.append("initial", new BasicDBObject("amount", 0).append("count", 0));
        dbObject.append("group", group);
        CommandResult cr = mongoTemplate.executeCommand(dbObject);
        return cr;
    }
	
	/**
	 * 获得返奖的详细信息
	 * @return
	 */
	public CommandResult getPrizeInfo(String gameCode, String termCode)
	{
		String colName = MgConstants.TERM_PRIZEd_ORDER + gameCode + "_" + termCode;
		
		BasicDBObject dbObject = new BasicDBObject();
		BasicDBObject group = new BasicDBObject("ns", colName);
		group.append("key", new BasicDBObject("channelCode", 1));
		group.append("cond", new BasicDBObject());
		group.append("$reduce", "function ( curr, result ) {result.bonus += curr.bonus;result.amount += curr.amount;result.count++;}");
		group.append("initial", new BasicDBObject("bonus", 0).append("count", 0).append("amount", 0));
		dbObject.append("group", group);
		CommandResult cr = mongoTemplate.executeCommand(dbObject);
		
		return cr;
	}
	
	/**
	 * 保存订单
	 * @param order
	 */
	public void save(TOrder order, String termCode)
	{
		if(order.getStatus() == OrderState.WAITING_BIG_PRIZING.getCode())
		{
			saveBig(order, termCode);
		}
		else if(order.getStatus() == OrderState.HIT.getCode())
		{
			saveLittle(order, termCode);
		}
		else if(order.getStatus() == OrderState.NOT_HIT.getCode())
		{
			saveNotHit(order, termCode);
		}
		else if(order.getStatus() == OrderState.REFUNDED.getCode())
		{
			saveRefundedOrder(order.getId(), order.getGameCode(), termCode, order.getChannelCode(), order.getType(), order.getAmount());
		}
	}

    /**
     * 清除这一期的退款订单信息
     * @param gameCode
     * @param termCode
     */
    public void clear(String gameCode, String termCode)
    {
        String colName = MgConstants.TERM_REFUNDED_ORDER + gameCode + "_" + termCode;
        this.mongoTemplate.dropCollection(colName);
    }

}
