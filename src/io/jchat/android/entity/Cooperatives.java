package io.jchat.android.entity;

import java.io.Serializable;
import java.util.List;

public class Cooperatives implements Serializable {
	
	private String totalArea;
	private String goodArea;
	private String avgDeep;
	private String avgPassRate;
	private String yesterdayArea;

	private String arealistareaName;
	private List<Citys> list;
	private List<Coop> listc;
	private String count;
	private String totalDistance;
	private String workType;
	
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<Coop> getListc() {
		return listc;
	}
	public void setListc(List<Coop> listc) {
		this.listc = listc;
	}
	public List<Citys> getList() {
		return list;
	}
	public void setList(List<Citys> list) {
		this.list = list;
	}
	private String sumListid;
	private String sumListcount;
	private String sumListisNewRecord;
	private String sumListcooperativeId;
	private String sumListcooperativeName;
	private String sumListtotalArea;
	private String sumListgoodArea;
	private String sumListdepth;
	private String sumListtotalDistance;
	private String sumListdistanceGoodRate;
	private String sumListyesterdayArea;
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
	public String getAvgDeep() {
		return avgDeep;
	}
	public void setAvgDeep(String avgDeep) {
		this.avgDeep = avgDeep;
	}
	public String getAvgPassRate() {
		return avgPassRate;
	}
	public void setAvgPassRate(String avgPassRate) {
		this.avgPassRate = avgPassRate;
	}
	public String getYesterdayArea() {
		return yesterdayArea;
	}
	public void setYesterdayArea(String yesterdayArea) {
		this.yesterdayArea = yesterdayArea;
	}
	public String getArealistareaName() {
		return arealistareaName;
	}
	public void setArealistareaName(String arealistareaName) {
		this.arealistareaName = arealistareaName;
	}
	public String getSumListid() {
		return sumListid;
	}
	public void setSumListid(String sumListid) {
		this.sumListid = sumListid;
	}
	public String getSumListisNewRecord() {
		return sumListisNewRecord;
	}
	public void setSumListisNewRecord(String sumListisNewRecord) {
		this.sumListisNewRecord = sumListisNewRecord;
	}
	public String getSumListcooperativeId() {
		return sumListcooperativeId;
	}
	public void setSumListcooperativeId(String sumListcooperativeId) {
		this.sumListcooperativeId = sumListcooperativeId;
	}
	public String getSumListcooperativeName() {
		return sumListcooperativeName;
	}
	public void setSumListcooperativeName(String sumListcooperativeName) {
		this.sumListcooperativeName = sumListcooperativeName;
	}
	public String getSumListtotalArea() {
		return sumListtotalArea;
	}
	public void setSumListtotalArea(String sumListtotalArea) {
		this.sumListtotalArea = sumListtotalArea;
	}
	public String getSumListgoodArea() {
		return sumListgoodArea;
	}
	public void setSumListgoodArea(String sumListgoodArea) {
		this.sumListgoodArea = sumListgoodArea;
	}
	public String getSumListdepth() {
		return sumListdepth;
	}
	public void setSumListdepth(String sumListdepth) {
		this.sumListdepth = sumListdepth;
	}
	public String getSumListtotalDistance() {
		return sumListtotalDistance;
	}
	public void setSumListtotalDistance(String sumListtotalDistance) {
		this.sumListtotalDistance = sumListtotalDistance;
	}
	public String getSumListdistanceGoodRate() {
		return sumListdistanceGoodRate;
	}
	public void setSumListdistanceGoodRate(String sumListdistanceGoodRate) {
		this.sumListdistanceGoodRate = sumListdistanceGoodRate;
	}
	public String getSumListyesterdayArea() {
		return sumListyesterdayArea;
	}
	public void setSumListyesterdayArea(String sumListyesterdayArea) {
		this.sumListyesterdayArea = sumListyesterdayArea;
	}
	public String getSumListcount() {
		return sumListcount;
	}
	public void setSumListcount(String sumListcount) {
		this.sumListcount = sumListcount;
	}
	
	
}
