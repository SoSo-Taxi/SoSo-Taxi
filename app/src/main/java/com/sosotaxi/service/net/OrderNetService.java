package com.sosotaxi.service.net;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/25
 * @UpdateTime 2020/7/25
 */

import android.util.Pair;

import com.sosotaxi.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * 订单网络服务
 */
public class OrderNetService extends BaseNetService{
    /**
     * 评价司机
     * @param orderId 订单ID
     * @param rate 评分
     * @return 响应结果
     * @throws IOException 输入输出异常
     * @throws JSONException JSON解析异常
     */
    public static Pair<Boolean,String> rateForDriver(long orderId, double rate) throws IOException, JSONException {

        // 构造URL
        StringBuffer urlBuffer=new StringBuffer();
        urlBuffer.append(Constant.RATE_FOR_DRIVER_URL);
        urlBuffer.append(orderId);
        urlBuffer.append("&rate=");
        urlBuffer.append(rate);
        String url= urlBuffer.toString();
        Pair<Response,String> result=get(url);

        Response response=result.first;
        String data=result.second;

        // 结果处理
        if(response.code()==200){
            JSONObject jsonObject=new JSONObject(data);
            int code=jsonObject.getInt("code");
            String message=jsonObject.getString("msg");
            return code==200?new Pair<Boolean, String>(true,message):new Pair<Boolean, String>(false,message);
        }
        return new Pair<>(false,"连接失败");
    }
}
