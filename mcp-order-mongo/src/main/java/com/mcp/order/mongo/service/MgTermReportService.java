/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.core.util.cons.TermReportType;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.mongo.common.MgConstants;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
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
@Service("mgTermReportService")
public class MgTermReportService {
	
	@Autowired
	private MongoOperations mongoTemplate;

    /**
     * 保存
     * @param mgTermReport
     */
    public void save(MgTermReport mgTermReport)
    {
        String colName = MgConstants.TERM_REPORT;
        this.mongoTemplate.save(mgTermReport, colName);
    }

    /**
     * 根据id查询
     * @param id
     */
    public MgTermReport findOne(String id)
    {
        String colName = MgConstants.TERM_REPORT;
        return this.mongoTemplate.findById(id, MgTermReport.class, colName);
    }
	
	/**
	 * 根据条件分页查询
	 * @param gameCode
	 * @param termCode
	 * @param channelCode
	 * @param p
	 */
	public Page<MgTermReport> findAll(String gameCode, String termCode, String channelCode, int orderType, int rptType, Pageable p)
	{
		String colName = MgConstants.TERM_REPORT;
		Criteria c = Criteria.where("gameCode").is(gameCode);
		if(!StringUtil.isEmpty(termCode))
		{
			c = c.and("termCode").is(termCode);
		}
		if(!StringUtil.isEmpty(channelCode))
		{
			c = c.and("channelCode").is(channelCode);
		}
        if(orderType > -1)
        {
            c = c.and("type").is(orderType);
        }
        if(rptType > -1)
        {
            c = c.and("rptType").is(rptType);
        }
		Query query = new Query(c);
		List<MgTermReport> mtrList = this.mongoTemplate.find(query.with(p), MgTermReport.class, colName);
		return new PageImpl<MgTermReport>(mtrList, p, this.mongoTemplate.count(query, colName));
	}

    /**
     * 根据（订单）类型查找期次报表信息
     * @param gameCode
     * @param termCode
     * @param orderType
     */
    public List<MgTermReport> findAll(String gameCode, String termCode, TOrderType orderType)
    {
        String colName = MgConstants.TERM_REPORT;
        Criteria c = Criteria.where("gameCode").is(gameCode);
        c = c.and("termCode").is(termCode);
        c = c.and("type").is(orderType.ordinal());
        Query query = new Query(c);
        List<MgTermReport> mtrList = this.mongoTemplate.find(query, MgTermReport.class, colName);
        return mtrList;
    }

    /**
     * 主要用于根据统计口径进行查询
     * @param gameCode
     * @param termCode
     * @param type
     * @return
     */
    public List<MgTermReport> findAll(String gameCode, String termCode, TermReportType type)
    {
        String colName = MgConstants.TERM_REPORT;
        Criteria c = Criteria.where("gameCode").is(gameCode);
        c = c.and("termCode").is(termCode);
        c = c.and("rptType").is(type.ordinal());
        Query query = new Query(c);
        List<MgTermReport> mtrList = this.mongoTemplate.find(query, MgTermReport.class, colName);
        return mtrList;
    }
	
