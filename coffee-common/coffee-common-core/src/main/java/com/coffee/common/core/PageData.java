package com.coffee.common.core;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回数据
 * @author rabit
 * @version v1.0
 * @date 2022/9/1 18:48
 */
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //返回的数据
    private List<T> list;
    //一共多少条数据
    private long total;
    //每页显示多少条
    private long size = 10;
    //当前查询页码
    private long pageNum;
    //一共多少页
    private long pages;

    public PageData() {
    }

    public PageData(List<T> list, long total, long size, long pageNum, long pages) {
        this.list = list;
        this.total = total;
        this.size = size;
        this.pageNum = pageNum;
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public static <T> PageData<T> covertData(IPage<T> page){
       return new PageData<>(page.getRecords(),page.getTotal(),page.getSize(),page.getCurrent(),page.getPages());
    }
}
