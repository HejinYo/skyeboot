package cn.hejinyo.base;

import cn.hejinyo.utils.PageQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/26 18:28
 * @Description :
 */
public interface BaseDao<T, ID extends Serializable> {
    /**
     * 删除给定主键的记录
     */
    Integer delete(ID id);

    /**
     * 删除给定多个实体的主键记录
     */
    Integer deleteArray(ID[] ids);

    Integer deleteList(List<T> entity);

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
     * 实体是否存在
     */
    boolean exsit(T entity);

    /**
     * 增加
     */
    Integer save(T entity);

    /**
     * 更新
     */
    Integer update(T entity);

}
