package cn.hejinyo.utils;

import lombok.Data;

/**
 * 查询参数
 */
@Data
public class PageQuery<T> {
    private static final long serialVersionUID = 1L;

    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //排序字段
    private String sidx;
    //排序方式
    private String order;
    //查询字段
    private T query;
    //排序字段
    private T ord;

}
