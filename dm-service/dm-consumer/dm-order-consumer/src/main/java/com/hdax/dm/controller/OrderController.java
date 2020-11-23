package com.hdax.dm.controller;
import org.springframework.ui.Model;
import com.hdax.dm.entity.order.OrderConditionVo;
import com.hdax.dm.entity.order.order;
import com.hdax.dm.service.OrderService;
import com.hdax.dm.util.AlipayConfig;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    @Autowired
  private OrderService orderService;






    @PostMapping("/submitorder")
    @ResponseBody
    public CommoResponse<String> addOrder(@RequestBody() CreateOrderRequest orderRequest){
        return orderService.addOrder(orderRequest);
    }
//
//
    @PostMapping("/confirmpay")
    @ResponseBody
    public CommoResponse<ByOrderNoVo> getOrderInfo(@RequestBody() Map<String,String> map){
        return orderService.queryOrderByOrderNo(map.get("orderNo").toString());
    }

    @PostMapping("/queryorderlist")
    @ResponseBody
    public CommoResponse<List<OrderInfoResponse>> queryOrderInfo(@RequestBody() OrderConditionVo conditionVo){
        return orderService.queryOrderInfo(conditionVo);
    }

    @RequestMapping("/pay")
    @ResponseBody
    public String pay(@RequestParam("orderNo")  String orderNo)  {
        return orderService.zhiFuBao(orderNo);
    }


    @RequestMapping("/alipayReturnNotice")
    public String shaXiangZhiFu(Model model, HttpServletRequest request, HttpServletResponse response){
        return orderService.alipayReturnNotice(model, request, response);
    }


}
