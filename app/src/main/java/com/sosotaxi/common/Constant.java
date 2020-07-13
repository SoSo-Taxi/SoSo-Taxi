/**
 * @Author 范承祥
 * @CreateTime 2020/7/9
 * @UpdateTime 2020/7/11
 */
package com.sosotaxi.common;

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
     * 区号EXTRA
     */
    public static final String EXTRA_AREA_CODE ="com.sosotaxi.ui.login.EXTRA_AREA_CODE";

    /**
     * 手机号EXTRA
     */
    public static final String EXTRA_PHONE="com.sosotaxi.ui.login.EXTRA_PHONE";

    public static final String EXTRA_PASSWORD="com.sosotaxi.ui.login.EXTRA_PASSWORD";

    public static final String EXTRA_TOKEN="com.sosotaxi.ui.login.EXTRA_TOKEN";

    public static final String EXTRA_IS_REGISTERED="com.sosotaxi.ui.login.EXTRA_IS_REGISTERED";

    public static final String EXTRA_IS_AUTHORIZED="com.sosotaxi.ui.login.EXTRA_IS_AUTHORIZED";

    public static final String EXTRA_IS_SUCCESSFUL="com.sosotaxi.ui.login.EXTRA_IS_SUCCESSFUL";

    public static final String EXTRA_RESPONSE_MESSAGE="com.sosotaxi.ui.login.EXTRA_RESPONSE_MESSAGE";

    public static final String IS_REGISTERED_URL="http://122.51.162.119:8001/user/isRegistered";

    public static final String EXTRA_ERROR="com.sosotaxi.ui.login.EXTRA_ERROR";

    public static final String REGISTER_URL="http://122.51.162.119:8001/user/registry";

    public static final String LOGIN_URL="http://122.51.162.119:8001/auth/login";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static final String SHARE_PREFERENCE_LOGIN="loginUser";

    public static final String USERNAME="com.sosotaxi.username";

    public static final String PASSWORD="com.sosotaxi.password";
}
