package com.mcp.order.model.ts;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.status.ReceiptState;
import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:36
 * To be the best of me!
 */
@Entity
@Table(name = "tticket")
@JsonFilter("tticket")
public class TTicket implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3957816035611121187L;

	//    一张票ticket是最基本的彩票单位。
    @Id
    @Column(length = 32)
    private String id;

    @Basic
    @Column(length = 32)
    private String orderId;
    
    @Basic
    private int seq;
    
    /**
     * 仅供竞彩使用，已经完成的场次数目，完成指：场次已经开奖
     */
    @Basic
    private int finishedCount;

    @Basic
    @Column(length = 32)
    private String terminalId;

    @Basic
	@Column(length = 32)
	private String stationId;
    
    @Basic
    @Column(length = 32)
    private String customerId;
    
    @Basic
    @Column(length = 10)
    private String channelCode;
    
    /**
     * 普通游戏为期次代码；
     * 竞彩，比赛的代码，竞彩由场次驱动，存储了所有场次的代码
     */
    @Basic
    @Column(length = 240)
    private String termCode;
	
	@Basic
	@Column(length = 10)
    private String gameCode;

    @Basic
    @Column(length = 10)
    private String betTypeCode;

    @Basic
    @Column(length = 10)
    private String playTypeCode;

    @Basic
    private long amount;  //总价

    @Basic
    private int multiple; //倍数

    @Basic
    private int price;//单注单价。

    @Basic
    @Column(length = 360)
    private String numbers;//单式多注之间用;分割。
    
    /**
     * 竞彩需要返回出票时的详细号码的赔率等信息
     */
    @Basic
    @Column(length = 360)
    private String rNumber;
    
    /**
     * 开奖号码
     */
    @Basic
    @Column(length = 80)
    private String dNumber;

    @Basic
    private int termIndex;	//从0开始

    @Basic
    private Date termIndexDeadline;

    @Basic
    private Date acceptTime;  // 数据入库，系统接受订单的时间。
    
    @Basic
    private Date createTime;   //创建时间 ，首次接收到请求的时间。

    @Basic
    @Column(length = 80)
    private String serialNumber;    //对应的实体票的序列号
    
    @Basic
    @Column(length = 2048)
    private String stubInfo;    //对应的实体票的票根信息

	@Basic
    private Date printTime; //最终出票完毕的时间。

    @Basic
    @Column(length = 80)
    private String encrypt;        //

    @Basic
    private int type;      //票类型。0 电子票和实体票均有  1 只有实体票  2 只有电子票

    @Basic
    private boolean bigBonus;//是否大奖，是否需要特殊处理的处理流程。

    @Basic
    @Column(length = 200)
    private String winDesc;      //中奖详情  。json格式的字符串描述。

    @Basic
    private long bonus;//奖金总额。 税前奖金。单位为分。

    @Basic
    private long bonusBeforeTax;
    
    /**
     * 可能的奖金，竞彩中会用到
     */
    @Basic
    private long possiblePrize;
    
    /**
     * 票据状态
     */
    @Basic
    private int receiptStatus;
    
    @Basic
    private int status;
    
    /**
     * 是否已经出纸质票
     */
    @Basic
    private boolean paper;
    
    /**
     * 打印的投注站id
     */
    @Basic
    @Column(length = 32)
    private String printerStationId;
    
    /**
     * 出票系统取走时间
     */
    @Basic
    private Date sysTakeTime;
    
    @Version
    private int version;
    
    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

    public String getStubInfo() {
		return stubInfo;
	}

	public void setStubInfo(String stubInfo) {
		this.stubInfo = stubInfo;
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getBetTypeCode() {
        return betTypeCode;
    }

    public void setBetTypeCode(String betTypeCode) {
        this.betTypeCode = betTypeCode;
    }

    public String getPlayTypeCode() {
        return playTypeCode;
    }

    public void setPlayTypeCode(String playTypeCode) {
        this.playTypeCode = playTypeCode;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
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

    @JsonIgnore
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getTermIndex() {
        return termIndex;
    }

    public void setTermIndex(int termIndex) {
        this.termIndex = termIndex;
    }

    public Date getTermIndexDeadline() {
        return termIndexDeadline;
    }

    public void setTermIndexDeadline(Date termIndexDeadline) {
        this.termIndexDeadline = termIndexDeadline;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public boolean isBigBonus() {
		return bigBonus;
	}

	public void setBigBonus(boolean bigBonus) {
		this.bigBonus = bigBonus;
	}

	public String getWinDesc() {
        return winDesc;
    }

    public void setWinDesc(String winDesc) {
        this.winDesc = winDesc;
    }

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public int getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(int receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getrNumber() {
		return rNumber;
	}

	public void setrNumber(String rNumber) {
		this.rNumber = rNumber;
	}

	public long getPossiblePrize() {
		return possiblePrize;
	}

	public void setPossiblePrize(long possiblePrize) {
		this.possiblePrize = possiblePrize;
	}

	public String getdNumber() {
		return dNumber;
	}

	public void setdNumber(String dNumber) {
		this.dNumber = dNumber;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getFinishedCount() {
		return finishedCount;
	}

	public void setFinishedCount(int finishedCount) {
		this.finishedCount = finishedCount;
	}

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}

	public String getPrinterStationId() {
		return printerStationId;
	}

	public void setPrinterStationId(String printerStationId) {
		this.printerStationId = printerStationId;
	}

	public Date getSysTakeTime() {
		return sysTakeTime;
	}

	public void setSysTakeTime(Date sysTakeTime) {
		this.sysTakeTime = sysTakeTime;
	}

    public long getBonusBeforeTax() {
        return bonusBeforeTax;
    }

    public void setBonusBeforeTax(long bonusBeforeTax) {
        this.bonusBeforeTax = bonusBeforeTax;
    }

    /**
	 * id,orderId,customerId,stationId,playTypeCode,betTypeCode,numbers,price,multiple,rNumber,gameCode,status
	 * 转换成String对象
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(Constants.SEP_DES);
		sb.append(orderId);
		sb.append(Constants.SEP_DES);
		sb.append(customerId);
		sb.append(Constants.SEP_DES);
		sb.append(stationId);
		sb.append(Constants.SEP_DES);
		sb.append(playTypeCode);
		sb.append(Constants.SEP_DES);
		sb.append(betTypeCode);
		sb.append(Constants.SEP_DES);
		sb.append(numbers);
		sb.append(Constants.SEP_DES);
		sb.append(price);
		sb.append(Constants.SEP_DES);
		sb.append(multiple);
		sb.append(Constants.SEP_DES);
		sb.append(rNumber);
		sb.append(Constants.SEP_DES);
		sb.append(gameCode);
		sb.append(Constants.SEP_DES);
		sb.append(status);
		sb.append(Constants.SEP_DES);
		sb.append(termCode);
		return sb.toString();
	}

    public TTicket clone()
    {
        TTicket t = new TTicket();
        t.setId(CoreUtil.getUUID());
        t.setOrderId(this.orderId);
        t.setPrinterStationId(this.printerStationId);
        t.setAmount(this.amount);
        t.setBonus(0);
        t.setBonusBeforeTax(0);
        t.setCreateTime(new Date());
        t.setAcceptTime(new Date());
        t.setBetTypeCode(this.betTypeCode);
        t.setChannelCode(this.channelCode);
        t.setCustomerId(this.customerId);
        t.setEncrypt(this.encrypt);
        t.setFinishedCount(0);
        t.setMultiple(this.multiple);
        t.setNumbers(this.numbers);
        t.setType(this.type);
        t.setStationId(this.stationId);
        t.setSeq(this.seq);
        t.setPaper(this.paper);
        t.setPlayTypeCode(this.playTypeCode);
        t.setPrice(this.price);
        t.setGameCode(this.getGameCode());
        t.setReceiptStatus(ReceiptState.NOT_TAKE_AWAY.getCode());
        return t;
    }
	
	/**
	 * 转换成算奖的字符串
	 * @return
	 */
	public String toCheckString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(Constants.SEP_DES);
		sb.append(termCode);
		sb.append(Constants.SEP_DES);
		sb.append(finishedCount);
		return sb.toString();
	}
}
