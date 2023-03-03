package main.model;

import main.enums.Status;

public class Cards extends Base{
    String nameOfUser;
    String password;
    String numberOfCard;
    String expiry;
    double balance;
    Status status;

    public Cards(String nameOfUser, String password, String numberOfCard, String expiry, double balance, Status status) {
        this.nameOfUser = nameOfUser;
        this.password = password;
        this.numberOfCard = numberOfCard;
        this.expiry = expiry;
        this.balance = balance;
        this.status = status;
    }

    public Cards() {
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberOfCard() {
        return numberOfCard;
    }

    public void setNumberOfCard(String numberOfCard) {
        this.numberOfCard = numberOfCard;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
