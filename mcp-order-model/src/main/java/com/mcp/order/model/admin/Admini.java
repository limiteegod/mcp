package com.mcp.order.model.admin;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;

/**
 * Created by limitee on 2014/7/29.
 */
@Entity
@Table(name = "admini")
@JsonFilter("admini")
public class Admini implements java.io.Serializable {


    private static final long serialVersionUID = 2299863111156588727L;

    @Id
    @Column(length=32)
    private String id;

    @Basic
    @Column(length=40, unique = true, nullable = false)
    private String name;

    @Basic
    @Column(length=40, nullable = false)
    private String password;

    @Basic
    private int type;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
