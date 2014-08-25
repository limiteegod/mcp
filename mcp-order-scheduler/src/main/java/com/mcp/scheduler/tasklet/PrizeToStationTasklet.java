package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.BonusLevelType;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.core.util.cons.TermReportType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgPrizeService;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.StationService;
import com.mcp.order.status.OrderState;
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
import java.util.List;

/**
 * 机构之间的奖金结算包括两个部分，一部分是渠道销售的中奖金额，返给渠道，
 * 一部分是，出票机构中奖的金额，从出票机构中扣除。
 */
public class PrizeToStationTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(PrizeToStationTasklet.class);
	
	private String termCode;
	
	private String gameCode;
	
	@Autowired
	private MoneyService moneyService;

    @Autowired
    private MgTermReportService mgTermReportService;

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

        //返奖给销售渠道
        List<MgTermReport> rptList = this.mgTermReportService.findAll(gameCode, termCode, TOrderType.CHANNEL);
        for(MgTermReport report:rptList)
        {
            long bonus = report.getTicketHitBonus();
            if(bonus > 0)
            {
                Station station = this.stationService.findOneByCode(report.getChannelCode());
                this.moneyService.rewardToStation(station.getId(), report.getId(), bonus,gameCode + termCode);
            }
        }

        //对出票渠道进行扣款
        List<MgTermReport> printRptList = this.mgTermReportService.findAll(gameCode, termCode, TermReportType.PRINT);
        for(MgTermReport report:printRptList)
        {
            long bonus = report.getTicketHitBonus();
            if(bonus > 0)
            {
                this.moneyService.stationReward(report.getChannelCode(), report.getId(), bonus);
            }
        }

        return RepeatStatus.FINISHED;
	}
}
