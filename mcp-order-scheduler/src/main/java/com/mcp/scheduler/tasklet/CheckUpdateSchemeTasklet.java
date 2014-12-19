package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.SchemeType;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.model.ts.Term;
import com.mcp.order.mongo.service.MgCheckService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.util.OrderStateUtil;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.order.status.SchemeState;
import com.mcp.scheduler.common.SchedulerContext;
import com.mcp.scheme.model.SchemeZh;
import com.mcp.scheme.service.SchemeZhService;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class CheckUpdateSchemeTasklet implements Tasklet {

	public static Logger log = Logger.getLogger(CheckUpdateSchemeTasklet.class);

	private String termCode;

    private String nextTermCode;

	private int gameType;

	private String gameCode;

	private String termId;

    @Autowired
    private MgCheckService mgCheckService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SchemeZhService schemeZhService;

    @Autowired
    private TermService termService;

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameType(String gameType) {
		this.gameType = Integer.parseInt(gameType);
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

    public void setNextTermCode(String nextTermCode) {
        this.nextTermCode = nextTermCode;
    }

    @PostConstruct
	public void init()
	{

	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
        String colName = this.mgCheckService.getSchemeColl(gameCode, termCode, SchemeType.SEQ_FOLLOW);
        log.info(colName);
        DBCollection drawColl = mgCheckService.getCollByName(colName);
        DBCursor cur = drawColl.find();
        cur = cur.snapshot();
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            String id = (String)obj.get("_id");
            try {
                long bonus = Long.valueOf(obj.get("bonus").toString());
                long bonusBeforeTax = Long.valueOf(obj.get("bonusBeforeTax").toString());
                log.info("scheme:" + id + ",bonus:" + bonus + ",bonusBeforeTax:" + bonusBeforeTax);
                SchemeZh scheme = this.schemeZhService.incrNewOrder(id, bonus, bonusBeforeTax, this.nextTermCode);
                //如果方案还在进行中，则生成下一期的订单
                if(scheme.getStatus() == SchemeState.RUNNING.getCode())
                {
                    TOrder oldOrder = this.orderService.findOneBySchemeIdAndTermCode(id, this.termCode, true);
                    TOrder newOrder = oldOrder.clone();
                    newOrder.setTermCode(this.nextTermCode);
                    List<TTicket> tList = newOrder.getTickets();
                    for(TTicket t:tList)
                    {
                        t.setTermCode(this.nextTermCode);
                    }

                    //设置订单状态，并发送出票通知
                    Term t = termService.findOneByGameCodeAndCode(gameCode, this.nextTermCode);
                    OrderStateUtil.updateOrderStatus(newOrder, true, t.getStatus());
                    this.orderService.save(newOrder);
                    PrintUtil.newOrder(SchedulerContext.getInstance().getSpringContext(), newOrder);
                }
            }
            catch (Exception e)
            {
                log.error("处理追号方案过程中出现错误,id:" + id);
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        cur.close();
        return RepeatStatus.FINISHED;
	}
}
