package com.hdax.dm.service;
import com.hdax.dm.client.ImageClient;
import com.hdax.dm.client.ItemClient;
import com.hdax.dm.entity.item.*;
import com.hdax.dm.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 如果在控制器还有一些复杂的逻辑的话， 运算 循环  等等  就不要写到控制器  在消费者里边 增加一个层次  在业务逻辑层完成这个操作
 */
@Service
public class ItemTypeService {

    /**
     * dm-common-client模块的
     */
    @Autowired
    private ItemClient itemClient;
    @Autowired
    private ImageClient imageClient;


    public  CommoResponse<List<ltemTypeResponse>> navList(){
        List<DmItemType> list = itemClient.cha(0L);
        //将DmItemType 转换成 ltemTypeResponse
        List<ltemTypeResponse> itemTypeResponses  = new ArrayList<>();


       list.forEach(itemType->{
            ltemTypeResponse item = new ltemTypeResponse();
            //spring框架提供的
            BeanUtils.copyProperties(itemType,item);

            //查询热门 商品
           List<DmItem> dmItems =itemClient.itemBannerList(itemType.getId());
           List<ItemResponse> hostItems =  new ArrayList<>();
           System.out.println("itemResponse2");
            for(int i=0; i<5;i++){
                DmItem dmItem  = dmItems.get(i);
                //图片
                 DmImage dmImage =  imageClient.getItemImage(dmItem.getId());
                ItemResponse itemResponse  =new ItemResponse();
                //图片加进去
               if(dmImage !=null){
                    itemResponse.setImgUrl(dmImage.getImgUrl());
               }
//                System.out.println("图片 ++++" +dmImage.getImgURL());

                BeanUtils.copyProperties(dmItem,itemResponse);
                hostItems.add(itemResponse);
                System.out.println(itemResponse);
            }
           item.setHotItems(hostItems);


            //查children
            List<DmItemType> childrenList = itemClient.cha( itemType.getId()); // itemType.getId()拿到本身id 我拿到id去查 看谁跟我一样
                List<ltemTypeResponse>  children  =new ArrayList<>();
            childrenList.forEach(child->{
               ltemTypeResponse itemChild = new ltemTypeResponse();
                //spring框架提供的
                BeanUtils.copyProperties(child,itemChild);
               children.add(itemChild);
            });
            item.setChildren(children);
           itemTypeResponses.add(item);
        });
        return VoUtil.returnSuccess("查询成功",itemTypeResponses);
    }

/**
 * 首页导航
 */

public CommoResponse<List<ltemTypeResponse>> lineNav(){

    List<DmItemType> dmItemTypes = itemClient.lineNavList();
    List<ltemTypeResponse> itemTypeResponses = new ArrayList<>();
    dmItemTypes.forEach(dmItem -> {
        ltemTypeResponse itemTypeResponse = new ltemTypeResponse();
        BeanUtils.copyProperties(dmItem,itemTypeResponse);
        itemTypeResponses.add(itemTypeResponse);
    });
    return VoUtil.returnSuccess("首页中间导航栏",itemTypeResponses);
}

/**
 * 楼层
 * @return
 */
public CommoResponse<List<FloorResponse>> floor(){
    List<FloorResponse> result=new ArrayList<>();
    List<DmItemType> oneList = itemClient.cha(0L); //一级分类
    for (int i = 0; i < oneList.size(); i++) {
        if(i>4){
            break;
        }
        FloorResponse floorResponse = new FloorResponse();
        List<DmItem> item2 = itemClient.findByTypeId(oneList.get(i).getId());//前五
        floorResponse.setFloor(i+1);
        floorResponse.setIndex(i+1);
        floorResponse.setItemTypeId(item2.get(1).getItemType1Id());
        floorResponse.setItemTypeName(item2.get(1).getItemType1Name());
        List<ItemsResponse> items=new ArrayList<>();
        item2.forEach(item->{
            ItemsResponse itemsResponse = new ItemsResponse();
            BeanUtils.copyProperties(item,itemsResponse);
            DmCinema cinema = itemClient.findCinemaById(item.getCinemaId());
            DmImage dmImage = imageClient.itemfloor(item.getId());
            if(dmImage!=null){
                itemsResponse.setImgUrl(dmImage.getImgUrl());
            }
            itemsResponse.setAddress(cinema.getAddress());
            itemsResponse.setAreaId(cinema.getAreaId());
            itemsResponse.setEndDate(item.getEndTime().toString());
            itemsResponse.setStartDate(item.getStartTime().toString());
            itemsResponse.setMinPrice(item.getMinPrice());
            items.add(itemsResponse);
        });
        floorResponse.setItems(items);
        result.add(floorResponse);
    }
    return VoUtil.returnSuccess("",result);
}

