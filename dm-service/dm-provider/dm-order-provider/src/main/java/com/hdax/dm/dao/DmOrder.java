package com.hdax.dm.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdax.dm.entity.order.order;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.user.DmUser;

import java.util.List;

public interface DmOrder extends BaseMapper<order> {

}
