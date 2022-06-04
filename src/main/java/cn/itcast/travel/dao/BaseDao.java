package cn.itcast.travel.dao;

public interface BaseDao<T> {
    T findById(int id);
    int save(T t);
    int delete(int id);
}
