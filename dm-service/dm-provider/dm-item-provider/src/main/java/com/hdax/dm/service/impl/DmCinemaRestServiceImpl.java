package com.hdax.dm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdax.dm.dao.DmCinemaDao;
import com.hdax.dm.entity.item.DmCinema;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping(path = {"/item/cinema"})
public class DmCinemaRestServiceImpl {
	@Resource
	private DmCinemaDao dmCinemaDao;


    /**
     * 楼层2
     * 热门排行前父类的id
     * 查询商品详情页下的cinema
     * @param id
     * @return
     */
	@PostMapping("/findById/{id}")
	public DmCinema findCinemaById(@PathVariable("id") Long id){
		QueryWrapper<DmCinema> wrapper = new QueryWrapper<>();
		wrapper.eq("id",id);
		return dmCinemaDao.selectOne(wrapper);
	}



    //列表页获取所有市区
    @PostMapping(path = "/city")
    public List<DmCinema> getCity(){
        QueryWrapper<DmCinema> queryWrapper = new QueryWrapper<>();
        return dmCinemaDao.selectList(queryWrapper);
    }


    //获取地址
    @PostMapping(path = "/address/{cinemaId}")
    public DmCinema getAddress(@PathVariable("cinemaId")Long cinemaId){
        QueryWrapper<DmCinema> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",cinemaId);
        return dmCinemaDao.selectOne(queryWrapper);
    }

}
