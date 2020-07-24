/**
 * @Author 屠天宇
 * @CreateTime 2020/7/24
 * @UpdateTime 2020/7/24
 */

package com.sosotaxi.service.net;

import android.util.Pair;

import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.google.gson.Gson;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.Passenger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.Response;


public class PersonalDataNetService extends BaseNetService{

    private static String[] mChoices = {"互联网-软件","通信-硬件","房地产-建筑","文化传媒"
            , "金融类","消费品","汽车-机械","教育-翻译","贸易-物流"
            ,"生物-医疗","能源-化工","政府机构","服务业","其他行业"
    };
    //通过用户名查询信息
    public static String getPersonalData(String username, String content) throws IOException, JSONException {
        if(username.isEmpty()){
            return null;
        }

        // GET请求
        Pair<Response,String> result = get(Constant.GET_PERSONAL_DATA_BY_USERNAME_URL+username);
        Response response=result.first;
        String data=result.second;
        // 结果处理
        if(response.code()==200){
            JSONObject jsonObject=new JSONObject(data);
            int code=jsonObject.getInt("code");
            if (code == 200){
                JSONObject passengerJson = jsonObject.getJSONObject("data");
                if (content == "industry"){
                    try {
                        int index = passengerJson.getInt(content);
                        return mChoices[index];
                    }catch (Exception e){
                        return null;
                    }
                }
                if (content == "birthYear"){
                    try {
                        short birth = (short) passengerJson.getInt(content);
                        return String.valueOf(birth);
                    }catch (Exception e){
                        return String.valueOf(-1);
                    }
                }
                return passengerJson.getString(content);
            }else {
                System.out.println(code);
            }
        }else {
            System.out.println(response.code());
        }
        return null;
    }

    public static boolean editPersonalData(Passenger passenger) throws IOException, JSONException {
        if (passenger == null){
            return false;
        }
        long userId = Long.parseLong(getPersonalData(passenger.getUsername(),"userId"));
        passenger.setUserId(userId);
        Gson gson = new Gson();
        String json = gson.toJson(passenger);

        System.out.println(json);
        // PUT请求
        Pair<Response,String> result = put(Constant.UPDATE_PERSONAL_DATA_BY_USERNAME_URL,json);
        Response response=result.first;
        String data=result.second;

        // 结果处理
        if(response.code()==200){
            JSONObject jsonObject=new JSONObject(data);
            int code=jsonObject.getInt("code");
            return code==200?true:false;
        }
        return false;
    }
}
