package main.model;

import main.enums.Status;

public class User extends Base{
    String fullName;
    String password;
    String phoneNumber;
    Status status;

    public User(String fullName, String password, String phoneNumber, Status status) {
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
