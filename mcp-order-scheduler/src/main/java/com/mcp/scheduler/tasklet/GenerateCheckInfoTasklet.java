package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.mongo.service.MgCheckService;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.TicketService;
import com.mcp.rmi.inter.SchemeInter;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class GenerateCheckInfoTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(GenerateCheckInfoTasklet.class);

    private String termCode;

    private int gameType;

    private String gameCode;

    private String termId;

    @Autowired
    private MgTermReportService mgTermReportService;

    @Autowired
    private MgCheckService mgCheckService;

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

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		log.info("统计票据级别数据.........................");

        CommandResult cr = this.mgCheckService.getTicketInfo(gameCode, termCode);
        BasicDBList list = (BasicDBList)cr.get("retval");
        for(int i = 0; i < list.size(); i++)
        {
            BasicDBObject obj = (BasicDBObject)list.get(i);
            this.mgTermReportService.updateTicketInfo(gameCode, termCode, obj.getString("channelCode"), TOrderType.values()[obj.getInt("type")], obj.getLong("hitCount"), obj.getLong("hitAmount"), obj.getLong("bonus"), obj.getLong("bonusBeforeTax"), obj.getLong("notHitCount"), obj.getLong("notHitAmount"));
        }

        log.info("统计中奖订单级别数据.........................");

        cr = this.mgCheckService.getOrderInfo(gameCode, termCode);
        list = (BasicDBList)cr.get("retval");
        for(int i = 0; i < list.size(); i++)
        {
            BasicDBObject obj = (BasicDBObject)list.get(i);
            this.mgTermReportService.updateOrderInfo(gameCode, termCode, obj.getString("channelCode"), TOrderType.values()[obj.getInt("type")], obj.getLong("hitCount"), obj.getLong("hitAmount"), obj.getLong("bonus"), obj.getLong("bonusBeforeTax"), obj.getLong("notHitCount"), obj.getLong("notHitAmount"));
        }

        log.info("出票中心角度统计订单中奖信息....................");
        cr = this.mgCheckService.getPrintInfo(gameCode, termCode);
        list = (BasicDBList)cr.get("retval");
        for(int i = 0; i < list.size(); i++)
        {
            BasicDBObject obj = (BasicDBObject)list.get(i);
            this.mgTermReportService.updatePrintInfo(gameCode, termCode, obj.getString("printStationId"), obj.getLong("hitCount"), obj.getLong("hitAmount"), obj.getLong("bonus"), obj.getLong("bonusBeforeTax"), obj.getLong("notHitCount"), obj.getLong("notHitAmount"));
        }


		return RepeatStatus.FINISHED;
	}
}
