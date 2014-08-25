/**
 * 
 */
package com.mcp.scheduler.main;


import com.mcp.order.dao.channel.ChannelContext;
import com.mcp.order.dao.finance.AccountOperator;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.mongo.MgAutoIncrId;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.rmi.common.RmiConstants;
import com.mcp.scheduler.common.SchedulerContext;
import com.mcp.scheduler.thread.JcDrawThread;
import com.mcp.scheduler.thread.ThreadPool;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ming.li
 *
 */
public class AppMain {
	
	private static Logger log = Logger.getLogger(AppMain.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("java.rmi.server.hostname", RmiConstants.RMI_ORDER_HOST);
		
		LotteryContext.getInstance();
		AccountOperator.getInstance();
		ChannelContext.getInstance();
		CmdContext.getInstance();
		SchedulerContext.getInstance();
		
		log.info("校验mongodb的基础数据....");
		//校验mongdb的数据
        log.info("校验竞彩id的自增序列....");
		MgAutoIncrIdService mgAutoIncrIdService = SchedulerContext.getInstance().getBean("mgAutoIncrIdService", MgAutoIncrIdService.class);
		MgAutoIncrId jcCheckTickt = mgAutoIncrIdService.getAutoIdByName(MgConstants.MG_JC_CHECK_TICKET_ID);
		if(jcCheckTickt == null)
		{
			jcCheckTickt = new MgAutoIncrId();
			jcCheckTickt.setId(MgConstants.MG_JC_CHECK_TICKET_ID);
			jcCheckTickt.setValue(0);
			mgAutoIncrIdService.save(jcCheckTickt);
		}
        //校验通知id
        log.info("校验通知的自增序列....");
        MgAutoIncrId notifyId = mgAutoIncrIdService.getAutoIdByName(MgConstants.MG_NOTIFY_ID);
        if(notifyId == null)
        {
            notifyId = new MgAutoIncrId();
            notifyId.setId(MgConstants.MG_NOTIFY_ID);
            notifyId.setValue(0);
            mgAutoIncrIdService.save(notifyId);
        }
        //校验出票id
        log.info("校验出票的自增序列....");
        notifyId = mgAutoIncrIdService.getAutoIdByName(MgConstants.MG_PRINT_ID);
        if(notifyId == null)
        {
            notifyId = new MgAutoIncrId();
            notifyId.setId(MgConstants.MG_PRINT_ID);
            notifyId.setValue(0);
            mgAutoIncrIdService.save(notifyId);
        }
		log.info("校验mongodb的基础数据....完成");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-quartz.xml");
		log.info(context);
		
		log.info("启动竞彩算奖扫描线程........");
		
		JcDrawThread footballTread = new JcDrawThread("T51");
		ThreadPool.getFixedInstance().execute(footballTread);
		
		log.info("核心引擎启动成功.........");
	}

}
