package com.hdax.dm.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdax.dm.dao.schedulerSeatDao;
import com.hdax.dm.dao.schedulerSeatPriceDao;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: 5G狼狗
 * @create: 2020-11-13 10:51
 **/
@RestController
@RequestMapping(path = "/scheduler/seat")
public class DmSchedulerSeatServiceImpl {
	@Resource
	private schedulerSeatDao dmSchedulerSeatDao;
    @Resource
    private schedulerSeatPriceDao schedulerSeatPriceDao;
	//找不到
	@PostMapping(path = "/getSchedulerSeatByOrder/{scheduleId}/{x}/{y}")
	public DmSchedulerSeat getSchedulerSeatByOrder(
			@PathVariable("scheduleId")Integer scheduleId,
			@PathVariable("x")Integer x,
			@PathVariable("y")Integer y){
		QueryWrapper<DmSchedulerSeat> wrapper = new QueryWrapper<>();
		if(scheduleId!=null){
			wrapper.eq("scheduleId",scheduleId);
		}
		if(x!=null){
			wrapper.eq("x",scheduleId);
		}
		if(y!=null){
			wrapper.eq("y",scheduleId);
		}
		return dmSchedulerSeatDao.selectOne(wrapper);
	}
	//找不到

	@PostMapping(path = "/modifySchedulerSeat")
	public void modifySchedulerSeat(@RequestBody DmSchedulerSeat schedulerSeat){
		if(schedulerSeat.getId()!=null){
			QueryWrapper<DmSchedulerSeat> wrapper = new QueryWrapper<>();
			wrapper.eq("scheduleId",schedulerSeat.getId());
			dmSchedulerSeatDao.update(schedulerSeat,wrapper);
		}else{
			dmSchedulerSeatDao.insert(schedulerSeat);
		}
	}


	@PostMapping("/findSeatByOrderNo/{orderNo}")
	public List<DmSchedulerSeat> findSeatByOrderNo(@PathVariable("orderNo")String orderNo){
		QueryWrapper<DmSchedulerSeat> wrapper = new QueryWrapper<>();
		wrapper.eq("orderNo",orderNo);
		return dmSchedulerSeatDao.selectList(wrapper);
	}


	@PostMapping("/findSeatScheduleId/{scheduleId}")
	public List<DmSchedulerSeat> findSeatScheduleId(@PathVariable("scheduleId")Long scheduleId){
		QueryWrapper<DmSchedulerSeat> wrapper = new QueryWrapper<>();
		wrapper.eq("scheduleId",scheduleId);
		return dmSchedulerSeatDao.selectList(wrapper);
	}


	@PostMapping("/findByScheduleIds/{scheduleId}")
	public List<DmSchedulerSeat> findByScheduleId(@PathVariable("scheduleId")Long scheduleId){
		QueryWrapper<DmSchedulerSeat> wrapper = new QueryWrapper<>();
		wrapper.eq("scheduleId",scheduleId);
		return dmSchedulerSeatDao.selectList(wrapper);
	}



    //根据X-Y-排期id定位座位
    @PostMapping(path = "/orderSeat")
    public DmSchedulerSeat orderSeat(@RequestBody DmSchedulerSeat dmSchedulerSeat){
        QueryWrapper<DmSchedulerSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("x",dmSchedulerSeat.getX());
        queryWrapper.eq("y",dmSchedulerSeat.getY());
        queryWrapper.eq("scheduleId",dmSchedulerSeat.getScheduleId());
        return dmSchedulerSeatDao.selectOne(queryWrapper);
    }

    //修改排期座位
    @PostMapping(path = "/orderSeatUpdate")
    public int updateOrderSeat(@RequestBody DmSchedulerSeat dmSchedulerSeat){
        return dmSchedulerSeatDao.updateById(dmSchedulerSeat);
    }


    @PostMapping(path = "/ByScheduleIdAndArea")
    public DmSchedulerSeatPrice ByScheduleIdAndArea(@RequestParam("scheduleId") Long scheduleId, @RequestParam("areaLevel")Long areaLevel){
        QueryWrapper<DmSchedulerSeatPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("scheduleId",scheduleId);
        queryWrapper.eq("areaLevel",areaLevel);
        return schedulerSeatPriceDao.selectOne(queryWrapper);
    }

}
