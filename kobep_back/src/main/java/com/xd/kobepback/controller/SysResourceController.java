package com.xd.kobepback.controller;


import com.xd.kobepback.dto.SysResourceTree;
import com.xd.kobepback.entity.SysResource;
import com.xd.kobepback.service.SysResourceService;
import com.xd.kobepback.util.UserUtil;
import com.xd.kobepcommon.core.Result;
import com.xd.kobepcommon.core.ResultCode;
import com.xd.kobepcommon.vo.SysResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 资源管理
 */
@RestController
@RequestMapping("/resource")
@Api(value = "资源controller", tags = {"资源操作接口"})
public class SysResourceController {

    private static final String MODULE_NAME = "系统资源模块";


    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取当前用户的菜单树
     * @return
     */
    @ApiOperation(value = "获取当前用户的菜单树", notes = "根据token查询当前用户权限的菜单树", httpMethod = "GET")
    @GetMapping("/menu/tree")
    public Result<List<SysResourceTree>> getMenuTree(){
        List<String> roleCodes = UserUtil.getRoles(request);
        List<SysResourceTree> list = sysResourceService.getMenuTreeByRoleCodes(roleCodes);
        return new Result(ResultCode.HTTP_REQUEST_OK,list);
    }

    /**
     * 获取所有的资源树
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "获取所有菜单的树", notes = "获取所有菜单的树", httpMethod = "GET")
    public Result<List<SysResourceTree>> getAllResourceTree(){
        List<SysResourceTree> list = sysResourceService.getAllResourceTree();
        return new Result(ResultCode.HTTP_REQUEST_OK,list);
    }

    @ApiOperation(value = "添加资源信息", notes = "添加资源信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysResource", value = "资源信息", required = true, dataType = "SysResource")
    @PostMapping
    public Result saveMenu(@RequestBody SysResource sysResource) {
        return new Result(ResultCode.HTTP_REQUEST_OK,sysResourceService.save(sysResource));
    }

    @ApiOperation(value = "修改资源信息", notes = "修改资源信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysResource", value = "资源信息", required = true, dataType = "SysResource")
    @PutMapping
    public Result updateMenu(@RequestBody SysResource sysResource) {
        return new Result(ResultCode.HTTP_REQUEST_OK,sysResourceService.updateById((sysResource)));
    }

    @ApiOperation(value = "查询资源信息", notes = "根据id查询资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "资源id", required = true, dataType = "integer")
    @GetMapping("/id/{id}")
    public Result<SysResource> getById(@PathVariable("id") Integer id){
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysResourceService.getById(id));
    }


    @ApiOperation(value = "删除资源信息", notes = "根据id删除资源信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "资源id", required = true, dataType = "integer")
    @DeleteMapping("/id/{id}")
    public Result<Boolean> deleteResource(@PathVariable("id") Integer id) {
        return new Result<>(ResultCode.HTTP_REQUEST_OK,sysResourceService.deleteResource(id));
    }

    /**
     * 对内服务 不用ApiResult保装
     * 根据角色查询资源信息
     * @param roleCode
     */
    @ApiOperation(value = "根据角色查询资源信息", notes = "根据角色查询资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "roleCode", value = "角色code", required = true, dataType = "string")
    @GetMapping("/role/{roleCode}")
    public Set<SysResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode){

        List<SysResource> sysResources = sysResourceService.findResourceByRoleCode(roleCode);
        Set<SysResourceVO> sysResourceVOS = new HashSet<>();
        sysResources.stream().forEach(sysResource -> {
            SysResourceVO resourceVO = new SysResourceVO();
            BeanUtils.copyProperties(sysResource, resourceVO);
            sysResourceVOS.add(resourceVO);
        });
        return sysResourceVOS;
    }

}
