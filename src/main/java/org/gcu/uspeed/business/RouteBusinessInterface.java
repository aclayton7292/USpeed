package org.gcu.uspeed.business;

import java.util.List;

public interface RouteBusinessInterface<T> {

        List<T> findAll();
        T findBy(T t);
        List<T> findAllWithID(T t);
        boolean create(T t);
        boolean remove(T t);
}