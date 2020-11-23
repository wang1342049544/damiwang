package com.hdax.dm.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdax.dm.MD5;
import com.hdax.dm.UserAgentUtil;
import com.hdax.dm.client.DmUserClient;
import com.hdax.dm.entity.user.DmUser;
import com.hdax.dm.service.UserService;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.DmUserResponse;
import com.hdax.dm.vo.VoUtil;
import com.hdax.dm.vo.user.*;
import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //想rbMQ里边发送一个通知  他才能消费
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DmUserClient dmUserClient;

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param map
     * @return
     */
    @PostMapping(path = "/code")
    public CommoResponse code(@RequestBody Map<String,String> map){
        //生成验证码
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        int code = random.nextInt(max);
        if(code < min){
            code = code + min;
        }
        redisTemplate.opsForValue().set("sms_"+map.get("phone"),code+"",1L, TimeUnit.MINUTES);//1分钟 过期，
        Map<String,String> message = new HashMap<>();
        message.put("phone",map.get("phone"));
        message.put("code",code+"");
        rabbitTemplate.convertAndSend("dmsms",message);
        return VoUtil.returnSuccess("短信发送成功",null);
    }

    /**
     * 注册
     * @param map
     * @return
     */
    @PostMapping(path = "/register")
    public CommoResponse register(@RequestBody  Map<String,String>map){
        String phone = map.get("phone");
        String password = map.get("password");
        String code = map.get("vcode");
        if(phone.length()!=11){
            return VoUtil.returnSuccess("1001","手机号格式不正确");
        }
        DmUser dmUser = dmUserClient.findUserByPhone(phone);
        if(dmUser==null){
            String vCode=redisTemplate.opsForValue().get("sms_"+map.get("phone"));
            if(code==null||!code.equals(vCode)){
                return VoUtil.returnSuccess("1004","验证码错误");
            }else{
                DmUser user=new DmUser();
                user.setPhone(phone);
                user.setPassword(MD5.getMd5(password,32));
                user.setNickName("尼古拉斯赵四");
                user.setRealName("尼古拉斯赵四");
                dmUserClient.addUser(user);
            }
        }else {
            return VoUtil.returnSuccess("1003","手机号已被注册");
        }
        return VoUtil.returnSuccess("注册成功",null);
    }

    /**
     *
     * 账号密码登录
     * @param map
     * @param request
     * @return
     */
      @PostMapping(path = "/login")
      public  CommoResponse AccountPasswordLogin(@RequestBody   Map<String,String> map , HttpServletRequest request){
          String phone = map.get("phone");
          String password = map.get("password");
          DmUser dmUser = dmUserClient.findUserByPhone(phone);
          if(!dmUser.getPassword().equals(MD5.getMd5(password,32))){
              return VoUtil.returnSuccess("1006","账号或密码错误");
          }
          StringBuffer tokenBuffer = new StringBuffer("token:PC-");
          Date date = new Date();
          DmUserResponse dmUserResponse = new DmUserResponse();
          BeanUtils.copyProperties(dmUser,dmUserResponse);
          tokenBuffer.append(MD5.getMd5(dmUser.getPhone(),32));
          tokenBuffer.append("-"+dmUser.getId());
          SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
          String dateFormat=format.format(date);
          tokenBuffer.append("-"+dateFormat);
          String userAgent=request.getHeader("User-Agent");
          try {
              UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(userAgent);
              tokenBuffer.append(MD5.getMd5(userAgentInfo.getDeviceType(),12));
          } catch (IOException e) {
              e.printStackTrace();
          }
          dmUserResponse.setUserId(dmUser.getId());
          dmUserResponse.setToken(tokenBuffer.toString());
          try{
              redisTemplate.opsForValue().set(tokenBuffer.toString(),new ObjectMapper().writeValueAsString(dmUser),30, TimeUnit.MINUTES);
          }catch (JsonProcessingException e){
              e.printStackTrace();
          }
          dmUserResponse.setGenTime(date.getTime());
          dmUserResponse.setExtTime(dmUserResponse.getGenTime()+7200000L);
          return VoUtil.returnSuccess("登录成功",dmUserResponse);
      }







    /**
     * 短信登录
     * @param map
     * @param request
     * @return
     */
    @PostMapping(path = "/msg/login")
