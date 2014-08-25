package com.mcp.order.model.ts;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "termlog")
public class TermLog implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3868727865536172073L;

	@Id
    @Column(length = 32)
    private String id;

    @Basic
    private Date hTime;

    @Basic
    private int handleCode;

    @Basic
    @Column(length = 32)
    private String userId;

    @Basic
    @Column(length = 32)
    private String termId;

    @Basic
    @Column(length = 80)
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date gethTime() {
		return hTime;
	}

	public void sethTime(Date hTime) {
		this.hTime = hTime;
	}

	public int getHandleCode() {
        return handleCode;
    }

    public void setHandleCode(int handleCode) {
    	this.handleCode = handleCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
