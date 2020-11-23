package com.hdax.dm.entity.item;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;

/**
 * 不需要实现序列化Serializable接口了
 * 继承 BaseEntity
 */

/**
 * 首页类型
 */
@TableName("dm_item_type  ") //表名字
public class DmItemType extends BaseEntity {
/**
 * 每个数据库表都有createdTime  updatedTime字段
 * 当一条数据新增的时候 createdTime赋予当前时间
 * 修改的时候updatedTime 赋予修改时间
 */

@TableId(type = IdType.AUTO)  //自增 在分布式不建议使用  因为两个微服务很可能调用一个数据库
private  Long id;
private String itemType;
private  Long level;
private Long parent;
private  String aliasName;

@TableField(value = "`key`")
private  Long key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