    /**
     * 热门演出排行
     * @param itemTypeId
     * @return
     */
    public CommoResponse<List<ItemsResponse>> seniority(Long itemTypeId){
        List<ItemsResponse> floorItemsResponses = new ArrayList<>();
        List<DmItem> dmItems = itemClient.seniorityList(itemTypeId);
        dmItems.forEach(item->{
            ItemsResponse floorItemsResponse = new ItemsResponse();
            BeanUtils.copyProperties(item, floorItemsResponse);
            DmCinema cinema = itemClient.findCinemaById(item.getCinemaId());
            floorItemsResponse.setAddress(cinema.getAddress());
            floorItemsResponse.setAreaId(cinema.getAreaId());
            floorItemsResponse.setAreaName(cinema.getAreaName());
            floorItemsResponses.add(floorItemsResponse);
        });
        return VoUtil.returnSuccess("热门演出排行",floorItemsResponses);
    }

    /**
     *  商品详情页
     *
     */
    public CommoResponse<itemDetaileResponse> findItemdesc(Long id){
        //根据商品的id查询商品
        DmItem dmItem = itemClient.findItemDetaileById(id);
        itemDetaileResponse itemDetaileResponse = new itemDetaileResponse();
        BeanUtils.copyProperties(dmItem,itemDetaileResponse);
        //放图片  cineam
        DmImage dmImage = imageClient.getItemImage(dmItem.getId());
        if(dmImage!=null){
            itemDetaileResponse.setImgUrl(dmImage.getImgUrl());
        }else{
            itemDetaileResponse.setImgUrl("static/img/big4.jpg");
        }
        //存放商品详情的地区 剧场
        DmCinema dmCinema = itemClient.findCinemaById(dmItem.getCinemaId());
        itemDetaileResponse.setAreaId(dmCinema.getAreaId());
        itemDetaileResponse.setAreaName(dmCinema.getAreaName());
        return VoUtil.returnSuccess("商品详情查询成功",itemDetaileResponse);
    }

    /**
     * 商品剧评
     * @param id
     * @return
     */

    public CommoResponse<List<ItemCommentResponse>> getComments(Long id){

    List<DmItemComment> dmItemCommentList = itemClient.getComments(id);
    List<ItemCommentResponse> itemCommentResponseList = new ArrayList<>();

    dmItemCommentList.forEach(commen->{
        ItemCommentResponse itemCommentResponse = new ItemCommentResponse();
        BeanUtils.copyProperties(commen,itemCommentResponse);
        itemCommentResponseList.add(itemCommentResponse);
    });
    return VoUtil.returnSuccess("商品剧评查询",itemCommentResponseList);
}


//    //详情热门推荐
//    public CommoResponse<List<ItemRecommendResponse>> getRecommend(Long itemTypeId){
//        List<ItemRecommendResponse> list = new ArrayList<>();
//        List<DmItem> dmItems = itemClient.findByTypeId(itemTypeId);
//        dmItems.forEach(dmItem->{
//            ItemRecommendResponse itemRecommendResponse = new ItemRecommendResponse();
//            itemRecommendResponse.setId(dmItem.getId());
//            itemRecommendResponse.setAddress("成龙耀莱影院");   //节目所在地址
//            itemRecommendResponse.setAreaId(dmItem.getCinemaId());
//            itemRecommendResponse.setAreaName("北京市海淀区五棵松蓝色港湾");  //城市名称
//            itemRecommendResponse.setEndDate(dmItem.getEndTime());
//            itemRecommendResponse.setStartDate(dmItem.getStartTime());
//            DmImage dmImage = imageClient.getItemImage(dmItem.getId());
//         itemRecommendResponse.setImgUrl(dmImage.getImgUrl());  //图片
//            itemRecommendResponse.setItemName(dmItem.getItemName());
//            itemRecommendResponse.setMinPrice(dmItem.getMinPrice());
//
//            list.add(itemRecommendResponse);
//
//        });
//        return VoUtil.returnSuccess("详情热门推荐",list);
//    }









