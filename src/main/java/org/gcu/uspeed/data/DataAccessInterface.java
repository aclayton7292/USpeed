package org.gcu.uspeed.data;

import java.util.List;
import java.util.Optional;

public interface DataAccessInterface <T> {

    List<T> findAll();
    List<T> findAllWithID(int id);
    List<T> findAllBy(T t);
    List<T> findAllByString(String search);
    Optional<T> findById(int id);
    Optional<T> findBy(T t);
    Optional<T> findByString(String search);
    boolean create(T t);
    boolean update(T t);
    boolean delete(T t);
}