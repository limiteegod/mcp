package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.BonusLevelType;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.mongo.service.MgCheckService;
import com.mcp.order.mongo.service.MgPrizeService;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.ReceiptState;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

public class CheckUpdateOrderTasklet implements Tasklet {

	public static Logger log = Logger.getLogger(CheckUpdateOrderTasklet.class);

	private String termCode;

	private int gameType;

	private String gameCode;

	private String termId;

    @Autowired
    private MgCheckService mgCheckService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MgPrizeService mgPrizeService;

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameType(String gameType) {
		this.gameType = Integer.parseInt(gameType);
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	@PostConstruct
	public void init()
	{

	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
        String sqlFinishedFormat = "update torder set finishedTicketCount=finishedTicketCount+%d,status=%d,bonus=bonus+%d,bonusBeforeTax=bonusBeforeTax+%d,dNumber='%s' where id='%s'";
        String sqlNotFinishedFormat = "update torder set finishedTicketCount=finishedTicketCount+%d,bonus=bonus+%d,bonusBeforeTax=bonusBeforeTax+%d,dNumber='%s' where id='%s'";
        int batchSize = 100;
        String[] sqlList = new String[batchSize];
        DBCollection drawColl = mgCheckService.getCollByName(this.mgCheckService.getOrderColl(gameCode, termCode));
        int count = 0, allCount = 0;
        DBCursor cur = drawColl.find();
        cur = cur.snapshot();
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            String id = (String)obj.get("_id");
            try {
                String channelCode = (String)obj.get("channelCode");
                String dNumber = (String)obj.get("dNumber");
                String customerId = (String)obj.get("customerId");
                TOrderType type = TOrderType.values()[(Integer)obj.get("type")];
                String stationId = (String)obj.get("stationId");
                long bonus = Long.valueOf(obj.get("bonus").toString());
                long bonusBeforeTax = Long.valueOf(obj.get("bonusBeforeTax").toString());
                int ticketCount = Integer.valueOf(obj.get("ticketCount").toString());
                int finishedTicketCountInDb = Integer.valueOf(obj.get("finishedTicketCountInDb").toString());
                int ticketCountInDb = Integer.valueOf(obj.get("ticketCountInDb").toString());
                int schemeType = Integer.valueOf(obj.get("schemeType").toString());
                String outerId = (String)obj.get("outerId");
                if(ticketCount + finishedTicketCountInDb >= ticketCountInDb)
                {
                    int hitStatus = OrderState.NOT_HIT.getCode();   //未中奖
                    BonusLevelType bonusLevelType = BonusLevelType.NOT_HIT;
                    if(bonusBeforeTax - bonus > 0)
                    {
                        //中大奖
                        hitStatus = OrderState.WAITING_BIG_PRIZING.getCode();
                        bonusLevelType = BonusLevelType.BIG_HIT;
                    }
                    else if(bonus > 0)
                    {
                        //中小奖
                        hitStatus = OrderState.HIT.getCode();
                        bonusLevelType = BonusLevelType.LITTLE_HIT;
                    }
                    else
                    {
                        bonusLevelType = BonusLevelType.NOT_HIT;
                    }
                    this.mgPrizeService.orderPrized(id, gameCode, termCode, channelCode, customerId, type, stationId, bonus, bonusBeforeTax, schemeType, outerId, bonusLevelType);
                    //订单已经完成
                    sqlList[count] = String.format(sqlFinishedFormat, ticketCount, hitStatus, bonus, bonusBeforeTax, dNumber, id);
                }
                else
                {
                    //订单未完成
                    sqlList[count] = String.format(sqlNotFinishedFormat, ticketCount, bonus, bonusBeforeTax, dNumber, id);
                }
                log.info(sqlList[count]);
                count++;
                allCount++;
                if(count == batchSize || !cur.hasNext())
                {
                    try {
                        if(count < batchSize)
                        {
                            String[] execSql = new String[count];
                            System.arraycopy(sqlList, 0, execSql, 0, count);
                            this.jdbcTemplate.batchUpdate(execSql);
                        }
                        else
                        {
                            this.jdbcTemplate.batchUpdate(sqlList);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        count = 0;
                    }
                }
            }
            catch (Exception e)
            {
                log.error("更新订单中奖状态过程中出现错误,id:" + id);
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        cur.close();
        log.info("算奖完成........订单总计:" + allCount);
        return RepeatStatus.FINISHED;
	}
}