    //热门推荐
    public CommoResponse<List<DmItemsResponse>> getRecommend(Map<String,Long> map){
        Long id = map.get("itemTypeId");
        List<DmItemsResponse> dmItemsResponseList = new ArrayList<>();
        List<DmItem> dmItemList = new ArrayList<>();
        if(id<8){
            dmItemList = itemClient.getRecommend(id);
        } else {
            dmItemList = itemClient.getRecommend2(id);
        }
        dmItemList.forEach(dmItem -> {
            DmItemsResponse dmItemsResponse=new DmItemsResponse();
            dmItemsResponse.setId(dmItem.getId());
            DmImage dmBaseImage = imageClient.getItemImage(dmItem.getId());
            dmItemsResponse.setItemName(dmItem.getItemName());
            if(dmBaseImage!=null){
                dmItemsResponse.setImgUrl(dmBaseImage.getImgUrl());
            }
            DmCinema dmCinemaList = itemClient.getAddress(dmItem.getCinemaId());
            dmItemsResponse.setAreaId(dmCinemaList.getAreaId());
            dmItemsResponse.setAreaName(dmCinemaList.getAreaName());
            dmItemsResponse.setAddress(dmCinemaList.getAddress());
            dmItemsResponse.setStartData(dmItem.getCreatedTime());
            dmItemsResponse.setEndDate(dmItem.getUpdatedTime());
            dmItemsResponse.setMinPrice(dmItem.getMinPrice());
            dmItemsResponseList.add(dmItemsResponse);
        });
        return VoUtil.returnSuccess("0000",dmItemsResponseList);
    }




    //列表页获取市区
    public CommoResponse<List<DmCinemaAreaNameResponse>> getCity(){
        List<DmCinemaAreaNameResponse> cinemaAreaNameResponseList = new ArrayList<>();
        List<DmCinema> cinemaList = itemClient.getCity();
        cinemaList.forEach(dmCinema -> {
            DmCinemaAreaNameResponse cinemaAreaNameResponse = new DmCinemaAreaNameResponse();
            cinemaAreaNameResponse.setId(dmCinema.getAreaId());
            cinemaAreaNameResponse.setName(dmCinema.getAreaName());
            cinemaAreaNameResponseList.add(cinemaAreaNameResponse);
        });
        return VoUtil.returnSuccess("0000",cinemaAreaNameResponseList);
    }



    //列表页获取分类
    public CommoResponse<List<DmItemTypeSortResponse>> getSort(Map<String,Long>map){
        List<DmItemTypeSortResponse> itemTypeSortResponseList = new ArrayList<>();
        List<DmItemType> itemTypeList = new ArrayList<>();
        for (String s : map.keySet()) {
            if("parent".equals(s)){
                Long parent = map.get("parent");
                itemTypeList = itemClient.cha(parent);
            }
            if("param".equals(s)){
                Long param = map.get("param");
                itemTypeList = itemClient.cha(param);
            }
            itemTypeList.forEach(dmItemType -> {
                DmItemTypeSortResponse dmItemTypeSortResponse = new DmItemTypeSortResponse();
                BeanUtils.copyProperties(dmItemType,dmItemTypeSortResponse);
                itemTypeSortResponseList.add(dmItemTypeSortResponse);
            });
        }

        return VoUtil.returnSuccess("0000",itemTypeSortResponseList);
    }




}
