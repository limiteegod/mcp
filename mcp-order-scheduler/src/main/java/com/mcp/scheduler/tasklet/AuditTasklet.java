package com.mcp.scheduler.tasklet;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.specification.StationSpecification;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.service.StationService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;

import javax.annotation.PostConstruct;
import java.util.List;


public class AuditTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(AuditTasklet.class);
	
	private String termCode;
	
	private String gameCode;
	
	@Autowired
	private MgTermReportService mgTermReportService;
	
	@Autowired
	private MgOrderService mgOrderService;
	
	@Autowired
	private MgTicketService mgTicketService;

    @Autowired
    private StationService stationService;

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	@PostConstruct
	public void init()
	{
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

        //先生成期次统计信息空记录，初始化的统计信息都是0
        /*Specifications<Station> specs = Specifications.where(StationSpecification.isStationTypeEqual(ConstantValues.Station_StationType_DEFAULT.getCode()));
        specs = specs.or(StationSpecification.isStationTypeEqual(ConstantValues.Station_StationType_CHANNEL.getCode()));
        List<Station> sList = this.stationService.findAll(specs);
        for(Station station:sList)
        {
            String key = this.gameCode + this.termCode + station.getCode();
            MgTermReport mtr = new MgTermReport();
            mtr.setId(key);
            mtr.setGameCode(this.gameCode);
            mtr.setTermCode(this.termCode);
            mtr.setChannelCode(station.getCode());
            this.mgTermReportService.save(mtr);
        }*/

		/*//返奖订单统计
		CommandResult cr = this.mgOrderService.getPrizeInfo(gameCode, termCode);
		BasicDBList list = (BasicDBList)cr.get("retval");
		for(int i = 0; i < list.size(); i++)
		{
			BasicDBObject obj = (BasicDBObject)list.get(i);
			this.mgTermReportService.updatePrizeInfo(gameCode, termCode, obj.getString("channelCode"), obj.getLong("count"), obj.getLong("amount"), obj.getLong("bonus"));
		}*/
		
		/*//退款票据统计
		cr = this.mgTicketService.getRefundInfo(gameCode, termCode);
		list = (BasicDBList)cr.get("retval");
		for(int i = 0; i < list.size(); i++)
		{
			BasicDBObject obj = (BasicDBObject)list.get(i);
			this.mgTermReportService.updateTicketRefundInfo(gameCode, termCode, obj.getString("channelCode"), obj.getLong("count"), obj.getLong("amount"));
		}*/
		
		/*//中奖票据统计
		cr = this.mgTicketService.getHitInfo(gameCode, termCode);
		list = (BasicDBList)cr.get("retval");
		for(int i = 0; i < list.size(); i++)
		{
			BasicDBObject obj = (BasicDBObject)list.get(i);
			this.mgTermReportService.updateTicketHitInfo(gameCode, termCode, obj.getString("channelCode"), obj.getLong("count"), obj.getLong("amount"), obj.getLong("bonus"));
		}*/

		/*//未中奖票据统计
		cr = this.mgTicketService.getNotHitInfo(gameCode, termCode);
		list = (BasicDBList)cr.get("retval");
		for(int i = 0; i < list.size(); i++)
		{
			BasicDBObject obj = (BasicDBObject)list.get(i);
			this.mgTermReportService.updateTicketNotHitInfo(gameCode, termCode, obj.getString("channelCode"), obj.getLong("count"), obj.getLong("amount"));
		}*/
		
		return RepeatStatus.FINISHED;
	}
}