package io.jchat.android.entity;

import java.util.List;

public class Citys {

	private String areaName;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public List<Coop> getList() {
		return list;
	}
	public void setList(List<Coop> list) {
		this.list = list;
	}
	private List<Coop> list;
}
