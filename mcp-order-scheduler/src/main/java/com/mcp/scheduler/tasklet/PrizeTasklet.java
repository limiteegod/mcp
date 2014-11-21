package com.mcp.scheduler.tasklet;

import com.mcp.core.util.cons.BonusLevelType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgPrizeService;
import com.mcp.order.service.MoneyService;
import com.mcp.order.service.OrderService;
import com.mcp.order.status.OrderState;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

public class PrizeTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(PrizeTasklet.class);
	
	private String termCode;
	
	private String gameCode;
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private MgOrderService mgOrderService;
	
	@Autowired
	private OrderService orderService;

    @Autowired
    private MgPrizeService mgPrizeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	@PostConstruct
	public void init()
	{
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
        String sqlFormat = "update torder set status=%d where id='%s'";
        int batchSize = 100;
        String[] sqlList = new String[batchSize];
        DBCollection drawColl = mgPrizeService.getCollByName(this.mgPrizeService.getOrderColl(gameCode, termCode, BonusLevelType.LITTLE_HIT));
        int count = 0, allCount = 0;
        DBCursor cur = drawColl.find();
        cur = cur.snapshot();
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            String id = (String)obj.get("_id");
            String customerId = (String)obj.get("customerId");
            String stationId = (String)obj.get("stationId");
            int type = Integer.valueOf(obj.get("type").toString());
            long bonus = Long.valueOf(obj.get("bonus").toString());
            int schemeType = Integer.valueOf(obj.get("schemeType").toString());

            if(schemeType == ConstantValues.TScheme_Type_Default.getCode() || schemeType == ConstantValues.TScheme_Type_Follow.getCode())
            {
                //普通无方案的订单，或者追号方案的订单，才进行返奖，合买方案返奖有方案自身驱动
                if(type == ConstantValues.TOrder_Type_Customer.getCode())
                {
                    moneyService.rewardToCustomer(stationId, customerId, id, bonus, "order");
                }
            }
            //渠道返奖和出票中心扣奖金，应该由统计一个统一的数字，进行扣取
            sqlList[count] = String.format(sqlFormat, OrderState.PRIZED.getCode(), id);
            log.info(sqlList[count]);
            count++;
            allCount++;
            if(count == batchSize || !cur.hasNext())
            {
                try {
                    if(count < batchSize)
                    {
                        String[] execSql = new String[count];
                        System.arraycopy(sqlList, 0, execSql, 0, count);
                        this.jdbcTemplate.batchUpdate(execSql);
                    }
                    else
                    {
                        this.jdbcTemplate.batchUpdate(sqlList);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    count = 0;
                }
            }
        }
        cur.close();
        log.info("返奖完成........订单总计:" + allCount);
        return RepeatStatus.FINISHED;

		/*int pIndex = 0, size = 100;
		do {
			PageRequest pr = new PageRequest(pIndex, size);
			List<TOrder> oList = this.mgOrderService.findLittle(gameCode, termCode, pr);
			for(TOrder t:oList)
			{
                try {
                    int schemeType = t.getSchemeType();
                    if(schemeType == ConstantValues.TScheme_Type_Default.getCode() || schemeType == ConstantValues.TScheme_Type_Follow.getCode())
                    {
                        //普通无方案的订单，或者追号方案的订单，才进行返奖，合买方案返奖有方案自身驱动
                        if(t.getType() == ConstantValues.TOrder_Type_Customer.getCode())
                        {
                            moneyService.rewardToCustomer(t.getStationId(), t.getCustomerId(), t.getId(), t.getBonus(), "order");
                        }
                        else
                        {
                            moneyService.rewardToStation(t.getCustomerId(), t.getId(), t.getBonus(), "order");
                        }
                    }
                    //出票机构支付奖金
                    moneyService.stationReward(t.getPrintStationId(), t.getId(), t.getBonus());

                    t.setStatus(OrderState.PRIZED.getCode());
                    orderService.updateStatusById(OrderState.PRIZED.getCode(), t.getId());

                    this.mgOrderService.savePrized(t, termCode);
                }
                catch (Exception e)
                {
                    log.error("返奖过程中出现错误,id:" + t.getId());
                    log.error(e.getStackTrace());
                } finally {
                    this.mgOrderService.deleteLittle(gameCode, termCode, t.getId());
                }
            }
			if(oList.size() == 0)
			{
				break;
			}
		} while(true);
		return RepeatStatus.FINISHED;*/
	}
}
