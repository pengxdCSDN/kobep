package com.xd.kobepcommon.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * Twitter的分布式自增ID雪花算法snowflake ，分布式ID能够按照时间有序生成
 */
public class SnowflakeUtil {

    /**
     * @param datacenterId //数据中心
     * @param machineId    //机器标识
     */
    public static long nextId(long datacenterId, long machineId) {
        Snowflake snowflake = IdUtil.createSnowflake(datacenterId, machineId);
        long nextId = snowflake.nextId();
        return nextId;
    }

    /**
     *
     * @param datacenterId
     * @param machineId
     * @return
     */
    public static String nextIdAsString(long datacenterId, long machineId){
        long nextId = SnowflakeUtil.nextId(datacenterId,machineId);
        return String.valueOf(nextId);
    }

}
