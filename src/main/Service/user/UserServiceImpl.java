package main.Service.user;

import main.model.User;

import java.util.ArrayList;

public class UserServiceImpl implements UserService{
    Long id = 0L;

    @Override
    public int create(User user) {
        user.setId(id++);
        userList.add(user);
        return 1;
    }

    @Override
    public ArrayList<User> showAll() {
        return userList;
    }

    @Override
    public void delete(Long id) {
        for (User user:userList) {
            if (user.getId().equals(id)){
                userList.remove(user);
                break;
            }
        }
    }

    @Override
    public User update(User update) {
        int i = 0;
        for (User user:userList) {
            if (user.getId().equals(update.getId())){
                userList.set(i,update);
                return userList.get(i);
            }
            i++;
        }
        return null;
    }

    @Override
    public User getById(Long id) {
        for (User user:userList) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User signIn(String userName, String password) {
        for (User user:userList) {
            if (user.getFullName().equals(userName) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
