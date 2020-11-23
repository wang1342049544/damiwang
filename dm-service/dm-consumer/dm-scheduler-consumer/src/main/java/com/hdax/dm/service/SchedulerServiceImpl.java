package com.hdax.dm.service;
import com.hdax.dm.client.SchedulerClient;
import com.hdax.dm.entity.scheduler.DmScheduler;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;
import com.hdax.dm.vo.juchang.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerServiceImpl {



    @Resource
    private SchedulerClient schedulerClient;
    /**
     * 根据排期获取剧场座位信息
     * @param scheduleId
     * @param cinemaId
     * @return
     */

    public CommoResponse<ScheduleIdVo> queryCinemaSeatArrayByScheduleId(Long scheduleId, Long cinemaId) {
        DmScheduler scheduler = schedulerClient.findById(scheduleId);
        ScheduleIdVo scheduleIdVo=new ScheduleIdVo();
        scheduleIdVo.setCinemaId(scheduler.getCinemaId());
        scheduleIdVo.setScheduleId(scheduleId);

        List<DmSchedulerSeatPrice> schedulerSeats=schedulerClient.findByScheduleIdPrice(scheduleId);
        List<SeatPriceListVo> seatPriceListVos=new ArrayList<>();
        for(DmSchedulerSeatPrice schedulerSeatPrice:schedulerSeats){
            SeatPriceListVo seatPriceListVo=new SeatPriceListVo();
            String state="";
            if(schedulerSeatPrice.getAreaLevel()==1){
                state="A";
            }else if(schedulerSeatPrice.getAreaLevel()==2){
                state="B";
            }else if(schedulerSeatPrice.getAreaLevel()==3){
                state="C";
            }
            seatPriceListVo.setAreaLevelName(state);
            seatPriceListVo.setPrice(schedulerSeatPrice.getPrice());
            seatPriceListVos.add(seatPriceListVo);
        }

        List<DmSchedulerSeat> list=schedulerClient.findSeatScheduleId(scheduleId);
        List<SeatInfoListVo> listVos=new ArrayList<>();
        for(DmSchedulerSeat schedulerSeat:list){
            SeatInfoListVo seatInfoListVo=new SeatInfoListVo();
            BeanUtils.copyProperties(schedulerSeat,seatInfoListVo);
            seatInfoListVo.setId(schedulerSeat.getId());
            seatInfoListVo.setCinemaId(cinemaId);
            String state="_";
            if(schedulerSeat.getAreaLevel()==1){
                state="A";
            }else if(schedulerSeat.getAreaLevel()==2){
                state="B";
            } else if (schedulerSeat.getAreaLevel()==3){
                state="C";
            }
            seatInfoListVo.setAreaLevel(state);
            seatInfoListVo.setId(schedulerSeat.getScheduleId());
            seatInfoListVo.setStatus(schedulerSeat.getStatus());
            listVos.add(seatInfoListVo);
        }
        scheduleIdVo.setSeatInfoList(listVos);
        scheduleIdVo.setSeatPriceList(seatPriceListVos);
        return VoUtil.returnSuccess("",scheduleIdVo);
    }

    /**
     *根据剧场Id查询剧场原始座位
     * @param scheduleId
     * @param cinemaId
     * @return
     */

    public CommoResponse queryOriginalCinemaSeatArray(Long scheduleId, Long cinemaId) {
        List<DmSchedulerSeat> cinemaSeats=schedulerClient.findByScheduleIdSeat(scheduleId);
        List<String> list=new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();
        int count=1;
        for(DmSchedulerSeat cinemaSeat:cinemaSeats){
            count++;
            if(cinemaSeat.getAreaLevel()==1){
                stringBuilder.append("a");
            }else if(cinemaSeat.getAreaLevel()==2){
                stringBuilder.append("b");
            }else if(cinemaSeat.getAreaLevel()==3){
                stringBuilder.append("c");
            }
            if(count==14){
                list.add(stringBuilder.toString());
                count=1;
                stringBuilder.setLength(0);
            }
        }
        if(stringBuilder.length()>0){
            list.add(stringBuilder.toString());
        }
        ScheduleIdSeat scheduleIdSeat=new ScheduleIdSeat();
        scheduleIdSeat.setSeatArray(list);
        scheduleIdSeat.setCinemaId(cinemaId);
        return VoUtil.returnSuccess("查询剧场原始座位成功",scheduleIdSeat);
    }


















    /**
     * 商品详情页API 根据商品ID查询商品排期
     * @param
     * @return
     */
    public CommoResponse<List<SchedulerByItemIdVo>> queryItemScheduler(Long itemId) {
        List<DmScheduler> schedulers= schedulerClient.findByItemId(itemId);
        List<SchedulerByItemIdVo> schedulerByItemIdVos=new ArrayList<>();
        for(DmScheduler scheduler:schedulers){
            SchedulerByItemIdVo schedulerByItemIdVo=new SchedulerByItemIdVo(scheduler.getId(),
                    scheduler.getTitle(),scheduler.getItemId(),scheduler.getStartTime(),
                    scheduler.getEndTime(),scheduler.getCinemaId());
            schedulerByItemIdVos.add(schedulerByItemIdVo);

        }
        return VoUtil.returnSuccess("",schedulerByItemIdVos);
    }
    /**
     * 商品详情页API  根据商品排期查询商品价格
     * @param
     * @return
     */
    public CommoResponse<List<SchedulerPriceByItemIdVo>> queryItemPrice(Long scheduleId) {
        List<DmSchedulerSeatPrice> scheduler=schedulerClient.queryItemPrice(scheduleId);
        List<SchedulerPriceByItemIdVo> priceByItemIdVos=new ArrayList<>();
        for(DmSchedulerSeatPrice schedulerSeat:scheduler){
            SchedulerPriceByItemIdVo schedulerPriceByItemIdVo=new SchedulerPriceByItemIdVo();
            schedulerPriceByItemIdVo.setId(schedulerSeat.getId());
            schedulerPriceByItemIdVo.setScheduleId(schedulerSeat.getScheduleId());
            schedulerPriceByItemIdVo.setIsHaveSeat(schedulerSeat.getSeatNum()>0?1:schedulerSeat.getSeatNum());
            DmSchedulerSeatPrice schedulerSeatPrice1= schedulerClient.findBySchedulerIdPrice(schedulerSeat.getScheduleId(),schedulerSeat.getAreaLevel()==0?1:schedulerSeat.getAreaLevel());
            schedulerPriceByItemIdVo.setPrice(schedulerSeatPrice1.getPrice());
            priceByItemIdVos.add(schedulerPriceByItemIdVo);
        }
        return VoUtil.returnSuccess("",priceByItemIdVos);
    }

}
