/**
 * @Author 范承祥
 * @CreateTime 2020/7/9
 * @UpdateTime 2020/7/18
 */
package com.sosotaxi.common;

import android.Manifest;

import okhttp3.MediaType;

/**
 * 常量类
 */
public class Constant {

    /**
     * 区号请求
     */
    public static final int SELECT_AREA_CODE_REQUEST=0;

    /**
     * 发送短信权限请求
     */
    public static final int PERMISSION_SEND_SMS_REQUEST=1;

    /**
     * 拨打电话权限请求
     */
    public static final int PERMISSION_CALL_PHONE_REQUEST=2;

    /**
     * 导航权限请求
     */
    public static final int PERMISSION_NAVIGATION_REQUEST=3;

    /**
     * 区号EXTRA
     */
    public static final String EXTRA_AREA_CODE ="com.sosotaxi.ui.login.EXTRA_AREA_CODE";

    /**
     * 手机号EXTRA
     */
    public static final String EXTRA_PHONE="com.sosotaxi.ui.login.EXTRA_PHONE";

    /**
     * 密码EXTRA
     */
    public static final String EXTRA_PASSWORD="com.sosotaxi.ui.login.EXTRA_PASSWORD";

    /**
     * TOKEN EXTRA
     */
    public static final String EXTRA_TOKEN="com.sosotaxi.ui.login.EXTRA_TOKEN";

    /**
     * 是否已注册EXTRA
     */
    public static final String EXTRA_IS_REGISTERED="com.sosotaxi.ui.login.EXTRA_IS_REGISTERED";

    /**
     * 是否已认证EXTRA
     */
    public static final String EXTRA_IS_AUTHORIZED="com.sosotaxi.ui.login.EXTRA_IS_AUTHORIZED";

    /**
     * 是否注册成功EXTRA
     */
    public static final String EXTRA_IS_SUCCESSFUL="com.sosotaxi.ui.login.EXTRA_IS_SUCCESSFUL";

    /**
     * 响应消息EXTRA
     */
    public static final String EXTRA_RESPONSE_MESSAGE="com.sosotaxi.ui.login.EXTRA_RESPONSE_MESSAGE";

    /**
     * 账单总价EXTRA
     */
    public static final String EXTRA_TOTAL="com.sosotaxi.ui.driverorder.EXTRA_TOTAL";

    /**
     * 错误EXTRA
     */
    public static final String EXTRA_ERROR="com.sosotaxi.ui.login.EXTRA_ERROR";

    /**
     * 司机EXTRA
     */
    public static final String EXTRA_DRIVER="com.sosotaxi.home.EXTRA_DRIVER";

    /**
     * 订单EXTRA
     */
    public static final String EXTRA_ORDER="com.sosotaxi.home.EXTRA_ORDER";

    /**
     * 查询是否已注册URL
     */
    public static final String IS_REGISTERED_URL="http://122.51.70.242:8001/user/isRegistered";

    /**
     * 注册URL
     */
    public static final String REGISTER_URL="http://122.51.70.242:8001/user/registry";

    /**
     * 登陆URL
     */
    public static final String LOGIN_URL="http://122.51.70.242:8001/auth/login";

    /**
     * 评价乘客URL
     */
    public static final String RATE_FOR_DRIVER_URL ="http://122.51.70.242:8001/passenger/rateForDriver?orderId=";

    /**
     * WebSocket地址
     */
    public static final String WEB_SOCKET_URI="ws://122.51.70.242:8001/webSocket?accessToken=";

    /**
     * BODY类型
     */
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * SharePreferences名
     */
    public static final String SHARE_PREFERENCE_LOGIN="loginUser";

    /**
     * 用户名键名
     */
    public static final String USERNAME="com.sosotaxi.username";

    /**
     * 密码键名
     */
    public static final String PASSWORD="com.sosotaxi.password";

    /**
     * TOKEN键名
     */
    public static final String TOKEN="com.sosotaxi.token";

    /**
     * 鹰眼授权码
     */
    public static final long SERVICE_ID=222372;

    /**
     * 广播过滤器
     */
    public static final String FILTER_CONTENT="com.sosotaxi.service.callback.content";

    /**
     * 查询时间间隔
     */
    public static final long TIME_INTERVAL=5000;

    public static final int PERMISSION_READ_CONTACTS_CODE = 200;
    public static final int SETTING_RESULT_CODE = 201;

    public static final String GET_PERSONAL_DATA_BY_USERNAME_URL = "http://122.51.162.119:8001/passenger/getByName?userName=";

    public static final String UPDATE_PERSONAL_DATA_BY_USERNAME_URL = "http://122.51.162.119:8001/passenger/updatePassenger";
}
