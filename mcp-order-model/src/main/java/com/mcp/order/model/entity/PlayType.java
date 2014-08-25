package com.mcp.order.model.entity;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:40
 * To be the best of me!
 */
public class PlayType implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7796567451630248336L;
	
	//    玩法类型，比如组选三、标准大乐透投注等。
    private String id;
    private String gameCode;
    private String typeCode;
    private String typeName;
    
    /**
     * 单注的价格
     */
    private int price;
    
    private int status;
    
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
	
	/**
     * 获得单注的价格
     */
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
}
