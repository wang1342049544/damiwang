package com.hdax.dm.service;



import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hdax.dm.DmEx;
import com.hdax.dm.client.*;
import com.hdax.dm.entity.carouse.Carouse;
import com.hdax.dm.entity.item.DmItem;
import com.hdax.dm.entity.order.DmOrderLinkUser;
import com.hdax.dm.entity.order.OrderConditionVo;
import com.hdax.dm.entity.order.order;
import com.hdax.dm.entity.scheduler.DmSchedulerSeat;
import com.hdax.dm.entity.scheduler.DmSchedulerSeatPrice;
import com.hdax.dm.entity.user.DmLinkUser;
import com.hdax.dm.test.RedisDistributedLock;
import com.hdax.dm.util.AlipayConfig;
import com.hdax.dm.util.IdWorker;
import com.hdax.dm.util.RedisUtil;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;
import com.hdax.dm.vo.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OderClient orderClient;
    @Resource
    private ItemClient itemClient;
    @Resource
    private SchedulerClient scheduleClient;
    @Resource
    private DmUserClient dmUserClient;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    private DefaultRedisScript<Long> script;
    @PostConstruct
    public void init(){
        script = new DefaultRedisScript<Long>();
        script.setResultType(Long.class);
        /**
         *      引入 lua工程配置文件
         */
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("testtSest.lua")));
    }

    public CommoResponse<String> addOrder(CreateOrderRequest orderRequest){
        //查询商品是否存在
        DmItem item = itemClient.findItemDetaileById(orderRequest.getItemId());
        if(item == null){
            System.out.println("商品不存在aaaaaaaaaaaaaaaaaaaaaaaa "+item);
            return VoUtil.returnSuccess("9876","商品不存在");

        }
        Map<String,String> map = new HashMap<>();
        //生成订单编号(分布式中唯一)
        String orderNo = IdWorker.getId();
        map.put("orderNo", orderNo);
        //x1_y1
        String[] seatArrays = orderRequest.getSeatPositions().split(",");
        //所有座位总价格
        String a =null;
        double totalPrice = 0d;
        double[] seatPrices = new double[seatArrays.length];
        //0:没座位 1:有座位 2:锁定待付款 3:已售出
        for (int i = 0; i < seatPrices.length; i++) {
            DmSchedulerSeat dmSchedulerSeat = new DmSchedulerSeat();
            dmSchedulerSeat.setX(Long.parseLong(seatArrays[i].split("_")[0]));
            dmSchedulerSeat.setY(Long.parseLong(seatArrays[i].split("_")[1]));
            dmSchedulerSeat.setScheduleId(orderRequest.getSchedulerId());
            DmSchedulerSeat dmSchedulerSeat1 = scheduleClient.orderSeat(dmSchedulerSeat);


                /**
                 *如果购票人为 null
                 *
                 */

            if(dmSchedulerSeat1.getUserId()!=null ){
               // System.out.println("购票人不能是null  购票人是"+dmSchedulerSeat1.getUserId());
            }else{
               // System.out.println("购票人是null  购票人是"+dmSchedulerSeat1.getUserId());
                return VoUtil.returnSuccess("9875","失败 没有购票人");
            }
            /**
             * 如果座位被购买的话
             * 1没够卖   其他 已经购买
             */

                if(dmSchedulerSeat1.getStatus()!=1L){
                    System.out.println("座位被购买 已经是 "+dmSchedulerSeat1.getStatus());//2
                    return VoUtil.returnSuccess("0000",orderNo);
                }



            //座位可以购买
            //锁定当前座位 2
            /**
             * 明天11.24 我把锁的代码弄到这下便
             */



            dmSchedulerSeat1.setStatus(2L);
            //更新下单用户
            dmSchedulerSeat1.setUserId(orderRequest.getUserId());
            dmSchedulerSeat1.setOrderNo(orderNo);

            //排期座位的修改
            scheduleClient.updateOrderSeat(dmSchedulerSeat1);
            DmSchedulerSeatPrice schedulerSeatPrice = scheduleClient.ByScheduleIdAndArea(orderRequest.getSchedulerId(),
                    dmSchedulerSeat1.getAreaLevel());
            double subTotal = schedulerSeatPrice.getPrice();
            seatPrices[i] = subTotal;
            // totalPrice = subTotal + subTotal;
            totalPrice += seatPrices[i];
            System.out.println(dmSchedulerSeat1);
            System.out.println("订单状态"+dmSchedulerSeat1.getStatus());
            Long k= dmSchedulerSeat1.getStatus();   // key  2
            //现在状态是2
          a =k.toString();

        }
        //订单新增
        order dmOrder = new order();
        dmOrder.setOrderNo(orderNo);//订单编号
        dmOrder.setUserId(orderRequest.getUserId());
        dmOrder.setSchedulerId(orderRequest.getSchedulerId());
        dmOrder.setItemId(orderRequest.getItemId());
        dmOrder.setItemName(item.getItemName());
        dmOrder.setOrderType(0L);
        dmOrder.setPayType("2");
        dmOrder.setSourceType(0L);
        dmOrder.setTotalCount((long)seatArrays.length);
        dmOrder.setIsNeedInsurance(0L);
        dmOrder.setInvoiceType(2L);
        dmOrder.setInvoiceHead("屎壳郎科技有限公司");
        dmOrder.setInvoiceNo("NDLDFSD9999999999");
        dmOrder.setTotalAmount(totalPrice);
        dmOrder.setInsuranceAmount(20D);
        //     dmOrder.setCreatedTime(LocalDateTime.now());
        //如果下单失败, 排期座位需要回滚数据
        orderClient.addOrder(dmOrder);

     /*  String key= a ;
        String value = UUID.randomUUID().toString().replace("-","");
        System.out.println("key是"+key+"value是"+ value);
        *//*
         * setIfAbsent <=> SET key value [NX] [XX] [EX <seconds>] [PX [millseconds]]
         * set expire time 5 mins
         * setnx  ==   SET key value [EX seconds|PX milliseconds] [NX|XX] [KEEPTTL]
             必选参数说明：
             SET：命令
             key：待设置的key
             value：设置的key的value，最好为随机字符串
            可选参数说明：
             NX：表示key不存在时才设置，如果存在则返回 null
             XX：表示key存在时才设置，如果不存在则返回NULL
             PX millseconds：设置过期时间，过期时间精确为毫秒
             EX seconds：设置过期时间，过期时间精确为秒
            有了随机数的 value 后，可以通过判断 key 对应的 value 值是否和指定的值一样，是一样的才能释放锁
         *//*



        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, value, 20000, TimeUnit.MILLISECONDS);
        if (flag) {
            log.info("{} 锁定成功，开始处理业务", key);
            try {
                // 模拟处理业务逻辑
                Thread.sleep(1000 * 10);
                //订单新增
                order dmOrder = new order();
                dmOrder.setOrderNo(orderNo);//订单编号
                dmOrder.setUserId(orderRequest.getUserId());
                dmOrder.setSchedulerId(orderRequest.getSchedulerId());
                dmOrder.setItemId(orderRequest.getItemId());
                dmOrder.setItemName(item.getItemName());
                dmOrder.setOrderType(0L);
                dmOrder.setPayType("2");
                dmOrder.setSourceType(0L);
                dmOrder.setTotalCount((long)seatArrays.length);
                dmOrder.setIsNeedInsurance(0L);
                dmOrder.setInvoiceType(2L);
                dmOrder.setInvoiceHead("屎壳郎科技有限公司");
                dmOrder.setInvoiceNo("NDLDFSD9999999999");
                dmOrder.setTotalAmount(totalPrice);
                dmOrder.setInsuranceAmount(20D);
                //     dmOrder.setCreatedTime(LocalDateTime.now());
                //如果下单失败, 排期座位需要回滚数据
                orderClient.addOrder(dmOrder);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 业务逻辑处理完毕，释放锁
            String lockValue = redisTemplate.opsForValue().get(key).toString();
            if (lockValue != null && lockValue.equals(value)) {
                System.out.println("lockValue========：" + lockValue);
                List<String> keys = new ArrayList<>();
                keys.add(key);
                Long execute = redisTemplate.execute(script, keys, lockValue);
                System.out.println("execute执行结果，1表示执行del，0表示未执行 ===== " + execute);
                log.info("{} 解锁成功，结束处理业务", key);
            }
            System.out.println("订单式+"+orderNo);
            return VoUtil.re("SUCCESS",orderNo);

        } else {
            log.info("{} 获取锁失败", key);
            System.out.println("所");
            return VoUtil.returnSuccess("","");
        }*/



        //订单联系人新增
        //新增订单用户信息
//        System.out.println(orderRequest.getLinkIds());
//        String[] linkIds = orderRequest.getLinkIds().split(",");
//
//        for (int i = 0; i < linkIds.length; i++) {
//            //查询联系人用户信息
//            if(linkIds[i]!=null){
//                DmLinkUser linkUser = dmUserClient.getLinkUserId(Long.parseLong(linkIds[i]));
//                if(linkUser!=null){
//                    DmOrderLinkUser orderLinkUser = new DmOrderLinkUser();
//                    orderLinkUser.setOrderId(Long.parseLong(orderNo));
//                    orderLinkUser.setLinkUserId(Long.parseLong(linkIds[i]));
//                    orderLinkUser.setLinkUserName(linkUser.getName());
//                    orderLinkUser.setPrice(seatPrices[i]);
//                    orderLinkUser.setX(Long.parseLong(seatArrays[i].split("_")[0]));
//                    orderLinkUser.setY(Long.parseLong(seatArrays[i].split("_")[1]));
//                    try {
//                        //添加订单联系人
//                        orderClient.addLinkUser(orderLinkUser);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
       return VoUtil.re("0000",orderNo);
    }










    //重置座位
    private void sendResetSeatMsg(Long scheduleId,String[] seatArrays){
        //将要发送的消息存储在Map集合中
        Map<String,Object> map = new HashMap<>();
        map.put("scheduleId",scheduleId);
        map.put("seatArrays",seatArrays);
        //发送消息到队列 (主题模式)
        //exchange, routingkey(queue)
        rabbitTemplate.convertAndSend("topicExchange",
                "toRestSeatQueue",
                map,
                new MessagePostProcessor() {
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        return message;
                    }
                });
    }


    public CommoResponse<ByOrderNoVo> queryOrderByOrderNo(String orderNo) {
        ByOrderNoVo byorderNoVo=new ByOrderNoVo();

        order order= orderClient.findByOrderId(orderNo);
        if (order != null){
            byorderNoVo.setItemName(order.getItemName());
            byorderNoVo.setPayAmount(order.getTotalAmount());
            byorderNoVo.setSeatCount(order.getTotalCount());
            byorderNoVo.setTotalAmount(order.getTotalAmount());
            byorderNoVo.setOrderNo(orderNo);
        }

        List<DmSchedulerSeat> schedulerSeats=scheduleClient.findSeatByOrderNo(orderNo);
        if(schedulerSeats!=null&&schedulerSeats.size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for(DmSchedulerSeat schedulerSeat:schedulerSeats){
                stringBuilder.append(schedulerSeat.getX()+"_"+schedulerSeat.getY()+",");
            }
            byorderNoVo.setSeatNames(stringBuilder.toString().substring(0,stringBuilder.length()-1));
        }


        return VoUtil.returnSuccess("",byorderNoVo);
    }









    //根据orderNo 查询订单
    public CommoResponse<PayOrderResponse> getOrderInfo(Map<String,String>map){
        String orderNo = map.get("orderNo");
        if (orderNo!=null) {
            order dmOrder = orderClient.orderFindById(orderNo);
            PayOrderResponse payOrderResponse = new PayOrderResponse();
            payOrderResponse.setOrderNo(dmOrder.getOrderNo());
            payOrderResponse.setItemName(dmOrder.getItemName());
            payOrderResponse.setSeatCount(dmOrder.getTotalCount());
            payOrderResponse.setSeatNames("");
            payOrderResponse.setTotalAmount(dmOrder.getTotalAmount());
            return VoUtil.returnSuccess("0000",payOrderResponse);
        } else {
            return VoUtil.returnSuccess("0000","订单信息异常");
        }
    }


    //订单管理
    public CommoResponse<List<OrderInfoResponse>> queryOrderInfo(OrderConditionVo conditionVo){
        List<order> dmOrderList = orderClient.getOrderInfo(conditionVo);
        List<OrderInfoResponse> orderInfoResponseList = new ArrayList<>();
        dmOrderList.forEach(dmOrder -> {
            OrderInfoResponse orderInfoResponse = new OrderInfoResponse();
            orderInfoResponse.setId(dmOrder.getId());
            orderInfoResponse.setItemName(dmOrder.getItemName());
            orderInfoResponse.setNum(dmOrder.getTotalCount());
            orderInfoResponse.setOrderNo(dmOrder.getOrderNo());
            orderInfoResponse.setOrderType(dmOrder.getOrderType());
            orderInfoResponse.setTotalAmount(dmOrder.getTotalAmount());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            orderInfoResponse.setSellTime(df.format(new Date()));
            //根据商品id查询商品单价
            DmItem dmItem = itemClient.findItemDetaileById(dmOrder.getItemId());
            orderInfoResponse.setUnitPrice(dmItem.getMinPrice());
            orderInfoResponseList.add(orderInfoResponse);
        });
        return VoUtil.returnSuccess("0000",orderInfoResponseList);

    }


    //支付
    public String zhiFuBao(String orderNo) {
        StringBuilder sb = null;
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            order order = orderClient.orderFindById(orderNo);
            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderNo() + "\","
                    + "\"total_amount\":\"" + order.getTotalAmount() + "\","
                    + "\"subject\":\"" + order.getItemName() + "\","
                    + "\"body\":\"" + order.getOrderNo() + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            Integer resultLen = result.length();
            sb = new StringBuilder(result);
            sb.delete(resultLen - 109, resultLen - 52);
            sb.insert(resultLen - 109, "<input id=\"submitId\" type=\"submit\" value=\"立即支付\" style=\"display:none\" >");
            System.out.println(sb.toString());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "zhiFuBao";
        }
        return sb.toString();
    }

    public String alipayReturnNotice(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintStream out = System.out;
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                params.put(name, valueStr);
            }

            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            if (signVerified) {
                //商户订单号
                String orderNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), "UTF-8");

                //支付宝交易号
                String tradingNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), "UTF-8");

                order order = orderClient.findByOrderId(orderNo);
                order.setOrderType(2L);
                order.setPayType("2");
                order.setAliTradeNo(tradingNo);//支付宝交易号
                orderClient.updateOrderStatus(order);//修改状态
                Long schedulerId = Long.parseLong(order.getSchedulerId() + "");//排期id
                List<DmOrderLinkUser> byOrderId = orderClient.findByOrderId2(Long.parseLong(order.getId().toString()));//查询座位
                for (DmOrderLinkUser l : byOrderId) { //找到票的 id
                    DmSchedulerSeat seat = scheduleClient.getSchedulerSeatByOrder(schedulerId, l.getX(), l.getY());
                    seat.setStatus(3L);//修改座位状态
                    scheduleClient.modifySchedulerSeat(seat);
                }
            } else {
                out.println("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "alipayReturnNotice方法错误";
        }
        return "redirect:http://localhost:8080/";
    }

}
