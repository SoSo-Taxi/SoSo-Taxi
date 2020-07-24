/**
 * @Author 屠天宇
 * @CreateTime 2020/7/24
 * @UpdateTime 2020/7/24
 */

package com.sosotaxi.service.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sosotaxi.model.Passenger;

import org.json.JSONException;

import java.io.IOException;

public class GetPersonalDataTask implements Runnable {

    private String username;
    private Handler mHandler;


    public GetPersonalDataTask() {
    }

    public GetPersonalDataTask( String username, Handler handler) {
        this.username = username;
        this.mHandler = handler;
    }

    @Override
    public void run() {
        try {
            Bundle bundle = new Bundle();
            String nickname = PersonalDataNetService.getPersonalData(username,"nickname");
            String company = PersonalDataNetService.getPersonalData(username,"company");
            String industry = PersonalDataNetService.getPersonalData(username,"industry");
            String job = PersonalDataNetService.getPersonalData(username,"occupation");
            String gender = PersonalDataNetService.getPersonalData(username,"gender");
            short birthYear = Short.parseShort(PersonalDataNetService.getPersonalData(username,"birthYear"));
            if (nickname != null && nickname != "null"){
                bundle.putString("nickname",nickname);
            }else {
                bundle.putString("nickname",username);
            }
            if (company != null && company != "null"){
                bundle.putString("company",company);
            }
            if (industry != null && industry != "null"){
                bundle.putString("industry",industry);
            }
            if (job != null && job != "null"){
                bundle.putString("job",job);
            }
            if (gender != null && gender != "null"){
                bundle.putString("gender",gender);
            }
            bundle.putShort("birthYear",birthYear);
            Message message=new Message();
            message.setData(bundle);
            mHandler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
