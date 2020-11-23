package com.hdax.dm.dao;

import com.hdax.dm.entity.goods.DmEsItem;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DmEsItemDao extends ElasticsearchRepository<DmEsItem,Long> {
}
