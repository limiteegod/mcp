package com.mcp.order.mongo.util;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.notify.RepN01Body;
import com.mcp.order.inter.notify.RepN02Body;
import com.mcp.order.inter.util.InterUtil;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.model.mongo.MgNotifyMsg;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.Term;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.order.mongo.service.MgNotifyMsgService;
import com.mcp.order.mongo.service.MgNotifyService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;

public class NotifyUtil {
	
	/**
	 * 订单状态改变通知
	 * @param context
	 * @param order
	 */
	public static void sendN02(ApplicationContext context, TOrder order) throws Exception
	{
		MgNotifyService mgNotifyService = context.getBean("mgNotifyService", MgNotifyService.class);
        MgAutoIncrIdService mgAutoIncrIdService = context.getBean("mgAutoIncrIdService", MgAutoIncrIdService.class);
		MgNotify mgNotify = mgNotifyService.findWait(order.getChannelCode());
		if(mgNotify != null)
		{
			RepN02Body repN02Body = new RepN02Body();
			repN02Body.setOrder(order);
			
			ObjectMapper om = new ObjectMapper();
			om.setFilters(CmdContext.getInstance().getFilterProviderByCode("N020101"));
			String bodyStr = om.writeValueAsString(repN02Body);
            String uuid = CoreUtil.getUUID();
			String repMsg = InterUtil.getMd5Message(uuid, "", order.getChannelCode(), bodyStr, "N02", mgNotify.getKey());
			
			//String id = MD5Util.MD5(bodyStr);
			MgNotifyMsg msg = new MgNotifyMsg();
			msg.setId(mgAutoIncrIdService.getAutoIdAndIncrByName(MgConstants.MG_NOTIFY_ID).getValue());
            msg.sethId(uuid);
			msg.setStatus(ConstantValues.NOTIFY_STATUS_UNUSED.getCode());
			msg.setMsg(repMsg);
			msg.setCreateTime(new Date());
			
			MgNotifyMsgService mgNotifyMsgService = context.getBean("mgNotifyMsgService", MgNotifyMsgService.class);
			mgNotifyMsgService.save(order.getChannelCode(), msg);
		}
	}
	
	/**
	 * 期次状态改变通知
	 * @param context
	 * @param term
	 */
	public static void sendN01(ApplicationContext context, Term term) throws Exception
	{
		sendN01(context, term, null);
	}
	
	/**
	 * 期次状态改变通知
	 * @param context
	 * @param term
	 * @param tip	附加信息
	 */
	public static void sendN01(ApplicationContext context, Term term, String tip) throws Exception
	{
		MgNotifyService mgNotifyService = context.getBean("mgNotifyService", MgNotifyService.class);
        MgAutoIncrIdService mgAutoIncrIdService = context.getBean("mgAutoIncrIdService", MgAutoIncrIdService.class);
		List<MgNotify> notifyList = mgNotifyService.findAll();
		
		for(MgNotify not:notifyList)
		{
			RepN01Body repN01Body = new RepN01Body();
			repN01Body.setTerm(term);
			repN01Body.setTip(tip);
			
			ObjectMapper om = new ObjectMapper();
			om.setFilters(CmdContext.getInstance().getFilterProviderByCode("N010101"));
			String bodyStr = om.writeValueAsString(repN01Body);
            String uuid = CoreUtil.getUUID();
			String repMsg = InterUtil.getMd5Message(uuid, "", not.getId(), bodyStr, "N01", not.getKey());
			
			//String id = MD5Util.MD5(bodyStr);
			MgNotifyMsg msg = new MgNotifyMsg();
			msg.setId(mgAutoIncrIdService.getAutoIdAndIncrByName(MgConstants.MG_NOTIFY_ID).getValue());
            msg.sethId(uuid);
			msg.setStatus(ConstantValues.NOTIFY_STATUS_UNUSED.getCode());
			msg.setMsg(repMsg);
			msg.setCreateTime(new Date());
			
			MgNotifyMsgService mgNotifyMsgService = context.getBean("mgNotifyMsgService", MgNotifyMsgService.class);
			mgNotifyMsgService.save(not.getId(), msg);
		}
	}
}
