/**
 * @Author 范承祥
 * @CreateTime 2020/7/12
 * @UpdateTime 2020/7/12
 */
package com.sosotaxi.service.net;

import android.util.Pair;

import com.google.gson.Gson;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * 登陆网络服务
 */
public class LoginNetService extends BaseNetService {

    /**
     * 查询用户名是否已注册
     * @param username 用户名
     * @return 是否注册
     * @throws IOException 输入输出异常
     * @throws JSONException JSON解析异常
     */
    public static boolean isRegistered(String username) throws IOException, JSONException {

        if(username.isEmpty()){
            return false;
        }

        // 生成JSON
        User user=new User();
        user.setUserName(username);
        Gson gson=new Gson();
        String json=gson.toJson(user);

        // POST请求
        Pair<Response,String> result = post(Constant.IS_REGISTERED_URL,json);
        Response response=result.first;
        String data=result.second;

        // 结果处理
        if(response.code()==200){
            JSONObject jsonObject=new JSONObject(data);
            int code=jsonObject.getInt("code");

            return code==200?false:true;
        }
        return false;
    }

    /**
     * 注册
     * @param user 用户
     * @return 是否成功注册
     * @throws IOException 输入输出异常
     * @throws JSONException JSON解析异常
     */
    public static boolean register(User user) throws IOException, JSONException {

        if(user.getUserName().isEmpty()||user.getPassword().isEmpty()||user.getRole().isEmpty()){
            return false;
        }

        // 生成JSON
        Gson gson=new Gson();
        String json=gson.toJson(user);

        // POST请求
        Pair<Response,String> result= post(Constant.REGISTER_URL,json);

        // 结果处理
        Response response=result.first;
        String data=result.second;
        String message=response.message();

        if(response.code()==200){
            JSONObject jsonObject=new JSONObject(data);
            int code=jsonObject.getInt("code");

            return code==200?true:false;
        }
        return false;
    }

    /**
     * 登陆
     * @param user 用户
     * @return 是否成功登陆
     * @throws IOException 输入输出异常
     * @throws JSONException JSON解析异常
     */
    public static boolean login(User user) throws IOException, JSONException {
        if(user.getUserName()==null||user.getPassword()==null){
            return false;
        }

        if(user.isRememberMe()==false){
            user.setRememberMe(true);
        }

        // 生成JSON
        Gson gson=new Gson();
        String json=gson.toJson(user);

        // POST请求
        Pair<Response,String> result=post(Constant.LOGIN_URL,json);

        // 结果处理
        Response response=result.first;
        String message=response.message();

        if(response.code()==200){
            // 去除前缀
            String token=response.header("Authorization").substring(7);
            user.setToken(token);
            return true;
        }
        return false;
    }
}
