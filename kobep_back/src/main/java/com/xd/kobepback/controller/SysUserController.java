package com.xd.kobepback.controller;


import com.xd.kobepback.dto.SysUserInfoDTO;
import com.xd.kobepback.entity.SysUser;
import com.xd.kobepback.entity.query.SysUserVoQuery;
import com.xd.kobepback.service.SysUserService;
import com.xd.kobepback.util.UserUtil;
import com.xd.kobepcommon.core.Result;
import com.xd.kobepcommon.core.ResultCode;
import com.xd.kobepcommon.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户controller", tags = {"用户操作接口"})
public class SysUserController {

    private static final String MODULE_NAME = "系统用户模块";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation(value = "获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
    @GetMapping("/info")
    public Result<SysUserInfoDTO> getInfo(){
        Integer userId = UserUtil.getUserId(request);
        List<String> roles =UserUtil.getRoles(request);
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysUserService.getUserInfo(userId, roles));
    }

    @ApiOperation(value = "根据用户名获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string")
    @GetMapping("/loadUserByUsername/{username}")
    public SysUserVo loadUserByUsername(@PathVariable(value = "username") String username){
        return sysUserService.loadUserByUsername(username);
    }

    @ApiOperation(value = "根据mobile获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string")
    @GetMapping("/loadUserByMobile/{mobile}")
    public SysUserVo loadUserByMobile(@PathVariable(value = "mobile") String mobile){
        return sysUserService.loadUserByMobile(mobile);
    }

    @ApiOperation(value = "获取用户角色信息", notes = "根据token获取用户角色信息", httpMethod = "GET")
    @GetMapping("/roles")
    public Result<List<String>> getRoles(){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,UserUtil.getRoles(request));
    }

    @ApiOperation(value = "获取用户信息 分页查询", notes = "用户信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "用户信息查询条件", required = false, dataType = "SysUserVoQuery")
    @GetMapping("/page")
    public Result<SysUserVoQuery> pageByQuery(SysUserVoQuery query){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysUserService.pageUserVoByQuery(query));
    }

    @ApiOperation(value = "添加用户", notes = "添加用户信息  带角色信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysUserVo", value = "用户信息", required = true, dataType = "SysUserVo")
    @PostMapping
    public Result<Boolean> save(@RequestBody SysUserVo sysUserVo){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysUserService.save(sysUserVo));
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息 带角色信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysUserVo", value = "用户信息", required = true, dataType = "SysUserVo")
    @PutMapping
    public Result<Boolean> update(@RequestBody SysUserVo sysUserVo){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysUserService.update(sysUserVo));
    }

    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "integer")
    @DeleteMapping("/id/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysUserService.delete(id));
    }

    @ApiOperation(value = "主键查询用户信息", notes = "查询用户信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "integer")
    @GetMapping("/id/{id}")
    public Result<SysUser> get(@PathVariable("id") Integer id){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysUserService.getById(id));
    }
}
