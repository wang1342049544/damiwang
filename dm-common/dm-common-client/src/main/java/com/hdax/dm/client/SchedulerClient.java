package com.hdax.dm.client;


import com.hdax.dm.entity.scheduler.DmScheduler;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "dm-scheduler-provider")
public interface SchedulerClient {

    /**
     * 排期 点击首页商品列表后,获取商品的图片 id  信息
     * @param itemId
     * @return
     */
    //根据商品id得到商品的排期
    @PostMapping(path = "/scheduler/scheduler/getTimePlan/{itemId}")
    public List<DmScheduler> getTimePlanByItemid(@PathVariable("itemId") Long itemId);

    //根据商品排期得到商品的价格
    @PostMapping(path = "/scheduler/scheduler/getPrice/{scheduleId}")
    public List<DmSchedulerSeatPrice> getPriceByschedulerId(@PathVariable("scheduleId") Long scheduleId);
    //根据商品的排期 以及座位得到商品的价格
    @PostMapping(path = "/scheduler/scheduler/findschedulePriceByseat/{scheduleId}/{areaLevel}")
    public DmSchedulerSeatPrice findschedulePriceByseat(@PathVariable("scheduleId") Long scheduleId,
                                                        @PathVariable("areaLevel") Long areaLevel);


    /**
     *schedule
     * @param
     * @return
     *
     */

    //1 scheduler
    @PostMapping("/scheduler/scheduler/findById/{id}")
    DmScheduler findById(@PathVariable("id")Long id);




    @PostMapping("/scheduler/scheduler/findByItemId/{itemId}")
    List<DmScheduler> findByItemId(@PathVariable("itemId") Long itemId);


    //2 seat
    @PostMapping(path = "/scheduler/seat/getSchedulerSeatByOrder/{scheduleId}/{x}/{y}")
    DmSchedulerSeat getSchedulerSeatByOrder(@PathVariable("scheduleId")Long scheduleId,
                                            @PathVariable("x")Long x,
                                            @PathVariable("y")Long y);

    @PostMapping(path = "/scheduler/seat/modifySchedulerSeat")
    void modifySchedulerSeat(@RequestBody DmSchedulerSeat schedulerSeat);



    @PostMapping("/scheduler/seat/findSeatByOrderNo/{orderNo}")
    List<DmSchedulerSeat> findSeatByOrderNo(@PathVariable("orderNo")String orderNo);






    //3 price

    @PostMapping(path = "/scheduler/seat/price/getSchedulerSeatPriceBySchedulerIdAndArea{schedulerId}/{areaLevel}")
    DmSchedulerSeatPrice getSchedulerSeatPriceBySchedulerIdAndArea(
            @PathVariable("schedulerId")Long schedulerId,
            @PathVariable("areaLevel")Long areaLevel);


    @PostMapping("/scheduler/seat/price/queryItemPrice/{scheduleId}")
    List<DmSchedulerSeatPrice> queryItemPrice(@PathVariable("scheduleId") Long scheduleId);


    @PostMapping("/scheduler/seat/price/findBySchedulerId/{scheduleId}/{areaLevel}")
    DmSchedulerSeatPrice findBySchedulerIdPrice(@PathVariable("scheduleId")Long scheduleId,
                                                @PathVariable("areaLevel")Long areaLevel) ;

/**
 * 选座
 */

@PostMapping("/scheduler/seat/price/findByScheduleId/{scheduleId}")
List<DmSchedulerSeatPrice> findByScheduleIdPrice(@PathVariable("scheduleId")Long scheduleId);

    @PostMapping("/scheduler/seat/findSeatScheduleId/{scheduleId}")
    List<DmSchedulerSeat> findSeatScheduleId(@PathVariable("scheduleId")Long scheduleId);


    //查询  座位
    @PostMapping("/scheduler/seat/findByScheduleIds/{scheduleId}")
    List<DmSchedulerSeat> findByScheduleIdSeat(@PathVariable("scheduleId")Long scheduleId);



////


    @PostMapping(path = "/scheduler/seat/orderSeat")
    DmSchedulerSeat orderSeat(@RequestBody DmSchedulerSeat dmSchedulerSeat);

    @PostMapping(path = "/scheduler/seat/orderSeatUpdate")
    int updateOrderSeat(@RequestBody DmSchedulerSeat dmSchedulerSeat);
    @PostMapping(path = "/scheduler/seat/ByScheduleIdAndArea")
    DmSchedulerSeatPrice ByScheduleIdAndArea(@RequestParam("scheduleId") Long scheduleId, @RequestParam("areaLevel")Long areaLevel);


}
