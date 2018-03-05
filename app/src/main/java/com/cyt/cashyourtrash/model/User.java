package com.cyt.cashyourtrash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("mobile")
    @Expose
    private Double mobile;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("walletMoney")
    @Expose
    private Integer walletMoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getMobile() {
        return mobile;
    }

    public void setMobile(Double mobile) {
        this.mobile = mobile;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getWalletMoney() {
        return walletMoney;
    }

    public void setWalletMoney(Integer walletMoney) {
        this.walletMoney = walletMoney;
    }

}