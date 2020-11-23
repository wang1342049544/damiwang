package com.hdax.dm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 每个数据库表都有createdTime  updatedTime字段
 * 当一条数据新增的时候 createdTime赋予当前时间
 * 修改的时候updatedTime 赋予修改时间
 */
public class BaseEntity implements Serializable {

    @TableField(fill = FieldFill.INSERT) //你在新增的时候
    protected LocalDateTime createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE) //你在修改的时候
    protected LocalDateTime updatedTime;



    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }


}