	/**
	 * 查询当前期次的所有channel的总计信息
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public MgTermReport findSum(String gameCode, String termCode)
	{
		String colName = MgConstants.TERM_REPORT;
		
		BasicDBObject dbObject = new BasicDBObject();
		BasicDBObject group = new BasicDBObject("ns", colName);
		group.append("key", new BasicDBObject("gameCode", 1).append("termCode", 1));
		group.append("cond", new BasicDBObject("gameCode", gameCode).append("termCode", termCode));
		StringBuffer sb = new StringBuffer("function ( curr, result ) {");
		sb.append("result.orderPrizeCount += curr.orderPrizeCount;");
		sb.append("result.orderPrizeBonus += curr.orderPrizeBonus;");
		sb.append("result.orderPrizeAmount += curr.orderPrizeAmount;");
		sb.append("result.ticketRefundCount += curr.ticketRefundCount;");
		sb.append("result.ticketRefundAmount += curr.ticketRefundAmount;");
		sb.append("result.ticketHitCount += curr.ticketHitCount;");
		sb.append("result.ticketHitAmount += curr.ticketHitAmount;");
		sb.append("result.ticketHitBonus += curr.ticketHitBonus;");
		sb.append("result.ticketNotHitCount += curr.ticketNotHitCount;");
		sb.append("result.ticketNotHitAmount += curr.ticketNotHitAmount;");
		sb.append("}");
		group.append("$reduce", sb.toString());
		group.append("initial", new BasicDBObject("orderPrizeCount", 0).append("orderPrizeAmount", 0).append("orderPrizeBonus", 0)
				.append("ticketRefundCount", 0).append("ticketRefundAmount", 0).append("ticketHitCount", 0)
				.append("ticketHitAmount", 0).append("ticketHitBonus", 0).append("ticketNotHitCount", 0)
				.append("ticketNotHitAmount", 0));
		dbObject.append("group", group);
		CommandResult cr = mongoTemplate.executeCommand(dbObject);
		BasicDBList list = (BasicDBList)cr.get("retval");
		if(list.size() == 0)
		{
			return new MgTermReport();
		}
		BasicDBObject obj = (BasicDBObject)list.get(0);
		
		MgTermReport mtr = new MgTermReport();
		mtr.setGameCode(gameCode);
		mtr.setTermCode(termCode);
		mtr.setOrderPrizeBonus(obj.getLong("orderPrizeBonus"));
		mtr.setOrderPrizeCount(obj.getLong("orderPrizeCount"));
		mtr.setOrderPrizeAmount(obj.getLong("orderPrizeAmount"));
		mtr.setTicketRefundCount(obj.getLong("ticketRefundCount"));
		mtr.setTicketRefundAmount(obj.getLong("ticketRefundAmount"));
		
		mtr.setTicketHitCount(obj.getLong("ticketHitCount"));
		mtr.setTicketHitAmount(obj.getLong("ticketHitAmount"));
		mtr.setTicketHitBonus(obj.getLong("ticketHitBonus"));
		mtr.setTicketNotHitCount(obj.getLong("ticketNotHitCount"));
		mtr.setTicketNotHitAmount(obj.getLong("ticketNotHitAmount"));
		
		return mtr;
	}
	
	
	/**
	 * 更新返奖信息
	 */
	public void updatePrizeInfo(String gameCode, String termCode, String channelCode, TOrderType orderType, long orderPrizeCount, long orderPrizeAmount, long orderPrizeBonus)
	{
		String colName = MgConstants.TERM_REPORT;
		Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(channelCode).and("type").is(orderType.ordinal()));
		Update update = new Update().set("orderPrizeCount", orderPrizeCount).set("orderPrizeAmount", orderPrizeAmount).set("orderPrizeBonus", orderPrizeBonus);
		this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
	}
	
	/**
	 * 更新出票失败退款信息
	 */
	public void updateTicketRefundInfo(String gameCode, String termCode, String channelCode, TOrderType orderType, long ticketRefundCount, long ticketRefundAmount)
	{
		String colName = MgConstants.TERM_REPORT;
		Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(channelCode).and("type").is(orderType.ordinal()));
		Update update = new Update().set("ticketRefundCount", ticketRefundCount).set("ticketRefundAmount", ticketRefundAmount);
		this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
	}

    /**
     * 更新出票失败退款信息
     */
    public void updateOrderRefundInfo(String gameCode, String termCode, String channelCode, TOrderType orderType, long orderRefundCount, long orderRefundAmount)
    {
        String colName = MgConstants.TERM_REPORT;
        Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(channelCode).and("type").is(orderType.ordinal()));
        Update update = new Update().set("orderRefundCount", orderRefundCount).set("orderRefundAmount", orderRefundAmount);
        this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
    }
	
	/**
	 * 更新中奖票据信息
	 */
	public void updateTicketHitInfo(String gameCode, String termCode, String channelCode, TOrderType orderType, long ticketHitCount, long ticketHitAmount, long ticketHitBonus, long ticketHitBonusBeforeTax)
	{
		String colName = MgConstants.TERM_REPORT;
		Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(channelCode).and("type").is(orderType.ordinal()));
		Update update = new Update().set("ticketHitCount", ticketHitCount).set("ticketHitAmount", ticketHitAmount).set("ticketHitBonus", ticketHitBonus).set("ticketHitBonusBeforeTax", ticketHitBonusBeforeTax);
		this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
	}

    /**
     * 更新票据统计信息
     */
    public void updateTicketInfo(String gameCode, String termCode, String channelCode, TOrderType orderType, long ticketHitCount, long ticketHitAmount, long ticketHitBonus, long ticketHitBonusBeforeTax,
                                 long ticketNotHitCount, long ticketNotHitAmount)
    {
        String colName = MgConstants.TERM_REPORT;
        Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(channelCode).and("type").is(orderType.ordinal()));
        Update update = new Update().set("ticketHitCount", ticketHitCount).set("ticketHitAmount", ticketHitAmount).set("ticketHitBonus", ticketHitBonus).set("ticketHitBonusBeforeTax", ticketHitBonusBeforeTax).set("ticketNotHitCount", ticketNotHitCount).set("ticketNotHitAmount", ticketNotHitAmount);
        this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
    }

    /**
     * 更新订单统计信息
     */
    public void updateOrderInfo(String gameCode, String termCode, String channelCode, TOrderType orderType, long orderHitCount, long orderHitAmount, long orderHitBonus, long orderHitBonusBeforeTax,
                                 long orderNotHitCount, long orderNotHitAmount)
    {
        String colName = MgConstants.TERM_REPORT;
        Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(channelCode).and("type").is(orderType.ordinal()));
        Update update = new Update().set("orderHitCount", orderHitCount).set("orderHitAmount", orderHitAmount).set("orderHitBonus", orderHitBonus).set("orderHitBonusBeforeTax", orderHitBonusBeforeTax).set("orderNotHitCount", orderNotHitCount).set("orderNotHitAmount", orderNotHitAmount);
        this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
    }

    /**
     * 更新出票维度信息
     */
    public void updatePrintInfo(String gameCode, String termCode, String printStationId, long ticketHitCount, long ticketHitAmount, long ticketHitBonus, long ticketHitBonusBeforeTax,
                                long ticketNotHitCount, long ticketNotHitAmount)
    {
        String colName = MgConstants.TERM_REPORT;
        Query query = new Query(Criteria.where("gameCode").is(gameCode).and("termCode").is(termCode).and("channelCode").is(printStationId).and("rptType").is(TermReportType.PRINT.ordinal()));
        Update update = new Update().set("ticketHitCount", ticketHitCount).set("ticketHitAmount", ticketHitAmount).set("ticketHitBonus", ticketHitBonus).set("ticketHitBonusBeforeTax", ticketHitBonusBeforeTax).set("ticketNotHitCount", ticketNotHitCount).set("ticketNotHitAmount", ticketNotHitAmount);
        this.mongoTemplate.upsert(query, update, MgTermReport.class, colName);
    }
}
