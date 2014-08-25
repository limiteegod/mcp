package com.mcp.order.model.admin;/*
 * User: yeeson he
 * Date: 13-7-30
 * Time: 下午2:39
 */

import javax.persistence.*;

@Entity
@Table(name = "clientFileVersion")
public class ClientFileVersion implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1249444243618829893L;
	
	//用于存储版本化的文件。暂未使用。
    @Id
    @Column(length = 32)
    private String id;    //!
    
    @Basic
    @Column(length=20)
    private String fileTypeCode;  //!
    
    @Basic
    @Column(length=20)
    private String fileTypeName;   //!
    
    @Basic
    @Column(length=20)
    private String clientCode;//！d00.00 内部的产品编号，用于产品线管理，以及使用产品编号查找版本化的帮助文件。
    
    @Basic
    @Column(length=20)
    private String fileVersionCode; //!
    
    @Basic
    @Column(length=200)
    private String fileUrl;    //!
    
    @Basic
    private int status;//文件的状态。 d0 可用 1 不可用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileTypeCode() {
        return fileTypeCode;
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getFileVersionCode() {
        return fileVersionCode;
    }

    public void setFileVersionCode(String fileVersionCode) {
        this.fileVersionCode = fileVersionCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
