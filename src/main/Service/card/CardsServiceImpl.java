package main.Service.card;

import main.enums.Status;
import main.model.Cards;

import java.util.ArrayList;

public class CardsServiceImpl implements CardsService {
    Long id = 0L;

    @Override
    public int create(Cards cards) {
        cards.setId(id++);
        cardsList.add(cards);
        return 1;
    }

    @Override
    public ArrayList<Cards> showAll() {
        return cardsList;
    }

    @Override
    public void delete(Long id) {
        for (Cards card:cardsList) {
            if (card.getId().equals(id)){
                cardsList.remove(card);
                break;
            }
        }
    }

    @Override
    public Cards update(Cards update) {
        int i = 0;
        for (Cards card:cardsList) {
            if (card.getId().equals(update.getId())){
                cardsList.set(i,update);
                return card;
            }
        }
        return null;
    }

    @Override
    public Cards getById(Long id) {
        for (Cards card:cardsList) {
            if (card.getId().equals(id)){
                return card;
            }
        }
        return null;
    }

    @Override
    public Cards getOwnCard(String number, String password) {
        for (Cards card:cardsList) {
            if (card.getNumberOfCard().equals(number) && card.getPassword().equals(password)){
                return card;
            }
        }
        return null;
    }

    public Long getCardId(String number){
        for (Cards card:cardsList) {
            if (card.getNumberOfCard().equals(number)){
                return card.getId();
            }
        }
        return null;
    }
    public boolean isTruePassword(String number,String password){
        for (Cards card:cardsList) {
            if (card.getNumberOfCard().equals(number) && card.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public void blockCard(Long id){
        for (Cards card:cardsList) {
            if (card.getId().equals(id)){
                card.setStatus(Status.BLOCKED);
            }
        }
    }
    public void unBlockCard(Long id){
        for (Cards card:cardsList) {
            if (card.getId().equals(id)){
                card.setStatus(Status.NOT_BLOCKED);
            }
        }
    }
    public ArrayList<Cards>blockedCards(){
        ArrayList<Cards>blockedCards = new ArrayList<>();
        for (Cards card:cardsList) {
            if (card.getStatus().equals(Status.BLOCKED)){
                blockedCards.add(card);
            }
        }
        return blockedCards;
    }
    public ArrayList<Cards>unBlockedCards(){
        ArrayList<Cards>unBlockedCards = new ArrayList<>();
        for (Cards card:cardsList) {
            if (card.getStatus().equals(Status.NOT_BLOCKED)){
                unBlockedCards.add(card);
            }
        }
        return unBlockedCards;
    }
    public boolean transferMoney(Long fromCardId,Long toCardId,double sum,double percent){
        Cards fromCard = new Cards();
        for (Cards card:cardsList) {
            if (card.getId().equals(fromCardId)){
                  fromCard = getById(fromCardId);
            }
        }
        if (fromCard!=null){
        for (Cards card2:cardsList) {
            if (card2.getId().equals(toCardId)){
                card2.setBalance(card2.getBalance()+sum);
                fromCard.setBalance((fromCard.getBalance()-sum)-sum*percent/100);
                return true;
            }
        }
        }
        return false;
    }
    public ArrayList<Cards> transferCards(Long ownerCardId){
        ArrayList<Cards>cards = new ArrayList<>();
        for (Cards card:cardsList) {
            if (!card.getId().equals(ownerCardId)){
                cards.add(card);
            }
        }
        return cards;
    }
    public boolean cashOut(Long cardId,double sum,double percent){
        for (Cards card:cardsList) {
            if (card.getId().equals(cardId)){
                card.setBalance((card.getBalance()-sum)-sum*percent/100);
                return true;
            }
        }
        return false;
    }
}
