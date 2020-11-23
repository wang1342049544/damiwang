package com.hdax.dm.service;
import com.hdax.dm.client.ImageClient;
import com.hdax.dm.client.ItemClient;
import com.hdax.dm.entity.carouse.Carouse;
import com.hdax.dm.entity.item.DmImage;
import com.hdax.dm.entity.item.DmItem;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 今日推荐
 * 即将开售
 */
@Service
public class TodayService {

    @Autowired
    private ImageClient imageClient;
    @Resource
    private ItemClient itemClient;

    //今日推荐
    public CommoResponse<List<Carouse>> recommend(){
        List<Carouse> recommend = new ArrayList<>();
        try {
            //将 6个值 付给recommend1
            List<DmItem>  recommend1 = itemClient.recommend();
            //循环遍历  6个值
            recommend1.forEach(hotItem->{
                Carouse itemResponse = new Carouse();
                //hotItem.getId() 获取6个的 id   赋值给  DmImage
                DmImage imgUrl = imageClient.floorImage(hotItem.getId());

                System.out.println(imgUrl);
                if(imgUrl != null){

                    itemResponse.setImgUrl(imgUrl.getImgUrl());
                }
                BeanUtils.copyProperties(hotItem,itemResponse);
                recommend.add(itemResponse);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return VoUtil.returnFail("", "查询失败");
        }
        return VoUtil.returnSuccess("",recommend);
    }


    /**
     * 即将开售
     * @return
     */
    public CommoResponse<List<Carouse>> kaishou(){
        List<Carouse> recommend = new ArrayList<>();
        try {
            //将 6个值 付给recommend1
            List<DmItem>  recommend1 = itemClient.recommend1();
            //循环遍历  6个值
            recommend1.forEach(hotItem->{
                Carouse itemResponse = new Carouse();
                //hotItem.getId() 获取6个的 id   赋值给  DmImage
                DmImage imgUrl = imageClient.kaishou(hotItem.getId());

                System.out.println(imgUrl);
                if(imgUrl != null){

                    itemResponse.setImgUrl(imgUrl.getImgUrl());
                }
                BeanUtils.copyProperties(hotItem,itemResponse);
                recommend.add(itemResponse);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return VoUtil.returnFail("", "查询失败");
        }
        return VoUtil.returnSuccess("",recommend);
    }


}
