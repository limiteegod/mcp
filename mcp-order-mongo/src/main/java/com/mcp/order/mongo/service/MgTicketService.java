/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.mongo.MgAutoIncrId;
import com.mcp.order.model.mongo.MgJcCheckTicket;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.mongo.common.MgConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
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
@Service("mgTicketService")
public class MgTicketService {
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	@Autowired
	private MgAutoIncrIdService mgAutoIncrIdService;

    /**
     * 保存在期次流程中退款的票据，ticket虽然有gamecode和termCode属性，但是竞彩游戏ticket中的termCode包含好几个期次
     * @param ticketId
     * @param gameCode
     * @param termCode
     */
    public void saveRefundedTicket(String ticketId, String gameCode, String termCode, String channelCode, TOrderType type,
                                   long amount)
    {
        String colName = MgConstants.TERM_REFUNDED_TICKET + gameCode + "_" + termCode;
        Query query = new Query(Criteria.where("_id").is(ticketId));
        Update update = new Update().set("channelCode", channelCode).set("amount", amount).set("type", type.ordinal());
        this.mongoTemplate.upsert(query, update, colName);
    }
	
	/**
	 * 分页查找出票成功的竞彩票据
	 * @param gameCode
	 * @return
	 */
	public Page<MgJcCheckTicket> findSuccessTicket(String gameCode, Pageable p)
	{
		String colName = MgConstants.TERM_SUCCESS_TICKET + gameCode;
		Query query = new Query();
		List<MgJcCheckTicket> tList = this.mongoTemplate.find(query.with(p), MgJcCheckTicket.class, colName);
		return new PageImpl<MgJcCheckTicket>(tList, p, this.mongoTemplate.count(query, colName));
	}
	
	/**
	 * 保存票据
	 * @param ticket
	 */
	public void save(TTicket ticket, int gameType)
	{
		String colName;
		String gameCode = ticket.getGameCode();
		if(gameType == ConstantValues.Game_Type_Jingcai.getCode())
		{
			colName = MgConstants.TERM_SUCCESS_TICKET + gameCode;
			
			MgJcCheckTicket mjct = new MgJcCheckTicket();
			MgAutoIncrId mgAutoIncrId = mgAutoIncrIdService.getAutoIdAndIncrByName(MgConstants.MG_JC_CHECK_TICKET_ID);
			mjct.setId(mgAutoIncrId.getValue());
			mjct.setTicketId(ticket.getId());
			mjct.setTermCode(ticket.getTermCode());
			mjct.setFinishedCount(ticket.getFinishedCount());
			
			mongoTemplate.save(mjct, colName);
		}
		else
		{
			String termCode = ticket.getTermCode();
			colName = MgConstants.TERM_DRAW_TICKET + gameCode + "_" + termCode;
			mongoTemplate.save(ticket, colName);
		}
	}
	
	/**
	 * 竞彩保存需要算奖的订单
	 * @param ticket
	 */
	public void jcSaveDraw(TTicket ticket, String termCode)
	{
		String colName = MgConstants.TERM_DRAW_TICKET + ticket.getGameCode() + "_" + termCode;
		mongoTemplate.save(ticket, colName);
	}

    /**
     * 更新竞彩出票成功的票的termCode，并增加finishedCount，以免重新算奖时，多增加票的扫描次数
     * @param id
     * @param gameCode
     * @param newTermCode
     */
    public void jcUpdateTermCode(long id, String gameCode, String newTermCode)
    {
        String sucColl = this.getJcSuccessColl(gameCode);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("termCode", newTermCode).inc("finishedCount", 1);
        this.mongoTemplate.upsert(query, update, sucColl);
    }
	
	/**
	 * 分页查询需要算奖的票据
	 * @param gameCode
	 * @param termCode
	 * @param p
	 * @return
	 */
	public List<TTicket> findFromDraw(String gameCode, String termCode, Pageable p)
	{
		String colName = MgConstants.TERM_DRAW_TICKET + gameCode + "_" + termCode;
		List<TTicket> oList = mongoTemplate.find(new Query().with(p), TTicket.class, colName);
		return oList;
	}

    /**
     * 获得等待算奖的collection
     * @param gameCode
     * @param termCode
     * @return
     */
    public DBCollection getDrawCollection(String gameCode, String termCode)
    {
        String colName = MgConstants.TERM_DRAW_TICKET + gameCode + "_" + termCode;
        return this.mongoTemplate.getCollection(colName);
    }
	
