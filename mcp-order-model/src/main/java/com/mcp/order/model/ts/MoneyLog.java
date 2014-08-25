/**
 *
 */
package com.mcp.order.model.ts;

import com.mcp.order.common.ConstantValues;
import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;

/**
 * @author ming.li
 */
@Entity
@Table(name = "moneylog")
@JsonFilter("moneylog")
public class MoneyLog implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2672310226872060023L;

	/**
     * 主键
     */
    @Id
    @Column(length = 32)
    private String id;

    /**
     * 业务定义代码
     */
    @Basic
    @Column(length = 40)
    private String handlerCode;
    /**
     * 对应于accountOperatorType.xml的操作码。只记录记账方的operationCode。
     */
    @Basic
    @Column(length = 40)
    private String operationCode;
    
    /**
     * 对应于accountOperatorType.xml的操作码的科目名称。
     */
    @Basic
    @Column(length = 40)
    private String subject;

    /**
     * 交易订单的id。可以为空。相同订单号的id说明是同一个操作的多条记录。orderId对应的table及字段由handlerCode确定。
     */
    @Basic
    @Column(length = 40)
    private String orderId;

    /**
     * 操作员的id。用于操作日志。
     */
    @Basic
    @Column(length = 32)
    private String userId;

    /**
     * 产生此条记录的时间，如果是异步写明细数据，则createTimeStamp和acceptTimeStamp是不一样的。
     */
    @Basic
    private long createTimeStamp;

    /**
     * 日志记录写入数据库的时间。插入时new Date（） 获取
     */
    @Basic
    private long acceptTimeStamp;


    /**
     * 出账操作的账户编码。
     */
    @Basic
    @Column(length = 40)
    private String fromAccountCode;

    /**
     * 出账操作的账户ID标示。使用该标示和fromAccountCode，可以获取特定数据库特定数据表的特定行的特定列。
     */
    @Basic
    @Column(length = 32)
    private String fromEntityId;


    /**
     * fromAccountCode 对应的账户数据，在操作前的数值。
     */
    @Basic
    private long stateBefore;

    /**
     * fromAccountCode 对应的账户数据，在操作后的数值。
     */
    @Basic
    private long stateAfter;

    /**
     * 资金变动的金额
     */

    @Basic
    private long amount;

    /**
     * 出账操作的账户编码。
     */
    @Basic
    @Column(length = 40)
    private String toAccountCode;

    /**
     * 出账操作的账户ID标示。使用该标示和fromAccountCode，可以获取特定数据库特定数据表的特定行的特定列。
     */
    @Basic
    @Column(length = 32)
    private String toEntityId;
    
    /**
     * 资金操作状态
     */
    @Basic
    private int status = ConstantValues.MoneyLog_Status_FINISHED.getCode();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandlerCode() {
        return handlerCode;
    }

    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public long getAcceptTimeStamp() {
        return acceptTimeStamp;
    }

    public void setAcceptTimeStamp(long acceptTimeStamp) {
        this.acceptTimeStamp = acceptTimeStamp;
    }

    public String getFromAccountCode() {
        return fromAccountCode;
    }

    public void setFromAccountCode(String fromAccountCode) {
        this.fromAccountCode = fromAccountCode;
    }

    public String getFromEntityId() {
        return fromEntityId;
    }

    public void setFromEntityId(String fromEntityId) {
        this.fromEntityId = fromEntityId;
    }

    public long getStateBefore() {
        return stateBefore;
    }

    public void setStateBefore(long stateBefore) {
        this.stateBefore = stateBefore;
    }

    public long getStateAfter() {
        return stateAfter;
    }

    public void setStateAfter(long stateAfter) {
        this.stateAfter = stateAfter;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getToAccountCode() {
        return toAccountCode;
    }

    public void setToAccountCode(String toAccountCode) {
        this.toAccountCode = toAccountCode;
    }

    public String getToEntityId() {
        return toEntityId;
    }

    public void setToEntityId(String toEntityId) {
        this.toEntityId = toEntityId;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
