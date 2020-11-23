package com.hdax.dm.service;
import com.hdax.dm.client.ImageClient;
import com.hdax.dm.entity.carouse.Carouse;
import com.hdax.dm.entity.item.DmImage;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 */
@Service
public class CarouseService {

    @Autowired
    private ImageClient imageClient;


    public CommoResponse<List<Carouse>> navList1() {
        List<DmImage> dmImages = null;
        List<Carouse> result = new ArrayList<>();
        try {
            dmImages = imageClient.queryAllCarousel();
            dmImages.forEach(item -> {
                Carouse carouse = new Carouse();
                carouse.setImgUrl(item.getImgUrl());
                carouse.setId(item.getId());
                result.add(carouse);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return VoUtil.returnFail("", "轮播查询失败");
        }
        return VoUtil.returnSuccess("", result);
    }
}