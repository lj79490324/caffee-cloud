package com.coffee.common.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回数据
 * @author rabit
 * @version v1.0
 * @date 2022/9/1 18:48
 */
@Schema(description = "分页返回结果")
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "数据列表")
    private List<T> list;

    @Schema(description = "数据列表总条数")
    private long total;

    @Schema(description = "数据列表单页条数",defaultValue = "10")
    private long size = 10;

    @Schema(description = "当前查询页码",defaultValue = "0")
    private long pageNum;

    @Schema(description = "当前数据总页数",defaultValue = "0")
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

    /**
     * mp的分页封装对象转换为自己定义的PageData对象
     * @param page mp的分页封装对象
     * @param <T> 封装的对象类型
     * @return 转换后的PageData对象
     */
    public static <T> PageData<T> covertData(IPage<T> page){
       return new PageData<>(page.getRecords(),page.getTotal(),page.getSize(),page.getCurrent(),page.getPages());
    }
}
