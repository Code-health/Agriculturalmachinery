package io.jchat.android.utils;

public class SOAP_UTILS {
    public class METHOD {
         public static final String LOGIN = "login";
         public static final String WORKINFOLIST = "workInfoList";
         public static final String CARINFOLIST = "carInfoList";
         public static final String CARINFODETAILS = "carInfoDetails";
         public static final String CARINFOTODAYDETAILS = "carInfoTodayDetails";
         public static final String CARINFOHISTORY="carInfoHistory";
         public static final String GETCAR="getCar";
         public static final String updatecar="updateCar";
         public static final String modifyPwd="modifyPwd";
         public static final String updatePhoto="updatePhoto";
         public static final String regionList="regionList";
         public static final String  update="update";
 		// 获取全部直播
 		public static final String GETLIVINGALL = "GetLivingALl";
        public static final String VIDEOEQUIPMENTLIST = "videoEquipmentList";
        public static final String GETAREALISTBYUSER = "getAreaListByUser";
        public static final String GETLASTWATCHDATA = "getLastWatchData";
    }

    public class ERROR {
        public static final String ERR0000 = "ERR 000";
        public static final String ERR0001 = "ERR 001";
        public static final String ERR0002 = "ERR 002";
        public static final String ERR0003 = "ERR 003";
        public static final String ERR0004 = "ERR 004";
        public static final String ERR0005 = "ERR 005 XML解析错误";
        public static final String ERR0006 = "ERR 006 本地错误";

    }

	public class ACTION {
		// 首页初始化
		public static final String LIVECIRCLE = "livecircle";
	}
	
    public static final String NAMESPACE = "MobileAlarm";
    public static final String IP_SIMPLE = "192.168.1.114";
    // public static final String IP =
    public static final String IP = "http://123.57.72.71/api/app/";// 外网
    
//	public static final String IP ="http://192.168.1.122/api/app/";//本地

    //public static final String IP = "http://123.56.109.160:8888/fm/api/app/";// 内网
//    public static final String IP = "http://123.56.109.160:8888/jfarm/app/";// 内网
//    public static final String IP = "http://192.168.1.114:8090/jfarm/app/";// 内网
//    public static final String IP = "http://123.56.169.25:8080/jfarm/app/collectData/";// 外网
    // public static final String IP = "http://200.20.30.231:8090/jfarm/a/";
    // public static final String IP = "http://123.56.109.160:8099/";
    public static final String DATAURL = IP+"collectData/";
    public static final String USERURL = IP+"user/";
    // public static final String URL = IP + "/phoneinvoke.asmx?wsdl";
    public static final String URL_WITHOUT_WSDL = IP + "/phoneinvoke.asmx";
    public static final String PIC_FILE = IP + "/manage/pic/";
    public static final String PIC_JOURNAL = IP + "/manage/magpic/";
    public static final String PIC_PUSH = IP + "/upload/";
    public static final String URL_SERVER = IP + "/apksource/version.xml";
    public static final String VIDEO_PATH = IP + "/manage/videofile/";
    public static final String ATTEND_PATH = IP + "/manage/pic/attend/";
    public static final String FOOD_PATH = IP + "/manage/pic/food/";
    public static final String AUDIO_PATH = IP + "/audio/";
    public static final String COL_PATH = IP + "/columns.xml";

    public static final String HTTP_PATHIP = "http://share.guzhang.tv";

    public static final String HTTP_NEWSINFO_PATH = HTTP_PATHIP
            + "/share/content.aspx?id=";

    // login type
    public static final int POLICE = 0;// 警察
    public static final int CITIZEN = 1;// 市民

}
