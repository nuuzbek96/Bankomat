package main.Service.card;

import main.Service.BaseService;
import main.model.Cards;
import main.model.User;

import java.util.ArrayList;
import java.util.function.Predicate;

public interface CardsService extends BaseService<Cards> {
    ArrayList<Cards>cardsList = new ArrayList<>();
    Cards getOwnCard(String number,String password);

    Predicate<String> isExist = cardNumber -> {
        for (Cards cards: cardsList) {
            if(cards.getNumberOfCard().equals(cardNumber)){
                return true;
            }
        }
        return false;
    };
}
