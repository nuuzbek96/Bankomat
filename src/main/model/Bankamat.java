package main.model;

import main.enums.Status;

public class Bankamat extends Base{
    String name;
    TransAction transAction;
    double percent;
    Status isActive;
    double wallet;
    public Bankamat(String name, double percent, Status isActive) {
        this.name = name;
        this.percent = percent;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransAction getTransAction() {
        return transAction;
    }

    public void setTransAction(TransAction transAction) {
        this.transAction = transAction;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Status getIsActive() {
        return isActive;
    }

    public void setIsActive(Status isActive) {
        this.isActive = isActive;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
}
