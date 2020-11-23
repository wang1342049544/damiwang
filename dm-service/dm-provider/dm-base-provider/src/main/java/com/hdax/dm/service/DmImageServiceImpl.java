package com.hdax.dm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.hdax.dm.dao.ImageDao;
import com.hdax.dm.entity.item.DmImage;
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
@RequestMapping(path = "/base/image")
public class DmImageServiceImpl {



    @Resource
    private ImageDao imageDao;

    /**
     * 获取图片
     * @param itemId
     * @return
     */
    @PostMapping(path = "/item/{itemId}")  //参数对应上 @PathVariable
    public DmImage itemBannerList(@PathVariable("itemId") Long itemId)  {
        QueryWrapper<DmImage> queryWrapper  = new QueryWrapper<>();
       queryWrapper.eq("type",2); //2海报图   1.没有类型
        queryWrapper.eq("targetId",itemId);
        return imageDao.selectOne(queryWrapper);
    }



    /**
     * 获取今日推荐
     */
    @PostMapping(path = "/item2/{targetId}")  //参数对应上 @PathVariable
    public DmImage floorImage(@PathVariable("targetId") Long targetId)  {
        QueryWrapper<DmImage> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("type","3");
        queryWrapper.eq("targetId",targetId);
        return imageDao.selectOne(queryWrapper);
    }

    /**
     *
     * 获取即将开售
     */
    @PostMapping(path = "/item3/{targetId}")  //参数对应上 @PathVariable
    public DmImage kaishou(@PathVariable("targetId") Long targetId)  {
        QueryWrapper<DmImage> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("type","4");
        queryWrapper.eq("targetId",targetId);
        return imageDao.selectOne(queryWrapper);
    }


    /**
     * 轮播图
     * @param
     * @return
     */
    @PostMapping(path = "/getAllCarousel")  //参数对应上 @PathVariable
    public List<DmImage> queryAllCarousel()  {
        QueryWrapper<DmImage> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("type",1);  //1代表轮播图
        List<DmImage> dmImages = imageDao.selectList(queryWrapper);
        return dmImages;
    }


    /**
     * 获取楼梯
     */
    @PostMapping(path = "/itemfloor/{targetId}")  //参数对应上 @PathVariable
    //		select * from dm_image where type=2 and targetId=3
    public DmImage itemfloor(@PathVariable("targetId") Long targetId)  {
        QueryWrapper<DmImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","5");
//        queryWrapper.eq("category","1");
        queryWrapper.eq("targetId",targetId);
        DmImage dmImage = imageDao.selectOne(queryWrapper);
        return dmImage;
    }
    //查询用户头像
    @PostMapping(path = "/userImage/{itemId}")
    public DmImage getUserImage(@PathVariable("itemId") Long itemId){
        QueryWrapper<DmImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("targetId",itemId);
        queryWrapper.eq("type",0);
        queryWrapper.eq("category",0);
        return imageDao.selectOne(queryWrapper);
    }
}
