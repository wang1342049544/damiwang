package com.hdax.dm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdax.dm.dao.ItemDao;
import com.hdax.dm.entity.item.DmItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品表
 * mybatis-plus 提供的 数据库方法 就不用写 xml文件了
 */
@RestController
@RequestMapping(path = "/item/item")
public class DmItemRestServiceImpl  {
    @Resource
    private ItemDao itemDao;

    /**
     * 首页  菜单查询  每个 比如演唱会   对应 的 5个位置
     * @param itemType1Id
     * @return
     */
    @PostMapping(path = "/isBanner/{itemType1Id}")  //参数对应上 @PathVariable
    public List<DmItem> itemBannerList(@PathVariable("itemType1Id") Long itemType1Id)  {
        QueryWrapper<DmItem> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("isBanner",1);  //第一个参数 数据库表叫什么是什么就叫什么
        queryWrapper.eq("itemType1Id",itemType1Id);  //第一个参数 数据库表叫什么是什么就叫什么
        return itemDao.selectList(queryWrapper);
    }

    @PostMapping(path = "/aaa/{itemType1Id}")
    public List<DmItem>  aaa(@PathVariable("itemType1Id") Long itemType1Id) {
        QueryWrapper<DmItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType1Id", itemType1Id);  //第一个参数 数据库表叫什么是什么就叫什么
        return itemDao.selectList(queryWrapper);
    }
    /**
     * 楼层
     * 热门推荐
     * @param
     * @return
     */
    @PostMapping("/findByTypeId/{typeId}")
    public List<DmItem> findByTypeId(@PathVariable("typeId") Long itemType1Id){
        QueryWrapper<DmItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType1Id",itemType1Id);
        queryWrapper.orderByDesc("avgScore");
        Page<DmItem> page = new Page<>(1, 5);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }


    //热门排行
    @PostMapping(path = "/seniority/{itemTypeId}")
    public List<DmItem> seniorityList(@PathVariable("itemTypeId")Long itemTypeId) {
        QueryWrapper<DmItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType1Id",itemTypeId);
        IPage<DmItem> iPage = new Page<>(1,10);
        return itemDao.selectPage(iPage, queryWrapper).getRecords();
    }

    /**
     * 商品详情页
     */
    @PostMapping(path = "/descitem/{id}")
    public DmItem findItemDetaileById(@PathVariable("id")Long id){
        QueryWrapper<DmItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return  itemDao.selectOne(queryWrapper);
    }


/**
 * 下确定单
 */
@PostMapping("/findItemById/{id}")
public DmItem findItemById(@PathVariable("id") Long id){
    QueryWrapper<DmItem> wrapper = new QueryWrapper<>();
    wrapper.eq("id",id);
    return itemDao.selectOne(wrapper);
}


    //热门推荐1
    @PostMapping(path = "/getRecommend/{id}")
    public List<DmItem> getRecommend(@PathVariable("id") Long id){
        QueryWrapper<DmItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("isRecommend",1);
        queryWrapper.eq("itemType1Id",id);
        IPage<DmItem> iPage=new Page<>(0,6);
        return itemDao.selectPage(iPage,queryWrapper).getRecords();
    }

    //热门推荐2
    @PostMapping(path = "/getRecommend2/{id}")
    public List<DmItem> getRecommend2(@PathVariable("id") Long id){
        QueryWrapper<DmItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("isRecommend",1);
        queryWrapper.eq("itemType2Id",id);
        IPage<DmItem> iPage=new Page<>(0,6);
        return itemDao.selectPage(iPage,queryWrapper).getRecords();
    }






}
