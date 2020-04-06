package com.xd.kobepcommon.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 描述:
 * fastjson与redis 反序列化对象的转换
 *
 */

public class FastJsonRedisUtils {

    private static Logger log = LoggerFactory.getLogger(FastJsonRedisUtils.class);

    //redis类缓存时 @type(类信息)作为key被缓存到redis，当redis反序列化时需要将该key去掉
    private static final String REMOVE_FIELD = "@type";

    public FastJsonRedisUtils() {
    }

    /**
     * @param redisTemplate
     * @param t             转为DTO的类名称
     * @param key           redis缓存的key
     * @return DTO
     * @throws Exception
     */
    public static <T> T redisToBean(RedisTemplate redisTemplate, Class<T> t, String key) {
        try {

            if (redisTemplate instanceof RedisTemplate && redisTemplate != null) {
                T dto = t.newInstance();
                String redisStr = JSON.toJSONString(redisTemplate.opsForValue().get(key));
                if ("null".equals(redisStr)) {
                    return null;
                }
                JSONObject jsonObject = JSON.parseObject(redisStr.toString());
                if (jsonObject.containsKey(REMOVE_FIELD)) {
                    jsonObject.remove(REMOVE_FIELD);
                }
                if (jsonObject != null) {
                    dto = (T) JSON.parseObject(jsonObject.toJSONString(), t);
                    return dto;
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * @param redisTemplate
     * @param t             转为DTO的类名称
     * @param hashkey       redis缓存的hashkey
     * @param key           redis缓存的key
     * @return DTO
     * @throws Exception
     */
    public static <T> T redisMapToBean(RedisTemplate redisTemplate, Class<T> t, String hashkey, String key) {

        try {
            if (redisTemplate instanceof RedisTemplate && redisTemplate != null) {
                T dto = t.newInstance();
                String redisStr = JSON.toJSONString(redisTemplate.opsForHash().get(hashkey, key));
                if ("null".equals(redisStr)) {
                    return null;
                }
                JSONObject jsonObject = JSON.parseObject(redisStr.toString());
                if (jsonObject.containsKey(REMOVE_FIELD)) {
                    jsonObject.remove(REMOVE_FIELD);
                }
                if (jsonObject != null) {
                    dto = (T) JSON.parseObject(jsonObject.toJSONString(), t);
                    return dto;
                }
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }


    /**
     * @param redisTemplate
     * @param t             转为DTO的类名称
     * @param key           redis缓存的key
     * @param start         开始
     * @param end           结束  0 到 -1代表所有值
     * @return List<T>
     * @throws Exception
     */
    public static <T> List<T> redisToList(RedisTemplate redisTemplate, Class<T> t, String key, long start, long end) {
        try {

            if (redisTemplate instanceof RedisTemplate && redisTemplate != null) {
                T dto = t.newInstance();
                List<T> redisList = redisTemplate.opsForList().range(key, start, end);
                if (redisList.size() <= 0) {
                    return null;
                }
                String redisStr = JSON.toJSONString(redisList.get(0));
                if ("null".equals(redisStr)) {
                    return null;
                }
                List<T> list = JSONObject.parseArray(redisStr, t);
                return list;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 将hash结构中的key对应的list数据返回
     *
     * @param redisTemplate
     * @param t             转为DTO的类名称
     * @param hash
     * @param key
     * @param <T>
     * @return
     */
    public static <T> List<T> redisHashToList(RedisTemplate redisTemplate, Class<T> t, String hash, String key) {
        try {

            if (redisTemplate instanceof RedisTemplate && redisTemplate != null) {
                T dto = t.newInstance();
                List<T> redisList = (List<T>) redisTemplate.opsForHash().get(hash, key);
                if (redisList.size() <= 0) {
                    return null;
                }
                String redisStr = JSON.toJSONString(redisList);
                if ("null".equals(redisStr)) {
                    return null;
                }
                List<T> list = JSONObject.parseArray(redisStr, t);
                return list;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    //redis-hash存储
    public static boolean putRedisHash(RedisTemplate redisTemplate, String hashkey, String key, Object object) {

        if (redisTemplate instanceof RedisTemplate && redisTemplate == null) {
            return false;
        }
        boolean flag = redisTemplate.opsForHash().hasKey(hashkey, key);
        if (flag) {
            //存在则删除
            redisTemplate.opsForHash().delete(hashkey, key);
        }
        redisTemplate.opsForHash().put(hashkey, key, object);
        return true;
    }


}
