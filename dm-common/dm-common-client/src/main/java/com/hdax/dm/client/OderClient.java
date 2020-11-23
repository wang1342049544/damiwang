package com.hdax.dm.client;

import com.hdax.dm.entity.item.DmItemType;
import com.hdax.dm.entity.order.DmOrderLinkUser;
import com.hdax.dm.entity.order.OrderConditionVo;
import com.hdax.dm.entity.order.order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "dm-order-provider")
public interface OderClient {

//订单管理
@PostMapping(path = "/order/order/queryorderlist/{keyword}/{orderTime}/{orderType}/{k}/{o}/{t}/{userId1}")
public List<order> queryorderlist(@PathVariable("keyword") String keyword,@PathVariable("orderTime") Integer orderTime ,@PathVariable("orderType") String orderType ,@PathVariable("k") Integer k,@PathVariable("o") Integer o,@PathVariable("t") Integer t,@PathVariable("userId1") Long userId1);

    @PostMapping(path = "/order/order/addOrder")
    int addOrder(@RequestBody order order);

    @PostMapping(path = "/order/order/orderFindById/{orderNo}")
    order orderFindById(@PathVariable("orderNo") String orderNo);


    @PostMapping(path = "/order/order/orderLinkUser/addLinkUser")
    int addLinkUser(@RequestBody DmOrderLinkUser orderLinkUser);


    @PostMapping(path = "/order/order/findByOrderId/{orderId}")
    order findByOrderId(@PathVariable("orderId") String orderNo);


    @PostMapping(path = "/order/order/orderInfo")
    List<order> getOrderInfo(@RequestBody OrderConditionVo orderConditionVo);

    @PostMapping("/order/order/updateOrderStatus")
    void updateOrderStatus(@RequestBody order order);


    @PostMapping("/order/order/findOrderNo/{orderNo}")
    List<DmOrderLinkUser> findByOrderId2(@PathVariable("orderNo")Long orderNo);

}
