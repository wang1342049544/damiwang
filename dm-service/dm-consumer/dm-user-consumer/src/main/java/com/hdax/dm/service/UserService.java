package com.hdax.dm.service;

import com.aliyuncs.CommonResponse;
import com.hdax.dm.client.DmUserClient;
import com.hdax.dm.client.ImageClient;
import com.hdax.dm.entity.item.DmImage;
import com.hdax.dm.entity.user.DmLinkUser;
import com.hdax.dm.entity.user.DmUser;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.VoUtil;
import com.hdax.dm.vo.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private DmUserClient dmUserClient;

    @Resource
    private ImageClient baseClient;
    @Resource
    private TokenServiceImpl tokenService;
    //个人信息
    public CommoResponse<UserInformationResponse> querypersoninfo(Long userId){
        UserInformationResponse userInformationResponse = new UserInformationResponse();
        DmUser querypersoninfo = dmUserClient.querypersoninfo(userId);
        BeanUtils.copyProperties(querypersoninfo,userInformationResponse);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String birthday = df.format(querypersoninfo.getBirthday());
        userInformationResponse.setBirthday(birthday);
        DmImage userImage = baseClient.getUserImage(userId);
        if (userImage != null){
            userInformationResponse.setImgUrl(userImage.getImgUrl());
        }else{
            userInformationResponse.setImgUrl("/static/img/img_01.21e9c6f.png");

        }

        return VoUtil.returnSuccess("个人信息",userInformationResponse);
    }

    //修改用户信息
    public CommoResponse modifypersoninfo(@RequestBody UserInformationRequest userInformationRequest, HttpServletRequest request, Long userId1){
        DmUser user = dmUserClient.getUserById(userId1);
        if (user != null){
            DmUser dmUser = new DmUser();
            dmUser.setNickName(userInformationRequest.getNickName());
            dmUser.setRealName(userInformationRequest.getRealName());
            dmUser.setPhone(userInformationRequest.getPhone());
            dmUser.setHobby(userInformationRequest.getHobby());
            dmUser.setSex(userInformationRequest.getSex());
            dmUser.setHobby(userInformationRequest.getHobby());
            dmUser.setIdCard(userInformationRequest.getIdCard());
//      dmUser.setCreatedTime(new DateTime());
            dmUser.setId(userId1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDateTime birthday = LocalDateTime.parse(userInformationRequest.getBirthday()+"",df);
                Date date = format.parse(userInformationRequest.getBirthday());
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime birthday = instant.atZone(zoneId).toLocalDateTime();
                Date date1 = new Date();
                LocalDateTime updateTime = instant.atZone(zoneId).toLocalDateTime();
                dmUser.setBirthday(birthday);
                dmUser.setUpdatedTime(updateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dmUserClient.modifypersoninfo(dmUser);
        }
        return VoUtil.returnSuccess("修改用户信息",null);
    }

    //常用购票人
    public CommoResponse<List<UserCommonTicketResponse>> ticketbuyerlist(Long userId){
        List<DmLinkUser> ticketbuyerlist = dmUserClient.ticketbuyerlist(userId);
        List<UserCommonTicketResponse> userCommonTicketResponses = new ArrayList<>();
        ticketbuyerlist.forEach(ticke->{
            UserCommonTicketResponse userCommonTicketResponse = new UserCommonTicketResponse();
            BeanUtils.copyProperties(ticke,userCommonTicketResponse);
            userCommonTicketResponse.setLinkId(ticke.getId());
            userCommonTicketResponses.add(userCommonTicketResponse);
        });
        return VoUtil.returnSuccess("常用购票人",userCommonTicketResponses);
    }
    //验证购票人是否存在
    public CommoResponse validatebuyerexist(String idCard){
        DmLinkUser dmLinkUser = dmUserClient.validatebuyerexist(idCard);
        if (dmLinkUser != null){
            return VoUtil.returnFailure("1009",null);
        }else {
            return VoUtil.returnSuccess("验证购票人",dmLinkUser);
        }
    }
    //添加购票人
    public CommoResponse addticketbuyer(@RequestBody UserCommonTicketRequest userCommonTicketRequest, HttpServletRequest request,Long userId1){
        DmLinkUser dmLinkUser = new DmLinkUser();
        dmLinkUser.setIdCard(userCommonTicketRequest.getIdCard());
        dmLinkUser.setName(userCommonTicketRequest.getName());
        dmLinkUser.setCardType(Long.parseLong(userCommonTicketRequest.getCardType()));
        DmUser token = null;
        try {
            token = tokenService.getToken(request.getHeader("token"));
            dmLinkUser.setUserId(token.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dmLinkUser.setUserId(userId1);
        dmUserClient.addticketbuyer(dmLinkUser);
        return VoUtil.returnSuccess("添加购票人",null);
    }

    //删除购票人
    @PostMapping(path = "/deleteticketbuyer/linkId")
    public CommoResponse deleteticketbuyer(Long linkId){
        dmUserClient.deleteticketbuyer(linkId);
        return VoUtil.returnSuccess("删除购票人",null);
    }






    //token获取个人信息
    public CommoResponse<DmUserInfoResponse> getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        Long id = Long.parseLong(token.split("-")[2]);
        System.out.println(id);
        DmUser dmUser = dmUserClient.getUserInfoById(id);
        DmUserInfoResponse dmUserInfoResponse = new DmUserInfoResponse();
        dmUserInfoResponse.setInterest(dmUser.getHobby());
        //日期
        BeanUtils.copyProperties(dmUser,dmUserInfoResponse);
        return VoUtil.returnSuccess("0000",dmUserInfoResponse);
    }
//
    //修改信息
    public CommoResponse updateUser(Map<String,String> map, HttpServletRequest request){
        String token = request.getHeader("token");
        Long id = Long.parseLong(token.split("-")[2]);
        String nickName = map.get("nickName");
        String phone =  map.get("phone");
        String idCard = map.get("idCard");
        String imgUrl = map.get("imgUrl");
        String birthday = map.get("birthday");
        String realName = map.get("realName");
        String sex = map.get("sex");
        Long s=Long.parseLong(sex);
        String hobby = map.get("interest");
        DmUser dmUser = new DmUser();
        dmUser.setNickName(nickName);
        dmUser.setPhone(phone);
        dmUser.setRealName(realName);
        try {
            if(birthday!=null){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(birthday);
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime birthday1 = instant.atZone(zoneId).toLocalDateTime();
                dmUser.setBirthday(birthday1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dmUser.setHobby(hobby);
        dmUser.setIdCard(idCard);
        dmUser.setSex(s);
        dmUser.setId(id);
        if(dmUserClient.updateUser(dmUser)==1){
            return VoUtil.returnSuccess("0000",null);
        } else {
            return VoUtil.returnSuccess("1010","修改失败");
        }
    }
//    //查询购票人
//    public CommoResponse<List<DmLinkUserResponse>> getLinkUser(HttpServletRequest request){
//        List<DmLinkUserResponse> linkUserResponseList =new ArrayList<>();
//        String token = request.getHeader("token");
//        Long id = Long.parseLong(token.split("-")[2]);
//        System.out.println(id);
//        List<DmLinkUser> linkUserList = dmUserClient.getLinkUser(id);
//        linkUserList.forEach( dmLinkUser -> {
//            DmLinkUserResponse linkUserResponse = new DmLinkUserResponse();
//            linkUserResponse.setLinkId(dmLinkUser.getId());
//            if(dmLinkUser.getCardType()==0){
//                linkUserResponse.setCardType("身份证");
//            }
//            if(dmLinkUser.getCardType()==1){
//                linkUserResponse.setCardType("军官证");
//            }
//            BeanUtils.copyProperties(dmLinkUser,linkUserResponse);
//            linkUserResponseList.add(linkUserResponse);
//        });
//        return VoUtil.returnSuccess("0000",linkUserResponseList);
//    }
//    //添加购票人
//    public CommoResponse addLinkUser(@RequestBody() Map<String,String>map, HttpServletRequest request){
//        String token = request.getHeader("token");
//        Long id = Long.parseLong(token.split("-")[2]);
//        String name = map.get("name");
//        String idCard = map.get("idCard");
//        String cardType = map.get("cardType");
//        DmLinkUser dmLinkUser = new DmLinkUser();
//        dmLinkUser.setIdCard(idCard);
//        dmLinkUser.setName(name);
//        dmLinkUser.setUserId(id);
//        Date todayDate = new Date();
//        LocalDateTime ldt = todayDate.toInstant()
//                .atZone( ZoneId.systemDefault() )
//                .toLocalDateTime();
//        dmLinkUser.setCreatedTime(ldt);
//        if(cardType.equals("身份证")){
//            dmLinkUser.setCardType(0L);
//        } else if(cardType.equals("军官证")){
//            dmLinkUser.setCardType(1L);
//        }
//        if(dmUserClient.addLinkUser(dmLinkUser)==1){
//            return VoUtil.returnSuccess("0000",null);
//        } else {
//            return VoUtil.returnSuccess("1001","添加失败");
//        }
//    }
//
//    //验证购票人是否存在
//    public CommoResponse ByIdCard(@RequestBody()Map<String,String>map){
//        String idCard = map.get("idCard");
//        DmLinkUser dmLinkUser= dmUserClient.ByIdCard(idCard);
//        if(dmLinkUser!=null){
//            return VoUtil.returnSuccess("1009","购票人已存在");
//        }else {
//            return VoUtil.returnSuccess("0000","购票人不存在");
//        }
//    }
//
//    //删除购票人
//    public CommoResponse deleteLinkUser(@RequestBody() Map<String,Long>map){
//        Long linkId = map.get("linkId");
//        int i = dmUserClient.deleteLinkUser(linkId);
//        if(i>0){
//            return VoUtil.returnSuccess("1010","联系人不存在");
//        } else {
//            return VoUtil.returnSuccess("0000",null);
//        }
//    }
}
