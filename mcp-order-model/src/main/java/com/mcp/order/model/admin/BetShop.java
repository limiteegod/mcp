/**
 * 
 */
package com.mcp.order.model.admin;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;

/**
 * @author ming.li
 *
 */
@Entity
@Table(name = "betshop")
@JsonFilter("betshop")
public class BetShop implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 655522734621010076L;
	
	@Id
    @Column(length = 32)
    private String id; //!
	
	@Basic
    @Column(length = 32)
    private String stationId;//机构的id

    @Basic
    @Column(length = 40)
    private String name;//机构的id
	
	@Basic
    @Column(length = 32)
    private String areaId;//!所属区域的ID。

    @Basic
    @Column(length = 32)
    private String conservatorId;//!对应的监管员的ID。
    
    @Basic
    private double longitude;   //经度NTU

    @Basic
    private double latitude;   //玮度NTU

    @Basic
    @Column(length = 200)
    private String address;   //!
    
    @Basic
    @Column(length = 40)
    private String managerName;   //!

    @Basic
    @Column(length = 20)
    private String managerPhone;   //!
    
    @Basic
    private int rank;//评级   //!

    @Basic
    private int point;//站点评级积分，用于站点评级。   //!


    @Basic
    @Column(length = 200)
    private String configUrl;//！配置文件的URL。

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getConservatorId() {
		return conservatorId;
	}

	public void setConservatorId(String conservatorId) {
		this.conservatorId = conservatorId;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}	
