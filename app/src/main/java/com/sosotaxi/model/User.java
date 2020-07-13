/**
 * @Author 范承祥
 * @CreateTime 2020/7/12
 * @UpdateTime 2020/7/12
 */
package com.sosotaxi.model;

/**
 * 用户类
 */
public class User {
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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
