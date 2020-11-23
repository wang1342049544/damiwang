package com.hdax.dm.client;

import com.hdax.dm.entity.goods.DmEsItemReturn;
import com.hdax.dm.entity.item.DmCinema;
import com.hdax.dm.entity.item.DmItem;
import com.hdax.dm.entity.item.DmItemComment;
import com.hdax.dm.entity.item.DmItemType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 远程调用
 * 调用
 *fallback 熔断第二种方式
 */
@FeignClient(name = "dm-item-provider")
public interface ItemClient {
    @PostMapping(path = "/item/itemType/parent/{parent}")
    public List<DmItemType> cha(@PathVariable("parent") Long parent);


    @PostMapping(path = "/item/item/isBanner/{itemType1Id}")
    public List<DmItem> itemBannerList(@PathVariable("itemType1Id") Long itemType1Id);

    /**
     *今日推荐
     * @return
     */
    @PostMapping(path = "/item/itemType/recommend")
    List<DmItem> recommend();

    /**
     * 即将开首调用的
     */
    @PostMapping(path = "/item/itemType/recommend1")
    List<DmItem> recommend1();


    /**
     * 查询中间导航栏
     */
    @PostMapping(path = "/item/itemType/lineNav")
    public List<DmItemType> lineNavList();





    /**
     * 楼层1
     * 热门推荐
     * @param typeId
     * @return
     */
    @PostMapping(path = "/item/item/findByTypeId/{typeId}")
    List<DmItem> findByTypeId(@PathVariable("typeId") Long typeId);


    /**楼层2
     * 热门排行前父类查询
     * 查询商品下的clone
     * @param id
     * @return
     */
    @PostMapping(path = "/item/cinema/findById/{id}")
    DmCinema findCinemaById(@PathVariable("id") Long id);



    /**
     * 热门排行2
     * @param itemTypeId
     * @return
     */
    @PostMapping(path = "/item/item/seniority/{itemTypeId}")
    public List<DmItem> seniorityList(@PathVariable("itemTypeId")Long itemTypeId);


    /**
     * 商品详情页
     */
    @PostMapping(path = "/item/item/descitem/{id}")
    public DmItem findItemDetaileById(@PathVariable("id")Long id);

    /**
     * 商品剧评
     * @param id
     * @return
     */
    @PostMapping(path = "/item/Comments/getComments/{id}")
    List<DmItemComment> getComments(@PathVariable("id")Long id);

    /**
     * 点击下单
     * @param id
     * @return
     */
    @PostMapping(path = "/item/item/findItemById/{id}")
    DmItem findItemById (@PathVariable("id")Long id);



    //111
    @PostMapping(path = "/item/cinema/city")
    List<DmCinema> getCity();

    //3333
    ///333
    @PostMapping(path = "/item/item/getRecommend/{id}")
    List<DmItem> getRecommend(@PathVariable("id") Long id);




    @PostMapping(path = "/item/item/getRecommend2/{id}")
    List<DmItem> getRecommend2(@PathVariable("id") Long id); //有问题没解决




    @PostMapping(path = "/item/item/aaa/{itemType1Id}")
    List<DmItem> aaa(@PathVariable("itemType1Id") Long itemType1Id); //有问题没解决


    //4144444
    //es querygoodsinfos
    @PostMapping("/item/esitem/querygoodsinfos")
    DmEsItemReturn querygoodsinfos(Map<String,Object> map);

    //获取地址
    @PostMapping(path = "/item/cinema/address/{cinemaId}")
    DmCinema getAddress(@PathVariable("cinemaId")Long cinemaId);


}
