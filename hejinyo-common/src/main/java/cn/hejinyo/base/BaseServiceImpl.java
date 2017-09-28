package cn.hejinyo.base;

import cn.hejinyo.utils.PageQuery;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/26 22:00
 * @Description :实现类（ 泛型：M 是 mapper 对象，T 是实体 ， ID 是主键泛型 ）
 */
public class BaseServiceImpl<M extends BaseDao<T, ID>, T, ID extends Serializable> implements BaseService<T, ID> {

    @Autowired
    protected M baseDao;

    @Override
    public int delete(ID id) {
        return baseDao.delete(id);
    }

    @Override
    public int deleteArray(ID[] ids) {
        return baseDao.deleteArray(ids);
    }

    @Override
    public int deleteList(List<T> entity) {
        return baseDao.deleteList(entity);
    }

    @Override
    public T findOne(ID id) {
        return baseDao.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> findList(T entity) {
        return baseDao.findList(entity);
    }

    @Override
    public List<T> findPage(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrder());
        return baseDao.findPage(pageQuery);
    }

    @Override
    public int count(T entity) {
        return baseDao.count(entity);
    }

    @Override
    public boolean exsit(T entity) {
        return baseDao.exsit(entity);
    }

    @Override
    public int save(T entity) {
        return baseDao.save(entity);
    }

    @Override
    public int update(T entity) {
        return baseDao.update(entity);
    }
}
