package com.hdax.dm.vo;

import java.io.Serializable;
import java.util.List;
/**
 *
 * 视图对象   这个对象就是返回前端界面 给视图查看的
 * 数组字段 没有注解可以完成
 * 针对类似mybatis-puls 类似的框架 希望有一个公共的解决方案
 *再VO里边 针对上边这种请求数据的类型  把 Dmitemtype 里边的所有属性 再定义一个类 进行相应封装 就可以
 *
 *
 * 序列化 防止服务之间调用的时候无法完成序列化和反序列化
 * 对象有可能转成流  回不来了
 */
public class ltemTypeResponse implements Serializable {
     private  Long id;
     private  String itemType;
     private  Long level;
     private  Long parent;
     private  String aliasName;
     private  List<ltemTypeResponse>  children;  //子数组 要求跟父数组一样  将ltemTypeResponse既作为子数组也作为父亲
     private  List<ItemResponse> hotItems; //热门节目

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

    public List<ltemTypeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<ltemTypeResponse> children) {
        this.children = children;
    }

    public List<ItemResponse> getHotItems() {
        return hotItems;
    }

    public void setHotItems(List<ItemResponse> hotItems) {
        this.hotItems = hotItems;
    }
}
