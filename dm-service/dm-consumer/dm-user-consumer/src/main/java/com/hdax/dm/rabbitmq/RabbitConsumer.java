package com.hdax.dm.rabbitmq;

import com.hdax.dm.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
/**消费监听器
 * 消费监听器
 * 消费监听器
 * 谁发短信都来找我 可以搭设另外的一个微服务
 *   这样我们就像消息队列里边发送了一个map集合  这个map将来会被谁消费？他会有一个专门消费监听器
 *  意思就是 只要我的队列里边 接收到了一条消息，只要处于空先状态，我的监听器就会生效我就会把这条消息消费掉
 *  创建RabbitConsumer
 */
@RabbitListener(queues = {"dmsms"})  //同时监听好多个队列
@Component
public class RabbitConsumer {

    @Resource  //注入短信工具类
    private SmsUtil smsUtil;

    //消费掉  告诉他手机好 验证码 是什么
    @RabbitHandler  //RabbitHandler触发的意思
    public void sendSms(Map<String, String> message){
        smsUtil.sendCode(message.get("phone"), message.get("code"));
    }

}
