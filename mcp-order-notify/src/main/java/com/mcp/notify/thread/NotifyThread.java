package com.mcp.notify.thread;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.model.mongo.MgNotifyMsg;
import com.mcp.order.mongo.service.MgNotifyMsgService;
import com.mcp.order.mongo.service.MgNotifyService;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;

import java.net.SocketTimeoutException;
import java.util.List;

public class NotifyThread implements Runnable {
	
	public static Logger log = Logger.getLogger(NotifyThread.class);
	
	private MgNotifyService mgNotifyService;
	
	private MgNotifyMsgService mgNotifyMsgService;
	
	/**
	 * 游戏代码
	 */
	private String mgNotifyId;

	public NotifyThread(MgNotifyMsgService mgNotifyMsgService, 
			MgNotifyService mgNotifyService, String mgNotifyId) {
		this.mgNotifyMsgService = mgNotifyMsgService;
		this.mgNotifyService = mgNotifyService;
		this.mgNotifyId = mgNotifyId;
	}

	@Override
	public void run() {
		while(true)
		{
            log.info(mgNotifyId + ",通知线程......................");
            MgNotify curNotify = mgNotifyService.findById(mgNotifyId);
            if(curNotify.getStatus() != ConstantValues.NOTIFY_CHANNEL_STATUS_RUNNING.getCode())
            {
                //如果渠道已经从列表中移除，则不再通知
                break;
            }
            int pIndex = 0, size = 100;
            do {
                PageRequest pr = new PageRequest(pIndex, size);
                List<MgNotifyMsg> tList = this.mgNotifyMsgService.findUnused(curNotify.getId(), pr).getContent();
                for(MgNotifyMsg m:tList)
                {
                    log.info(curNotify.getId() + ",发送:" + m.getMsg());
                    boolean sended = false;
                    try {
                        String content = HttpClientUtil.request(curNotify.getUrl(), m.getMsg(), HttpClientUtil.POST, null);
                        log.info(curNotify.getId() + ",接收:" + content);
                        sended = true;
                    }
                    catch (SocketTimeoutException e)
                    {
                        sended = true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                        this.mgNotifyService.wait(this.mgNotifyId);
                        break;
                    }
                    if(sended)
                    {
                        mgNotifyMsgService.msgSended(curNotify.getId(), m.getId());
                    }
                }
                if(tList.size() == 0)
                {
                    break;
                }
            } while(true);
            try {
                Thread.sleep(10000);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
		}
	}
}
