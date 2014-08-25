package com.mcp.order.model.admin;

import javax.persistence.*;
import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午11:38
 * To be the best of me!
 */
@Entity
@Table(name = "clientVersion")
public class ClientVersion implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6319198874465965370L;
	
	//    客户端类型 .用于销售渠道统计，客户端软件分发等。
    @Id
    @Column(length = 32)
    private String id;       //!
    
    @Basic
    @Column(length=20)
    private String clientCode;//！d00.00 内部的产品编号，用于产品线管理，以及使用产品编号查找版本化的帮助文件。
    
    @Basic
    @Column(length=20)
    private String developTeamName; //开发组名称。用于研发管理。
    
    @Basic
    @Column(length=20)
    private String clientType;  //!客户端类型：0 安卓 1 ios 2 html5 3 网站 4 其它
    
    @Basic
    @Column(length=20)
    private String versionCode;   // 版本号
    
    @Basic
    @Column(length=20)
    private String compatibleVersion;//兼容机型。
    
    @Basic
    @Column(length=200)
    private String downloadUrl;     //下载地址
    
    @Basic
    @Column(length=100)
    private String downloadNote;      //下载说明
    
    @Basic
    private int downloadCount;      //d0下载次数
    
    @Basic
    private Date createTime;              //!上线时间
    
    @Basic
    private int status;//!当前状态。d0 正常，1 等待上线 2  下线

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getDevelopTeamName() {
        return developTeamName;
    }

    public void setDevelopTeamName(String developTeamName) {
        this.developTeamName = developTeamName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getCompatibleVersion() {
        return compatibleVersion;
    }

    public void setCompatibleVersion(String compatibleVersion) {
        this.compatibleVersion = compatibleVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadNote() {
        return downloadNote;
    }

    public void setDownloadNote(String downloadNote) {
        this.downloadNote = downloadNote;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
