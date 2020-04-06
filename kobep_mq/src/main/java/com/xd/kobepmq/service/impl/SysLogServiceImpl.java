package com.xd.kobepmq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xd.kobepmq.mapper.SysLogMapper;
import com.xd.kobepmq.entity.SysLog;
import com.xd.kobepmq.entity.query.SysLogQuery;
import com.xd.kobepmq.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 系统日志服务实现类
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysLogQuery pageByQuery(SysLogQuery query) {
        query.setDesc("create_time");
        sysLogMapper.pageByQuery(query);
        return query;
    }
}
