package com.xd.kobepjpa.service.impl;

import com.xd.kobepjpa.repository.SysUserRepository;
import com.xd.kobepjpa.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(value = "transactionManagerKobepjpa",rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {



    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public List<Map<String, Object>> findByUserId(int userId) throws Exception{
        String sql = "SELECT * FROM sys_user where user_Id = ?";
        List<Object> params = new ArrayList<>();
        params.add(userId);
        List<Map<String, Object>> list = sysUserRepository.findMapBySql(sql,params);
        return  list;
    }



}
