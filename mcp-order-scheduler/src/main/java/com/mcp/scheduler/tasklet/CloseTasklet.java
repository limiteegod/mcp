package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.specification.StationSpecification;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.service.StationService;
import com.mcp.rmi.inter.SchemeInter;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 前台停售任务主类，取消未支付的订单及方案
 * @author ming.li
 */
public class CloseTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(CloseTasklet.class);
	
	private String termCode;
	
	private String gameCode;

    @Autowired
    private StationService stationService;

    @Autowired
    private MgTermReportService mgTermReportService;

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
		//schemeInter.cancelUnAffordedZh(gameCode, termCode);

        //先生成期次统计信息空记录，初始化的统计信息都是0
        Specifications<Station> specs = Specifications.where(StationSpecification.isStationTypeEqual(ConstantValues.Station_StationType_DEFAULT.getCode()));
        specs = specs.or(StationSpecification.isStationTypeEqual(ConstantValues.Station_StationType_CHANNEL.getCode()));
        List<Station> sList = this.stationService.findAll(specs);
        for(Station station:sList)
        {
            TOrderType[] oTypes = TOrderType.values();
            for(TOrderType type:oTypes)
            {
                String key = this.gameCode + this.termCode + station.getCode() + type.getFlag();
                MgTermReport mtr = new MgTermReport();
                mtr.setId(key);
                mtr.setGameCode(this.gameCode);
                mtr.setTermCode(this.termCode);
                mtr.setChannelCode(station.getCode());
                mtr.setType(type.ordinal());
                this.mgTermReportService.save(mtr);
            }
        }

		return RepeatStatus.FINISHED;
	}
}
