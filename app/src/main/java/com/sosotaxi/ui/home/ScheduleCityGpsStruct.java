package com.sosotaxi.ui.home;



public class ScheduleCityGpsStruct {


    private String sortLetters;
    private String cityName;
    private String longitude;
    private String latitude;



    public void setStrLongitude(String longitude){
        this.longitude=longitude;
    }
    public String getStrLongitude(){
        return longitude;
    }
    public void setStrLatitude(String latitude){
        this.latitude=latitude;
    }
    public String getStrLatitude(){
        return latitude;
    }



    public void setStrCityName(String strCityName){
        this.cityName=strCityName;
    }
    public String getStrCityName(){
        return cityName;
    }

    public void setSortLetters(String sortLetters){
        this.sortLetters= sortLetters;
    }
    public String getSortLetters(){
        return sortLetters;
    }






}
