package com.hdax.dm.service.impl;

import com.hdax.dm.dao.DmEsItemDao;
import com.hdax.dm.entity.goods.DmEsItem;
import com.hdax.dm.entity.goods.DmEsItemReturn;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/item/esitem")
public class DmEsItemRestServiceImpl {

  @Resource
  private ElasticsearchRestTemplate esTemplate;   //es内部类


  @PostMapping(path = "/querygoodsinfos")
  public DmEsItemReturn querygoodsinfos(@RequestBody Map<String,Object> map){

  //组合查询 {"bool":{}} //term精确查询 //match匹配度查询 //range 范围查询
    NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

    if(map.get("keyword") != null && map.get("keyword") != ""){
      boolQueryBuilder.must(QueryBuilders.matchQuery("itemname",map.get("keyword").toString()));
      boolQueryBuilder.should(QueryBuilders.matchQuery("abstractmessage",map.get("keyword")));
    }

    if(Long.parseLong(map.get("itemTypeId1").toString())!=0){
      boolQueryBuilder.must(QueryBuilders.termQuery("itemtype1id",map.get("itemTypeId1")));
    }
    if(Long.parseLong(map.get("itemTypeId2").toString())!=0){
      boolQueryBuilder.must(QueryBuilders.termQuery("itemtype2id",map.get("itemTypeId2")));
    }
    if(Long.parseLong(String.valueOf(map.get("areaId"))) !=0 ){
      boolQueryBuilder.must(QueryBuilders.termQuery("areaid",map.get("areaId")));
      System.out.println("剧场"+map.get("areaId"));
    }
    if(!map.get("startTime").equals("") && !map.get("endTime").equals("")){
//
      boolQueryBuilder.must(QueryBuilders.rangeQuery("starttime").gte(map.get("startTime")+"T00:00:00.000Z"))//2018-06-28T10:55:46.000Z
        .must(QueryBuilders.rangeQuery("endtime").lte(map.get("endTime").toString()+"T23:59:59.000Z"));
    }
    Sort sort=Sort.by(Sort.Order.desc("starttime"));  //Sort.by("").descending();//降序
    if(map.get("sort").toString().equals("recommend")){
      sort=Sort.by(Sort.Order.desc("isrecommend"));
    }
    if(map.get("sort").toString().equals("recentShow")){
      sort=Sort.by(Sort.Order.desc("createdtime"));
    }
    if(map.get("sort").toString().equals("recentSell")){
      sort=Sort.by(Sort.Order.desc("starttime"));
    }
    Pageable pageable= PageRequest.of(Integer.parseInt(map.get("currentPage").toString()),Integer.parseInt(map.get("pageSize").toString()),sort);
    NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.withQuery(boolQueryBuilder).build();
    nativeSearchQuery.setPageable(pageable);
    SearchHits<DmEsItem> search = esTemplate.search(nativeSearchQuery, DmEsItem.class);
    long count = search.getTotalHits();

    DmEsItemReturn dmEsItemReturn =new DmEsItemReturn();
    int pageSize = (int) map.get("pageSize");
    List<DmEsItem> list=search.stream().map(new Function<SearchHit<DmEsItem>, DmEsItem>() {
      @Override
      public DmEsItem apply(SearchHit<DmEsItem> dmEsItemSearchHit) {
        return dmEsItemSearchHit.getContent();
      }
    }).collect(Collectors.toList());
    dmEsItemReturn.setList(list);
    dmEsItemReturn.setTotalElements(count);
    dmEsItemReturn.setTotalPages(count/pageSize==0?count/pageSize:count/pageSize+1);

    return dmEsItemReturn;
  }












}
