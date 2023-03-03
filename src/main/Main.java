package main;

import main.Service.bankamat.BankamatSeviceImpl;
import main.Service.card.CardsServiceImpl;
import main.Service.transaction.TransactionServiceImpl;
import main.Service.user.UserServiceImpl;
import main.enums.Service;
import main.enums.Status;
import main.model.Bankamat;
import main.model.Cards;
import main.model.TransAction;
import main.model.User;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements Constants {
    static Scanner intScan = new Scanner(System.in);
    static Scanner strScan = new Scanner(System.in);
    static Scanner doubleScan = new Scanner(System.in);
    static UserServiceImpl userService = new UserServiceImpl();
    static TransactionServiceImpl transactionService = new TransactionServiceImpl();
    static CardsServiceImpl cardsService = new CardsServiceImpl();
    static BankamatSeviceImpl bankamatSevice = new BankamatSeviceImpl();

    public static void main(String[] args) {
        User user = new User("Admin", "root", "Admin", Status.ADMIN);
        userService.create(user);
        Cards card = new Cards("Behruz", "2222", "1234123412341234", "22/33", 1000000d, Status.NOT_BLOCKED);
        Cards card2 = new Cards("Sardor", "2222", "1234123412341235", "22/33", 0d, Status.NOT_BLOCKED);
        cardsService.create(card);
        cardsService.create(card2);
        int choose = 1;
        while (choose != 0) {
            System.out.println(MAIN_MENU);
            choose = intScan.nextInt();
            switch (choose) {
                case 1 -> {
                    if (signInAdmin()) {
                        adminSide();
                    }
                }
                case 2 -> {
                    Long id = signInUSer();
                    if (id >= 0) {
                        userSide(id);
                    } else if (id == -2) {
                        System.out.println(CARD_BLOCKED);
                    }
                }
                case 0 -> {

                }
                default -> {
                    System.out.println(WRONG_INPUT);
                }
            }
        }
    }

    public static boolean signInAdmin() {
        while (true) {
            System.out.println(NAME);
            String name = strScan.nextLine();
            if (name.equals("0")) {
                return false;
            } else if (!userService.isExist.test(name)) {
                System.out.println(NAME_EXCEPTION);
                return false;
            } else {
                System.out.println(PASSWORD);
                String password = strScan.nextLine();
                User admin = userService.signIn(name, password);
                if (admin != null) {
                    return true;
                }
            }
        }
    }

    public static Long signInUSer() {
        while (true) {
            Long cardId;
            System.out.println(CARD_NUMBER);
            String cardNumber = strScan.nextLine();
            String password;
            if (cardNumber.equals("0")) {
                return -1L;
            }
            int check = checkCard(cardNumber);
            if (check == -1) {
                System.out.println(DIGIT_EXCEPTION);
                break;
            } else if (check == 0) {
                System.out.println(LENGTH_EXCEPTION);
                break;
            } else if (cardsService.isExist.test(cardNumber)) {

                System.out.println(PASSWORD);
                password = strScan.nextLine();
                Status cardStatus = checkPassword(cardNumber, password);

                if (cardStatus.equals(Status.BLOCKED)) {
                    cardId = cardsService.getCardId(cardNumber);
                    cardsService.blockCard(cardId);
                    return -2L;
                }
                cardId = cardsService.getCardId(cardNumber);
                Cards card = cardsService.getById(cardId);
                if (card != null) {
                    return card.getId();
                }
            } else {
                System.out.println(WRONG_INPUT);
                break;
            }
        }
        return -1L;
    }

    public static Status checkPassword(String number, String password) {
        int count = 3;
        while (true) {
            if (count < 3) {
                System.out.println(PASSWORD);
                password = strScan.nextLine();
            }
            if (password.equals("0") && count > 0) {
                return Status.NOT_BLOCKED;
            }
            if (!cardsService.isTruePassword(number, password)) {
                System.out.println("you have " + (count - 1) + " " + CARD_WILL_BLOCK);
                count--;
            } else {
                Cards card = cardsService.getOwnCard(number, password);
                return card.getStatus();
            }
            if (count == 0) {
                return Status.BLOCKED;
            }
        }
    }

    public static void adminSide() {
        System.out.println(BANKAMAT_MAIN);
        System.out.print(CHOOSE);
        int choose = intScan.nextInt();
        switch (choose) {
            case 1 -> {
                bankamatCrud();
            }
            case 2 -> {
                crudCard();
            }
            case 0 -> {

            }
        }
    }

    public static void crudCard() {
        int choose = 1;
        while (choose != 0) {
            System.out.println("1. create card\t2. show all cards\t3.unblock card\t4. block card\t 0. back");
            choose = intScan.nextInt();
            switch (choose) {
                case 1 -> {
                    createCard();
                }
                case 2 -> {
                    showAllCards();
                }
                case 3 -> {
                    unBlockCard();
                }
                case 4 -> {
                    blockCard();
                }
                case 0 -> {

                }
            }
        }
    }

    public static void blockCard() {
        ArrayList<Cards> unBlockCard = cardsService.unBlockedCards();
        int i = 1;
        for (Cards card : unBlockCard) {
            System.out.println(i++ + ". status : " + card.getStatus() + " || card number : " + card.getNumberOfCard() + " || user : "
                    + card.getNameOfUser());
        }
        System.out.print("\nchoose card : ");
        int choose = intScan.nextInt();
        if (choose > 0 && choose <= unBlockCard.size()) {
            System.out.println("do you want to block card ? 1. yes 0. no");
            int option = intScan.nextInt();
            if (option == 1) {
                cardsService.blockCard(unBlockCard.get(choose - 1).getId());
            } else {
                System.out.println("action has canceled.");
            }
        }
    }

    public static void unBlockCard() {
        ArrayList<Cards> blockedCards = cardsService.blockedCards();
        int i = 1;
        for (Cards card : blockedCards) {
            System.out.println(i++ + ". status : " + card.getStatus() + " || card number : " + card.getNumberOfCard() + " || user : "
                    + card.getNameOfUser());
        }
        System.out.print("\nchoose card : ");
        int choose = intScan.nextInt();
        if (choose > 0 && choose <= blockedCards.size()) {
            System.out.println("do you want to unblock card ? 1. yes 0. no");
            int option = intScan.nextInt();
            if (option == 1) {
                cardsService.unBlockCard(blockedCards.get(choose - 1).getId());
            } else {
                System.out.println("action has canceled.");
            }
        }
    }

    public static void showAllCards() {
        ArrayList<Cards> cards = cardsService.showAll();
        int i = 1;
        for (Cards card : cards) {
            System.out.println(i++ + ". status :" + card.getStatus() + " || user : " + card.getNameOfUser() + " || card number :" +
                    card.getNumberOfCard());
        }
    }

    public static void createCard() {
        String number;
        String password;
        while (true) {
            System.out.println("Enter number of card (0. back) : ");
            number = strScan.nextLine();
            if (number.equals("0")) {
                return;
            }
            if (number.length() != 16) {
                System.out.println("card length must be 16 numbers !");
            } else {
                boolean isDigit = false;
                for (int i = 0; i < number.length(); i++) {
                    if (!Character.isDigit(number.charAt(i))) {
                        System.out.println("card number exist only numbers !");
                        isDigit = false;
                        break;
                    }
                    isDigit = true;
                }
                if (isDigit) {
                    if (cardsService.isExist.test(number)) {
                        System.out.println("There is card with this number !");
                    } else {
                        break;
                    }
                }
            }
        }
        while (true) {
            System.out.println("Enter password : ");
            password = strScan.nextLine();
            if (password.length() != 4) {
                System.out.println("password must length 4 number");
            } else {
                boolean isDigit = false;
                for (int i = 0; i < password.length(); i++) {
                    if (!Character.isDigit(password.charAt(i))) {
                        System.out.println("Password must contains only numbers !");
                        isDigit = false;
                        break;
                    }
                    isDigit = true;
                }
                if (isDigit) {
                    break;
                }
            }
        }
        System.out.println("Enter full name of User : ");
        String name = strScan.nextLine();
        System.out.println("Enter expiry");
        String expiry = strScan.nextLine();
        Cards card = new Cards(name, password, number, expiry, 0d, Status.NOT_BLOCKED);
        cardsService.create(card);
    }

    public static void bankamatCrud() {
        int choose = 1;
        while (choose != 0) {
            System.out.println(BANKAMAT_CRUD);
            choose = intScan.nextInt();
            switch (choose) {
                case 1 -> {
                    createBankamat();
                }
                case 2 -> {
                    ArrayList<Bankamat> allBankamats = bankamatSevice.showAll();
                    int i = 1;
                    for (Bankamat b : allBankamats) {
                        System.out.println(i++ + ". " + b.getName() + " || percent : " + b.getPercent());
                    }
                    System.out.println(CHOOSE);
                    int act = intScan.nextInt();
                    if (act != 0 && act <= allBankamats.size()) {
                        Bankamat bankamat = bankamatSevice.getById(allBankamats.get(act - 1).getId());
                        transActionHistory(bankamat.getId());
                    }
                }
                case 3 -> {
                    updateBankamat();
                }
                case 4 -> {
                    notActive();
                }
                case 0 -> {

                }
            }
        }
    }

    public static void createBankamat() {
        while (true) {
            System.out.println(NAME_FOR_BANKAMAT);
            String name = strScan.nextLine();
            if (bankamatSevice.isExist.test(name)) {
                System.out.println(EXIST_EXCEPTION);
                return;
            } else {
                System.out.println(PERCENT_FOR_SERVICE);
                double percent = doubleScan.nextDouble();

                Bankamat bankamat = new Bankamat(name, percent, Status.ACTIVE);
                bankamatSevice.create(bankamat);
                return;
            }
        }
    }

    public static void transActionHistory(Long bankamatId) {
        System.out.print("\n\n" + TRANSACTION);
        int choose = intScan.nextInt();
        switch (choose) {
            case 1 -> {
                showAllHistory(bankamatId);
            }
            case 2 -> {
                fillBalanceTransActionHistory();
            }
            case 3 -> {
                ArrayList<Bankamat> bankamats = bankamatSevice.showAll();
                int in = 1;
                for (Bankamat bankamat : bankamats) {
                    System.out.println(in++ + ". " + bankamat.getName() + " || percent for services : " + bankamat.getPercent());
                }
                System.out.println(CHOOSE);
                int chooseBankamat = strScan.nextInt();
                if (chooseBankamat > 0 && chooseBankamat <= bankamats.size()) {
                    transferHistory(bankamats.get(chooseBankamat - 1).getId());
                } else {
                    System.out.println("Choose correct option");
                }
            }
            case 4 -> {
                ArrayList<Bankamat> bankamats = bankamatSevice.showAll();
                int in = 1;
                for (Bankamat bankamat : bankamats) {
                    System.out.println(in++ + ". " + bankamat.getName() + " || percent for services : " + bankamat.getPercent());
                }
                System.out.println(CHOOSE);
                int chooseBankamat = strScan.nextInt();
                if (chooseBankamat > 0 && chooseBankamat <= bankamats.size()) {
                    getMoney(bankamats.get(chooseBankamat - 1).getId());
                } else {
                    System.out.println("Choose correct option");
                }
            }
            case 0 -> {

            }
        }
    }

    public static void getMoney(Long bankamatId) {
        ArrayList<TransAction> historyCashOut = transactionService.showCashOut(Service.CASHED_OUT, bankamatId);
        int i = 1;
        if (historyCashOut.size() > 0) {
            for (TransAction t : historyCashOut) {
                System.out.println(i++ + ". service type : " + t.getTypeOfService() + " || card : "
                        + t.getNumOfCard() + " || sum : " + t.getSum());
            }
        } else {
            System.out.println("There is not any cash out history. ");
        }

    }

    public static void transferHistory(Long bankamatId) {
        ArrayList<TransAction> historyTransfer = transactionService.showTransfer(Service.TRANSFER, bankamatId);
        int i = 1;
        if (historyTransfer.size() > 0) {
            for (TransAction t : historyTransfer) {
                System.out.println(i++ + ". service type : " + t.getTypeOfService() + " || from card : " + t.getNumOfCard()
                        + " || to card : " + t.getNumOfSendCard() + " || sum : " + t.getSum());
            }
        } else {
            System.out.println("there is not any transfer history. ");
        }
    }

    public static void fillBalanceTransActionHistory() {
        ArrayList<Bankamat> bankamats = bankamatSevice.showAll();
        int in = 1;
        for (Bankamat bankamat : bankamats) {
            System.out.println(in++ + ". " + bankamat.getName() + " || percent for services : " + bankamat.getPercent());
        }
        System.out.println(CHOOSE);
        int chooseBankamat = strScan.nextInt();
        if (chooseBankamat > 0 && chooseBankamat <= bankamats.size()) {
            ArrayList<TransAction> historyFillBalance = transactionService.showFill
                    (Service.FILL_BALANCE, bankamats.get(chooseBankamat - 1).getId());
            if (historyFillBalance.size() > 0) {
                int i = 1;
                for (TransAction t : historyFillBalance) {
                    System.out.println(i++ + ". service type : " + t.getTypeOfService() + " || card : " + t.getNumOfCard() + " || " + t.getSum());
                }
            } else {
                System.out.println("there is not any history yet");
            }
        } else {
            System.out.println("Choose correct option");
        }
    }

    public static void notActive() {
        ArrayList<Bankamat> bankamats = bankamatSevice.showAll();
        int i = 1;
        for (Bankamat bankamat : bankamats) {
            System.out.println(i + ". " + bankamat.getName() + " || percent for services : " + bankamat.getPercent());
        }
        System.out.println(CHOOSE);
        int chooseBankamat = strScan.nextInt();
        if (chooseBankamat > 0 && chooseBankamat <= bankamats.size()) {
            Bankamat deleteBankamat = bankamatSevice.getById(bankamats.get(chooseBankamat - 1).getId());
            System.out.println("do you want to delete Bankamat ? 1. yes || 0. no");
            int option = intScan.nextInt();
            if (option == 1) {
                bankamatSevice.delete(deleteBankamat.getId());
            } else if (option > 2) {
                System.out.println("Bankamat is active.");
            }
        } else {
            System.out.println("Choose correct option.");
        }
    }

    public static void updateBankamat() {
        ArrayList<Bankamat> bankamats = bankamatSevice.showAll();
        int i = 1;
        if (bankamats.size() > 0) {
            for (Bankamat bankamat : bankamats) {
                System.out.println(i + ". " + bankamat.getName() + " || percent for services : " + bankamat.getPercent());
            }
            System.out.println(CHOOSE);
            int chooseBankamat = strScan.nextInt();
            if (chooseBankamat > 0 && chooseBankamat <= bankamats.size()) {
                Bankamat updateBankamat = bankamatSevice.getById(bankamats.get(chooseBankamat - 1).getId());
                System.out.println("Enter new percent for service : ");
                double percent = doubleScan.nextDouble();
                updateBankamat.setPercent(percent);
                bankamatSevice.update(updateBankamat);
            } else {
                System.out.println("Choose correct option.");
            }
        } else {
            System.out.println("you have not bankamat yet");
        }
    }

    public static void showAllHistory(Long id) {
        ArrayList<TransAction> history = transactionService.getHistory();
        if (history.size() > 0) {
            for (TransAction t : history) {
                if (t.getBankamatId().equals(id)) {
                    System.out.println(t.getTypeOfService() + " || user card : " + t.getNumOfCard() + " || sum :" + t.getSum());
                }
            }
        } else {
            System.out.println("there is not history !");
        }
    }

    public static void showBankamats(ArrayList<Bankamat> bankamats) {
        int i = 1;
        for (Bankamat bankamat : bankamats) {
            System.out.println(i++ + ". " + bankamat.getName() + " || percent for service : " + bankamat.getPercent());
        }
    }

    public static void userSide(Long bankCard) {
        ArrayList<Bankamat> bankamats = bankamatSevice.showAll();
        int choose = 1;
        showBankamats(bankamats);
        System.out.println("Choose bankamat : ");
        int chooseBankamat = intScan.nextInt();
        if (chooseBankamat > 0 && chooseBankamat <= bankamats.size()) {
            while (choose != 0)
                if (bankamats.size() > 0) {
                    System.out.println("1. Show balance \t 2. Transfer money\t 3. Cash out " +
                            "\t 4. Fill balance \t 5. Change password 6. See history\t 0. Back");
                    choose = intScan.nextInt();
                    switch (choose) {
                        case 1 -> {
                            showBalance(bankCard);
                        }
                        case 2 -> {
                            transferMoney(bankCard, bankamats.get(chooseBankamat - 1).getId());
                        }
                        case 3 -> {
                            cashOut(bankCard, bankamats.get(chooseBankamat - 1).getId());
                        }
                        case 4 -> {
                            fillBalance(bankCard, bankamats.get(chooseBankamat - 1).getId());
                        }
                        case 5 -> {
                            changePassword(bankCard);
                        }
                        case 6 -> {
                            seeHistory(bankCard);
                        }
                        case 0 -> {

                        }
                    }
                } else {
                    System.out.println("There is no any bankamat.");
                }
        } else {
            System.out.println("choose correct option.");
        }
    }

    public static void seeHistory(Long cardId) {
        Cards card = cardsService.getById(cardId);
        ArrayList<TransAction> history = transactionService.historyOfCard(card.getNumberOfCard());
        int i = 1;
        if (history.size()>0) {
            for (TransAction t : history) {
                if (t.getTypeOfService().equals(Service.TRANSFER)) {
                    System.out.println(i + ". " + t.getTypeOfService() + " || from : " + t.getNumOfCard() + " || to :" +
                            t.getNumOfSendCard() + " || sum : " + t.getSum());
                } else {
                    System.out.println(i + "." + t.getTypeOfService() + " || " + t.getNumOfCard() + " || sum :" + t.getSum());
                }
                i++;
            }
        }else {
            System.out.println("you have not history ! ");
        }
    }

    public static void changePassword(Long cardId) {
        System.out.println("enter new password : ");
        String password = strScan.nextLine();
        if (password.length() == 3) {
            for (int i = 0; i < password.length(); i++) {
                if (!Character.isDigit(password.charAt(i))) {
                    System.out.println("wrong input !");
                    return;
                }
            }
            System.out.println("do you want to change password ? 1. yes 0. not");
            int choose = intScan.nextInt();
            if (choose == 1) {
                Cards card1 = cardsService.getById(cardId);
                card1.setPassword(password);
                cardsService.update(card1);
            } else {
                System.out.println("Action canceled ! ");
            }
        }
    }

    public static void fillBalance(Long cardId, Long bankamatId) {
        Bankamat bankamat = bankamatSevice.getById(bankamatId);
        double percent = bankamat.getPercent();
        System.out.println("Enter sum of money : ");
        double sum = doubleScan.nextDouble();
        Cards card = cardsService.getById(cardId);
        card.setBalance(card.getBalance() + sum - (sum * percent / 100));
        TransAction transAction = new TransAction(card.getNumberOfCard(), sum, Service.FILL_BALANCE, bankamatId);
        transactionService.create(transAction);
        bankamat.setWallet(bankamat.getWallet() + sum * percent / 100);
    }

    public static void cashOut(Long cardId, Long bankamatId) {
        System.out.println("Enter sum of money you want to get : ");
        double sum = doubleScan.nextDouble();
        Bankamat bankamat = bankamatSevice.getById(bankamatId);
        double percent = bankamat.getPercent();
        Cards card = cardsService.getById(cardId);
        if (card.getBalance() >= sum + (sum * percent / 100)) {
            boolean isCashOut = cardsService.cashOut(cardId, sum, percent);
            if (isCashOut) {
                TransAction transAction = new TransAction(card.getNumberOfCard(), sum, Service.CASHED_OUT, bankamatId);
                transactionService.create(transAction);
                bankamat.setWallet(bankamat.getWallet() + sum * percent / 100);
                System.out.println("cash out successfully");
            } else {
                System.out.println("action canceled !");
            }
        } else {
            System.out.println("you have not enough money.");
        }
    }

    public static void showBalance(Long cardId) {
        Cards card = cardsService.getById(cardId);
        System.out.println("Balance : " + card.getBalance());
    }

    public static int checkCard(String cardNumber) {
        if (cardNumber.length() == 16) {
            for (int i = 0; i < cardNumber.length(); i++) {
                if (!Character.isDigit(cardNumber.charAt(i))) {
                    return -1;
                }
            }
        } else {
            return 0;
        }
        return 1;
    }

    public static void transferMoney(Long userCardId, Long bankamatId) {
        Bankamat bankamat = bankamatSevice.getById(bankamatId);
        double percent = bankamat.getPercent();
        Cards ownCard = cardsService.getById(userCardId);
        ArrayList<Cards> allCards = cardsService.transferCards(userCardId);
        int i = 1;
        for (Cards card : allCards) {
            if (card.getStatus() != Status.BLOCKED) {
                if (!card.getId().equals(userCardId)) {
                    System.out.println(i++ + ". " + card.getNumberOfCard());
                }
            }
        }
        System.out.println("choose card which you want to transfer money : ");
        int choose = intScan.nextInt();
        if (choose > 0 && choose <= allCards.size()) {
            Cards card = cardsService.getById(allCards.get(choose - 1).getId());
            System.out.println("Enter sum of transfer : ");
            double sum = doubleScan.nextDouble();
            if (ownCard.getBalance() >= sum + (sum * percent / 100)) {
                boolean isTransfer = cardsService.transferMoney(userCardId, card.getId(), sum, percent);
                if (isTransfer) {
                    System.out.println("Money transfer successfully.");
                    TransAction transAction = new TransAction(ownCard.getNumberOfCard(),
                            card.getNumberOfCard(), sum, Service.TRANSFER, bankamatId);
                    transactionService.create(transAction);
                    bankamat.setWallet(bankamat.getWallet() + sum * percent / 100);
                } else {
                    System.out.println("action was canceled !");
                }
            } else {
                System.out.println("you have not enough money");
            }
        } else {
            System.out.println("wrong input.");
        }
    }

}