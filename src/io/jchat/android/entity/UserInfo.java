package io.jchat.android.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
	private String id;
	private String isNewRecord;
	private String remarks;
	private String createDate;
	private String updateDate;
	private String loginName;
	private String no;
	private String name;
	private String email;
	private String phone;
	private String mobile;
	private String loginIp;
	private String loginDate;
	private String loginFlag;
	private String oldLoginIp;
	private String oldLoginDate;
	private String roleNames;
	private String admin;
    private String loginPassWd;
    private String cardid;
    private String userType;//用户权限
    private String officeid;
    private String photo;
    public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getOfficeid() {
		return officeid;
	}
	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	public String getOfficename() {
		return officename;
	}
	public void setOfficename(String officename) {
		this.officename = officename;
	}
	private String officename;
    
	public String getuserType() {
		return userType;
	}
	public void setuserType(String userType) {
		this.userType = userType;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsNewRecord() {
		return isNewRecord;
	}
	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	public String getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getLoginPassWd() {
		return loginPassWd;
	}
	public void setLoginPassWd(String loginPassWd) {
		this.loginPassWd = loginPassWd;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getOldLoginIp() {
		return oldLoginIp;
	}
	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}
	public String getOldLoginDate() {
		return oldLoginDate;
	}
	public void setOldLoginDate(String oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}
	
	
}
