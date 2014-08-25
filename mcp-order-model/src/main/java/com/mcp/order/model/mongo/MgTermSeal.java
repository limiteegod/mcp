package com.mcp.order.model.mongo;

import java.util.Date;

/**
 * Created by ming.li on 2014/4/11.
 */
public class MgTermSeal {

    private String id;

    /**
     * 游戏代码
     */
    private String gameCode;

    /**
     * 期次代码
     */
    private String termCode;

    /**
     * 封存时间
     */
    private Date sTime;

    /**
     * 封存文件的路径
     */
    private String filePath;

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

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