public CommoResponse login(@RequestBody Map<String,String> map , HttpServletRequest request){
    //手机号
    String phone = map.get("phone");
    //验证码
    String vcode = map.get("vcode");
    /**
     * Token
     */
    StringBuffer tokenBuffer = new StringBuffer("token:PC-");



    if(phone.length() != 11){
        return VoUtil.returnFailure("1001","手机号不能超过11");
    }
    //从redis中获取验证码
    String code = redisTemplate.opsForValue().get("sms_"+map.get("phone"));
    DmUserResponse dmUserResponse = new DmUserResponse();
    //比较
    if(code!=null && code.equals(vcode)){//验证码成功!
        DmUser dmUser = dmUserClient.findUserByPhone(phone);
        if(dmUser == null){
            //自动注册
            DmUser user = new DmUser();
            user.setPhone(phone);
            user.setNickName("尼古拉斯赵四");
            user.setRealName("尼古拉斯赵四");
            dmUserClient.addUser(user);
        }
        BeanUtils.copyProperties(dmUser,dmUserResponse);
        dmUserResponse.setUserId(dmUser.getId());
        /**
         * Token  我们根据的是手机号MD5加密
         */
        tokenBuffer.append( MD5.getMd5(dmUser.getPhone(),32));//拿到32位长度)
        tokenBuffer.append("_"+dmUser.getId());
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
         Date date =   new Date();
        String dateString =format.format(date);
        tokenBuffer.append("_"+dateString);
        String userAgent = request.getHeader("User-Agent");
        try {
        UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(userAgent);
        tokenBuffer.append("_"+MD5.getMd5(userAgentInfo.getDeviceType(),12))    ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        dmUserResponse.setToken(tokenBuffer.toString());
        //存   redis
        try{
            redisTemplate.opsForValue().set(tokenBuffer.toString(),new ObjectMapper().writeValueAsString(dmUser),30,TimeUnit.MINUTES);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        dmUserResponse.setGenTime(date.getTime());
        dmUserResponse.setExtTime(dmUserResponse.getGenTime()+7200000L);
    }
    return VoUtil.returnSuccess("登录成功",dmUserResponse);
}

    /**
     * 获取短信验证码
     * @param map
     * @return
     */
    @PostMapping(path = "/msg")
    public CommoResponse msg(@RequestBody Map<String,String> map){
        //生成验证码
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        int code = random.nextInt(max);
        if(code < min){
            code = code + min;
        }
        //存储验证码 Redis
        //opsForValue 操作的意思
        redisTemplate.opsForValue().set("sms_"+map.get("phone"),code+"",1L, TimeUnit.MINUTES);//1分钟 过期
        //短信通知(异步) Rabbitmq
        //异步  你在网页访问的时候，你点击发送 动态验证码  其他的页面功能能做吗？肯定可以  并不会出现阻塞的情况，
        Map<String,String> message = new HashMap<>();
        message.put("phone",map.get("phone"));
        message.put("code",code+"");
        rabbitTemplate.convertAndSend("dmsms",message);
        return VoUtil.returnSuccess("短信发送成功",null);
        /**
         *   这样我们就像消息队列里边发送了一个map集合  这个map将来会被谁消费？他会有一个专门消费监听器
         *  意思就是 只要我的队列里边 接收到了一条消息，只要处于空先状态，我的监听器就会生效我就会把这条消息消费掉
         *  创建RabbitConsumer
         */

    }


    //个人信息
//    @PostMapping(path = "/querypersoninfo")
//    public CommoResponse<UserInformationResponse> querypersoninfo(@RequestBody Map<String,String> map, HttpServletRequest request){
//        String[] tokens = request.getHeader("token").split("-");
//        Long userId1 = Long.parseLong(tokens[2]);
//        return userService.querypersoninfo(userId1);
//    }
    //修改个人信息
//    @PostMapping(path = "/modifypersoninfo")
//    public CommoResponse modifypersoninfo (@RequestBody UserInformationRequest userInformationRequest, HttpServletRequest request){
//        String[] tokens = request.getHeader("token").split("-");
//        Long userId1 = Long.parseLong(tokens[2]);
//        return userService.modifypersoninfo(userInformationRequest,request,userId1);
//    }
    //选座结账
    @PostMapping(path = "/ticketbuyerlist")
    public CommoResponse<List<UserCommonTicketResponse>> ticketbuyerlist(@RequestBody Map<String,String> map, HttpServletRequest request){
        String[] tokens = request.getHeader("token").split("-");
        Long userId1 = Long.parseLong(tokens[2]);
        return userService.ticketbuyerlist(userId1);
    }

    //验证购票人是否存在
    @PostMapping(path = "/validatebuyerexist")
    public CommoResponse validatebuyerexist(@RequestBody Map<String,String> map){
        return userService.validatebuyerexist(map.get("idCard"));
    }
    //添加购票人
    @PostMapping(path = "/addticketbuyer")
    public CommoResponse addticketbuyer(@RequestBody UserCommonTicketRequest userCommonTicketRequest, HttpServletRequest request){
        String[] tokens = request.getHeader("token").split("-");
        Long userId1 = Long.parseLong(tokens[2]);
        return userService.addticketbuyer(userCommonTicketRequest,request,userId1);
    }
    //删除购票人
    @PostMapping(path = "/deleteticketbuyer")
    public CommoResponse deleteticketbuyer(@RequestBody Map<String,String> map){
        return userService.deleteticketbuyer(Long.parseLong(map.get("linkId")));
    }



//
    //个人信息
    @PostMapping(path = "/querypersoninfo")
    public CommoResponse<DmUserInfoResponse> getUserInfo(HttpServletRequest request){
        return userService.getUserInfo(request);
    }

    //修改信息
    @PostMapping(path = "/modifypersoninfo")
    public CommoResponse updateUser(@RequestBody() Map<String,String>map,HttpServletRequest request){
        return userService.updateUser(map,request);
    }
//
//    //购票人
//    @PostMapping(path = "/ticketbuyerlist")
//    public CommoResponse<List<DmLinkUserResponse>> getLinkUser(HttpServletRequest request){
//        return userService.getLinkUser(request);
//    }
//
//    //添加购票人 addticketbuyer
//    @PostMapping(path = "/addticketbuyer")
//    public CommoResponse addLinkUser(@RequestBody() Map<String,String>map,HttpServletRequest request){
//        return userService.addLinkUser(map, request);
//    }
//
//    //验证购票人是否已经存在  validatebuyerexist
//    @PostMapping(path = "/validatebuyerexist")
//    public CommoResponse ByIdCard(@RequestBody()Map<String,String>map){
//        return userService.ByIdCard(map);
//    }
//
//    //删除购票人  deleteticketbuyer
//    @PostMapping(path = "/deleteticketbuyer")
//    public CommoResponse deleteLinkUser(@RequestBody() Map<String,Long>map){
//        return userService.deleteLinkUser(map);
  //  }














}
