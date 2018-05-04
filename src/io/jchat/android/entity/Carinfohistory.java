package io.jchat.android.entity;

import java.util.List;

import org.json.JSONArray;

public class Carinfohistory {

	private String goodArea;
	private String result;
	private String totalArea;
	private String avgDepth;
	
	public String getAvgDepth() {
		return avgDepth;
	}
	public void setAvgDepth(String avgDepth) {
		this.avgDepth = avgDepth;
	}
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getGoodArea() {
		return goodArea;
	}
	public void setGoodArea(String goodArea) {
		this.goodArea = goodArea;
	}
	public String getAvgPassRate() {
		return avgPassRate;
	}
	public void setAvgPassRate(String avgPassRate) {
		this.avgPassRate = avgPassRate;
	}
	
	
	private String avgPassRate;
	private List<Carinfohistortylist> historyList;
	public List<Carinfohistortylist> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<Carinfohistortylist> carhis_list2) {
		this.historyList = carhis_list2;
	}
	
	
}
