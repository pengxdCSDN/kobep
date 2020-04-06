package com.xd.kobepback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.kobepback.dto.SysUserInfoDTO;
import com.xd.kobepback.entity.SysResource;
import com.xd.kobepback.entity.SysUser;
import com.xd.kobepback.entity.SysUserRole;
import com.xd.kobepback.entity.query.SysUserVoQuery;
import com.xd.kobepback.mapper.SysUserMapper;
import com.xd.kobepback.mapper.SysUserRoleMapper;
import com.xd.kobepback.service.SysResourceService;
import com.xd.kobepback.service.SysUserService;
import com.xd.kobepcommon.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUserVo loadUserByUsername(String username) {
        return sysUserMapper.loadUserByUsername(username);
    }

    @Override
    public SysUserVo loadUserByMobile(String mobile) {
        return sysUserMapper.loadUserByMobile(mobile);
    }

    @Override
    public SysUserInfoDTO getUserInfo(Integer userId, List<String> roles) {
        SysUserInfoDTO sysUserInfoDTO = new SysUserInfoDTO();
        SysUser sysUser = sysUserMapper.selectById(userId);
        //设置用户信息
        sysUserInfoDTO.setSysUser(sysUser);
        //设置角色列表
        sysUserInfoDTO.setRoles(roles);
        Set<SysResource> sysResources = sysResourceService.getSysResourceRoleCodes(roles);
        //设置权限列表（menu.permission）
        List<String> permissions = sysResourceService.findPermission(roles);
        sysUserInfoDTO.setPermissions(permissions);
        return sysUserInfoDTO;
    }

    @Override
    public SysUserVoQuery pageUserVoByQuery(SysUserVoQuery query) {
        query.setOptimizeCountSql(false);
        Integer total = sysUserMapper.countUserByQuery(query.getUsername());
        query.setTotal(total);
        sysUserMapper.pageUserVoByQuery(query);
        return query;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(SysUserVo sysUserVo) {
        // 新增用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVo, sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        this.save(sysUser);
        sysUserVo.setUserId(sysUser.getUserId());
        // 角色用户信息维护
        bindUserWithRole(sysUserVo);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(SysUserVo sysUserVo) {
        // 新增用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVo, sysUser);
        this.updateById(sysUser);
        // 删除原来的角色用户绑定信息
        deleteUserWithRole(sysUserVo.getUserId());
        // 角色用户信息维护
        bindUserWithRole(sysUserVo);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean delete(Integer userId) {
        this.removeById(userId);
        // 删除原来的角色用户绑定信息
        deleteUserWithRole(userId);
        return Boolean.TRUE;
    }

    /**
     * 绑定用户与角色信息
     * @param sysUserVo
     */
    private void bindUserWithRole(SysUserVo sysUserVo) {
        sysUserVo.getSysRoleVoList().forEach(role -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role.getRoleId());
            sysUserRole.setUserId(sysUserVo.getUserId());
            sysUserRoleMapper.insert(sysUserRole);
        });
    }

    /**
     * 删除用户与角色信息
     * @param userId
     */
    private void deleteUserWithRole(Integer userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(wrapper);
    }
}
