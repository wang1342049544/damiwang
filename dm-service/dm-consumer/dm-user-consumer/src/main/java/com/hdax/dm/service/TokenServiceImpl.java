package com.hdax.dm.service;

import com.alibaba.fastjson.JSON;
import com.hdax.dm.MD5;
import com.hdax.dm.UserAgentUtil;
import com.hdax.dm.entity.user.DmUser;

import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public String generateToken(DmUser usersVo, String userAgent) throws Exception {
        //token规则:
        // token:PC-234fsd23234-3-jskhdf
        // 终端: PC- / MOBILE
        // 用户信息(phone) 用户唯一信息-
        // 用户id-
        // token生成时间 yyyyMMddHHmmss
        // 验证部分-
        StringBuilder builder = new StringBuilder();
        builder.append("token:");
        //判断终端类型
        UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(userAgent);
        System.out.println(userAgentInfo.getDeviceType());
        if(userAgentInfo.getDeviceType().equals("Personal computer")){
            builder.append("PC-");
        }else if(userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)){
            if (UserAgentUtil.CheckAgent(userAgent)) {
                builder.append("OTHER-");
            }else{
                builder.append("PC-");
            }
        }else{
            builder.append("MOBILE-");
        }
        builder.append(MD5.getMd5(usersVo.getPhone(),32));
        builder.append("-");
        builder.append(usersVo.getId());
        builder.append("-");
        builder.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        builder.append("-");
        builder.append(MD5.getMd5(userAgent,6));
        return builder.toString();
    }

    //保存token到 redis

    public void saveToken(String token, DmUser usersVo) throws Exception {

        String termimal = token.substring(token.indexOf(":")+1,8);
        if (termimal.equals("PC")) {
            redisTemplate.opsForValue().set(token, JSON.toJSONString(usersVo),2 * 60 * 60, TimeUnit.SECONDS);
        }else if (termimal.equals("MOBILE")){
            redisTemplate.opsForValue().set(token, JSON.toJSONString(usersVo));
        }
    }


    public void deleteToken(String token) throws Exception {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        connection.del(stringRedisSerializer.serialize(token));
    }

    //验证token

    public boolean validateToken(String token, String userAgent) throws Exception {
        if (redisTemplate.opsForValue().get(token)==null) {
            return false;
        }else{
            String userAgentMD5 = MD5.getMd5(userAgent,6);
            return token.split("-")[4].equals(userAgentMD5);
        }
    }

    //置换token

    public String replaceToken(String token, String userAgent) throws Exception {
        String newToken = "";
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //判断token是否存在
        if (redisTemplate.opsForValue().get(token) == null) {
            throw new Exception("token过期");
        }else{
            //kjdvsdvjksdv-lock
            if (redisTemplate.opsForValue().get(token+"-lock")!=null) {
                throw new Exception("已经置换过token");
            }
            //token的生成时间
            String dateStr = token.split("-")[3];
            long genTime = connection.ttl(token.getBytes()) * 1000;//redis中key的剩余时间
            //long nowTime = Calendar.getInstance().getTimeInMillis();
            //如果有效期超过30 * 60 * 1000毫秒 不允许置换
            if(genTime > 30 * 60 * 1000){
                throw new Exception("置换时间过短,只有不足30分钟才能置换,剩余时间:"+(genTime/1000 - 30 * 60)+"s");
            }else{
                //获取用户信息
                DmUser usersVo = JSON.parseObject(redisTemplate.opsForValue().get(token).toString(), DmUser.class);
                newToken = generateToken(usersVo,userAgent);
                saveToken(newToken,usersVo);//保存新的token
                connection.setNX((token+"-lock").getBytes(),"lock".getBytes());
                connection.expire((token+"-lock").getBytes(),genTime/1000);//80
            }
        }
        return newToken;
    }


    public DmUser getToken(String token) throws Exception {
        if (redisTemplate.opsForValue().get(token)!=null) {
            DmUser users = new DmUser();
            users = JSON.parseObject(redisTemplate.opsForValue().get(token).toString(), DmUser.class);
            return users;
        }
        return null;
    }
}
