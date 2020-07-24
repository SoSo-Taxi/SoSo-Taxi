/**
 * @Author 屠天宇
 * @CreateTime 2020/7/23
 * @UpdateTime 2020/7/23
 */

package com.sosotaxi.common;

public class TelephoneEncryption {
    public static String telephoneEncryption(String phoneNum){
        //中间四位用*代替
        if (phoneNum.length() == 11){
            phoneNum = phoneNum.substring(0,3) + "****" + phoneNum.substring(7);
        }
        return phoneNum;
    }
}
