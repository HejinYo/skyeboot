package cn.hejinyo.utils;

import lombok.Getter;

import java.util.HashMap;

/**
 * 查询参数
 */
@Getter
public class PageQuery extends HashMap<String, Object> {

    //当前页
    private static final String PAGENUM = "pageNum";
    //每页的数量
    private static final String PAGESIZE = "pageSize";
    //排序字段
    private static final String SIDX = "sidx";
    //排序方式
    private static final String SORT = "sort";

    private int pageNum;
    private int pageSize;
    private String order;

    public PageQuery(HashMap<String, Object> parameters) {
        this.putAll(parameters);
        this.pageNum = Integer.parseInt(parameters.get(PAGENUM).toString());
        this.pageSize = Integer.parseInt(parameters.get(PAGESIZE).toString());
        String sidx = null != parameters.get(SIDX) && StringUtils.isNotEmpty(parameters.get(SIDX).toString()) ? StringUtils.underscoreName(parameters.get(SIDX).toString()) : null;
        if (null != sidx) {
            this.order = sidx;
        }
        String sort = null;
        if (null != sidx && null != parameters.get(SORT) && StringUtils.isNotEmpty(parameters.get(SORT).toString())) {
            sort = parameters.get(SORT).toString().toLowerCase().contains("desc") ? "DESC" : "ASC";
            this.order = sidx + " " + sort;
        }
        this.put(PAGENUM, pageNum);
        this.put(PAGESIZE, pageSize);
        this.put(SIDX, sidx);
        this.put(SORT, sort);
    }

}
