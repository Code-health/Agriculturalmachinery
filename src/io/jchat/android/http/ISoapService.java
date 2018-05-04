package io.jchat.android.http;

/**
 * webService请求接口
 * 
 * @author huanyu 类名称：ISoapService 创建时间:2014-11-4 下午7:08:50
 */
public interface ISoapService extends IASoapService {

	/**
	 * 用户登录--用户名|密码
	 * 
	 * @param property_va
	 */
	void login(Object[] property_va);

	/**
	 * 合作社列表
	 * 
	 * @param property_va
	 */
	void workInfoList(Object[] property_va);

	/**
	 * 单个拖拉机历史信息 全年 按天分
	 */
	void carInfoHistory(Object[] property_va);

	/**
	 * 拖拉机列表页
	 * 
	 * @param property_va
	 */
	void carInfoList(Object[] property_va, boolean isPage);

	/**
	 * 拖拉机详细信息页
	 * 
	 * @param property_va
	 */
	void carInfoDetails(Object[] property_va);

	/**
	 * 拖拉机当日详细信息页
	 * 
	 * @param property_va
	 */
	void carInfoTodayDetails(Object[] property_va);

	/**
	 * 通讯录页
	 * 
	 * @param property_va
	 */
	void getLivingALl(Object[] property_va, boolean isPage);

	// 获取农机信息
	void getCar(Object[] property_va);

	// 提交农机信息
	void updateCar(Object[] property_va);

	// 修改密码
	void modifyPwd(Object[] property_va);

	// 设置面积
	void updatePhoto(Object[] property_va);
	
	//获取地区市县村
	void regionList(Object[] property_va);
	//上传地块名
	void update(Object[] property_va);
}
