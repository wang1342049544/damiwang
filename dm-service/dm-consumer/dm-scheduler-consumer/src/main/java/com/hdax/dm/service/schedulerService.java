package com.hdax.dm.service;

import com.hdax.dm.client.SchedulerClient;
import com.hdax.dm.entity.scheduler.DmScheduler;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;
import com.hdax.dm.vo.scheduler.paiqi.itemschedulerPriceResponse;
import com.hdax.dm.vo.scheduler.paiqi.itemschedulerResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class schedulerService {

    @Resource
    private SchedulerClient schedulerClient;



    //根据商品id查询桑商品的排期
    public CommoResponse<List<itemschedulerResponse>> findByScheduler(Long itemId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<DmScheduler> dmSchedulerList = schedulerClient.getTimePlanByItemid(itemId);
        List<itemschedulerResponse> itemschedulerResponseList = new ArrayList<>();
        dmSchedulerList.forEach(dmScheduler -> {
            itemschedulerResponse itemschedulerResponse = new itemschedulerResponse();
            itemschedulerResponse.setItemId(dmScheduler.getItemId());
            itemschedulerResponse.setTitle(dmScheduler.getTitle());
            itemschedulerResponse.setStartTime(simpleDateFormat.format(dmScheduler.getStartTime()));
            itemschedulerResponse.setEndTime(simpleDateFormat.format(dmScheduler.getEndTime()));
            itemschedulerResponse.setCinemaId(dmScheduler.getCinemaId());
            itemschedulerResponse.setId(dmScheduler.getId());
            itemschedulerResponseList.add(itemschedulerResponse);
        });
        return VoUtil.returnSuccess("商品排期查询成功",itemschedulerResponseList);
    }

    //根据商排期查询到商品的价格
    public CommoResponse<List<itemschedulerPriceResponse>> getPriceByschedulerId(Long scheduleId){
        List<DmSchedulerSeatPrice> dmSchedulerSeatPriceList = schedulerClient.getPriceByschedulerId(scheduleId);
        List<itemschedulerPriceResponse> itemschedulerPriceResponseList = new ArrayList<>();
        dmSchedulerSeatPriceList.forEach(dmSchedulerSeatPrice -> {
            itemschedulerPriceResponse itemschedulerPriceResponse = new itemschedulerPriceResponse();
            itemschedulerPriceResponse.setId(dmSchedulerSeatPrice.getId());
            itemschedulerPriceResponse.setScheduleId(dmSchedulerSeatPrice.getScheduleId());
            itemschedulerPriceResponse.setIsHaveSeat(dmSchedulerSeatPrice.getSeatNum()>0?1:dmSchedulerSeatPrice.getSeatNum());

            //根据商品的id以及座位的区域查询价格
            DmSchedulerSeatPrice dmSchedulerSeatPrice1 = schedulerClient.findschedulePriceByseat(dmSchedulerSeatPrice.getScheduleId(),
                    dmSchedulerSeatPrice.getAreaLevel());
            itemschedulerPriceResponse.setPrice(dmSchedulerSeatPrice1.getPrice());
            itemschedulerPriceResponseList.add(itemschedulerPriceResponse);
        });
        return VoUtil.returnSuccess("商品排期价格查询成功",itemschedulerPriceResponseList);
    }




}
