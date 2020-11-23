package com.hdax.dm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdax.dm.dao.DmLinkUserDao;
import com.hdax.dm.dao.DmUserDao;
import com.hdax.dm.entity.user.DmLinkUser;
import com.hdax.dm.entity.user.DmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(path = "/user/user")
public class DmUserRestServiceImpl {

    @Autowired
    private DmUserDao dmUserDao;
    @Resource
    private DmLinkUserDao dmLinkUserDao;

    // 注册手机号，添加手机号信息
    @PostMapping(path = "/insert")
    public int addUser(@RequestBody DmUser dmUser){
        return dmUserDao.insert(dmUser);
    }



    //查询登陆账号是否存在
    @PostMapping(path = "/valid/{phone}")
    public DmUser findUserByPhone(@PathVariable("phone") String phone){
        QueryWrapper<DmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);//手机号
        return dmUserDao.selectOne(queryWrapper);
    }



//密码登录
@PostMapping("/login2")
public DmUser login2(@RequestBody DmUser dmUser){
    QueryWrapper<DmUser> queryWrapper = new QueryWrapper();
    queryWrapper.eq("phone",dmUser.getPhone());
    queryWrapper.eq("password",dmUser.getPassword());
    return dmUserDao.selectOne(queryWrapper);
}



    //根据用户token返回用户信息
    @PostMapping(path = "/querypersoninfo/{userId}")
    public DmUser querypersoninfo(@PathVariable("userId")Long userId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",userId);
        return dmUserDao.selectOne(queryWrapper);
    }
    //修改用户信息
    @PostMapping(path = "/modifypersoninfo")
    public int modifypersoninfo(@RequestBody DmUser dmUser){
        return dmUserDao.updateById(dmUser);
    }
    //查询常用购票人
    @PostMapping(path = "/ticketbuyerlist/{userId}")
    public List<DmLinkUser> ticketbuyerlist(@PathVariable("userId") Long userId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId",userId);
        return dmLinkUserDao.selectList(queryWrapper);
    }
    //验证购票人是否存在
    @PostMapping(path = "/validatebuyerexist/{idCard}")
    public DmLinkUser validatebuyerexist(@PathVariable("idCard")String idCard){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("idCard",idCard);
        return dmLinkUserDao.selectOne(queryWrapper);
    }
    //添加购票人
    @PostMapping(path = "/addticketbuyer")
    public int addticketbuyer(@RequestBody DmLinkUser dmLinkUser){
        return dmLinkUserDao.insert(dmLinkUser);
    }
    //删除购票人
    @PostMapping(path = "/deleteticketbuyer/{linkId}")
    public int deleteticketbuyer(@PathVariable("linkId")Long linkId){
        return dmLinkUserDao.deleteById(linkId);
    }
    //根据Id查询用户
    @PostMapping(path = "/getUserById/{userId}")
    public DmUser getUserById(@PathVariable("userId")Long userId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",userId);
        return dmUserDao.selectOne(queryWrapper);
    }


    //查询用户
    @PostMapping(path = "/getUserInfo/{id}")
    public DmUser getUserInfoById(@PathVariable("id") Long id){
        QueryWrapper<DmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return dmUserDao.selectOne(queryWrapper);
    }
    //查询购票人
    @PostMapping(path = "/LinkUser/getLinkUserId/{id}")
    public DmLinkUser getLinkUserId(@PathVariable("id") Long id){
        QueryWrapper<DmLinkUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return dmLinkUserDao.selectOne(queryWrapper);
    }





    //修改
    @PostMapping(path = "/update")
    public int updateUser(@RequestBody DmUser dmUser){
        return dmUserDao.updateById(dmUser);
    }
//
//    //常用购票人
//    @PostMapping("/linkUserList/{userId}")
//    public List<DmLinkUser> getLinkUser(@PathVariable("userId")Long userId){
//        QueryWrapper<DmLinkUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("userId",userId);
//        return linkUserDao.selectList(queryWrapper);
//    }
//
//    //添加购票人
//    @PostMapping("/addLinkUser")
//    public int addLinkUser(@RequestBody DmLinkUser dmLinkUser){
//        return linkUserDao.insert(dmLinkUser);
//    }
//
//    //查询购票人是否存在
//    @PostMapping("/exist/{idCard}")
//    public DmLinkUser ByIdCard(@PathVariable("idCard") String idCard){
//        QueryWrapper<DmLinkUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("idCard",idCard);
//        return linkUserDao.selectOne(queryWrapper);
//    }
//
//    //删除购票人
//    @PostMapping("/deleteLinkUser/{linkId}")
//    public int deleteLinkUser(@PathVariable("linkId")Long linkId){
//        return linkUserDao.deleteById(linkId);
//    }



}
