package com.sosotaxi.ui.home;

import java.lang.reflect.Member;

public class Recommand {
    private int status;
    private String message;
    private String recommendStop;
    private float distanceToMe;
    private float recommendLongitude;
    private float recommendLatitude;

    public void setStatus(int status){
      this.status=status ;
    };

    public void setMessage(String message){
        this.message=message;
    }

    public void setRecommendStop(String recommendStop){
        this.recommendStop=recommendStop;
    }

    public void setDistanceToMe(float distanceToMe){
        this.distanceToMe=distanceToMe;
    }

    public void setRecommendLatitude(float recommendLatitude){
        this.recommendLatitude=recommendLatitude;
    }

    public void setRecommendLongitude(float recommendLongitude){
        this.recommendLongitude=recommendLongitude;
    }

    public int getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public String getRecommendStop(){
        return  recommendStop;
    }

    public float getDistanceToMe(){
        return  distanceToMe;
    }

    public float getRecommendLongitude(){
        return recommendLongitude;
    }

    public  float getRecommendLatitude(){
        return recommendLatitude;
    }



}
