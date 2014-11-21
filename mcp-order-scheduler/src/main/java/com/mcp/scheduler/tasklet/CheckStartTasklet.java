package com.mcp.scheduler.tasklet;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.model.mongo.MgJcCheckTicket;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.mongo.service.MgCheckService;
import com.mcp.order.mongo.service.MgPrizeService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.service.TicketService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CheckStartTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(CheckStartTasklet.class);
	
	private String termCode;
	
	private int gameType;
	
	private String gameCode;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MgTicketService mgTicketService;

    @Autowired
    private MgCheckService mgCheckService;

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

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
        log.info("查看是否存在已经算奖的表，并清除.........................");
        this.mgCheckService.clear(gameCode, termCode);
        this.mgPrizeService.clear(gameCode, termCode);
		if(gameType == ConstantValues.Game_Type_Jingcai.getCode()) {
            log.info("竞彩算奖，开始导出ticket数据.........................");
            DBCollection sucCol = this.mgTicketService.getJcSuccessCollection(gameCode);
            DBCursor cur = sucCol.find();
            cur = cur.snapshot();
            while (cur.hasNext()) {
                DBObject obj = cur.next();
                long id = Long.valueOf(obj.get("_id").toString());
                String ticketTermCode = (String) obj.get("termCode");
                String ticketId = (String) obj.get("ticketId");
                int hitIndex = ticketTermCode.indexOf(this.termCode);
                if(hitIndex > -1)   //包含这一期
                {
                    try {
                        TTicket ticket = ticketService.incrFinishedCount(ticketId);
                        if(ticket.getFinishedCount() == ticket.getTermCode().split(Constants.SEP_COMMA).length)  //最后一期
                        {
                            mgTicketService.removeJc(gameCode, id);
                            mgTicketService.jcSaveDraw(ticket, termCode);
                        }
                        else
                        {
                            //为了防止重新算奖的时候，再次扫描到同一条记录，需要更新记录的字段值
                            String newTermCode = ticketTermCode.substring(0, hitIndex) + ticketTermCode.substring(hitIndex + this.termCode.length());
                            this.mgTicketService.jcUpdateTermCode(id, this.gameCode, newTermCode);
                        }
                    } catch (Exception e) {
                        log.error("竞彩等待算奖的票据处理出现错误,id:" + ticketId);
                        e.printStackTrace();
                    }
                }
            }
            cur.close();
        }
        return RepeatStatus.FINISHED;
	}
}
