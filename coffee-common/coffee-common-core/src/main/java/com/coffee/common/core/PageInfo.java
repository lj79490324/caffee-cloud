package com.coffee.common.core;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 分页参数设置
 * @author rabit
 * @version v1.0
 * @date 2022/9/1 18:16
 */
@Schema(description = "分页参数")
public class PageInfo {

    public PageInfo() {
    }

    public PageInfo(Integer pageSize, Integer pageNum, String keyWords) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.keyWords = keyWords;
    }

    @Schema(description = "当前显示条数",defaultValue = "10")
    private Integer pageSize = 10;

    @Schema(description = "页码",defaultValue = "0")
    private Integer pageNum = 0;

    @Schema(description = "搜索关键字",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String keyWords;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    /**
     * pageData转换为参数
     * @param <T>
     * @return
     */
    public <T> Page<T> covertParam(){
        Page<T> info = new Page<>();
        info.setSize(this.getPageSize()>0?this.getPageSize():0);
        info.setCurrent(this.getPageNum());
        return info;
    }

}
