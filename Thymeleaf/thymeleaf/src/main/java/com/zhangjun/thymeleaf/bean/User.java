package com.zhangjun.thymeleaf.bean;

import java.util.Date;

public class User {

    private String code; //编号
    private String uname;
    private String phone;
    private String email;
    private Date registerTime;
    private int  role;


    public User() {
    }

    public User(String code, String uname, String phone, String email, Date registerTime,int role) {
        this.code = code;
        this.uname = uname;
        this.phone = phone;
        this.email = email;
        this.registerTime = registerTime;
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public String getUname() {
        return uname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
