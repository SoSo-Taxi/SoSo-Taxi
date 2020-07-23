/**
 * @Author 屠天宇
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */

package com.sosotaxi.ui.userInformation.setting.emergencyContact;

public class PhoneInfo {
    private String name;
    private String number;
    private String sortKey;
    private int id;

    public PhoneInfo(String name, String number, String sortKey, int id) {
        this.name = name;
        this.number = number;
        this.sortKey = sortKey;
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}