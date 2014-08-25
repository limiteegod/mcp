package com.mcp.scheduler.tasklet;

import com.mcp.core.util.ssh.SshConfiguration;
import com.mcp.core.util.ssh.SshUtil;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgNotifyService;
import com.mcp.order.mongo.service.MgOrderService;
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
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封存的任务类，对需要封存文件的渠道，生成相应的文件
 * @author ming.li
 */
public class SealTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(SealTasklet.class);
	
	private String termCode;
	
	private String gameCode;
	
	private String iFolder;
	
	@Autowired
	private MgNotifyService mgNotifyService;
	
	@Autowired
	private MgOrderService mgOrderService;

    @Autowired
    private MongoTemplate mongoTemplate;

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
		//如果文件夹不存在，则创建文件夹
		/*File folder = new File(this.iFolder);
		if(!folder.exists())
		{
			folder.mkdirs();
		}
		String absolutePath = folder.getAbsolutePath();
        List<MgNotify> notifyList = mgNotifyService.findAll();
        String colName = MgConstants.TERM_PRIZEd_ORDER + gameCode + "_" + termCode;
        this.exportOrderToFile(colName, "_prized.txt", notifyList);
        colName = MgConstants.TERM_BIG_HIT_ORDER + gameCode + "_" + termCode;
        this.exportOrderToFile(colName, "_big.txt", notifyList);
        colName = MgConstants.TERM_NOT_HIT_ORDER + gameCode + "_" + termCode;
        this.exportOrderToFile(colName, "_notHit.txt", notifyList);

		//生成文件之后，使用ssh，上传文件到ftp服务器
		SshConfiguration sshConfig = new SshConfiguration();
		sshConfig.setHost(SchConstants.FTP_HOST);
		sshConfig.setPort(Integer.parseInt(SchConstants.FTP_PORT));
		sshConfig.setUsername(SchConstants.FTP_USER);
		sshConfig.setPassword(SchConstants.FTP_PASSWORD);
		
		SshUtil sshUtil = new SshUtil(sshConfig);
		for(MgNotify not:notifyList)
		{
            String toFileFolder = not.getFtpPath() + "/" + this.iFolder;
            sshUtil.runCmd("mkdir -p " + toFileFolder, "UTF-8");

            //上传小奖已经返奖文件
			String fileName = absolutePath + "/" + not.getId() + "_prized.txt";
			log.info(fileName);
			String toFileName = toFileFolder + "/" + not.getId() + "_prized.txt";
			log.info(toFileName);
			sshUtil.upload(fileName, toFileName);

            //上传大奖中奖文件
            fileName = absolutePath + "/" + not.getId() + "_big.txt";
            log.info(fileName);
            toFileName = toFileFolder + "/" + not.getId() + "_big.txt";
            log.info(toFileName);
            sshUtil.upload(fileName, toFileName);

            //上传未中奖文件
            fileName = absolutePath + "/" + not.getId() + "_notHit.txt";
            log.info(fileName);
            toFileName = toFileFolder + "/" + not.getId() + "_notHit.txt";
            log.info(toFileName);
            sshUtil.upload(fileName, toFileName);
		}
		sshUtil.close();*/
		
		return RepeatStatus.FINISHED;
	}

    /**
     *
     * @param colName
     * @throws Exception
     */
    private void exportOrderToFile(String colName, String fileSuffix, List<MgNotify> notifyList) throws Exception
    {
        Map<String, BufferedWriter> writerMap = new HashMap<String, BufferedWriter>();
        for(MgNotify not:notifyList)
        {
            String fileName = this.iFolder + "/" + not.getId() + fileSuffix;
            //如果文件存在，则删除原来的文件
            File file = new File(fileName);
            if(file.exists())
            {
                file.delete();
            }
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(writer);
            writerMap.put(not.getId(), bw);
        }

        //写入文件
        DBCollection col = this.mongoTemplate.getCollection(colName);
        BasicDBObject query = new BasicDBObject();
        DBCursor cur = col.find(query);
        while(cur.hasNext())
        {
            DBObject obj = cur.next();
            String line = obj.get("_id") + "," + obj.get("outerId") + "," + obj.get("bonus") + "," + obj.get("bonusBeforeTax");
            BufferedWriter bw = writerMap.get(obj.get("channelCode"));
            if(bw != null)
            {
                bw.write(line + "\r\n");
            }
        }
        cur.close();

        //关闭所有的文件句柄
        for(MgNotify not:notifyList)
        {
            BufferedWriter bw = writerMap.get(not.getId());
            if(bw != null)
            {
                bw.close();
            }
        }
    }

}