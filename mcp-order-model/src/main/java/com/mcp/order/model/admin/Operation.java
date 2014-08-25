package com.mcp.order.model.admin;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;

/**
 * Created by hadoop on 2014/6/24.
 */
@Entity
@Table(name = "operation")
@JsonFilter("operation")
public class Operation {

    @Id
    @Column(length = 32)
    private String id;

    @Basic
    @Column(length = 40)
    private String name;

    @Basic
    @Column(length = 100)
    private String url;

    @Basic
    private int type;

    @Basic
    @Column(length = 32)
    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
