package com.vivekcorp.driverside.model;

import android.graphics.Bitmap;

public class DonatorInfo {
    String donatorName;
    String phoneNum;
    String address;
    String latitude;
    String longitude;
    Bitmap bitmap;

    public DonatorInfo() {
    }

    public DonatorInfo(String donatorName, String phoneNum, String address, Bitmap bitmap,String latitude,String longitude) {
        this.donatorName = donatorName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.bitmap = bitmap;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
