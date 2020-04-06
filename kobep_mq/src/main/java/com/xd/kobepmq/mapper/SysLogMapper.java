package com.xd.kobepmq.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.kobepmq.entity.SysLog;
import com.xd.kobepmq.entity.query.SysLogQuery;


public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 日志信息分页查询
     * @param query
     */
    IPage<SysLog> pageByQuery(SysLogQuery query);
}
