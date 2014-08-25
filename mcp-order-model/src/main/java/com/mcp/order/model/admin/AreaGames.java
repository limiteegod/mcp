package com.mcp.order.model.admin;/*
 * User: yeeson he
 * Date: 13-7-30
 * Time: 下午3:00
 */

import javax.persistence.*;

@Entity
@Table(name = "areagames")
public class AreaGames implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3768415034176733403L;
	//地区和游戏的多对多表。
    @Id
    @Column(length = 32)
    private String id;         //!
    
    @Basic
    @Column(length=32)
    private String gameId;        //!
    
    @Basic
    @Column(length=32)
    private String areaId;           //!
    
    @Basic
    private int status; //! d0 可用 1 不可用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
