package com.xd.kobepgateway.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import com.xd.kobepgateway.constant.Oauth2Constant;
import com.xd.kobepgateway.constant.RedisPrefixConstant;
import com.xd.kobepgateway.vo.JwtParseInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * @description: jwt解析工具类
 **/
/*
public final class JwtUtil {

    private static Logger log = LoggerFactory.getLogger(JwtUtil.class);


    //jwt解析
    public static JwtParseInfoVo jwtParseToBean(String token) {

        try {
            String accessToken = null;
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            if (token.startsWith(Oauth2Constant.HEAR_BEARER_PREFIX)){
                String[] jwtArr = token.split(" ");
                if (jwtArr.length > 2){
                    return null;
                }
                accessToken = jwtArr[1];
            }else {
                accessToken = token;
            }
            Jwt jwt = JwtHelper.decode(accessToken);
            String jwtStr = jwt.getClaims();
            JSONObject jsonObject = JSONObject.parseObject(jwtStr);
            JwtParseInfoVo jwtParseInfoVo = JSON.parseObject(jsonObject.toJSONString(), JwtParseInfoVo.class);
            return jwtParseInfoVo;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    //jwt存储在redis
    public static boolean jwtToRedis(RedisTemplate redisTemplate, JwtParseInfoVo jwtParseInfoVo, String token) {

        try {
            if (redisTemplate instanceof RedisTemplate && redisTemplate != null) {
                Long userId = jwtParseInfoVo.getUserId();
                String jtl = jwtParseInfoVo.getJti();
                String exp = jwtParseInfoVo.getExp();
                //缓存redis的时间
                Long expRedis = null;
                //获取当前时间秒数
                Long milliSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                expRedis = Long.valueOf(exp) - milliSecond;
                if (expRedis < 0) {
                    return false;
                }
                String key = String.format("%s:%s:%s", RedisPrefixConstant.JWT_USER_TOKEN,userId,jtl);
                String tokenRedis = (String) redisTemplate.opsForValue().get(key);
                if (!StringUtils.isEmpty(tokenRedis)) {
                    redisTemplate.delete(key);
                }
                redisTemplate.opsForValue().set(key, token, expRedis, TimeUnit.SECONDS);
                return true;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }


    //判断jwt是否在redis上
    //返回true表示redis对应的token有效，则否
    public static boolean checkJwtInRedis(RedisTemplate redisTemplate,String token){
        try {
            JwtParseInfoVo jwtParseInfoVo = null;
            String accessToken = null;
            if (!StringUtils.isEmpty(token)){
                //请求头参数为Authorization -- Bearer token方式
                if (token.startsWith(Oauth2Constant.HEAR_BEARER_PREFIX)){
                    String[] jwtArr = token.split(" ");
                    if (jwtArr.length > 2){
                        return false;
                    }
                    accessToken = jwtArr[1];
                }else {
                    accessToken = token;
                }
                jwtParseInfoVo = JwtUtil.jwtParseToBean(accessToken);
                if (jwtParseInfoVo == null){
                    return false;
                }
                String key = String.format("%s:%s:%s", RedisPrefixConstant.JWT_USER_TOKEN,jwtParseInfoVo.getUserId(),jwtParseInfoVo.getJti());
                String redisJwt = (String) redisTemplate.opsForValue().get(key);
                if (StringUtils.isEmpty(redisJwt)){
                    return false;
                }
                if (accessToken.equals(redisJwt)){
                    return true;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }



}
*/
