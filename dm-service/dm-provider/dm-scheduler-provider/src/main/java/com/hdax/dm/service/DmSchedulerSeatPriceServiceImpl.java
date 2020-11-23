package com.hdax.dm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.hdax.dm.dao.schedulerSeatPriceDao;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: 5G狼狗
 * @create: 2020-11-13 11:03
 **/
@RestController
@RequestMapping(path = "/scheduler/seat/price")
public class DmSchedulerSeatPriceServiceImpl {


	@Resource
	private schedulerSeatPriceDao schedulerSeatPriceDao;

	@PostMapping(path = "/getSchedulerSeatPriceBySchedulerIdAndArea/{schedulerId}/{areaLevel}")
	public DmSchedulerSeatPrice getSchedulerSeatPriceBySchedulerIdAndArea(
			@PathVariable("schedulerId") Long schedulerId,
			@PathVariable("areaLevel") Long areaLevel) {
		QueryWrapper<DmSchedulerSeatPrice> wrapper = new QueryWrapper<>();
		wrapper.eq("scheduleId", schedulerId);
		wrapper.eq("areaLevel", areaLevel);
		return schedulerSeatPriceDao.selectOne(wrapper);
	}


	@PostMapping("/findByScheduleId/{scheduleId}")
	public List<DmSchedulerSeatPrice> findByScheduleId(@PathVariable("scheduleId") Long scheduleId) {
		QueryWrapper<DmSchedulerSeatPrice> wrapper = new QueryWrapper<>();
		wrapper.eq("scheduleId", scheduleId);
		return schedulerSeatPriceDao.selectList(wrapper);
	}


















	/**
	 * 商品详情页API  根据商品排期查询商品价格
	 *
	 * @param
	 * @return
	 */
	@PostMapping("/queryItemPrice/{scheduleId}")
	public List<DmSchedulerSeatPrice> queryItemPrice(@PathVariable("scheduleId") Integer scheduleId) {
		QueryWrapper<DmSchedulerSeatPrice> wrapper = new QueryWrapper<>();
		wrapper.eq("scheduleId", scheduleId);
		return schedulerSeatPriceDao.selectList(wrapper);
	}

	//商品详情页API  根据商品排期查询商品价格
	@PostMapping("/findBySchedulerId/{scheduleId}/{areaLevel}")
	public DmSchedulerSeatPrice findBySchedulerId(
			@PathVariable("scheduleId") Long scheduleId,
			@PathVariable("areaLevel") Integer areaLevel) {
		QueryWrapper<DmSchedulerSeatPrice> wrapper = new QueryWrapper<>();
		wrapper.eq("scheduleId", scheduleId);
		wrapper.eq("areaLevel", areaLevel);
		return schedulerSeatPriceDao.selectOne(wrapper);
	}
}