	/**
	 * 算奖之后，从
	 * @param gameCode
	 * @param termCode
	 * @param id
	 */
	public void deleteFromDraw(String gameCode, String termCode, String id)
	{
		String colName = MgConstants.TERM_DRAW_TICKET + gameCode + "_" + termCode;
		this.mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), colName);
	}
	
	/**
	 * 分页查询竞彩出票成功的票
	 * @param gameCode
	 * @param termCode
	 * @param minId
	 * @param p
	 * @return
	 */
	public List<MgJcCheckTicket> findToDraw(String gameCode, String termCode, long minId, Pageable p)
	{
		String colName =  MgConstants.TERM_SUCCESS_TICKET + gameCode;
		List<MgJcCheckTicket> oList = mongoTemplate.find(new Query(Criteria.where("_id").gt(minId).and("termCode").regex(termCode)).with(p), MgJcCheckTicket.class, colName);
		return oList;
	}

    /**
     * 获得竞彩的出票成功的记录集
     * @param gameCode
     * @return
     */
    public DBCollection getJcSuccessCollection(String gameCode)
    {
        String colName =  MgConstants.TERM_SUCCESS_TICKET + gameCode;
        return this.mongoTemplate.getCollection(colName);
    }
	
	/**
	 * 当竞彩的票已经完成时，删除竞彩的一张票
	 */
	public void removeJc(String gameCode, long id)
	{
		String colName =  MgConstants.TERM_SUCCESS_TICKET + gameCode;
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), colName);
	}
	
	/**
	 * 保存中奖的票据
	 * @param t
	 * @param termCode
	 */
	public void saveHit(TTicket t, String termCode)
	{
		String colName = MgConstants.TERM_HIT_TICKET + t.getGameCode() + "_" + termCode;
		mongoTemplate.save(t, colName);
	}
	
	/**
	 * 保存未中奖的票据
	 * @param t
	 * @param termCode
	 */
	public void saveNotHit(TTicket t, String termCode)
	{
		String colName = MgConstants.TERM_NOT_HIT_TICKET + t.getGameCode() + "_" + termCode;
		mongoTemplate.save(t, colName);
	}
	
	/**
	 * 获取票据失败信息
	 * @return
	 */
	public CommandResult getRefundInfo(String gameCode, String termCode)
	{
		String colName = MgConstants.TERM_REFUNDED_TICKET + gameCode + "_" + termCode;
		
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
	 * 获取票据失败信息
	 * @return
	 */
	public CommandResult getHitInfo(String gameCode, String termCode)
	{
		String colName = MgConstants.TERM_HIT_TICKET + gameCode + "_" + termCode;
		
		BasicDBObject dbObject = new BasicDBObject();
		BasicDBObject group = new BasicDBObject("ns", colName);
		group.append("key", new BasicDBObject("channelCode", 1));
		group.append("cond", new BasicDBObject());
		group.append("$reduce", "function ( curr, result ) {result.amount += curr.amount;result.bonus += curr.bonus;result.count++;}");
		group.append("initial", new BasicDBObject("amount", 0).append("count", 0).append("bonus", 0));
		dbObject.append("group", group);
		CommandResult cr = mongoTemplate.executeCommand(dbObject);
		
		return cr;
	}
	
	/**
	 * 获取票据失败信息
	 * @return
	 */
	public CommandResult getNotHitInfo(String gameCode, String termCode)
	{
		String colName = MgConstants.TERM_NOT_HIT_TICKET + gameCode + "_" + termCode;
		
		BasicDBObject dbObject = new BasicDBObject();
		BasicDBObject group = new BasicDBObject("ns", colName);
		group.append("key", new BasicDBObject("channelCode", 1));
		group.append("cond", new BasicDBObject());
		group.append("$reduce", "function ( curr, result ) {result.amount += curr.amount;result.count++;}");
		group.append("initial", new BasicDBObject("amount", 0).append("count", 0));
		dbObject.append("group", group);
		CommandResult cr = mongoTemplate.executeCommand(dbObject);
		
		return cr;
	}

    /**
     * 获得算奖的collection
     * @param gameCode
     * @param termCode
     * @return
     */
    public String getColl(String gameCode, String termCode)
    {
        return String.format("%s%s_%s", MgConstants.TERM_DRAW_TICKET, gameCode, termCode);
    }

    /**
     * 获得已经出票成功的竞彩的集合名称
     * @param gameCode
     * @return
     */
    public String getJcSuccessColl(String gameCode)
    {
        return String.format("%s%s", MgConstants.TERM_SUCCESS_TICKET, gameCode);
    }

    /**
     * 根据名称获得集合
     * @param name
     * @return
     */
    public DBCollection getCollByName(String name)
    {
        return this.mongoTemplate.getCollection(name);
    }

    /**
     * 清理游戏期次相关信息
     */
    public void clear(String gameCode, String termCode)
    {
        //删除算奖的票据
        String colName = getColl(gameCode, termCode);
        this.mongoTemplate.dropCollection(colName);

        //删除退款的票据
        String refundedColName = MgConstants.TERM_REFUNDED_TICKET + gameCode + "_" + termCode;
        this.mongoTemplate.dropCollection(refundedColName);
    }

}
