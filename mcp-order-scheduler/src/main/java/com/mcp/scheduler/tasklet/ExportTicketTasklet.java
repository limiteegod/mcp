package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.dao.specification.TicketSpecification;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.mongo.util.NotifyUtil;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TicketService;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TicketState;
import com.mcp.scheduler.common.SchedulerContext;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;

import javax.annotation.PostConstruct;
import java.util.Iterator;

public class ExportTicketTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(ExportTicketTasklet.class);
	
	private String gameCode;
	
	private String termCode;

    private int gameType;

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MgTicketService mgTicketService;
	
	@Autowired
	private MgOrderService mgOrderService;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private OrderService orderService;

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    @PostConstruct
	public void init()
	{
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		log.info("开始导出ticket数据.........................");
		
		//把出票失败的票据信息写入文件，注意不要累加pIndex，因为后面的处理更新了ticket的状态
		int pIndex = 0, size = 100;
		do {
			PageRequest pr = new PageRequest(pIndex, size);
			Specifications<TTicket> specs = Specifications.where(TicketSpecification.isGameCodeEqual(gameCode));
		    specs = specs.and(TicketSpecification.isTermCodeLike(termCode));
		    Specifications<TTicket> statusSpecs = Specifications.where(TicketSpecification.isStatusEqual(TicketState.WAITING_PRINT.getCode()));
		    statusSpecs = statusSpecs.or(TicketSpecification.isStatusEqual(TicketState.TAKE_AWAY.getCode()));
		    statusSpecs = statusSpecs.or(TicketSpecification.isStatusEqual(TicketState.NEED_UPDATE.getCode()));
		    statusSpecs = statusSpecs.or(TicketSpecification.isStatusEqual(TicketState.PRINT_FAILURE.getCode()));
		    statusSpecs = statusSpecs.or(TicketSpecification.isStatusEqual(TicketState.PRINT_ERROR.getCode()));
            //statusSpecs = statusSpecs.or(TicketSpecification.isStatusEqual(TicketState.CANCELED.getCode()));
		    specs = specs.and(statusSpecs);
		    
			Page<TTicket> failurePage = ticketService.findAll(specs, pr);
			Iterator<TTicket> it = failurePage.iterator();
			while(it.hasNext())
			{
				TTicket t = it.next();
                log.info("出票失败,id:" + t.getId());
				try {
                   /* if(this.gameType == ConstantValues.Game_Type_Gaopin.getCode())
                    {
                        TTicket ticketBack = ticketService.printBack(t.getId(), Constants.TICKET_PRINT_RECEIPT_SUCCESS, "SYSTEM", null, "SYSTEM", "SYSTEM", false);
                        mgTicketService.save(ticketBack, this.gameType);
                        TOrder order = orderService.incrPrintCount(ticketBack.getOrderId(), 1);
                        if(order.getStatus() == OrderState.SUCCESS.getCode())
                        {
                            //发送出票成功通知
                            NotifyUtil.sendN02(SchedulerContext.getInstance().getSpringContext(), order);
                        }
                    }
                    else
                    {*/
                        PrintUtil.fail(SchedulerContext.getInstance().getSpringContext(), t, this.termCode);
                   // }
                }
                catch(Exception e)
                {
                    log.error("处理出票失败订单出现错误,id:" + t.getId());
                    e.printStackTrace();
                }
			}
			if(!failurePage.hasNextPage())
			{
				break;
			}
		} while(true);
		
		return RepeatStatus.FINISHED;
	}
}
