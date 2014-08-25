package com.mcp.deploy.database;

import com.mcp.core.util.DesUtil;
import com.mcp.deploy.common.DeployContext;
import com.mcp.deploy.common.JdbcContext;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.mongo.MgAutoIncrId;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.order.mongo.service.MgNotifyService;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Set;

public class ClearMdb {
	
	private static Logger log = Logger.getLogger(ClearMdb.class);

	public static void main(String[] args) throws Exception {
        JdbcTemplate jdbcTemplate = JdbcContext.getInstance().getBean(
                "jdbcTemplate", JdbcTemplate.class);
        String[] flushNames = {"schemezh", "schemeshare", "schemehm"};
        String[] tablesNames = {"area", "areagames", "clientFileVersion", "conservator", "cplatform", "customer", "customeraccount", "customerpresent", "game", "gamegrade", "term", "payment", "station", "stationgame", "stationgameterminal", "terminal", "terminalversion", "torder", "tticket", "withdraw", "promotion", "moneylog", "termlog", "betshop", "jodds", "admini", "operation", "useroperation"};

        for(String s:flushNames)
        {
            try {
                jdbcTemplate.execute("delete from " + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for(String s:tablesNames)
        {
            try {
                jdbcTemplate.execute("drop table " + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

		MongoTemplate mongoTemplate = DeployContext.getInstance().getBean(
				"mongoTemplate", MongoTemplate.class);
		Set<String> colNameSet = mongoTemplate.getCollectionNames();
		for(String colName:colNameSet)
		{
			if(colName.startsWith("term") && !colName.equals("term_cur"))
			{
				mongoTemplate.dropCollection(colName);
				log.info("drop collection:" + colName);
			}
			
			if(colName.startsWith("scheme"))
			{
				mongoTemplate.dropCollection(colName);
				log.info("drop collection:" + colName);
			}
			
			if(colName.startsWith("notify"))
			{
				mongoTemplate.dropCollection(colName);
				log.info("drop collection:" + colName);
			}

            if(colName.startsWith("check"))
            {
                mongoTemplate.dropCollection(colName);
                log.info("drop collection:" + colName);
            }

            if(colName.startsWith("prize_order"))
            {
                mongoTemplate.dropCollection(colName);
                log.info("drop collection:" + colName);
            }
		}
		
		MgNotifyService mgNotifyService = DeployContext.getInstance().getBean(
				"mgNotifyService",MgNotifyService.class);
		MgNotify mgNotify= new MgNotify();
		mgNotify.setId("Q0001");
		mgNotify.setKey("123456");
        mgNotify.setFtpPath("/home");
        mgNotify.setStatus(ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode());
		mgNotify.setUrl("http://192.168.1.50:8080/mcp-filter/main/notify.htm");
		mgNotifyService.saveWait(mgNotify);
		
		mgNotify= new MgNotify();
		mgNotify.setId("Q0005");
		mgNotify.setKey("123456");
        mgNotify.setFtpPath("/home");
        mgNotify.setStatus(ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode());
		mgNotify.setUrl("http://192.168.1.50:8080/mcp-filter/main/notify.htm");
		mgNotifyService.saveWait(mgNotify);
		
		mgNotify= new MgNotify();
		mgNotify.setId("Q0003");
		mgNotify.setKey("CePEYAR/Snc=");
        mgNotify.setFtpPath("/home");
        mgNotify.setStatus(ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode());
		mgNotify.setUrl("http://192.168.1.50:8080/mcp-filter/main/notify.htm");
		mgNotifyService.saveWait(mgNotify);

        mgNotify= new MgNotify();
        mgNotify.setId("Q0006");
        mgNotify.setKey("2tMpcljdB0s=");
        mgNotify.setFtpPath("/home");
        mgNotify.setStatus(ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode());
        mgNotify.setUrl("http://192.168.1.53:8080/ghsc/main/interface.htm");
        mgNotifyService.saveWait(mgNotify);

        MgAutoIncrIdService mgAutoIncrIdService = DeployContext.getInstance().getBean("mgAutoIncrIdService", MgAutoIncrIdService.class);
        MgAutoIncrId printId = mgAutoIncrIdService.getAutoIdByName(MgConstants.MG_PRINT_ID);
        if(printId == null){
            printId = new MgAutoIncrId();
            printId.setId(MgConstants.MG_PRINT_ID);
            printId.setValue(0);
            mgAutoIncrIdService.save(printId);
        }

		try {
			InitDatabase.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
