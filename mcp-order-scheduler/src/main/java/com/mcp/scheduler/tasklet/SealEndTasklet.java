package com.mcp.scheduler.tasklet;

import com.mcp.core.util.ssh.SshConfiguration;
import com.mcp.core.util.ssh.SshUtil;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.model.mongo.MgTermSeal;
import com.mcp.order.mongo.service.MgNotifyService;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgTermSealService;
import com.mcp.scheduler.common.SchConstants;
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

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

/**
 * 封存结束的任务类，记录期次封存信息
 * @author ming.li
 */
public class SealEndTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(SealEndTasklet.class);
	
	private String termCode;
	
	private String gameCode;
	
	private String iFolder;

    @Autowired
    private MgTermSealService mgTermSealService;

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public void setiFolder(String iFolder) {
		this.iFolder = iFolder;
	}

	@PostConstruct
	public void init()
	{
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

        MgTermSeal mgTermSeal = new MgTermSeal();
        mgTermSeal.setId(this.gameCode + "_" + this.termCode);
        mgTermSeal.setGameCode(this.gameCode);
        mgTermSeal.setTermCode(this.termCode);
        mgTermSeal.setFilePath(this.iFolder);
        mgTermSeal.setsTime(new Date());
        this.mgTermSealService.save(mgTermSeal);

		return RepeatStatus.FINISHED;
	}
}