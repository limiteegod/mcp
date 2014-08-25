package com.mcp.notify.main;

import com.mcp.notify.common.NotifyContext;
import com.mcp.notify.thread.NotifyThread;
import com.mcp.notify.thread.ThreadPool;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.mongo.service.MgNotifyMsgService;
import com.mcp.order.mongo.service.MgNotifyService;
import org.apache.log4j.Logger;

import java.util.List;

public class AppMain {
	
	private static Logger log = Logger.getLogger(AppMain.class);

	public static void main(String[] args) {
		log.info("starting notify system...............");
		NotifyContext.getInstance();
		MgNotifyService mgNotifyService = NotifyContext.getInstance().getBean("mgNotifyService", MgNotifyService.class);
		MgNotifyMsgService mgNotifyMsgService = NotifyContext.getInstance().getBean("mgNotifyMsgService", MgNotifyMsgService.class);
		mgNotifyService.clear();
		while(true)
		{
        	List<MgNotify> all = mgNotifyService.findAllWait();
        	for(MgNotify mgNotify:all)
        	{
                log.info("add to notify queen..............." + mgNotify.getId());
                mgNotifyService.run(mgNotify.getId());
                ThreadPool.getFixedInstance().execute(new NotifyThread(mgNotifyMsgService, mgNotifyService, mgNotify.getId()));
        	}
        	//5分钟的扫描间隔
        	try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
	}

}
