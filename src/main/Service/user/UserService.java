package main.Service.user;

import main.Service.BaseService;
import main.model.User;

import java.util.ArrayList;
import java.util.function.Predicate;

public interface UserService extends BaseService<User> {
    ArrayList<User> userList =new ArrayList<>();

    Predicate<String> isExist = userName -> {
        for (User user: userList) {
            if(user.getFullName().equals(userName)){
                return true;
            }
        }
        return false;
    };
    User signIn(String userName,String password);
}
