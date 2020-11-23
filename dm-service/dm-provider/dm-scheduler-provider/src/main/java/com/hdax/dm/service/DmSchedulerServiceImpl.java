package com.hdax.dm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.hdax.dm.dao.schedulerDao;
import com.hdax.dm.dao.schedulerSeatDao;
import com.hdax.dm.dao.schedulerSeatPriceDao;
import com.hdax.dm.entity.scheduler.DmScheduler;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping(path = "/scheduler/scheduler")
public class DmSchedulerServiceImpl {

    @Resource
    private schedulerDao schedulerDao;

    @Resource
    private schedulerSeatPriceDao schedulerSeatPriceDao;

    @Resource
    private schedulerSeatDao schedulerSeatDao;

    /**
     * 排期 点击首页商品列表后,获取商品的图片 id  信息
     * @param itemId
     * @return
     */

    //根据商品id得到商品的排期
        @PostMapping(path = "/getTimePlan/{itemId}")
        public List<DmScheduler> getTimePlanByItemid(@PathVariable("itemId") Long itemId) {
            QueryWrapper<DmScheduler> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("itemId", itemId);
            return schedulerDao.selectList(queryWrapper);
        }

        //根据商品排期得到商品的价格
        @PostMapping(path = "/getPrice/{scheduleId}")
        public List<DmSchedulerSeatPrice> getPriceByschedulerId(@PathVariable("scheduleId") Long scheduleId){
            QueryWrapper<DmSchedulerSeatPrice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("scheduleId",scheduleId);
            return schedulerSeatPriceDao.selectList(queryWrapper);
        }


        //根据商品的排期 以及座位得到商品的价格
        @PostMapping(path = "/findschedulePriceByseat/{scheduleId}/{areaLevel}")
        public DmSchedulerSeatPrice findschedulePriceByseat(@PathVariable("scheduleId") Long scheduleId
                , @PathVariable("areaLevel")Long areaLevel){
            QueryWrapper<DmSchedulerSeatPrice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("scheduleId",scheduleId);
            queryWrapper.eq("areaLevel",areaLevel);
        return schedulerSeatPriceDao.selectOne(queryWrapper);
    }
    /**
     * scheduler
     * @param
     * @return
     */

    @PostMapping("/findById/{id}")
    public DmScheduler findById(@PathVariable("id")Long id){
        QueryWrapper<DmScheduler> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return schedulerDao.selectOne(wrapper);
    }





    @PostMapping("/findByItemId/{itemId}")
    public List<DmScheduler> findByItemId(@PathVariable("itemId") Long itemId){
        QueryWrapper<DmScheduler> wrapper = new QueryWrapper<>();
        wrapper.eq("itemId",itemId);
        return schedulerDao.selectList(wrapper);
    }


/**
 *选座
 */







}
