package com.xd.kobepjpa.service.impl;

import com.xd.kobepjpa.repository.SysUserRepository;
import com.xd.kobepjpa.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(value = "transactionManagerKobepjpa",rollbackFor = Exception.class)
@Slf4j
public class SysUserServiceImpl implements SysUserService {



    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private Environment env;


    @Override
    public List<Map<String, Object>> findByUserId(int userId) throws Exception{
        String sql = "SELECT * FROM sys_user where user_Id = ?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list = sysUserRepository.findMapBySql(sql,params);
        log.info(env.getProperty("kobep.name"),"xxx");
        return  list;
    }



}
