package io.jchat.android.entity;

import java.io.Serializable;

public class TractorList implements Serializable {

	private String pageNo;
	private String pageSize;
	private String count;
	private String listisNewRecord;
	private String listcarId;
	private String listdepth;
	private String listpassRate;
	private String listworkLandarea;
	private String listqualifiedLandarea;
	private String listyesterdayArea;
	private String maxResults;
	private String firstResult;
	private String html;
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getListisNewRecord() {
		return listisNewRecord;
	}
	public void setListisNewRecord(String listisNewRecord) {
		this.listisNewRecord = listisNewRecord;
	}
	public String getListcarId() {
		return listcarId;
	}
	public void setListcarId(String listcarId) {
		this.listcarId = listcarId;
	}
	public String getListdepth() {
		return listdepth;
	}
	public void setListdepth(String listdepth) {
		this.listdepth = listdepth;
	}
	public String getListpassRate() {
		return listpassRate;
	}
	public void setListpassRate(String listpassRate) {
		this.listpassRate = listpassRate;
	}
	public String getListworkLandarea() {
		return listworkLandarea;
	}
	public void setListworkLandarea(String listworkLandarea) {
		this.listworkLandarea = listworkLandarea;
	}
	public String getListqualifiedLandarea() {
		return listqualifiedLandarea;
	}
	public void setListqualifiedLandarea(String listqualifiedLandarea) {
		this.listqualifiedLandarea = listqualifiedLandarea;
	}
	public String getListyesterdayArea() {
		return listyesterdayArea;
	}
	public void setListyesterdayArea(String listyesterdayArea) {
		this.listyesterdayArea = listyesterdayArea;
	}
	public String getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(String maxResults) {
		this.maxResults = maxResults;
	}
	public String getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(String firstResult) {
		this.firstResult = firstResult;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

}
