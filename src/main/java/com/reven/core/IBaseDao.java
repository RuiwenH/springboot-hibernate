package com.reven.core;

import java.util.List;

public interface IBaseDao<T> {

    void save(T entity);

    void delete(Long id);

    void delete(Integer id);

    void update(T entity);

    T getById(Long id);

    T getById(Integer id);

    List<T> getByIds(Long[] ids);

    List<T> findAll();

    List<T> findListByHql(String queryString, Object[] values);

}
