package com.mcp.order.model.entity;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.JcValidator;
import com.mcp.order.validator.Validator;

import javax.persistence.Basic;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:40
 * To be the best of me!
 */
public class BetType implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3713720928276548134L;
	
	//    投注方式，比如单式、复式等。
    private String id;
    private String gameCode;
    private String typeCode;
    private String typeName;
    
    /**
     * 算奖用的类，对应Spring中已经定义好的bean
     */
    private String check;
    
	/**
     * 校验用的类
     */
    private String validatorClass;
    
    private Validator validator;
    
    private JcValidator jcValidator;
    
    private int status;
    
    /**
	 * 单位为秒，后台用提前停售时间
	 */
	@Basic
	private int offset;
    
    public String getCheck() {
		return check;
	}
    
	public void setCheck(String check) {
		this.check = check;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getValidatorClass() {
		return validatorClass;
	}
	
	public void setValidatorClass(String validatorClass) {
		this.validatorClass = validatorClass;
	}
	
	public Validator getValidator()
	{
		if(this.validator == null)
		{
			try {
				this.validator = (Validator)Class.forName(this.validatorClass).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CoreException(ErrCode.E0999, "请检查校验类");
			}
		}
		return this.validator;
	}
	
	public JcValidator getJcValidator()
	{
		if(this.jcValidator == null)
		{
			try {
				this.jcValidator = (JcValidator)Class.forName(this.validatorClass).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CoreException(ErrCode.E0999, "请检查校验类");
			}
		}
		return this.jcValidator;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
