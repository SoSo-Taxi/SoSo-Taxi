/**
 * @Author 范承祥
 * @CreateTime 2020/7/17
 * @UpdateTime 2020/7/18
 */
package com.sosotaxi.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.sosotaxi.R;

/**
 * 联系帮手类
 */
public class ContactHelper {

    /**
     * 发送短信
     * @param context 上下文
     * @param phone 手机号
     * @param content 短信内容
     */
    public static void sendMessage(Context context, String phone,String content){
        if(phone.isEmpty()&&content.isEmpty()){
            // 提示手机号或短信内容为空
            Toast.makeText(context, R.string.error_phone_content_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        // 添加+
        if(!phone.startsWith("+")){
            phone="+"+phone;
        }

        // 发送短信
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phone,null,content,null,null);
        Toast.makeText(context, R.string.hint_send_successful, Toast.LENGTH_SHORT).show();
    }

    /**
     * 拨打电话
     * @param context 上下文
     * @param phone 手机号
     */
    public static void makeCall(Context context, String phone){
        if(phone.isEmpty()){
            // 提示手机号为空
            Toast.makeText(context, R.string.error_phone_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        // 添加+
        if(!phone.startsWith("+")){
            phone="+"+phone;
        }

        // 跳转至通话界面
        Intent intent=new Intent(Intent.ACTION_CALL);
        Uri uri= Uri.parse("tel:"+phone);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
