package com.hdax.dm.client;

import com.hdax.dm.entity.item.DmCinema;
import com.hdax.dm.entity.item.DmImage;
import com.hdax.dm.entity.item.DmItem;
import com.hdax.dm.entity.item.DmItemType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient( name = "dm-base-provider")

public interface ImageClient  {

    @PostMapping(path = "/base/image/item/{itemId}")
    DmImage getItemImage(@PathVariable("itemId") Long itemId);

    /**
     * 轮播图
     * @param
     * @return
     */
    @PostMapping("/base/image/getAllCarousel")
    List<DmImage> queryAllCarousel();

    /**
     * 获取今日推荐
     */
    @PostMapping("/base/image/item2/{targetId}")
    DmImage floorImage(@PathVariable("targetId")Long targetId);


    /**
     * 获取即将开售
     */
    @PostMapping("/base/image/item3/{targetId}")
    DmImage kaishou(@PathVariable("targetId")Long targetId);


    /**
     * 获取楼层图片
     */
    @PostMapping("/base/image/itemfloor/{targetId}")
    DmImage itemfloor(@PathVariable("targetId")Long targetId);




    ///4
    @PostMapping(path = "/children/{id}")
    DmItemType findChildren(@PathVariable("id") Long id);
    //
    @PostMapping(path = "/base/image/userImage/{userId}")
    public DmImage getUserImage(@PathVariable("userId") Long userId);


}
