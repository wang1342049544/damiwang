package com.hdax.dm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdax.dm.entity.item.DmItemType;

/**
 * 数据访问层的接口
 *
 *
 * 加个泛型 引入实体类 证明依赖没问题
 */
public interface DmltemTypeDao extends BaseMapper<DmItemType> {

}
