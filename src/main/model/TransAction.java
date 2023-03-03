package main.model;

import main.enums.Service;

public class TransAction extends Base{
    String numOfCard;
    String numOfSendCard;
    double sum;
    Service typeOfService;
    Long bankamatId;

    public TransAction(String numOfCard, String numOfSendCard, double sum, Service typeOfService,Long bankamatId) {
        this.numOfCard = numOfCard;
        this.numOfSendCard = numOfSendCard;
        this.sum = sum;
        this.typeOfService = typeOfService;
        this.bankamatId = bankamatId;
    }

    public TransAction(String numOfCard, double sum, Service typeOfService, Long bankamatId) {
        this.numOfCard = numOfCard;
        this.sum = sum;
        this.typeOfService = typeOfService;
        this.bankamatId = bankamatId;
    }

    public String getNumOfCard() {
        return numOfCard;
    }

    public void setNumOfCard(String numOfCard) {
        this.numOfCard = numOfCard;
    }

    public String getNumOfSendCard() {
        return numOfSendCard;
    }

    public void setNumOfSendCard(String numOfSendCard) {
        this.numOfSendCard = numOfSendCard;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Service getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(Service typeOfService) {
        this.typeOfService = typeOfService;
    }

    public Long getBankamatId() {
        return bankamatId;
    }

    public void setBankamatId(Long bankamatId) {
        this.bankamatId = bankamatId;
    }

}
