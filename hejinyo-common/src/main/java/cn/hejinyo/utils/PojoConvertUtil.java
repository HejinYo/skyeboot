package cn.hejinyo.utils;

import net.sf.cglib.beans.BeanCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/22 21:24
 * @Description :
 */
public class PojoConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(PojoConvertUtil.class);

    private static Lock initLock = new ReentrantLock();

    private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    /**
     * 初始化 BeanCopier
     *
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier initCopier(Class source, Class target) {
        initLock.lock();
        BeanCopier find = beanCopierMap.get(source.getName() + "_" + target.getName());
        if (find != null) {
            initLock.unlock();
            return find;
        }
        BeanCopier beanCopier = BeanCopier.create(source, target, false);
        beanCopierMap.put(source.getName() + "_" + target.getName(), beanCopier);
        initLock.unlock();
        return beanCopier;
    }


    /**
     * 获取BeanCopier
     *
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier getBeanCopier(Class source, Class target) {
        BeanCopier beanCopier = beanCopierMap.get(source.getClass().getName() + "_" + target.getName());
        if (beanCopier != null) {
            return beanCopier;
        }
        return initCopier(source, target);
    }


    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        try {
            T target = targetClass.newInstance();
            beanCopier.copy(source, target, null);
            return target;

        } catch (Exception e) {
            logger.error("对象拷贝失败,{}", e);
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     */
/*    public static <E> List<E> convert(List source, Class<E> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return new ArrayList<E>();
            }
            List result = new ArrayList<E>();

            for (Object each : source) {
                result.add(convert(each, targetClass));
            }
            return result;
        } catch (Exception e) {
            logger.error("对象拷贝失败,{}", e);
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }*/


}
