package com.sosotaxi.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/23
 * @UpdateTime 2020/7/23
 */
public class Passenger {
    /** 用户id */
    private long userId;

    private String username;

    private short birthYear;

    private String gender;
    /** 昵称 */
    private String nickname;

    /** 行业 */
    private Short industry;

    /** 公司 */
    private String company;

    /** 职业 */
    private String occupation;

    /** 紧急联系人 */
    private JSONObject urgentContact;

    /** 常用地址 */
    private JSONObject commonAddress;

    /** 乘车偏好 */
    private JSONArray preference;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public short getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(short birthYear) {
        this.birthYear = birthYear;
    }

    public Short getIndustry() {
        return industry;
    }

    public void setIndustry(Short industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public JSONObject getUrgentContact() {
        return urgentContact;
    }

    public void setUrgentContact(JSONObject urgentContact) {
        this.urgentContact = urgentContact;
    }

    public JSONObject getCommonAddress() {
        return commonAddress;
    }

    public void setCommonAddress(JSONObject commonAddress) {
        this.commonAddress = commonAddress;
    }

    public JSONArray getPreference() {
        return preference;
    }

    public void setPreference(JSONArray preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", industry=" + industry +
                ", company='" + company + '\'' +
                ", occupation='" + occupation + '\'' +
                ", urgentContact=" + urgentContact +
                ", commonAddress=" + commonAddress +
                ", preference=" + preference +
                '}';
    }
}
