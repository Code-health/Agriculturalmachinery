package io.jchat.android.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.f.c;

import android.annotation.SuppressLint;
import android.util.Log;
import io.jchat.android.entity.AddressNameItme;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.entity.Carinfohistory;
import io.jchat.android.entity.Citys;
import io.jchat.android.entity.Contacts;
import io.jchat.android.entity.Coop;
import io.jchat.android.entity.Cooperatives;
import io.jchat.android.entity.TractorDetails;
import io.jchat.android.entity.TractorDetailslist;
import io.jchat.android.entity.TractorList;
import io.jchat.android.entity.TractorTodayDetail;
import io.jchat.android.entity.Updatecoop;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.entity.UserInfo;
import io.jchat.android.http.AsyncTaskBase.HttpObjectResult;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;

public class SoapService implements ISoapService {
	private AsyncTaskBase asynTaskBase = new AsyncTaskBase();
	private SoapRes soapRes = new SoapRes();
	String a = "1";

	@Override
	public void login(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "username", "password" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.LOGIN;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.LOGIN);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					System.out.println(obj.toString() + "登陆");
					JSONObject user_obj = new JSONObject(obj.toString());
					UserInfo user = new UserInfo();

					user.setId(user_obj.get("id").toString());
					if (user_obj.get("id").toString().equals("0")) {// 登陆失败
						a = "0";
					}

