package cn.hejinyo.annotation;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    int save(T t);

    int save(Map<String, Object> parameter);

    int saveBatch(List<T> list);

    int delete(Object id);

    int delete(Map<String, Object> parameter);

    int deleteBatch(Object[] id);

    int update(T t);

    int update(Map<String, Object> parameter);

    T get(Object id);

    List<T> list(Object id);

    List<T> list(Map<String, Object> parameter);

    List<T> listPage(Object id);

    List<T> listPage(Map<String, Object> parameter);

    int count();

    int count(Map<String, Object> parameter);

}
