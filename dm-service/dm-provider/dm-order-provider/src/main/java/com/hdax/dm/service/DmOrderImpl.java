package com.hdax.dm.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdax.dm.dao.DmOrder;
import com.hdax.dm.dao.DmOrderLike;
import com.hdax.dm.entity.order.DmOrderLinkUser;
import com.hdax.dm.entity.order.OrderConditionVo;
import com.hdax.dm.entity.order.order;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.user.DmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(path = "/order/order")
public class DmOrderImpl {


    @Resource
    private DmOrder dmOrder;
    @Resource
    private DmOrderLike orderLinkUserDao;
    /**
     *  根据订单号查询订单详情
     * @param
     * @return
     */

@PostMapping(path = "/queryorderlist/{keyword}/{orderTime}/{orderType}/{k}/{o}/{t}/{userId1}")
public List<order> queryorderlist(@PathVariable("keyword") String keyword,@PathVariable("orderTime") Integer orderTime ,@PathVariable("orderType") String orderType ,@PathVariable("k") Integer k,@PathVariable("o") Integer o,@PathVariable("t") Integer t,@PathVariable("userId1") Long userId1){
    QueryWrapper<order> queryWrapper = new QueryWrapper<>();

    if (k != -9){
        queryWrapper.eq("orderNo",keyword);
    }
    if (o != -9){
        queryWrapper.eq("orderType",Integer.parseInt(orderType));
    }
    if (t != -9){
        Calendar cal=Calendar.getInstance();
        int y=cal.get(Calendar.YEAR); //年
        int m=(cal.get(Calendar.MONTH));//月
        String ym = String.valueOf(y+"-"+m++);
        System.out.println(m+"vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        if (orderTime == 1){
            queryWrapper.like("createdTime",y);
        } else if (orderTime == 2){
            queryWrapper.like("createdTime",ym);
        }
    }
    queryWrapper.eq("userId",userId1);   //登陆的id条件
    return dmOrder.selectList(queryWrapper);
}


/**
 *
 */
//订单号查询订单
@PostMapping(path = "/orderFindById/{orderNo}")
public order orderFindById(@PathVariable("orderNo") String orderNo){
    QueryWrapper<order> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("orderNo",orderNo);
    return dmOrder.selectOne(queryWrapper);
}

    //添加订单
    @PostMapping(path = "/addOrder")
    public int addOrder(@RequestBody order order){
        return dmOrder.insert(order);
    }
    //添加订单购票人
    @PostMapping(path = "/orderLinkUser/addLinkUser")
    public int addLinkUser(@RequestBody DmOrderLinkUser orderLinkUser){
        return orderLinkUserDao.insert(orderLinkUser);
    }


    @PostMapping("/findByOrderId/{orderId}")
    public order findByOrderId(@PathVariable("orderId")String orderId){
        QueryWrapper<order> wrapper = new QueryWrapper<>();
        wrapper.eq("orderNo",orderId);
        return dmOrder.selectOne(wrapper);
    }

    //订单信息管理
    @PostMapping(path = "/orderInfo")
    public List<order> getOrderInfo(@RequestBody OrderConditionVo orderConditionVo){
        QueryWrapper<order> queryWrapper = new QueryWrapper<>();
        if(orderConditionVo!=null){
            LocalDateTime time = LocalDateTime.now();
          //  queryWrapper.and(wrapper -> wrapper.like("orderNo", orderConditionVo.getKeyword()).or().like("itemName", orderConditionVo.getKeyword()));
            if(orderConditionVo.getOrderTime() !=null &&  orderConditionVo.getOrderTime() == 1){
                queryWrapper.between("createdTime",time.plusYears(-1L),time);
            }else if(orderConditionVo.getOrderTime() !=null &&  orderConditionVo.getOrderTime() == 2){
                queryWrapper.between("createdTime",time.plusMonths(-3L),time);
            }


            if(orderConditionVo.getOrderType()!=null && orderConditionVo.getOrderType() == -1){
                queryWrapper.eq("orderType",orderConditionVo.getOrderType());  //已取消
            }else if(orderConditionVo.getOrderType()!=null && orderConditionVo.getOrderType() == 0){
                queryWrapper.eq("orderType",orderConditionVo.getOrderType());  //待支付
            }else if(orderConditionVo.getOrderType()!=null && orderConditionVo.getOrderType() == 2){
                queryWrapper.eq("orderType",orderConditionVo.getOrderType());  //完成
            }
        }
        return dmOrder.selectList(queryWrapper);
    }


    /**
     * 修改
     */
    @PostMapping("/updateOrderStatus")
    public void updateOrder(@RequestBody order order){
        QueryWrapper<order> wrapper = new QueryWrapper<>();
        wrapper.eq("orderNo",order.getOrderNo());
        dmOrder.update(order,wrapper);
    }

    @PostMapping("/findOrderNo/{orderNo}")
    public List<DmOrderLinkUser> findByOrderId(@PathVariable("orderNo")Long orderNo){
        QueryWrapper<DmOrderLinkUser> wrapper = new QueryWrapper<>();
        wrapper.eq("orderId",orderNo);
        return orderLinkUserDao.selectList(wrapper);
    }


}
