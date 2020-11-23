package com.hdax.dm.controller;
import com.hdax.dm.entity.carouse.Carouse;
import com.hdax.dm.service.CarouseService;
import com.hdax.dm.vo.CommoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 轮播图
 */
@RestController
//@RequestMapping(path = "/api/index")
public class CarouseController {

    @Autowired
    private CarouseService carouseService;


    @PostMapping("/carousel")
    public CommoResponse<List<Carouse>> allCarousel(){
        return carouseService.navList1();
    }


    /**
     * 亲自的轮播
     * @return
     */
    @PostMapping("/getCarouselData")
    public CommoResponse<List<Carouse>> all(){
        return carouseService.navList1();
    }




}
