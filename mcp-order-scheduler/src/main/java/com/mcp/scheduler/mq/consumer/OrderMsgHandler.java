package com.mcp.scheduler.mq.consumer;

public class OrderMsgHandler implements MsgHandler {
	
	/*@Autowired
	private OrderService orderService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private RedisHelp redisHelp;
	
	private static Logger log = Logger.getLogger(OrderMsgHandler.class);

	@Override
	public void handler(McpMessage msg) throws Exception {
		log.info(msg.toString());
		ObjectMapper om = new ObjectMapper();
		if(msg.getCode() == 2001)
		{
			String ct = msg.getContent();
			TOrder order = om.readValue(ct, TOrder.class);
			
			//根据当期的状态设置订单状态
			Term t = termService.findOneByGameCodeAndCode(order.getGameCode(), order.getTermCode());
			int orderStatus = -1, ticketStatus = -1;
			if(t.getStatus() == TermState.ON_SALE.getCode())
			{
				orderStatus = OrderState.WAITING_PRINT.getCode();
				ticketStatus = TicketState.WAITING_PRINT.getCode();
			} 
			else if(t.getStatus() == TermState.NOT_ON_SALE.getCode())
			{
				orderStatus = OrderState.PRESALE.getCode();
				ticketStatus = TicketState.PRESALE.getCode();
			}
			order.setStatus(orderStatus);
			List<TTicket> tList = order.getTickets();
			for(int i = 0; i < tList.size(); i++)
			{
				tList.get(i).setStatus(ticketStatus);
			}
			orderService.save(order);
			//如果期次已经开售，发送消息通知出票引擎
			if(orderStatus == OrderState.WAITING_PRINT.getCode())
			{
				*//*WzlOrderId wzlOrderId = new WzlOrderId();
				wzlOrderId.setOrderId(order.getId());
				wzlOrderId.setGameCode(order.getGameCode());
				wzlOrderId.setTermCode(order.getTermCode());
				mcpMsgSender.sendMsg(SchedulerContext.getInstance().getSpringContext(), 4000, wzlOrderId);*//*
			}
		}
	}*/

}
