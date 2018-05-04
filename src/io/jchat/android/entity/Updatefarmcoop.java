package io.jchat.android.entity;

import java.util.List;

import android.content.Context;
import io.jchat.android.newactivity.AddFarmMachiney;
import io.jchat.android.newactivity.CooperativeActivity;
import io.jchat.android.newactivity.NongjisuoActivity;
import io.jchat.android.newactivity.SettingWorkAreaActivity;
import io.jchat.android.newactivity.UploadPictureActivityOne;
import io.jchat.android.newactivity.UploadPictureActivitySensorOne;
import io.jchat.android.newactivity.UploadPictureActivitySensorthree;
import io.jchat.android.newactivity.UploadPictureActivitySensortwo;

public class Updatefarmcoop {

	private String Host_ID;
	private String id;//主机id
	private  static UploadPictureActivityOne activityOne;
	private static AddFarmMachiney addFarmMachiney;
	private List<Updatecoop> list;
	private String coopid;
	private String lowBarLength;
	private static CooperativeActivity coopactivity;
	private static NongjisuoActivity nongactivity;
	private static Context context;
	private static int mYear;
	private static SettingWorkAreaActivity settworkactivity;
	
	public static SettingWorkAreaActivity getSettworkactivity() {
		return settworkactivity;
	}
	public static void setSettworkactivity(SettingWorkAreaActivity settworkactivity) {
		Updatefarmcoop.settworkactivity = settworkactivity;
	}
	public static int getmYear() {
		return mYear;
	}
	public static void setmYear(int mYear) {
		Updatefarmcoop.mYear = mYear;
	}
	public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		Updatefarmcoop.context = context;
	}
	public static NongjisuoActivity getNongactivity() {
		return nongactivity;
	}
	public static void setNongactivity(NongjisuoActivity nongactivity) {
		Updatefarmcoop.nongactivity = nongactivity;
	}
	public static CooperativeActivity getCoopactivity() {
		return coopactivity;
	}
	public static void setCoopactivity(CooperativeActivity coopactivity) {
		Updatefarmcoop.coopactivity = coopactivity;
	}
	public String getLowBarLength() {
		return lowBarLength;
	}
	public void setLowBarLength(String lowBarLength) {
		this.lowBarLength = lowBarLength;
	}
	public String getLowBarPloughLength() {
		return lowBarPloughLength;
	}
	public void setLowBarPloughLength(String lowBarPloughLength) {
		this.lowBarPloughLength = lowBarPloughLength;
	}
	public String getLowBarPloughHeight() {
		return lowBarPloughHeight;
	}
	public void setLowBarPloughHeight(String lowBarPloughHeight) {
		this.lowBarPloughHeight = lowBarPloughHeight;
	}
	public String getPloughLength() {
		return ploughLength;
	}
	public void setPloughLength(String ploughLength) {
		this.ploughLength = ploughLength;
	}
	public String getLowBarSensorPosition() {
		return lowBarSensorPosition;
	}
	public void setLowBarSensorPosition(String lowBarSensorPosition) {
		this.lowBarSensorPosition = lowBarSensorPosition;
	}
	public String getPloughPosition() {
		return ploughPosition;
	}
	public void setPloughPosition(String ploughPosition) {
		this.ploughPosition = ploughPosition;
	}
    
	private String lowBarPloughLength;
	private String lowBarPloughHeight;
	private String ploughLength;
	private String lowBarSensorPosition;
	private String ploughPosition;
	private String ploughgroundheight;
	public String getPloughgroundheight() {
		return ploughgroundheight;
	}
	public void setPloughgroundheight(String ploughgroundheight) {
		this.ploughgroundheight = ploughgroundheight;
	}
	public String getSensor1direction() {
		return sensor1direction;
	}
	public void setSensor1direction(String sensor1direction) {
		this.sensor1direction = sensor1direction;
	}
	public String getSensor4direction() {
		return sensor4direction;
	}
	public void setSensor4direction(String sensor4direction) {
		this.sensor4direction = sensor4direction;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	private String sensor1direction;
	private String sensor4direction;
	private String worktype;
	public String getCoopid() {
		return coopid;
	}
	public void setCoopid(String coopid) {
		this.coopid = coopid;
	}
	public List<Updatecoop> getList() {
		return list;
	}

	public void setList(List<Updatecoop> list) {
		this.list = list;
	}
	
	public static AddFarmMachiney getAddFarmMachiney() {
		return addFarmMachiney;
	}
	public static void setAddFarmMachiney(AddFarmMachiney addFarmMachiney) {
		Updatefarmcoop.addFarmMachiney = addFarmMachiney;
	}
	public static UploadPictureActivityOne getActivityOne() {
		return activityOne;
	}
	public  static void setActivityOne(UploadPictureActivityOne activityOne) {
		Updatefarmcoop.activityOne = activityOne;
	}
	public static UploadPictureActivitySensorOne getActivitySensorOne() {
		return activitySensorOne;
	}
	public static void setActivitySensorOne(UploadPictureActivitySensorOne activitySensorOne) {
		Updatefarmcoop.activitySensorOne = activitySensorOne;
	}
	public static UploadPictureActivitySensortwo getActivitySensortwo() {
		return activitySensortwo;
	}
	public static void setActivitySensortwo(UploadPictureActivitySensortwo activitySensortwo) {
		Updatefarmcoop.activitySensortwo = activitySensortwo;
	}
	public static UploadPictureActivitySensorthree getActivitySensorthree() {
		return activitySensorthree;
	}
	public static void setActivitySensorthree(UploadPictureActivitySensorthree activitySensorthree) {
		Updatefarmcoop.activitySensorthree = activitySensorthree;
	}

	private static UploadPictureActivitySensorOne activitySensorOne;
	private static UploadPictureActivitySensortwo activitySensortwo;
	private static UploadPictureActivitySensorthree activitySensorthree;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getHost_ID() {
		return Host_ID;
	}
	public void setHost_ID(String host_ID) {
		Host_ID = host_ID;
	}
	public String getAgricultural() {
		return Agricultural;
	}
	public void setAgricultural(String agricultural) {
		Agricultural = agricultural;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getJijuhao() {
		return Jijuhao;
	}
	public void setJijuhao(String jijuhao) {
		Jijuhao = jijuhao;
	}
	public String getCar_number() {
		return car_number;
	}
	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}
	public String getVIN() {
		return VIN;
	}
	public void setVIN(String vIN) {
		VIN = vIN;
	}
	public String getLead_qinghao() {
		return Lead_qinghao;
	}
	public void setLead_qinghao(String lead_qinghao) {
		Lead_qinghao = lead_qinghao;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public String getAuthorize() {
		return authorize;
	}
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}
	public String getAnti_dismantle() {
		return anti_dismantle;
	}
	public void setAnti_dismantle(String anti_dismantle) {
		this.anti_dismantle = anti_dismantle;
	}
	public String getSoftware_Version() {
		return Software_Version;
	}
	public void setSoftware_Version(String software_Version) {
		Software_Version = software_Version;
	}
	public String getSm_card() {
		return Sm_card;
	}
	public void setSm_card(String sm_card) {
		Sm_card = sm_card;
	}
	public String getSensor1() {
		return sensor1;
	}
	public void setSensor1(String sensor1) {
		this.sensor1 = sensor1;
	}
	public String getSensor2() {
		return sensor2;
	}
	public void setSensor2(String sensor2) {
		this.sensor2 = sensor2;
	}
	public String getSensor3() {
		return sensor3;
	}
	public void setSensor3(String sensor3) {
		this.sensor3 = sensor3;
	}
	public String getSensor4() {
		return sensor4;
	}
	public void setSensor4(String sensor4) {
		this.sensor4 = sensor4;
	}
	public String getSensor5() {
		return sensor5;
	}
	public void setSensor5(String sensor5) {
		this.sensor5 = sensor5;
	}
	public String getSensor6() {
		return sensor6;
	}
	public void setSensor6(String sensor6) {
		this.sensor6 = sensor6;
	}
	public String getLink_length1() {
		return link_length1;
	}
	public void setLink_length1(String link_length1) {
		this.link_length1 = link_length1;
	}
	public String getLink_length2() {
		return link_length2;
	}
	public void setLink_length2(String link_length2) {
		this.link_length2 = link_length2;
	}
	public String getMachine_spacing() {
		return Machine_spacing;
	}
	public void setMachine_spacing(String machine_spacing) {
		Machine_spacing = machine_spacing;
	}
	public String getModifier() {
		return Modifier;
	}
	public void setModifier(String modifier) {
		Modifier = modifier;
	}
	
	
	private String Agricultural;
	private String phone;
	private String Jijuhao;
	private String car_number;
	private String VIN;
	private String Lead_qinghao;
	private String brand;
	private String model;
	private String radius;
	private String authorize;
	private String anti_dismantle;
	private String Software_Version;
	private String Sm_card;
	private String sensor1;
	private String sensor2;
	private String sensor3;
	private String sensor4;
	private String sensor5;
	private String sensor6;
	private String link_length1;
	private String link_length2;
	private String Machine_spacing;
	private String Modifier;
	
	
}
