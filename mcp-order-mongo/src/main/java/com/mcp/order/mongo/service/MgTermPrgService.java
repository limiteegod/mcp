/**
 * 
 */
package com.mcp.order.mongo.service;

import com.mcp.order.model.mongo.MgTermPrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author ming.li
 *
 */
@Service("mgTermPrgService")
public class MgTermPrgService {
	
	private String colName = "term_prg";
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	public void initSychronize(String gameCode, String termCode, int allCount)
	{
		String all = "sychronize_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = new MgTermPrg();
		mtp.setId(all);
		mtp.setCur(allCount);
		mongoTemplate.save(mtp, colName);
		
		String finished = "sychronize_" + gameCode + "_" + termCode + "_finished";
		mtp = new MgTermPrg();
		mtp.setId(finished);
		mtp.setCur(0);
		mongoTemplate.save(mtp, colName);
	}
	
	public int incSychronize(String gameCode, String termCode, int step)
	{
		String finished = "sychronize_" + gameCode + "_" + termCode + "_finished";
		MgTermPrg mtp = mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(finished)), new Update().inc("cur", Integer.valueOf(step)), MgTermPrg.class, colName);
		return mtp.getCur() + step;
	}
	
	public int querySychronizeAll(String gameCode, String termCode)
	{
		String all = "sychronize_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(all)), MgTermPrg.class, colName);
		return mtp.getCur();
	}
	
	public void initPrize(String gameCode, String termCode, int allCount)
	{
		String all = "prize_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = new MgTermPrg();
		mtp.setId(all);
		mtp.setCur(allCount);
		mongoTemplate.save(mtp, colName);
		
		String finished = "prize_" + gameCode + "_" + termCode + "_finished";
		mtp = new MgTermPrg();
		mtp.setId(finished);
		mtp.setCur(0);
		mongoTemplate.save(mtp, colName);
	}
	
	public int incPrize(String gameCode, String termCode, int step)
	{
		String finished = "prize_" + gameCode + "_" + termCode + "_finished";
		MgTermPrg mtp = mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(finished)), new Update().inc("cur", Integer.valueOf(step)), MgTermPrg.class, colName);
		return mtp.getCur() + step;
	}
	
	public int queryPrizeAll(String gameCode, String termCode)
	{
		String all = "prize_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(all)), MgTermPrg.class, colName);
		return mtp.getCur();
	}
	
	public void initExportData(String gameCode, String termCode, int allCount)
	{
		String all = "exportData_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = new MgTermPrg();
		mtp.setId(all);
		mtp.setCur(allCount);
		mongoTemplate.save(mtp, colName);
		
		String finished = "exportData_" + gameCode + "_" + termCode + "_finished";
		mtp = new MgTermPrg();
		mtp.setId(finished);
		mtp.setCur(0);
		mongoTemplate.save(mtp, colName);
	}
	
	public int incExportData(String gameCode, String termCode, int step)
	{
		String finished = "exportData_" + gameCode + "_" + termCode + "_finished";
		MgTermPrg mtp = mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(finished)), new Update().inc("cur", Integer.valueOf(step)), MgTermPrg.class, colName);
		return mtp.getCur() + step;
	}
	
	public int queryExportDataAll(String gameCode, String termCode)
	{
		String all = "exportData_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(all)), MgTermPrg.class, colName);
		return mtp.getCur();
	}
	
	public void initDraw(String gameCode, String termCode, int allCount)
	{
		String all = "draw_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = new MgTermPrg();
		mtp.setId(all);
		mtp.setCur(allCount);
		mongoTemplate.save(mtp, colName);
		
		String finished = "draw_" + gameCode + "_" + termCode + "_finished";
		mtp = new MgTermPrg();
		mtp.setId(finished);
		mtp.setCur(0);
		mongoTemplate.save(mtp, colName);
	}
	
	public void initSchemeDraw(String gameCode, String termCode, int allCount)
	{
		String all = "schemedraw_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = new MgTermPrg();
		mtp.setId(all);
		mtp.setCur(allCount);
		mongoTemplate.save(mtp, colName);
		
		String finished = "schemedraw_" + gameCode + "_" + termCode + "_finished";
		mtp = new MgTermPrg();
		mtp.setId(finished);
		mtp.setCur(0);
		mongoTemplate.save(mtp, colName);
	}
	
	/**
	 * 增加算奖进度，并返回当前进度
	 * @param gameCode
	 * @param termCode
	 * @param step
	 * @return
	 */
	public int incDraw(String gameCode, String termCode, int step)
	{
		String finished = "draw_" + gameCode + "_" + termCode + "_finished";
		MgTermPrg mtp = mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(finished)), new Update().inc("cur", Integer.valueOf(step)), MgTermPrg.class, colName);
		return mtp.getCur() + step;
	}
	
	public int queryDrawAll(String gameCode, String termCode)
	{
		String all = "draw_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(all)), MgTermPrg.class, colName);
		return mtp.getCur();
	}
	
	/**
	 * 增加方案算奖进度，并返回当前进度
	 * @param gameCode
	 * @param termCode
	 * @param step
	 * @return
	 */
	public int incSchemeDraw(String gameCode, String termCode, int step)
	{
		String finished = "schemedraw_" + gameCode + "_" + termCode + "_finished";
		MgTermPrg mtp = mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(finished)), new Update().inc("cur", Integer.valueOf(step)), MgTermPrg.class, colName);
		return mtp.getCur() + step;
	}
	
	
	public int querySchemeDrawAll(String gameCode, String termCode)
	{
		String all = "schemedraw_" + gameCode + "_" + termCode + "_all";
		MgTermPrg mtp = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(all)), MgTermPrg.class, colName);
		return mtp.getCur();
	}
	
	public int querySchemeDrawFinished(String gameCode, String termCode)
	{
		String all = "schemedraw_" + gameCode + "_" + termCode + "_finished";
		MgTermPrg mtp = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(all)), MgTermPrg.class, colName);
		return mtp.getCur();
	}
}
