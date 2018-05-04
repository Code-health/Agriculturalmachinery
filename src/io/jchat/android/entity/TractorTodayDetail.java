package io.jchat.android.entity;

import java.io.Serializable;

public class TractorTodayDetail implements Serializable {


	private String isNewRecord;
	private String carId;
	private String workSerial;
	private String depth;
	private String passRate;
	private String beginTime;
	private String endTime;
	private String totalDistance;
	private String totalLandarea;
	public String getIsNewRecord() {
		return isNewRecord;
	}
	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getWorkSerial() {
		return workSerial;
	}
	public void setWorkSerial(String workSerial) {
		this.workSerial = workSerial;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getPassRate() {
		return passRate;
	}
	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}
	public String getTotalLandarea() {
		return totalLandarea;
	}
	public void setTotalLandarea(String totalLandarea) {
		this.totalLandarea = totalLandarea;
	}
}
