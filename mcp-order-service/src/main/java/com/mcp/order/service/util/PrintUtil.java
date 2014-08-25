package com.mcp.order.service.util;

import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgPrint;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.order.mongo.service.MgPrintService;
import com.mcp.order.service.StationService;
import com.mcp.order.status.OrderState;
import org.springframework.context.ApplicationContext;

public class PrintUtil {
	
	/**
	 * 有新的订单，通过此方法判断是否需要发送出票通知
	 * @param context
	 * @param order
	 */
	public static void newOrder(ApplicationContext context, TOrder order)
	{
		if(order.getStatus() == OrderState.WAITING_PRINT.getCode())
		{
			StationService stationService = context.getBean("stationService", StationService.class);
            MgAutoIncrIdService mgAutoIncrIdService = context.getBean("mgAutoIncrIdService", MgAutoIncrIdService.class);
            MgPrintService mgPrintService = context.getBean("mgPrintService", MgPrintService.class);
			Station pStation = stationService.findOne(order.getPrintStationId());
            MgPrint mgPrint = new MgPrint();
            mgPrint.setId(mgAutoIncrIdService.getAutoIdAndIncrByName(MgConstants.MG_PRINT_ID).getValue());
            mgPrint.setOrderId(order.getId());
            mgPrint.setGameCode(order.getGameCode());
            mgPrint.setTermCode(order.getTermCode());
            mgPrintService.save(pStation.getCode(), mgPrint);
		}
	}
}
