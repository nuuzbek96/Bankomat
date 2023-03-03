package main.Service.bankamat;

import main.Service.BaseService;
import main.model.Bankamat;
import main.model.User;

import java.util.ArrayList;
import java.util.function.Predicate;

public interface BankamatService extends BaseService<Bankamat> {

    ArrayList<Bankamat>bankamats = new ArrayList<>();

    Predicate<String> isExist = bankamatName -> {
        for (Bankamat bankamat: bankamats) {
            if(bankamat.getName().equals(bankamatName)){
                return true;
            }
        }
        return false;
    };
}
