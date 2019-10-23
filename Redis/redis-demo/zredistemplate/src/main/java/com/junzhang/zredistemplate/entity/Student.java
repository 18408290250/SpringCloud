package com.junzhang.zredistemplate.entity;

import java.io.Serializable;

//implements Serializable
public class Student{

    private String name;
    private String sno;

    public Student() {
    }
    public Student(String name, String sno) {
        this.name = name;
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public String getSno() {
        return sno;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSno(String sno) {
        this.sno = sno;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sno='" + sno + '\'' +
                '}';
    }


}
