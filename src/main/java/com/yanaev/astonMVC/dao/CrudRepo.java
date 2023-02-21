package com.yanaev.astonMVC.dao;

import java.util.List;
import java.util.Optional;

public interface CrudRepo<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    boolean deleteById(Long id);
    boolean save(T entity);
    void updateById(Long id,T entity);
}
