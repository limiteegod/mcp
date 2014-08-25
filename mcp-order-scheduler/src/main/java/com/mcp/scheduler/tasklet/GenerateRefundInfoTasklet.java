package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.specification.TicketSpecification;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.mongo.util.NotifyUtil;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TicketService;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TicketState;
import com.mcp.scheduler.common.SchedulerContext;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;

import javax.annotation.PostConstruct;
import java.util.Iterator;

public class GenerateRefundInfoTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(GenerateRefundInfoTasklet.class);
	
	private String gameCode;
	
	private String termCode;

    @Autowired
    private MgTicketService mgTicketService;

    @Autowired
    private MgOrderService mgOrderService;

    @Autowired
    private MgTermReportService mgTermReportService;

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	@PostConstruct
	public void init()
	{
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		log.info("统计退款票据级别数据.........................");
        CommandResult cr = this.mgTicketService.getRefundInfo(gameCode, termCode);
        BasicDBList list = (BasicDBList)cr.get("retval");
        for(int i = 0; i < list.size(); i++)
        {
            BasicDBObject obj = (BasicDBObject)list.get(i);
            this.mgTermReportService.updateTicketRefundInfo(gameCode, termCode, obj.getString("channelCode"), TOrderType.values()[obj.getInt("type")], obj.getLong("count"), obj.getLong("amount"));
            log.info("channelCode:" + obj.getString("channelCode") + ",count:" + obj.getLong("count") + ",amount:" + obj.getLong("amount"));
        }


        log.info("统计退款订单级别数据.........................");
        cr = this.mgOrderService.getRefundInfo(gameCode, termCode);
        list = (BasicDBList)cr.get("retval");
        for(int i = 0; i < list.size(); i++)
        {
            BasicDBObject obj = (BasicDBObject)list.get(i);
            this.mgTermReportService.updateOrderRefundInfo(gameCode, termCode, obj.getString("channelCode"), TOrderType.values()[obj.getInt("type")], obj.getLong("count"), obj.getLong("amount"));
            log.info("channelCode:" + obj.getString("channelCode") + ",count:" + obj.getLong("count") + ",amount:" + obj.getLong("amount"));
        }

		return RepeatStatus.FINISHED;
	}
}
