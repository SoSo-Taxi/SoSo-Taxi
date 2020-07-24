/**
 * @Author 屠天宇
 * @CreateTime 2020/7/24
 * @UpdateTime 2020/7/24
 */

package com.sosotaxi.service.net;

import com.sosotaxi.model.Passenger;

import org.json.JSONException;

import java.io.IOException;

public class EditPersonalDataTask implements Runnable {
    private Passenger passenger;

    public EditPersonalDataTask(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void run() {
        try {
            if (PersonalDataNetService.editPersonalData(passenger)){
                System.out.println("成功");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("fail");
    }
}
