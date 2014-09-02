package com.mcp.order.service.util;

import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.mongo.MgPrint;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgPrintService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.mongo.util.NotifyUtil;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.StationService;
import com.mcp.order.service.TicketService;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TicketState;
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


    /**
     * ticket print failure
     * @param context
     * @param t
     * @param termCode
     */
    public static void fail(ApplicationContext context, TTicket t, String termCode) throws Exception
    {
        OrderService orderService = context.getBean("orderService", OrderService.class);
        MgOrderService mgOrderService = context.getBean("mgOrderService", MgOrderService.class);
        MoneyService moneyService = context.getBean("moneyService", MoneyService.class);
        TicketService ticketService = context.getBean("ticketService", TicketService.class);
        MgTicketService mgTicketService = context.getBean("mgTicketService", MgTicketService.class);

        TOrder order = orderService.incrFailureTicket(t.getOrderId());
        if(StringUtil.isEmpty(termCode))
        {
            termCode = order.getTermCode();
        }
        mgOrderService.save(order, termCode);

        //订单所有的票都有出票结果
        if(order.getPrintCount() + order.getPrintFailCount() >= order.getTicketCount())
        {
            if(order.getPrintCount() > 0)
            {
                //部分出票成功
                int status = order.getStatus();
                order.setStatus(OrderState.PARTIAL_SUCCESS.getCode());
                NotifyUtil.sendN02(context, order);
                order.setStatus(status);
            }
        }
        //订单已经完成，通知渠道
        if(order.getFinishedTicketCount() >= order.getTicketCount())
        {
            NotifyUtil.sendN02(context, order);
        }

        //对票据进行退款，并更新订单信息
        String customerId = t.getCustomerId();
        if(order.getType() == TOrderType.CHANNEL)
        {
            //退款给渠道
            moneyService.refundToStation(customerId, t.getOrderId(), t.getId(), t.getAmount());
        }
        else
        {
            //退款给彩民
            moneyService.refundToCustomer(t.getStationId(), customerId, t.getOrderId(), t.getId(), t.getAmount());
        }
        ticketService.updateStatusById(TicketState.REFUNDED.getCode(), t.getId());

        //保存到已退款的票据collection
        mgTicketService.saveRefundedTicket(t.getId(), t.getGameCode(), termCode, t.getChannelCode(), order.getType(), t.getAmount());
    }
}
