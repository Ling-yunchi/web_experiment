package cn.itcast.travel.dao;

import java.util.List;

public interface BaseDao<T> {
    T findById(int id);

    List<T> findAll();

    int save(T t);

    int delete(int id);
}
