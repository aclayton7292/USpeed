package org.gcu.uspeed.business;

import java.util.List;

public interface UserBusinessInterface<T> {

        List<T> findAll();
        T login(T t);
        boolean register(T t);
        boolean remove(T t);
}