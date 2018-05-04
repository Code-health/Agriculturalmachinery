package io.jchat.android.entity;

public class TractorDetailslist {
	private String isNewRecord;
	private String carId;
	private String workSerial;
	
	private String toolId;
	private String workLandarea;
	private String overlapRate;
	private String landname;
	
	public String getLandname() {
		return landname;
	}
	public void setLandname(String landname) {
		this.landname = landname;
	}
	public String getOverlapRate() {
		return overlapRate;
	}
	public void setOverlapRate(String overlapRate) {
		this.overlapRate = overlapRate;
	}
	public String getWorkLandarea() {
		return workLandarea;
	}
	public void setWorkLandarea(String workLandarea) {
		this.workLandarea = workLandarea;
	}
	public String getToolId() {
		return toolId;
	}
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	public String getWorkDistanceArea() {
		return workDistanceArea;
	}
	public void setWorkDistanceArea(String workDistanceArea) {
		this.workDistanceArea = workDistanceArea;
	}
	public String getQualifiedLandarea() {
		return qualifiedLandarea;
	}
	public void setQualifiedLandarea(String qualifiedLandarea) {
		this.qualifiedLandarea = qualifiedLandarea;
	}
	private String workDistanceArea;
	private String qualifiedLandarea;
	
	private String depth;
	private String passRate;
	private String beginTime;
	private String endTime;
	private String totalDistance;
	private String totalLandarea;
	private String distance;
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getPassRateA() {
		return passRateA;
	}
	public void setPassRateA(String passRateA) {
		this.passRateA = passRateA;
	}
	public String getPassRateB() {
		return passRateB;
	}
	public void setPassRateB(String passRateB) {
		this.passRateB = passRateB;
	}
	private String passRateA;
	private String passRateB;
	
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
