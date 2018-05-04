package io.jchat.android.entity;

public class CoopFarmList {
	
	private String names;
	private int type ;
	private String todayArea;
	public String getTodayArea() {
		return todayArea;
	}
	public void setTodayArea(String todayArea) {
		this.todayArea = todayArea;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	private String nums;
	private String marks;

}
