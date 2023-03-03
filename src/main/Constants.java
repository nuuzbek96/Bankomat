package main;

public interface Constants {
    String MAIN_MENU = "1. Sign in Admin \t 2. Sign in User \t 0. Exit";
    String WRONG_INPUT = "Wrong input please try again.";
    String NAME = "Enter userName (0.back) : ";
    String PASSWORD = "Enter password(0.back)";
    String NAME_EXCEPTION = "There is not account with this name : ";
    String CARD_NUMBER = "Enter card number (0.back) : ";
    String DIGIT_EXCEPTION = "your card must exist only digits .";
    String LENGTH_EXCEPTION = "your card number must length 16 digits";
    String CARD_BLOCKED = "Your card has blocked.";
    String CARD_WILL_BLOCK = "opportunity after your card will be blocked ! ";
    String BANKAMAT_MAIN = "1. Bankamat options\t 2. BankCards \t 0.Back";
    String CHOOSE = "Choose :";
    String BANKAMAT_CRUD = "1. create bankamat\t 2. TransAction history\t 3. Update\t 4. Delete \t 0.back";
    String NAME_FOR_BANKAMAT = "Enter name for bankamat (0.back): ";
    String EXIST_EXCEPTION = "There is bankamat with this name : ";
    String PERCENT_FOR_SERVICE = "Enter service percentage for service : ";
    String TRANSACTION = "1. all history\t 2. fill balance\t 3. transfer\t 4. get money";
}
