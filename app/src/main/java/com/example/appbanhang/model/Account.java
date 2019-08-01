package com.example.appbanhang.model;

import java.io.Serializable;

public class Account implements Serializable {

    private String userName;
    private int id;
    private String email;
    private String sdt;
    private String diaChi;
    private String passWord;

    public Account(String userName, int id, String email, String sdt, String diaChi, String passWord) {
        this.userName = userName;
        this.id = id;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.passWord = passWord;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Account() {
    }

    public Account(String userName, String email, String sdt, String passWord) {
        this.userName = userName;
        this.email = email;
        this.sdt = sdt;
        this.passWord = passWord;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
