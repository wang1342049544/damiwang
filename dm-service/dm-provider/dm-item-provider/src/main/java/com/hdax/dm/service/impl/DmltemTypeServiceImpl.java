package com.hdax.dm.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdax.dm.dao.DmltemTypeDao;
import com.hdax.dm.dao.ItemDao;
import com.hdax.dm.entity.item.DmItem;
import com.hdax.dm.entity.item.DmItemType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 因为不加真正扣控制器了
 * 既做业务逻辑 也做控制器
 */
@RestController
@RequestMapping(path = "/item/itemType")
public class DmltemTypeServiceImpl   {

    @Resource  //@Autowired
    private DmltemTypeDao dmltemTypeDao;
    @Resource
    private ItemDao ItemDao;


    @PostMapping(path = "/parent/{parent}")  //参数对应上 @PathVariable
    public List<DmItemType> cha(@PathVariable("parent") Long parent) {
        QueryWrapper<DmItemType> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("parent",parent);  //第一个参数 数据库表叫什么是什么就叫什么
        return dmltemTypeDao.selectList(queryWrapper);
    }

    //子集
    @PostMapping(path = "/children/{id}")
    public DmItemType findChildren(@PathVariable("id") Long id) {
        QueryWrapper<DmItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return dmltemTypeDao.selectOne(queryWrapper);
    }

    /**
     * 今日推荐
     * @return
     * SELECT * from  dm_item  where isRecommend =1 LIMIT 0,6
     *
     */
    @PostMapping(path = "/recommend")
    public List<DmItem> findRecommend(){
        QueryWrapper<DmItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isRecommend","1");
        Page<DmItem> page = new Page(1,6);
        List<DmItem> records = ItemDao.selectPage(page, queryWrapper).getRecords();
        return records;
    }

    /**
     * 即将开售
     * @return
     * SELECT * from  dm_item  where isRecommend =1 LIMIT 0,6
     *
     */
    @PostMapping(path = "/recommend1")
    public List<DmItem> findRecommend1(){
        QueryWrapper<DmItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isRecommend","0");
        Page<DmItem> page = new Page(1,6);
        List<DmItem> records = ItemDao.selectPage(page, queryWrapper).getRecords();
        return records;
    }

    /**
     * 查询中间导航栏
     * @return
     */
    @PostMapping(path = "/lineNav")
    public List<DmItemType> findByLevel() {
        QueryWrapper<DmItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",1);
        return dmltemTypeDao.selectList(queryWrapper);
    }






}