					user.setIsNewRecord(user_obj.get("isNewRecord").toString());
					// user.setRemarks(user_obj.get("remarks").toString());
					// user.setCreateDate(user_obj.get("createDate").toString());
					// user.setUpdateDate(user_obj.get("updateDate").toString());
					user.setLoginName(user_obj.get("loginName").toString());
					user.setName(user_obj.get("name").toString());
					// user.setEmail(user_obj.get("email").toString());
					// user.setPhone(user_obj.get("phone").toString());
					// user.setMobile(user_obj.get("mobile").toString());
					// user.setLoginIp(user_obj.get("loginIp").toString());
					// user.setLoginDate(user_obj.get("loginDate").toString());
					user.setLoginFlag(user_obj.get("loginFlag").toString());
					// user.setOldLoginIp(user_obj.get("oldLoginIp").toString());
					user.setuserType("-2");
					try {
						user.setuserType(user_obj.get("userType").toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
					user.setRoleNames(user_obj.get("roleNames").toString());
					try {
						user.setPhoto(user_obj.get("photo").toString());
					} catch (Exception e) {
						// TODO: handle exception
						user.setPhoto("0");
					}

					user.setAdmin(user_obj.get("admin").toString());
					user.setCardid(user_obj.get("no").toString());
					JSONObject us = new JSONObject(user_obj.get("office").toString());
					user.setOfficeid(us.get("id").toString());
					user.setOfficename(us.get("name").toString());
					soapRes.setObj(user);
					soapRes.setDlpand("1");
					soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setDlpand(a);
					a = "1";
					soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setDlpand("1");
				soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getLivingALl(Object[] property_va, final boolean isPage) {
		// TODO Auto-generated method stub
		String[] property_nm = { "pagesize", "pageindex", "UserId" };
		String url = "http://123.56.88.189:8027/guzhang/GetLivingALl/10/1/196";
		// String url="http://123.56.88.189:8027/guzhang/GetLivingALl";
		// String url=SOAP_UTILS.USERURL+SOAP_UTILS.METHOD.GETLIVINGALL;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIVINGALL);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray nowliving_array = new JSONArray(obj.toString());
					List<Contacts> nowliving_list = new ArrayList<Contacts>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array.get(i);
						Contacts nowliving = new Contacts();
						nowliving.setAnswerCount(json_nowliving.get("AnswerCount").toString());
						nowliving.setAttention(json_nowliving.get("Attention").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						// nowliving.setCcEndTime(json_nowliving.get("CcEndTime").toString());
						// nowliving.setCcStartTime(json_nowliving.get("CcStartTime").toString());
						nowliving.setCclive(json_nowliving.get("Cclive").toString());
						nowliving.setCcroomid(json_nowliving.get("Ccroomid").toString());
						nowliving.setCourseType(json_nowliving.get("CourseType").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						nowliving.setDealAdvise(json_nowliving.get("DealAdvise").toString());
						nowliving.setDealControl(json_nowliving.get("DealControl").toString());
						nowliving.setDealOperate(json_nowliving.get("DealOperate").toString());
						nowliving.setDescribeCc(json_nowliving.get("DescribeCc").toString());
						nowliving.setHotlive(json_nowliving.get("Hotlive").toString());
						nowliving.setId(json_nowliving.get("Id").toString());
						nowliving.setLaud(json_nowliving.get("Laud").toString());
						nowliving.setLiveContent(json_nowliving.get("LiveContent").toString());
						nowliving.setLiveCount(json_nowliving.get("LiveCount").toString());
						nowliving.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						nowliving.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						nowliving.setLivings(json_nowliving.get("Livings").toString());
						nowliving.setNameCc(json_nowliving.get("NameCc").toString());
						nowliving.setToplive(json_nowliving.get("Toplive").toString());
						nowliving.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						nowliving.setUserResume(json_nowliving.get("UserResume").toString());
						nowliving_list.add(nowliving);
					}

					soapRes.setObj(nowliving_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGALL);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGALL);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGALL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void workInfoList(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "userId" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.WORKINFOLIST;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.WORKINFOLIST);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@SuppressLint("NewApi")
			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {

					JSONObject user_obj = new JSONObject(obj.toString());
					System.out.println(obj.toString());
					Cooperatives user = new Cooperatives();

					user.setTotalArea(user_obj.get("totalArea").toString());
					user.setGoodArea(user_obj.get("goodArea").toString());
					user.setAvgDeep(user_obj.get("avgDeep").toString());
					user.setAvgPassRate(user_obj.get("avgPassRate").toString());
					// user.setYesterdayArea(user_obj.get("yesterdayArea").toString());

					JSONArray area_array = user_obj.getJSONArray("areaInfoList");
					int asd1 = area_array.length();
					List<Citys> list = new ArrayList<Citys>();
					for (int i = 0; i < area_array.length(); i++) {
						Citys ct = new Citys();
						JSONObject area_obj = (JSONObject) area_array.get(i);
						ct.setAreaName(area_obj.get("areaName").toString());
						// user.setArealistareaName();

						JSONArray sum_array = area_obj.getJSONArray("sumList");
						int asd = sum_array.length();
						List<Coop> lists = new ArrayList<Coop>();
						for (int j = 0; j < sum_array.length(); j++) {
							JSONObject sum_obj = (JSONObject) sum_array.get(j);
							Coop co = new Coop();
							co.setId(sum_obj.get("id").toString());
							co.setIsNewRecord(sum_obj.get("isNewRecord").toString());
							co.setCooperativeId(sum_obj.get("cooperativeId").toString());
							co.setCooperativeName(sum_obj.get("cooperativeName").toString());
							co.setDepth(sum_obj.get("depth").toString());
							co.setTotalArea(sum_obj.get("totalArea").toString());
							co.setGoodArea(sum_obj.get("goodArea").toString());
							co.setTotalDistance(sum_obj.get("totalDistance").toString());
							// user.setSumListdistanceGoodRate(sum_obj.get("distanceGoodRate").toString());
							// user.setSumListyesterdayArea(sum_obj.get("yesterdayArea").toString());
							lists.add(co);
						}
						ct.setList(lists);
						list.add(ct);
					}
					user.setList(list);
					soapRes.setObj(user);
					// soapRes.setObj(user_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.WORKINFOLIST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.WORKINFOLIST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.WORKINFOLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void carInfoList(Object[] property_va, final boolean isPage) {
		// TODO Auto-generated method stub

		String[] property_nm = { "cooperativeId", "pageNo", "pageSize" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.CARINFOLIST;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CARINFOLIST);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					System.out.println("carInfoList::::" + obj.toString());
					JSONObject user_obj = new JSONObject(obj.toString());
					List<TractorList> tractor_list = new ArrayList<TractorList>();
					TractorList user = new TractorList();

					user.setPageNo(user_obj.get("pageNo").toString());
					user.setPageSize(user_obj.get("pageSize").toString());
					user.setCount(user_obj.get("count").toString());
					if (!user_obj.get("count").toString().equals("0")) {

						JSONArray area_array = user_obj.getJSONArray("list");

						for (int i = 0; i < area_array.length(); i++) {
							JSONObject area_obj = (JSONObject) area_array.get(i);
							user.setListisNewRecord(area_obj.get("isNewRecord").toString());
							user.setListcarId(area_obj.get("carId").toString());
							user.setListdepth(area_obj.get("depth").toString());
							user.setListpassRate(area_obj.get("passRate").toString());
							user.setListworkLandarea(area_obj.get("workLandarea").toString());
							user.setListqualifiedLandarea(area_obj.get("qualifiedLandarea").toString());
							user.setListyesterdayArea(area_obj.get("yesterdayArea").toString());

						}

					}
					user.setMaxResults(user_obj.get("maxResults").toString());
					user.setFirstResult(user_obj.get("firstResult").toString());
					user.setHtml(user_obj.get("html").toString());
					user.setCount(user_obj.get("count").toString());
					tractor_list.add(user);
					soapRes.setObj(tractor_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.CARINFOLIST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.CARINFOLIST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.CARINFOLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void carInfoDetails(Object[] property_va) {
		// TODO Auto-generated method stub

		String[] property_nm = { "workDate", "carId" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.CARINFODETAILS;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CARINFODETAILS);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject user_obj = new JSONObject(obj.toString());
					String SS = obj.toString();
					if (user_obj.get("result").equals("ok")) {
						List<TractorDetails> tractorinfo_list = new ArrayList<TractorDetails>();
						TractorDetails user = new TractorDetails();
						JSONArray area_array = user_obj.getJSONArray("detailsList");

						List<TractorDetailslist> tractorinfoDetails_list = new ArrayList<TractorDetailslist>();
						for (int i = 0; i < area_array.length(); i++) {
							TractorDetailslist tdl = new TractorDetailslist();
							JSONObject area = (JSONObject) area_array.get(i);
							tdl.setBeginTime(area.getString("beginTime"));
							tdl.setEndTime(area.getString("endTime"));
							tdl.setDepth(area.getString("depth"));
							tdl.setWorkSerial(area.getString("workSerial")); 
							// tdl.setTotalLandarea(area.getString("totalLandarea"));
							tdl.setWorkDistanceArea(area.getString("workDistanceArea"));
							tdl.setPassRate(area.getString("passRate"));
							tdl.setPassRateA(area.getString("passRateA"));
							tdl.setPassRateB(area.getString("passRateB"));
							tdl.setDistance(area.getString("distance"));
							tdl.setOverlapRate(area.getString("overlapRate"));
							tdl.setWorkLandarea(area.getString("workLandarea"));
							tdl.setLandname(area.getString("landname"));
							tractorinfoDetails_list.add(tdl);
						}
						user.setDetailsList(tractorinfoDetails_list);
						tractorinfo_list.add(user);
						soapRes.setObj(tractorinfo_list);
						soapRes.setCode(SOAP_UTILS.METHOD.CARINFODETAILS);
						EventCache.commandActivity.post(soapRes);
					} else {
						soapRes.setObj(null);
						soapRes.setCode(SOAP_UTILS.METHOD.CARINFODETAILS);
						EventCache.commandActivity.post(soapRes);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.CARINFODETAILS);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.CARINFODETAILS);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void carInfoTodayDetails(Object[] property_va) {
		// TODO Auto-generated method stub

		String[] property_nm = { "carId" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.CARINFOTODAYDETAILS;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					System.out.println("carInfoTodayDetails;;;;;");
					JSONArray area_array = new JSONArray(obj.toString());
					System.out.println("carInfoTodayDetails;;;;;" + obj.toString());
					List<TractorTodayDetail> tractorinfodetail_list = new ArrayList<TractorTodayDetail>();

					for (int i = 0; i < area_array.length(); i++) {
						JSONObject area_obj = (JSONObject) area_array.get(i);
						TractorTodayDetail user = new TractorTodayDetail();
						user.setIsNewRecord(area_obj.get("isNewRecord").toString());
						user.setCarId(area_obj.get("carId").toString());
						user.setWorkSerial(area_obj.get("workSerial").toString());
						user.setDepth(area_obj.get("depth").toString());
						user.setPassRate(area_obj.get("passRate").toString());
						user.setBeginTime(area_obj.get("beginTime").toString());
						user.setEndTime(area_obj.get("endTime").toString());
						user.setTotalDistance(area_obj.get("totalDistance").toString());
						user.setTotalLandarea(area_obj.get("totalLandarea").toString());

						tractorinfodetail_list.add(user);
					}

					soapRes.setObj(tractorinfodetail_list);
					soapRes.setCode(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void carInfoHistory(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "carId", "beginTime", "endTime", "type" };
		String url = "http://123.57.72.71/api/app/v2/" + SOAP_UTILS.METHOD.CARINFOHISTORY;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CARINFOHISTORY);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject carhis = new JSONObject(obj.toString());
					System.out.println(obj.toString() + "似的士大夫大师傅地方");
					if (carhis.getString("result").equals("ok")) {
						List<Carinfohistory> carhis_list = new ArrayList<Carinfohistory>();
						Carinfohistory carfo = new Carinfohistory();
						carfo.setGoodArea(carhis.getString("goodArea"));
						carfo.setAvgPassRate(carhis.getString("avgPassRate"));
						carfo.setTotalArea(carhis.getString("totalArea"));
						carfo.setAvgDepth(carhis.getString("avgDepth"));

						JSONArray area_array = carhis.getJSONArray("historyList");
						List<Carinfohistortylist> carhis_list2 = new ArrayList<Carinfohistortylist>();
						for (int i = 0; i < area_array.length(); i++) {

							Carinfohistortylist carfol = new Carinfohistortylist();
							JSONObject area_obj = (JSONObject) area_array.get(i);
							try {
								carfol.setId(area_obj.getString("id"));
								carfol.setIsNewRecord(area_obj.getString("isNewRecord"));
								carfol.setCarId(area_obj.getString("carId"));
								carfol.setWorkDate(area_obj.getString("workDate"));
								carfol.setWorkLandarea(area_obj.getString("workLandarea"));
								// carfol.setType(area_obj.getString("type"));
								carfol.setDepth(area_obj.getString("depth"));
								carfol.setPassRate(area_obj.getString("passRate"));
								carfol.setQualifiedLandarea(area_obj.getString("qualifiedLandarea"));
								carfol.setDistance(area_obj.getString("distance"));
								carfol.setCooperativeId(area_obj.getString("cooperativeId"));
								carfol.setCooperativeName(area_obj.getString("cooperativeName"));
								carfol.setPlateNumber(area_obj.getString("plateNumber"));
								carfol.setOwner(area_obj.getString("owner"));
								carfol.setBrand(area_obj.getString("brand"));
								carfol.setOverlapRate(area_obj.getString("overlapRate"));
								carhis_list2.add(carfol);
							} catch (Exception e) {
								// TODO: handle exception
								carfol.setCarId(area_obj.getString("carId"));
								carfol.setWorkDate(area_obj.getString("workDate"));
								carfol.setWorkLandarea(area_obj.getString("workLandarea"));
								carhis_list2.add(carfol);
							}

						}
						carfo.setHistoryList(carhis_list2);
						carhis_list.add(carfo);
						soapRes.setObj(carhis_list);
						soapRes.setCode(SOAP_UTILS.METHOD.CARINFOHISTORY);
						EventCache.commandActivity.post(soapRes);
					} else {
						soapRes.setObj(null);
						soapRes.setCode(SOAP_UTILS.METHOD.CARINFOHISTORY);
						EventCache.commandActivity.post(soapRes);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.CARINFOHISTORY);
					EventCache.commandActivity.post(soapRes);
				}

			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.CARINFOHISTORY);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getCar(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "carId", "userId" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.GETCAR;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCAR);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub

				try {
					JSONObject car = new JSONObject(obj.toString());
					System.out.println(obj.toString());
					Updatefarmcoop up = new Updatefarmcoop();
					up.setHost_ID(car.getString("carId"));
					up.setJijuhao(car.getString("toolId"));
					up.setVIN(car.getString("flameNumber"));
					up.setCar_number(car.getString("plateNumber"));
					up.setLead_qinghao(car.getString("engineNumber"));
					up.setBrand(car.getString("brand"));
					up.setModel(car.getString("model"));
					up.setRadius(car.getString("wheelRadius"));
					up.setAgricultural(car.getString("owner"));
					up.setPhone(car.getString("ownerPhone"));
					up.setId(car.getString("id"));
					try {
						up.setLowBarSensorPosition(car.getString("lowBarSensorPosition"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setPloughgroundheight(car.getString("ploughGroundHeight"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setWorktype(car.getString("workType"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setSensor1direction(car.getString("sensor4Direction"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setSensor1direction(car.getString("sensor1Direction"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setPloughPosition(car.getString("ploughPosition"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setPloughLength(car.getString("ploughLength"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setLowBarPloughHeight(car.getString("lowBarPloughHeight"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setLowBarPloughLength(car.getString("lowBarPloughLength"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						up.setLowBarLength(car.getString("lowBarLength"));

					} catch (Exception e) {
						// TODO: handle exception
					}
					JSONObject ca = new JSONObject(car.getString("office"));
					up.setCoopid(ca.getString("id"));
					JSONArray sj = new JSONArray(car.getString("olist"));
					List<Updatecoop> list = new ArrayList<Updatecoop>();
					for (int i = 0; i < sj.length(); i++) {
						JSONObject cars = sj.getJSONObject(i);
						Updatecoop uc = new Updatecoop();
						if (cars.getString("type").equals("3")) {
							uc.setCoop(cars.getString("name"));
							uc.setCoopid(cars.getString("id"));
							list.add(uc);
						}
					}
					up.setList(list);
					soapRes.setObj(up);
					soapRes.setCode(SOAP_UTILS.METHOD.GETCAR);
					EventCache.commandActivity.post(soapRes);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETCAR);
					EventCache.commandActivity.post(soapRes);
				}

			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCAR);
				EventCache.commandActivity.post(soapRes);
			}

		});
	}

	@Override
	public void updateCar(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "carId", "toolId", "office", "plateNumber", "flameNumber", "engineNumber", "brand",
				"model", "wheelRadius", "owner", "ownerPhone", "count", "serverLience", "flag", "downDepth", "upDepth",
				"codeversion", "iccid", "sensor1", "sensor2", "sensor3", "sensor4", "sensor5", "sensor6", "sensor7",
				"sensor8", "lowBarLength", "lowBarPloughLength", "lowBarPloughHeight", "ploughPosition",
				"lowBarSensorPosition", "ploughLength", "uploadType", "uploadStart", "uploadEnd", "cooperativeName",
				"cooperativeId", "parentId", "officeId" };
		String url = SOAP_UTILS.IP + SOAP_UTILS.METHOD.updatecar;
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.updatecar);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject car = new JSONObject(obj.toString());
					System.out.println(obj.toString());

					soapRes.setObj(obj.toString());
					soapRes.setCode(SOAP_UTILS.METHOD.updatecar);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.updatecar);
				EventCache.commandActivity.post(soapRes);
			}

		});
	}

	@Override
	public void modifyPwd(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "username", "oldPassword", "newPassword" };
		String url = "http://123.57.72.71/api/app/modifyPwd";
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.modifyPwd);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject car = new JSONObject(obj.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				soapRes.setObj(obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.modifyPwd);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.modifyPwd);
				EventCache.commandActivity.post(soapRes);
			}

		});
	}

	@Override
	public void updatePhoto(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "id", "photo" };
		String url = "http://123.57.72.71/api/app/updatePhoto";
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.updatePhoto);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject car = new JSONObject(obj.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				soapRes.setObj(obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.updatePhoto);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.updatePhoto);
				EventCache.commandActivity.post(soapRes);
			}

		});
	}

	@Override
	public void regionList(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "type", "parentId" };
		String url = "http://123.57.72.71/api/app/regionList";
		// String url = "http://192.168.1.122/api/app/regionList";
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.regionList);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray car = new JSONArray(obj.toString());
					List<AddressNameItme> list = new ArrayList<AddressNameItme>();
					for (int i = 0; i < car.length(); i++) {
						JSONObject js = (JSONObject) car.get(i);
						AddressNameItme add = new AddressNameItme();
						add.setId(js.getString("id"));
						add.setName(js.getString("name"));
						add.setType(js.getString("type"));
						add.setColor(-1);
						list.add(add);
					}

					soapRes.setObj(list);
					soapRes.setCode(SOAP_UTILS.METHOD.regionList);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.regionList);
					EventCache.commandActivity.post(soapRes);
				}

			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.regionList);
				EventCache.commandActivity.post(soapRes);
			}

		});
	}

	@Override
	public void update(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "id", "landName" };
		String url = "http://123.57.72.71/api/app/workHistoryDetails/update";
		// String url =
		// "http://192.168.1.122/api/app/workHistoryDetails/update";
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.update);
		asynTaskBase.setUrl(url);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				// JSONObject car = new JSONObject(obj.toString());
				soapRes.setObj(obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.update);
				EventCache.commandActivity.post(soapRes);

			}

			@Override
			public void soapError() {
				// TODO Auto-generated method stub
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.update);
				EventCache.commandActivity.post(soapRes);
			}

		});
	}
}
