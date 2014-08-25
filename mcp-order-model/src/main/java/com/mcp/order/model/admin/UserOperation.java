package com.mcp.order.model.admin;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;

/**
 * Created by hadoop on 2014/6/24.
 */
@Entity
@Table(name = "useroperation")
@JsonFilter("useroperation")
public class UserOperation {

    @Id
    @Column(length = 32)
    private String id;

    @Basic
    private int userType;

    @Basic
    @Column(length = 32)
    private String operationId;

    @Transient
    private Operation operation;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
