package cn.hejinyo.base;

import cn.hejinyo.utils.PageQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/26 21:57
 * @Description :
 */
public interface BaseService<T, ID extends Serializable> {
    /**
     * 删除给定主键的记录
     */
    int delete(ID id);

    /**
     * 删除给定多个实体的主键记录
     */
    int delete(List<T> entity);

    /**
     * 根据主键查找一条记录
     */
    T findOne(ID id);

    /**
     * 返回所有记录
     */
    List<T> findAll();

    List<T> findList(T entity);

    List<T> findPage(PageQuery pageQuery);

    /**
     * 实体属性的记录数量
     */
    int count(T entity);

    /**
     * 是否存在给定实体属性的记录
     */
    boolean exsit(T entity);

    /**
     * 增加
     */
    int save(T entity);

    /**
     * 更新
     */
    int update(T entity);

}
