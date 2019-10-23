package com.zhangjun.thymeleaf.bean;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private String name;
    private int id;
    private int vip;

    public int getVip() {
        return vip;
    }

    public Student() {
    }
    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public Student(String name, int id, int vip) {
        this.name = name;
        this.id = id;
        this.vip = vip;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int isVip() {
        return vip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\":"+"\""+this.id+"\""+","+
                "\"name\":"+"\""+this.name+"\""+","+
                "\"vip\":"+"\""+this.vip+"\""+
                "}";
    }
}
