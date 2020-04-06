package com.xd.kobepback.dto;

import com.xd.kobepback.entity.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserInfoDTO {

    private SysUser sysUser;

    private List<String> roles;

    private List<String> permissions;
}
