package com.hdax.dm.controller;
import com.hdax.dm.entity.carouse.Carouse;
import com.hdax.dm.service.TodayService;
import com.hdax.dm.vo.CommoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 今日推荐
 * 即将开售
 */
@RestController
//@RequestMapping(path = "/api/index")
public class TodayController {

    @Autowired
    private TodayService todayService;

    /**
     * 猜你喜欢
     * @return
     */
    @PostMapping(path = "/getGuessYouLike")
    public CommoResponse<List<Carouse>> getGuessYouLike() {
        return todayService.recommend();
    }

    /**
     * 今日推荐
     * @return
     */
    @PostMapping(path = "/recommend")
    public CommoResponse<List<Carouse>> recommend() {
        return todayService.recommend();
    }



    /**
     * 即将开首
     * @return
     */
    @PostMapping(path = "/sell")
    public CommoResponse<List<Carouse>> kaishou() {
        return todayService.kaishou();
    }
}
