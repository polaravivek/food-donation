package com.vivekcorp.fooddonation.models;

public class UserInfoModel {

    String email, name, phoneNo ;

    public UserInfoModel() {
    }

    public UserInfoModel(String email, String name, String phoneNo) {
        this.email = email;
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
