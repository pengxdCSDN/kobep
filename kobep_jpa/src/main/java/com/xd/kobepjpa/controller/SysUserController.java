package com.xd.kobepjpa.controller;

import com.xd.kobepjpa.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
//@RefreshScope
@Api(value = "用户controller", tags = {"用户操作接口"})
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @ApiOperation(value = "主键查询用户信息", notes = "查询用户信息", httpMethod = "GET")
    @GetMapping(value = "/findId/{userId}")
    public List<Map<String, Object>> findByKid(@PathVariable int userId, HttpServletRequest httpServletRequest) throws Exception{


        return sysUserService.findByUserId(userId);

    }
}
