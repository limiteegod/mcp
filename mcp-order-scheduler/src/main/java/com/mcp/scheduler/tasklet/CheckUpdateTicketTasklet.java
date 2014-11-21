package com.mcp.scheduler.tasklet;

import com.mcp.order.mongo.service.MgCheckService;
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
import java.util.ArrayList;
import java.util.List;

public class CheckUpdateTicketTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(CheckUpdateTicketTasklet.class);
	
	private String termCode;
	
	private int gameType;
	
	private String gameCode;
	
	private String termId;

    @Autowired
    private MgCheckService mgCheckService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String sqlFormat = "update tticket set receiptStatus=%d,bonus=%d,bonusBeforeTax=%d,dNumber='%s' where id='%s'";
        int batchSize = 100;
        String[] sqlList = new String[batchSize];
        DBCollection drawColl = mgCheckService.getCollByName(this.mgCheckService.getTicketColl(gameCode, termCode));
        int count = 0, allCount = 0;
        DBCursor cur = drawColl.find();
        cur = cur.snapshot();
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            String id = (String)obj.get("_id");
            String dNumber = (String)obj.get("dNumber");
            long bonus = Long.valueOf(obj.get("bonus").toString());
            long bonusBeforeTax = Long.valueOf(obj.get("bonusBeforeTax").toString());
            int receiptStatus = ReceiptState.NOT_HIT.getCode();
            if(bonus > 0)
            {
                receiptStatus = ReceiptState.NOT_CLAIM_PRIZE.getCode();
            }
            sqlList[count] = String.format(sqlFormat, receiptStatus, bonus, bonusBeforeTax, dNumber, id);
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
        cur.close();
        log.info("算奖完成........票据总计:" + allCount);
        return RepeatStatus.FINISHED;
	}
}
