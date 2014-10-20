package com.mcp.order.model.ts;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.cons.SchemeType;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:35
 * To be the best of me!
 */
@Entity
@Table(name = "torder")
@JsonFilter("torder")
public class TOrder implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2316992846961730575L;

	/*
    一个订单order包含属于同一个彩种的多个票ticket。只有所有的ticket的状态完全一致，
    才能说明这个order的状态是正常的。否则就是状态不完整的order。
     */
    @Id
    @Column(length = 32)
    private String id;

    /**
     * 普通游戏，存储为的购买的期次
     * 如果为竞彩，存储的为最后一场的场次代码
     */
    @Basic
    @Column(length = 40)
    private String termCode;

    @Basic
    @Column(length = 32)
    private String schemeId; //对应的TSchema的id，可以为空。

    @Basic
    @Column(length = 32)
    private String stationId; //对应的投注站的id

    @Basic
    @Column(length = 10)
    private String gameCode;//对应的彩种编号。

    @Basic
    @Column(length = 40)
    private String outerId;  //外部id，供存储第三方销售系统的订单号。可以为空。

    @Basic
    @Column(length = 32)
    private String customerId;
    
    @Basic
    private SchemeType schemeType = SchemeType.NONE;
    
    @Basic
    @Column(length = 20)
    private String channelCode;

    @Basic
    private long amount;         //订单总额。
    
    /**
     * 订单总的票的数目
     */
    @Basic
    private int ticketCount;

    /**
     * -1表示未开奖，0未中奖，>0中奖
     */
    @Basic
    private long bonus;    //奖金总额

    /**
     * 税前奖金
     */
    @Basic
    private long bonusBeforeTax;
    
    @Basic
    private Date takeAwayTime; //彩票取走实体票的时间。
    
    /**
     * 订单的所有票是否取走
     */
    @Basic
    private boolean takeAway;

    @Basic
    private Date createTime;   //创建时间 ，首次接收到请求的时间。

    @Basic
    private Date acceptTime;  // 数据入库，系统接受订单的时间。

	@Basic
    private Date printTime; //最终全部出票完毕的时间。

    @Basic
    @Column(length = 20)
    private String platform;   //来源平台。android、ios等。  0 安卓 1 ios 2 html5 3 网站 4 其它
    
    @Basic
    private int payType; // 0 现金支付 1 第三方支付
    
    @Basic
    @Column(length = 40)
    private String notes;//订单备注，一般由销售系统产生。用于需要原值传回的内容。可以为空。

    @Basic
    @Column(length = 80)
    private String encrypt;

    @Basic
    private int status;
	
    /**
     * 已经完成的票的数目
     */
	@Basic
	private int finishedTicketCount;
	
	/**
	 * 已经出票的数目
	 */
	@Basic
	private int printCount;

    /**
     * 已经出票失败的数目
     */
    @Basic
    private int printFailCount = 0;
	
	/**
	 * 竞彩倍数在订单级别，需要记录下来
	 */
	@Basic
	private int multiple = 1;	//默认为1倍
	
	/**
	 * 竞彩专用，竞彩需要记录订单级别投注的号码
	 */
	@Basic
	@Column(length = 400)
	private String numbers;
	
	/**
     * 开奖号码
     */
    @Basic
    @Column(length = 80)
    private String dNumber;
    
    /**
     * 打印的投注站id
     */
    @Basic
    @Column(length = 32)
    private String printStationId;
    
    /**
	 * 订单类型，目前有两种，一种为彩民订单，一种为渠道直接投注订单
	 */
	@Basic
	private TOrderType type = TOrderType.CUSTOMER;

    @Version
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getdNumber() {
		return dNumber;
	}

	public void setdNumber(String dNumber) {
		this.dNumber = dNumber;
	}

    public TOrderType getType() {
        return type;
    }

    public void setType(TOrderType type) {
        this.type = type;
    }

    @Transient
    private List<TTicket> tickets;
    
    public List<TTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<TTicket> tickets) {
		this.tickets = tickets;
	}

	public Date getTakeAwayTime() {
		return takeAwayTime;
	}

	public void setTakeAwayTime(Date takeAwayTime) {
		this.takeAwayTime = takeAwayTime;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public SchemeType getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(SchemeType schemeType) {
        this.schemeType = schemeType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

    public long getBonusBeforeTax() {
        return bonusBeforeTax;
    }

    public void setBonusBeforeTax(long bonusBeforeTax) {
        this.bonusBeforeTax = bonusBeforeTax;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public boolean isTakeAway() {
		return takeAway;
	}

	public void setTakeAway(boolean takeAway) {
		this.takeAway = takeAway;
	}

	public int getFinishedTicketCount() {
		return finishedTicketCount;
	}

	public void setFinishedTicketCount(int finishedTicketCount) {
		this.finishedTicketCount = finishedTicketCount;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}
	
	public String getPrintStationId() {
		return printStationId;
	}

	public void setPrintStationId(String printStationId) {
		this.printStationId = printStationId;
	}

    public int getPrintFailCount() {
        return printFailCount;
    }

    public void setPrintFailCount(int printFailCount) {
        this.printFailCount = printFailCount;
    }

    public TOrder clone()
	{
		TOrder order = new TOrder();
		order.setId(CoreUtil.getUUID());
		order.setAmount(amount);
		order.setBonus(0);
        order.setBonusBeforeTax(0);
		order.setChannelCode(channelCode);
		order.setCreateTime(new Date());
        order.setAcceptTime(new Date());
		order.setCustomerId(customerId);
		order.setEncrypt(encrypt);
		order.setFinishedTicketCount(0);
		order.setGameCode(gameCode);
		order.setMultiple(multiple);
		order.setNotes(notes);
		order.setNumbers(numbers);
		order.setPayType(payType);
		order.setPlatform(platform);
		order.setPrintCount(0);
		order.setPrintStationId(printStationId);
		order.setSchemeId(schemeId);
		order.setSchemeType(schemeType);
		order.setStationId(stationId);
		order.setStatus(status);
		order.setTermCode(termCode);
		order.setTicketCount(ticketCount);
        if(this.tickets != null)
        {
            List<TTicket> tList = new ArrayList<TTicket>();
            for(TTicket t:this.tickets)
            {
                TTicket newTicket = t.clone();
                newTicket.setOrderId(order.getId());
                tList.add(newTicket);
            }
            order.setTickets(tList);
        }
		return order;
	}
	
	/**
	 * id,schemeId,schemeType,customerId,stationId,gameCode,termCode,bonus,status,amount
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(Constants.SEP_DES);
		sb.append(schemeId);
		sb.append(Constants.SEP_DES);
		sb.append(schemeType);
		sb.append(Constants.SEP_DES);
		sb.append(customerId);
		sb.append(Constants.SEP_DES);
		sb.append(stationId);
		sb.append(Constants.SEP_DES);
		sb.append(gameCode);
		sb.append(Constants.SEP_DES);
		sb.append(termCode);
		sb.append(Constants.SEP_DES);
		sb.append(bonus);
		sb.append(Constants.SEP_DES);
		sb.append(status);
		sb.append(Constants.SEP_DES);
		sb.append(amount);
		sb.append(Constants.SEP_DES);
		sb.append(ticketCount);
		sb.append(Constants.SEP_DES);
		sb.append(printCount);
		sb.append(Constants.SEP_DES);
		sb.append(payType);
		sb.append(Constants.SEP_DES);
		sb.append(printStationId);
		return sb.toString();
	}
}
