package com.kyle.healthcare.database.user_info;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {
    int id;
    boolean gender;// "true" indicate as male, otherwise it's for female
    String name;
    String phoneNumber;
    String password;
    String carNumber;
    String idNumber;

    public User(boolean gender, String name, String phoneNumber, String password, String carNumber, String idNumber) {
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.carNumber = carNumber;
        this.idNumber = idNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
