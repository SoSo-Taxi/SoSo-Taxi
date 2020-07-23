/**
 * @Author 范承祥
 * @CreateTime 2020/7/12
 * @UpdateTime 2020/7/20
 */
package com.sosotaxi.model;

/**
 * 用户类
 */
public class User {
    /** 用户id */
    private transient long userId;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 角色 */
    private String role;

    /** 记住我 */
    private boolean rememberMe;

    /** 令牌 */
    private String token;

    /** 电话号码 */
    private transient String phoneNumber;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private Short birthYear;

    /** 头像路径 */
    private String avatarPath;

    /** 真实姓名 */
    private String realName;

    /** 身份证号 */
    private String idCardNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        phoneNumber=userName;
        // 去除加号
        if(userName.startsWith("+")){
            userName=userName.substring(1);
        }
        // 去除空格
        int index=userName.indexOf(" ");
        if(index!=-1){
            String code=userName.substring(0,index);
            String phone=userName.substring(index+1);
            userName=code+phone;
        }
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Short getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Short birthYear) {
        this.birthYear = birthYear;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
}
