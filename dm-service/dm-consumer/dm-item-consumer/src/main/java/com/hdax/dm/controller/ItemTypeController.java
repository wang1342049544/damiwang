package com.hdax.dm.controller;
import com.hdax.dm.DmEx;
import com.hdax.dm.service.EsService;
import com.hdax.dm.service.ItemTypeService;
import com.hdax.dm.vo.*;
import com.hdax.dm.vo.es.AreaItemVo;
import com.hdax.dm.vo.es.EsItemResponse;
import com.hdax.dm.vo.es.ItemInfoResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class ItemTypeController {

@Autowired
private ItemTypeService itemTypeService;

    @Resource
    private EsService esService;
    /**
     * 首页菜单
     * 熔断器注解fallbackMetho表示一旦出错 错误的出发方法是谁
     * @return
     */
    @PostMapping(path = "/nav")
    @HystrixCommand(fallbackMethod = "navFallback")
    public CommoResponse<List<ltemTypeResponse>> nav()  throws DmEx{
       // throw   new DmEx("1001",":自定义异常");
      return  itemTypeService.navList();
    }


    /**
     * 首页导航
     * @return
     * @throws DmEx
     */
    @PostMapping(path = "/lineNav")
    @HystrixCommand(fallbackMethod = "itemTypeFallback")
    public CommoResponse<List<ltemTypeResponse>> lineNav() throws DmEx{
        return itemTypeService.lineNav();
    }


    /**
     * 楼层
     */
    @PostMapping("/floor")
    public CommoResponse<List<FloorResponse>> floor(){
        return itemTypeService.floor();
    }


/**
 * 热门演出排行
 */
@PostMapping(path = "/seniority")
public CommoResponse<List<ItemsResponse>> seniority(@RequestBody Map<String,String> map) throws DmEx{
    return itemTypeService.seniority(Long.parseLong(map.get("itemTypeId")));
}


    /*商品详情页*/
    @PostMapping(path="/getItems")
    public CommoResponse<itemDetaileResponse> findItemdesc(@RequestBody Map<String,String> map){
        return itemTypeService.findItemdesc(Long.parseLong(map.get("id")));
    }


    //商品剧评
    @PostMapping(path = "/getComments")
    public CommoResponse<List<ItemCommentResponse>> getComments(@RequestBody Map<String,Long> map){
        return itemTypeService.getComments(map.get("id"));
    }

    //添加剧评
    @GetMapping(path = "/getComments")
    public CommoResponse getComments2(@RequestBody Map<String,String> map){
        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
                "uuuuuuuuuuuuuuuuuuuuuuuuu"+map.get("comment")+"iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        return null;
    }


    //详情热门推荐
    @PostMapping(path = "/getRecommend")
    public CommoResponse<List<DmItemsResponse>> getRecommend(@RequestBody Map<String,Long> map){
        return itemTypeService.getRecommend(map);
    }
    //3333
    @PostMapping(path = "/guessLike")
    public CommoResponse<List<DmItemsResponse>> getGuessLike(@RequestBody() Map<String,Long>map){
        return itemTypeService.getRecommend(map);
    }
///////


    @PostMapping(path = "/queryCity")
    public CommoResponse<List<DmCinemaAreaNameResponse>> queryCity(){

        return itemTypeService.getCity();
    }


    @PostMapping(path = "/sortGoods")
    public CommoResponse<List<DmItemTypeSortResponse>> sortGoods(@RequestBody() Map<String,Long>map){
        return itemTypeService.getSort(map);
    }




    //全文检索
    @PostMapping(path = "/queryGoodsInfos")
    public CommoResponse<ItemInfoResponse> es(@RequestBody()EsItemResponse esItemVo){
        return esService.es(esItemVo);
    }

    //商品列表商品条件查询
   // @PostMapping("/queryGoodsInfos")
   // public CommoResponse<AreaItemVo> querygoodsinfos(@RequestBody Map<String,Object> map){
   //     return esService.querygoodsinfos(map);
   // }


}
