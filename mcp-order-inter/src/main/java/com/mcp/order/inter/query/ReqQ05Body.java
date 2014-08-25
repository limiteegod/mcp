package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ05Body extends ReqBody {
	
	private String stationId;
	
	private String keyword;
	
	private double longitude;
	private double latitude;
	private double longitudeGap;
	private double latitudeGap;
	
	/**
	 * 页号
	 */
	private int startIndex = 0;
	
	/**
	 * 每页记录数
	 */
    private int size = 10;

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public double getLongitudeGap() {
		return longitudeGap;
	}

	public void setLongitudeGap(double longitudeGap) {
		this.longitudeGap = longitudeGap;
	}

	public double getLatitudeGap() {
		return latitudeGap;
	}

	public void setLatitudeGap(double latitudeGap) {
		this.latitudeGap = latitudeGap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mcp.core.inter.define.Body#validate()
	 */
	@Override
	public void validate() throws CoreException {
		
	}
}
