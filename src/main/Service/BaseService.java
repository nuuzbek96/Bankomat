package main.Service;

import java.util.ArrayList;

public interface BaseService<T>{

    int create(T t);

    ArrayList<T> showAll();

    void delete(Long id);

    T update(T update);

    T getById(Long id);

}
