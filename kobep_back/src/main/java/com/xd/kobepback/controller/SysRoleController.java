package com.xd.kobepback.controller;


import com.xd.kobepback.dto.SysRoleDTO;
import com.xd.kobepback.entity.SysRole;
import com.xd.kobepback.entity.query.SysRoleQuery;
import com.xd.kobepback.service.SysRoleService;
import com.xd.kobepcommon.core.Result;
import com.xd.kobepcommon.core.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "角色controller", tags = {"角色操作接口"})
public class SysRoleController {

    private static final String MODULE_NAME = "系统角色模块";


    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "添加角色", notes = "角色信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysRoleDTO", value = "角色信息", required = true, dataType = "SysRoleDTO")
    @PostMapping
    public Result<Boolean> save(@RequestBody SysRoleDTO sysRoleDTO){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysRoleService.save(sysRoleDTO));
    }

    @ApiOperation(value = "修改角色", notes = "角色信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysRoleDTO", value = "角色信息", required = true, dataType = "SysRoleDTO")
    @PutMapping
    public Result<Boolean> update(@RequestBody SysRoleDTO sysRoleDTO){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysRoleService.updateById(sysRoleDTO));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "integer")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable("id") Integer id){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysRoleService.deleteById(id));
    }

    @ApiOperation(value = "查询角色信息", notes = "查询角色信息以及相关联的资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "integer")
    @GetMapping("/{id}")
    public Result<SysRoleDTO> getSysRoleInfo(@PathVariable("id") Integer id){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysRoleService.getRoleInfoWithResourceById(id));
    }

    @ApiOperation(value = "角色信息分页查询", notes = "角色信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "sysRoleQuery", value = "角色信息查询类", required = false, dataType = "SysRoleQuery")
    @GetMapping("/page")
    public Result<SysRoleQuery> pageByQuery(SysRoleQuery sysRoleQuery){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysRoleService.pageByQuery(sysRoleQuery));
    }

    @ApiOperation(value = "查询所有角色信息", notes = "查询角色信息", httpMethod = "GET")
    @GetMapping
    public Result<List<SysRole>> listRole(){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysRoleService.listSysRole());
    }
}
