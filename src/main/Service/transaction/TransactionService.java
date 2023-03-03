package main.Service.transaction;

import main.enums.Service;
import main.enums.Status;
import main.model.TransAction;

import java.util.ArrayList;

public interface TransactionService {
    ArrayList<TransAction> history = new ArrayList<>();

    int create(TransAction transAction);

    ArrayList<TransAction> showCashOut(Service status,Long bankamatId);

    ArrayList<TransAction> showFill(Service status,Long bankamatId);

    ArrayList<TransAction> showTransfer(Service status,Long bankamatId);

    Double totalSum(ArrayList<TransAction> allTransActions);

    ArrayList<TransAction> getHistory();
}
