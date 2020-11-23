package com.hdax.dm.service;

import com.hdax.dm.client.ImageClient;
import com.hdax.dm.client.ItemClient;
import com.hdax.dm.entity.goods.DmEsItem;
import com.hdax.dm.entity.goods.DmEsItemReturn;
import com.hdax.dm.entity.item.DmItem;


import com.hdax.dm.es.Es;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;

import com.hdax.dm.vo.es.*;
import org.checkerframework.checker.units.qual.A;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EsService {
    @Autowired
    private ElasticsearchRestTemplate esTemplate;
    @Resource
    private ImageClient baseClient;

    @Resource
    private ItemClient itemClient;

    //全文检索
    public CommoResponse<ItemInfoResponse> es(EsItemResponse esItemVo){
        NativeSearchQueryBuilder nativeSearchQueryBuilder
                = new NativeSearchQueryBuilder();
        //组合查询 {"bool":{}} //term精确查询 //match匹配度查询 //range 范围查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(esItemVo.getAreaId()!=0){
            boolQueryBuilder.must(QueryBuilders.termQuery("areaid",esItemVo.getAreaId()));
        }
        if(esItemVo.getItemTypeId1()!=0){
            boolQueryBuilder.must(QueryBuilders.termQuery("itemtype1id",esItemVo.getItemTypeId1()));
        }
        if(esItemVo.getItemTypeId2()!=0){
            boolQueryBuilder.must(QueryBuilders.termQuery("itemtype2id",esItemVo.getItemTypeId2()));
        }

        if(esItemVo.getKeyword().trim()!=""){
            boolQueryBuilder.must(QueryBuilders.matchQuery("itemname",esItemVo.getKeyword()));
            //给查询结果“加分”
            boolQueryBuilder.should(QueryBuilders.matchQuery("abstractmessage",esItemVo.getKeyword()));
        }

        if(esItemVo.getStartTime()!="" && esItemVo.getEndTime()!=""){
            //yyyy-MM-DDTHH:mm:ssZ
            boolQueryBuilder.must(QueryBuilders.rangeQuery("starttime").gte(esItemVo.getStartTime()+"'T'00:00:00'Z'"));
            boolQueryBuilder.must(QueryBuilders.rangeQuery("endtime").lte(esItemVo.getStartTime()+"'T'23:59:59'Z'"));
        }
        //排序
        Sort sort = Sort.by(Sort.Order.desc("starttime"));
        if(esItemVo.getSort()!="" && esItemVo.getSort().equals("recommend")){
            sort = Sort.by("isrecommend").descending();
        }
        if(esItemVo.getSort()!="" && esItemVo.getSort().equals("recentSell")){
            sort = Sort.by("createdtime").descending();
        }
        if(esItemVo.getSort()!="" && esItemVo.getSort().equals("recentShow")){
            sort = Sort.by(Sort.Order.desc("starttime"));//Sort.by("starttime").descending();
        }
        Query query = nativeSearchQueryBuilder.withQuery(boolQueryBuilder).build();
        Pageable pageable = PageRequest.of(esItemVo.getCurrentPage()-1,esItemVo.getPageSize(),sort);
        query.setPageable(pageable);

        SearchHits<Es> searchHits = esTemplate.search(query, Es.class);
        ItemInfoResponse itemResponse = new ItemInfoResponse();
        itemResponse.setCurrentPage(esItemVo.getCurrentPage());
        itemResponse.setPageSize(esItemVo.getPageSize());

        int totalCount = (int)searchHits.getTotalHits();

        if(totalCount % itemResponse.getPageSize() == 0){
            itemResponse.setPageCount(totalCount / itemResponse.getPageSize());
        }else{
            itemResponse.setPageCount(totalCount / itemResponse.getPageSize()+1);
        }

        List<ItemRowsResponse> vos = searchHits.stream().map(new Function<SearchHit<Es>, ItemRowsResponse>() {
            @Override
            public ItemRowsResponse apply(SearchHit<Es> itemSearchHit) {
                ItemRowsResponse itemRowsResponse = new ItemRowsResponse();
                itemRowsResponse.setId(itemSearchHit.getContent().getId());
                itemRowsResponse.setItemName(itemSearchHit.getContent().getItemname());
                itemRowsResponse.setAbstractMessage(itemSearchHit.getContent().getAbstractmessage());
                itemRowsResponse.setMinPrice(itemSearchHit.getContent().getMinprice());
                itemRowsResponse.setMaxPrice(itemSearchHit.getContent().getMaxprice());
                itemRowsResponse.setEndTime(itemSearchHit.getContent().getEndtime().toString());
                itemRowsResponse.setStartTime(itemSearchHit.getContent().getStarttime().toString());
                itemRowsResponse.setAddress(itemSearchHit.getContent().getAdress());
                itemRowsResponse.setAreaName(itemSearchHit.getContent().getAreaname());
                itemRowsResponse.setImgUrl(itemSearchHit.getContent().getImgurl());
                itemRowsResponse.setLongitude(itemSearchHit.getContent().getLongitude());
                itemRowsResponse.setLatitude(itemSearchHit.getContent().getLatitude());
                return itemRowsResponse;
            }
        }).collect(Collectors.toList());
        itemResponse.setRows(vos);
        return VoUtil.returnSuccess("0000",itemResponse);
    }


    /**
     *
     */

    public CommoResponse<AreaItemVo> querygoodsinfos(Map<String,Object> map){
        AreaItemVo areaItemVo = new AreaItemVo();
        //itemTypeId1整形商品一级分类ID（全部请传0）
        //temTypeId2 整形 商品二级分类ID（全部请传0）非必填
        //areaId整形城市Id（全部请传0）非必填
        //startTime 字符串 开始时间(年-月-日) 非必填
        //endTime 字符串 结束时间(年-月-日) 非必填
        //sort 字符串 指定排序的字段("recommend"："推荐","recentShow":"最近演出","recentSell"：最近上架)非必填
        //keyword字符串搜索关键词非必填
        DmEsItemReturn querygoodsinfos = itemClient.querygoodsinfos(map);
        areaItemVo.setCurrentPage(Long.valueOf(String.valueOf(map.get("currentPage"))).longValue());//当前页
        areaItemVo.setPageSize((Long.valueOf(String.valueOf(map.get("pageSize"))).longValue()));//每页显示
        areaItemVo.setPageCount(querygoodsinfos.getTotalPages());//总页数
        areaItemVo.setTotal(querygoodsinfos.getTotalElements());//总条数
        List<DmEsItem> list = querygoodsinfos.getList();
        List<AreaItemRowsVo>ItemVo=new ArrayList<>();
        list.forEach(item -> {
            AreaItemRowsVo areaItemRowsVo = new AreaItemRowsVo();
            areaItemRowsVo.setId(item.getId());
            areaItemRowsVo.setImgUrl(item.getImgurl());
            areaItemRowsVo.setItemName(item.getItemname());
            areaItemRowsVo.setAbstractMessage(item.getAbstractmessage());
            areaItemRowsVo.setStartTime(item.getStarttime());
            areaItemRowsVo.setEndTime(item.getEndtime());
            areaItemRowsVo.setMaxPrice(item.getMaxprice());
            areaItemRowsVo.setMinPrice(item.getMinprice());
            areaItemRowsVo.setAdress(item.getAdress());
            areaItemRowsVo.setLongitude(item.getLongitude());
            areaItemRowsVo.setLatitude(item.getLatitude());
            ItemVo.add(areaItemRowsVo);
        });
        areaItemVo.setRows(ItemVo);
        return VoUtil.returnSuccess("",areaItemVo);
    }

}
