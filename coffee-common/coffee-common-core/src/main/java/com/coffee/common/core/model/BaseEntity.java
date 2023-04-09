package com.coffee.common.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "基础实体",description = "基础实体，封装所有通用的字段")
public class BaseEntity {

    @Schema(description = "上次更新时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @Schema(description = "上次更新时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    @Schema(description = "是否删除")
    @JsonIgnore
    private Integer deleted;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
