package com.mcp.scheduler.tasklet;

import com.mcp.core.util.StringUtil;
import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.model.ts.Term;
import com.mcp.order.mongo.service.MgCheckService;
import com.mcp.order.mongo.service.MgOrderService;
import com.mcp.order.mongo.service.MgTicketService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.TicketService;
import com.mcp.rmi.inter.SchemeInter;
import com.mcp.scheduler.common.SchedulerContext;
import com.mcp.scheme.service.SchemeZhService;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import java.util.List;

public class CheckTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(CheckTasklet.class);
	
	private String termCode;
	
	private int gameType;
	
	private String gameCode;
	
	private String termId;
	
	//开奖号码
	private String[] number;
	
	//开奖号码
	private String dNumber;
	
	//奖级信息，竞彩则包含各场次的开奖号码
	private PrizeDescription pd;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MgTicketService mgTicketService;

    @Autowired
    private MgCheckService mgCheckService;

    @Autowired
    private SchemeZhService schemeZhService;

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

	@PostConstruct
	public void init()
	{
		Term term = this.termService.findOne(this.termId);
		this.dNumber = term.getWinningNumber();
        if(StringUtil.isEmpty(this.dNumber))
        {
            log.error("gameCode:" + this.gameCode + ",termCode:" + this.termCode + ",无开奖号码............");
        }
        this.dNumber = this.dNumber.trim(); //有时发现录入的开奖号码的最后有空白字符，导致算奖结果不正确
		if(gameType == ConstantValues.Game_Type_Normal.getCode() || gameType == ConstantValues.Game_Type_Gaopin.getCode())
		{
			this.number = dNumber.split(",|\\|");
			ObjectMapper om = new ObjectMapper();
			try {
				this.pd = om.readValue(term.getPrizeDesc(), PrizeDescription.class);
			} catch (Exception e) {
				throw new RuntimeException("奖级信息的格式错误..........");
			}
		}
		if(this.gameType == ConstantValues.Game_Type_Jingcai.getCode())
		{
			this.pd = new PrizeDescription();
			this.pd.putJcDrawNumber(this.termCode, this.dNumber);
		}
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
        DBCollection drawColl = this.mgTicketService.getDrawCollection(gameCode, termCode);
        DBCursor cur = drawColl.find();
        cur = cur.snapshot();
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            TTicket t = new TTicket();
            try {
                t.setId((String) obj.get("_id"));
                t.setOrderId((String) obj.get("orderId"));
                t.setGameCode((String)obj.get("gameCode"));
                t.setTermCode((String)obj.get("termCode"));
                t.setPlayTypeCode((String)obj.get("playTypeCode"));
                t.setBetTypeCode((String)obj.get("betTypeCode"));
                t.setNumbers((String)obj.get("numbers"));
                t.setrNumber((String)obj.get("rNumber"));
                t.setMultiple(Integer.valueOf(obj.get("multiple").toString()));
                t.setChannelCode((String)obj.get("channelCode"));
                t.setAmount(Long.valueOf(obj.get("amount").toString()));
                t.setPrinterStationId((String) obj.get("printerStationId"));

                String checkCode = getCheckCode(t);
                Check check = SchedulerContext.getInstance().getCheckByCode(checkCode);
                CheckParam cp = check.check(t, number, this.pd);
                long bonus = cp.getBonus()*t.getMultiple();
                long bonusBeforeTax = cp.getBonusBeforeTax()*t.getMultiple();

                String orderId = t.getOrderId();
                TOrder order = this.orderService.findOne(orderId);
                //保存到票据状态
                this.mgCheckService.ticketChecked(t.getId(), gameCode, termCode, t.getChannelCode(), this.dNumber, bonus, bonusBeforeTax, t.getAmount(), order.getType(), t.getPrinterStationId());

                //保存订单状态
                this.mgCheckService.orderChecked(orderId, gameCode, termCode, order.getChannelCode(), this.dNumber, bonus, bonusBeforeTax, order.getFinishedTicketCount(), order.getTicketCount(), order.getCustomerId(), order.getType(), order.getStationId(), order.getSchemeType(), order.getAmount(), order.getOuterId());
                //保存方案状态
                String schemeId = order.getSchemeId();
                if(!StringUtil.isEmpty(schemeId))
                {
                    this.mgCheckService.schemeChecked(schemeId, gameCode, termCode, order.getChannelCode(), bonus, bonusBeforeTax, order.getSchemeType());
                }
            }
            catch (Exception e)
            {
                log.error("算奖过程中出现错误,id:" + t.getId());
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        cur.close();
        return RepeatStatus.FINISHED;
		/*int pIndex = 0, size = 100;
		do {
			PageRequest pr = new PageRequest(pIndex, size);
			List<TTicket> tList = this.mgTicketService.findFromDraw(gameCode, termCode, pr);
			for(TTicket t:tList)
			{
                try {
                    String checkCode = getCheckCode(t);
                    Check check = SchedulerContext.getInstance().getCheckByCode(checkCode);
                    CheckParam cp = check.check(t, number, this.pd);
                    long bonus = cp.getBonus()*t.getMultiple();
                    long bonusBeforeTax = cp.getBonusBeforeTax()*t.getMultiple();
                    //保存到票据状态
                    this.mgCheckService.ticketChecked(t.getId(), gameCode, termCode, t.getChannelCode(), this.dNumber, bonus, bonusBeforeTax);
                    String orderId = t.getOrderId();
                    TOrder order = this.orderService.findOne(orderId);
                    //保存订单状态
                    this.mgCheckService.orderChecked(orderId, gameCode, termCode, order.getChannelCode(), this.dNumber, bonus, bonusBeforeTax);
                    //保存方案状态
                    String schemeId = order.getSchemeId();
                    if(!StringUtil.isEmpty(schemeId))
                    {
                        this.mgCheckService.schemeChecked(schemeId, order.getSchemeType(), gameCode, termCode, order.getChannelCode(), bonus, bonusBeforeTax);
                    }*/
                    /*t.setBonus(prize);
                    t.setBonusBeforeTax(bonusBeforeTax);
                    t.setdNumber(dNumber);
                    if(prize > 0)
                    {
                        this.mgTicketService.saveHit(t, termCode);
                    }
                    else
                    {
                        this.mgTicketService.saveNotHit(t, termCode);
                    }*/
                    //udpate tticket set receiptStatus=?,bonus=?,bonusBeforeTax=? where id=?
                    //ticketService.updateCheckInfo(dNumber, prize, bonusBeforeTax, t.getId());

                    /*TOrder order = this.orderService.findOne(t.getOrderId());

                    TOrder order = orderService.incrBonusById(dNumber, prize, bonusBeforeTax, t.getOrderId());
                    mgOrderService.save(order, termCode);

                    //方案中奖
                    if(prize > 0 && order.getSchemeType() != ConstantValues.TScheme_Type_Default.getCode())
                    {
                        schemeInter.hit(order.getSchemeId(), order.getSchemeType(), prize, bonusBeforeTax);
                    }*/
                /*catch (Exception e)
                {
                    log.error("算奖过程中出现错误,id:" + t.getId());
                    log.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    this.mgTicketService.deleteFromDraw(gameCode, termCode, t.getId());
                }
            }
			if(tList.size() == 0)
			{
				break;
			}
		} while(true);*/
	}
	
	/**
	 * 获得算奖所用的实例bean，如果是竞彩，校验期次的开奖号码是否完整
	 * @param ticket
	 * @return
	 */
	private String getCheckCode(TTicket ticket)
	{
		if(this.gameType == ConstantValues.Game_Type_Jingcai.getCode())
		{
			//校验场次开奖号码是否存在，不存在，则从数据库中寻找
			String termCode = ticket.getTermCode();
			String[] termCodeArray = termCode.split(",");
			for(int j = 0; j < termCodeArray.length; j++)
			{
				if(!this.pd.hasJcDrawNumber(termCodeArray[j]))
				{
					//pd中没有开奖号码，则从数据库读取
					Term t = termService.findOneByGameCodeAndCode(gameCode, termCodeArray[j]);
					this.pd.putJcDrawNumber(termCodeArray[j], t.getWinningNumber());
				}
			}
			
			//竞彩，使用一个算奖类
			return gameCode + ticket.getPlayTypeCode() + "00";
		}
		else
		{
			return gameCode + ticket.getPlayTypeCode() + ticket.getBetTypeCode();
		}
	}
}
