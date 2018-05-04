package io.jchat.android.entity;

import java.io.Serializable;
import java.util.List;

public class TractorDetails{

	
	private String totalArea;
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<TractorDetailslist> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<TractorDetailslist> detailsList) {
		this.detailsList = detailsList;
	}
	private String goodArea;
	private String avgPassRate;
	private String result;
	private List<TractorDetailslist> detailsList;
}
