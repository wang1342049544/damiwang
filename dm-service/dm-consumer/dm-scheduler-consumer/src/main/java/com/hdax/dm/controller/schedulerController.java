package com.hdax.dm.controller;
import com.hdax.dm.service.SchedulerServiceImpl;
import com.hdax.dm.service.schedulerService;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.juchang.ScheduleIdVo;
import com.hdax.dm.vo.juchang.SchedulerByItemIdVo;
import com.hdax.dm.vo.juchang.SchedulerPriceByItemIdVo;
import com.hdax.dm.vo.scheduler.paiqi.itemschedulerPriceResponse;
import com.hdax.dm.vo.scheduler.paiqi.itemschedulerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class schedulerController {

    @Resource
    private schedulerService schedulerService;
    @Resource
    private SchedulerServiceImpl schedulerServiceImpl;

    //根据商品id得到商品的排期
    @PostMapping(path = "/getTimePlan")
    public CommoResponse<List<itemschedulerResponse>> getTimePlanByItemid(@RequestBody Map<String,String> map){
        return schedulerService.findByScheduler(Long.parseLong(map.get("itemId")));
    }

    //根据商品的id 以及座位查询商品的价格
    @PostMapping(path = "/getPrice")
    public CommoResponse<List<itemschedulerPriceResponse>> getItemPriceByItemareaLevel(@RequestBody Map<String,String> map){
        return schedulerService.getPriceByschedulerId(Long.parseLong(map.get("scheduleId")));
    }





    /**
     * 商品详情页API  根据商品ID查询商品排期
     * @param
     * @return
     */
    @PostMapping("/queryItemScheduler")
    public  CommoResponse<List<SchedulerByItemIdVo>> queryItemScheduler(@RequestBody Map<String,Long> map){
        return  schedulerServiceImpl.queryItemScheduler(map.get("itemId"));
    }

    /**
     * 商品详情页API  根据商品排期查询商品价格
     * @param map
     * @return
     */
    @PostMapping("/queryItemPrice")
    public  CommoResponse<List<SchedulerPriceByItemIdVo>> queryItemPrice(@RequestBody Map<String,Long> map){
        return  schedulerServiceImpl.queryItemPrice(map.get("scheduleId"));
    }


    /**
     * xuanzuo
     * @param map
     * @return
     */

    //  根据排期获取剧场座位信息
    @PostMapping("/getSchedule")
    public  CommoResponse<ScheduleIdVo>  queryCinemaSeatArrayByScheduleId(@RequestBody Map<String,Long> map){
        return  schedulerServiceImpl.queryCinemaSeatArrayByScheduleId(map.get("scheduleId"), map.get("cinemaId"));
    }


    /**
     * 根据剧场Id查询剧场原始座位
     * @param map
     * @return  根据剧场Id查询剧场原始座位
     */
    @PostMapping("/getSeatList")
    public CommoResponse queryOriginalCinemaSeatArray(@RequestBody Map<String,Long> map){
        return  schedulerServiceImpl.queryOriginalCinemaSeatArray(map.get("scheduleId"),map.get("cinemaId"));
    }






}
