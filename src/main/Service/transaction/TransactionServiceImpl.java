package main.Service.transaction;

import main.enums.Service;
import main.model.TransAction;

import java.util.ArrayList;

public class TransactionServiceImpl implements TransactionService{
    Long id = 0L;

    @Override
    public int create(TransAction transAction) {
        transAction.setId(id++);
        history.add(transAction);
        return 1;
    }

    @Override
    public ArrayList<TransAction> showCashOut(Service status,Long bankamatId) {
        ArrayList<TransAction>cashOut =  new ArrayList<>();
        for (TransAction t:history) {
            if (t.getBankamatId().equals(bankamatId)) {
                if (t.getTypeOfService().equals(status)) {
                    cashOut.add(t);
                }
            }
        }
        return cashOut;
    }

    @Override
    public ArrayList<TransAction> showFill(Service status,Long bankamatId) {
        ArrayList<TransAction>fillBalance =  new ArrayList<>();
        for (TransAction t:history) {
            if (t.getBankamatId().equals(bankamatId)) {
                if (t.getTypeOfService().equals(status)) {
                    fillBalance.add(t);
                }
            }
        }
        return fillBalance;
    }

    @Override
    public ArrayList<TransAction> showTransfer(Service status,Long bankamatId) {
        ArrayList<TransAction>transfers =  new ArrayList<>();
        for (TransAction t:history) {
            if (t.getBankamatId().equals(bankamatId)) {
                if (t.getTypeOfService().equals(status)) {
                    transfers.add(t);
                }
            }
        }
        return transfers;
    }

    @Override
    public Double totalSum(ArrayList<TransAction> allTransActions) {
        double total = 0D;
        for (TransAction t:history) {
            total += t.getSum();
        }
        return total;
    }
    public ArrayList<TransAction>historyOfCard(String numberOfCard){
        ArrayList<TransAction>historyOfCard = new ArrayList<>();
        for (TransAction transAction:history) {
            if (transAction.getNumOfCard()!=null || transAction.getNumOfSendCard()!=null){
                if (transAction.getNumOfCard().equals(numberOfCard) || transAction.getNumOfSendCard().equals(numberOfCard)){
                    historyOfCard.add(transAction);
                }
            }
        }
        return historyOfCard;
    }

    @Override
    public ArrayList<TransAction> getHistory() {
        return history;
    }
}
