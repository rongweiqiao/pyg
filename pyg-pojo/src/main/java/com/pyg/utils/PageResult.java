package com.pyg.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 这个类主要封装分页页面数据,前端因为用了插件,只需要给总记录数和封装的实体类数据
 */
public class PageResult implements Serializable {
    private long total;
    private List rows;

    public PageResult() {
    }

    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
