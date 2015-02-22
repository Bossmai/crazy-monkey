package com.mead.android.crazymonkey.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {

	// 系统架构（请保留下划线，下划线隔开的为两个数据）
	private String ARCH = "armeabi-v7a_armeabi";
	// 品牌
	private String BRAND = "Huawei";
	// 品牌
	private String DEVICE = "hwu8860";
	// 指纹
	private String FINGERPRINT = "Huawei/U8860/hwu8860\\:4.0.3/HuaweiU8860/CHNC00B923SP03\\:user/ota-rel-keys,release-keys";
	// 硬件
	private String HARDWARE = "huawei";
	// 制造商
	private String MANUFACTURER = "HUAWEI";
	// 型号
	private String MODEL = "U8860";
	// 产品名
	private String PRODUCT = "U8860";
	// 系统版本
	private String RELEASE = "4.0.3";
	// 系统版本值
	private String SDK = "15";
	// gprs wifi none
	private String connect_mode = "wifi";
	// 密度 1.0
	private String density = "1.0";
	// 160
	private String densityDpi = "160";
	// no messages
	private String get;
	// 无线路由器地址
	private String getBSSID = "87-98-75-A3-D3";
	// 序列号
	private String getDeviceId = "129376401254125";
	// 基站位置（需要手动更改）
	private String getJiZhan = "";
	// 手机号码
	private String getLine1Number = "13908725345";
	// mac地址
	private String getMacAddress = "65-87-98-AE-E5";
	// 屏幕分辨率
	private String getMetrics = "320X480";
	// 国家iso代码
	private String getNetworkCountryIso = "cn";
	// 网络类型
	/*
	 * 460 06 (unknown) China
	 * 460 00 China Mobile China
	 * 460 02 China Mobile China
	 * 460 07 China Mobile China
	 * 460 03 China Telecom China
	 * 460 05 China Telecom China
	 * 460 20 China Tietong China
	 * 460 01 China Unicom China
	 */
	private String getNetworkOperator = "460000";
	// 网络类型名
	private String getNetworkOperatorName = "中国移动";
	// 网络类型
	/*
	 * NETWORK_TYPE_CDMA 4
	 * NETWORK_TYPE_EDGE 2
	 * NETWORK_TYPE_EHRPD 14
	 * NETWORK_TYPE_EVDO_0 5
	 * NETWORK_TYPE_EVDO_A 6
	 * NETWORK_TYPE_EVDO_B 12
	 * NETWORK_TYPE_GPRS 1
	 * NETWORK_TYPE_HSDPA 8
	 * NETWORK_TYPE_HSPA 10
	 * NETWORK_TYPE_HSPAP 15
	 * NETWORK_TYPE_HSUPA 9
	 * NETWORK_TYPE_IDEN 11
	 * NETWORK_TYPE_LTE 13
	 * NETWORK_TYPE_UMTS 3
	 * NETWORK_TYPE_UNKNOWN 0
	 */
	private String getNetworkType = "1";
	// 手机类型
	/*
	 * PHONE_TYPE_GSM 1
	 * PHONE_TYPE_NONE 0
	 * PHONE_TYPE_SIP 3
	 * PHONE_TYPE_CDMA 2
	 */
	private String getPhoneType = "2";

	// 固件版本
	private String getRadioVersion = "";
	// 无线路由器名
	private String getSSID = "";
	// 手机卡国家
	private String getSimCountryIso = "cn";
	// 运营商
	private String getSimOperator = "460000";
	// 运营商名字
	private String getSimOperatorName = "中国移动";
	// 手机卡序列号
	private String getSimSerialNumber = "084906003702543346";

	// 手机卡状态 SIM_OK 0 SIM_NO -1 SIM_UNKNOW -2
	private String getSimState = "0";
	// android_id 9774d56d682e549b
	private String getString = "9774d56d682e549b";
	// IMEI
	private String getSubscriberId = "351451208401216";
	// gps 位置
	private String gps = "";
	// 位置模拟类型
	private String location_mode = "0";
	// 缩放比例
	private String scaledDensity = "1.0";
	// cpu型号
	private String setCpuName = "armeabi-v7a";
	// 签名
	private String sign = "00E1BB5ED2C86A12389A1416CF963C1B";
	// 横向 160.0
	private String xdpi = "160.0";
	// 纵向 160.0
	private String ydpi = "160.0";

	public Device() {
		super();
	}
	
	public String getARCH() {
		return ARCH;
	}

	public void setARCH(String aRCH) {
		ARCH = aRCH;
	}

	public String getBRAND() {
		return BRAND;
	}

	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}

	public String getDEVICE() {
		return DEVICE;
	}

	public void setDEVICE(String dEVICE) {
		DEVICE = dEVICE;
	}

	public String getFINGERPRINT() {
		return FINGERPRINT;
	}

	public void setFINGERPRINT(String fINGERPRINT) {
		FINGERPRINT = fINGERPRINT;
	}

	public String getHARDWARE() {
		return HARDWARE;
	}

	public void setHARDWARE(String hARDWARE) {
		HARDWARE = hARDWARE;
	}

	public String getMANUFACTURER() {
		return MANUFACTURER;
	}

	public void setMANUFACTURER(String mANUFACTURER) {
		MANUFACTURER = mANUFACTURER;
	}

	public String getMODEL() {
		return MODEL;
	}

	public void setMODEL(String mODEL) {
		MODEL = mODEL;
	}

	public String getPRODUCT() {
		return PRODUCT;
	}

	public void setPRODUCT(String pRODUCT) {
		PRODUCT = pRODUCT;
	}

	public String getRELEASE() {
		return RELEASE;
	}

	public void setRELEASE(String rELEASE) {
		RELEASE = rELEASE;
	}

	public String getSDK() {
		return SDK;
	}

	public void setSDK(String sDK) {
		SDK = sDK;
	}

	public String getConnect_mode() {
		return connect_mode;
	}

	public void setConnect_mode(String connect_mode) {
		this.connect_mode = connect_mode;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getDensityDpi() {
		return densityDpi;
	}

	public void setDensityDpi(String densityDpi) {
		this.densityDpi = densityDpi;
	}

	public String getGet() {
		return get;
	}

	public void setGet(String get) {
		this.get = get;
	}

	public String getGetBSSID() {
		return getBSSID;
	}

	public void setGetBSSID(String getBSSID) {
		this.getBSSID = getBSSID;
	}

	public String getGetDeviceId() {
		return getDeviceId;
	}

	public void setGetDeviceId(String getDeviceId) {
		this.getDeviceId = getDeviceId;
	}

	public String getGetJiZhan() {
		return getJiZhan;
	}

	public void setGetJiZhan(String getJiZhan) {
		this.getJiZhan = getJiZhan;
	}

	public String getGetLine1Number() {
		return getLine1Number;
	}

	public void setGetLine1Number(String getLine1Number) {
		this.getLine1Number = getLine1Number;
	}

	public String getGetMacAddress() {
		return getMacAddress;
	}

	public void setGetMacAddress(String getMacAddress) {
		this.getMacAddress = getMacAddress;
	}

	public String getGetMetrics() {
		return getMetrics;
	}

	public void setGetMetrics(String getMetrics) {
		this.getMetrics = getMetrics;
	}

	public String getGetNetworkCountryIso() {
		return getNetworkCountryIso;
	}

	public void setGetNetworkCountryIso(String getNetworkCountryIso) {
		this.getNetworkCountryIso = getNetworkCountryIso;
	}

	public String getGetNetworkOperator() {
		return getNetworkOperator;
	}

	public void setGetNetworkOperator(String getNetworkOperator) {
		this.getNetworkOperator = getNetworkOperator;
	}

	public String getGetNetworkOperatorName() {
		return getNetworkOperatorName;
	}

	public void setGetNetworkOperatorName(String getNetworkOperatorName) {
		this.getNetworkOperatorName = getNetworkOperatorName;
	}

	public String getGetNetworkType() {
		return getNetworkType;
	}

	public void setGetNetworkType(String getNetworkType) {
		this.getNetworkType = getNetworkType;
	}

	public String getGetPhoneType() {
		return getPhoneType;
	}

	public void setGetPhoneType(String getPhoneType) {
		this.getPhoneType = getPhoneType;
	}

	public String getGetRadioVersion() {
		return getRadioVersion;
	}

	public void setGetRadioVersion(String getRadioVersion) {
		this.getRadioVersion = getRadioVersion;
	}

	public String getGetSSID() {
		return getSSID;
	}

	public void setGetSSID(String getSSID) {
		this.getSSID = getSSID;
	}

	public String getGetSimCountryIso() {
		return getSimCountryIso;
	}

	public void setGetSimCountryIso(String getSimCountryIso) {
		this.getSimCountryIso = getSimCountryIso;
	}

	public String getGetSimOperator() {
		return getSimOperator;
	}

	public void setGetSimOperator(String getSimOperator) {
		this.getSimOperator = getSimOperator;
	}

	public String getGetSimOperatorName() {
		return getSimOperatorName;
	}

	public void setGetSimOperatorName(String getSimOperatorName) {
		this.getSimOperatorName = getSimOperatorName;
	}

	public String getGetSimSerialNumber() {
		return getSimSerialNumber;
	}

	public void setGetSimSerialNumber(String getSimSerialNumber) {
		this.getSimSerialNumber = getSimSerialNumber;
	}

	public String getGetSimState() {
		return getSimState;
	}

	public void setGetSimState(String getSimState) {
		this.getSimState = getSimState;
	}

	public String getGetString() {
		return getString;
	}

	public void setGetString(String getString) {
		this.getString = getString;
	}

	public String getGetSubscriberId() {
		return getSubscriberId;
	}

	public void setGetSubscriberId(String getSubscriberId) {
		this.getSubscriberId = getSubscriberId;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getLocation_mode() {
		return location_mode;
	}

	public void setLocation_mode(String location_mode) {
		this.location_mode = location_mode;
	}

	public String getScaledDensity() {
		return scaledDensity;
	}

	public void setScaledDensity(String scaledDensity) {
		this.scaledDensity = scaledDensity;
	}

	public String getSetCpuName() {
		return setCpuName;
	}

	public void setSetCpuName(String setCpuName) {
		this.setCpuName = setCpuName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getXdpi() {
		return xdpi;
	}

	public void setXdpi(String xdpi) {
		this.xdpi = xdpi;
	}

	public String getYdpi() {
		return ydpi;
	}

	public void setYdpi(String ydpi) {
		this.ydpi = ydpi;
	}

}
