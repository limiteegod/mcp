package com.mcp.scheme.scheduler.common;

import com.mcp.order.model.mongo.MgAutoIncrId;
import com.mcp.rmi.common.RmiConstants;
import com.mcp.scheme.mongo.common.MgSchemeConstants;
import com.mcp.scheme.mongo.service.MgAutoIncrIdService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
	
	public static Logger log = Logger.getLogger(AppMain.class);

	public static void main(String[] args) {
		
		System.setProperty("java.rmi.server.hostname", RmiConstants.RMI_SCHEME_HOST);
		SchemeContext.getInstance();
		
		log.info("校验mongodb的基础数据....");
		MgAutoIncrIdService mgAutoIncrIdService = SchemeContext.getInstance().getBean("mgAutoIncrIdService", MgAutoIncrIdService.class);
		MgAutoIncrId zhId = mgAutoIncrIdService.getAutoIdByName(MgSchemeConstants.MG_SCHEME_ZH_ID);
		if(zhId == null)
		{
			zhId = new MgAutoIncrId();
			zhId.setId(MgSchemeConstants.MG_SCHEME_ZH_ID);
			zhId.setValue(0);
			mgAutoIncrIdService.save(zhId);
		}
		log.info("校验mongodb的基础数据....完成");

        log.info("启动quartz任务......");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-quartz.xml");
        log.info(context);
	}
}
