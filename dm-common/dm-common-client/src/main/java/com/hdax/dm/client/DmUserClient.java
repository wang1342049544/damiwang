package com.hdax.dm.client;

import com.hdax.dm.entity.user.DmLinkUser;
import com.hdax.dm.entity.user.DmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "dm-user-provider")
@RequestMapping(path = "/user/user")
public interface DmUserClient {
    //查询登陆账号是否存在
    @PostMapping(path = "/valid/{phone}")
    DmUser findUserByPhone(@PathVariable("phone") String phone);

    // 注册手机号，添加手机号信息
    @PostMapping(path = "/insert")
    int addUser(@RequestBody DmUser dmUser);

    //密码登录
    @PostMapping("/login2")
    DmUser login2(@RequestBody DmUser dmUser);






    //根据用户token返回用户信息
    @PostMapping(path = "/querypersoninfo/{userId}")
    public DmUser querypersoninfo(@PathVariable("userId") Long userId);
    //修改用户信息
    @PostMapping(path = "/modifypersoninfo")
    public int modifypersoninfo(@RequestBody DmUser dmUser);
    //查询常用购票人
    @PostMapping(path = "/ticketbuyerlist/{userId}")
    public List<DmLinkUser> ticketbuyerlist(@PathVariable("userId") Long userId);
    //验证购票人是否存在
    @PostMapping(path = "/validatebuyerexist/{idCard}")
    public DmLinkUser validatebuyerexist(@PathVariable("idCard")String idCard);
    //添加购票人
    @PostMapping(path = "addticketbuyer")
    public int addticketbuyer(@RequestBody DmLinkUser dmLinkUser);
    //删除购票人
    @PostMapping(path = "/deleteticketbuyer/{linkId}")
    public int deleteticketbuyer(@PathVariable("linkId")Long linkId);
    //根据Id查询用户
    @PostMapping(path = "/getUserById/{userId}")
    public DmUser getUserById(@PathVariable("userId")Long userId);

    //token获取个人信息
    @PostMapping(path = "/getUserInfo/{id}")
    DmUser getUserInfoById(@PathVariable("id") Long id);
//
//
@PostMapping(path = "/LinkUser/getLinkUserId/{id}")
DmLinkUser getLinkUserId(@PathVariable("id") Long id);


    @PostMapping(path = "/update")
    int updateUser(@RequestBody DmUser dmUser);





//    //查询购票人
//    @PostMapping("/linkUserList/{userId}")
//    List<DmLinkUser> getLinkUser(@PathVariable("userId")Long userId);
//
//    @PostMapping("/addLinkUser")
//    int addLinkUser(@RequestBody DmLinkUser dmLinkUser);
//
//    @PostMapping("/deleteLinkUser/{linkId}")
//    int deleteLinkUser(@PathVariable("linkId")Long linkId);
//
//    @PostMapping("/exist/{idCard}")
//    DmLinkUser ByIdCard(@PathVariable("idCard") String idCard);




}
