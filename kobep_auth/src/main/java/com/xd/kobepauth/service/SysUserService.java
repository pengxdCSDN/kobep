package com.xd.kobepauth.service;

import com.xd.kobepauth.service.fallback.SysUserServiceFallback;
import com.xd.kobepcommon.vo.SysUserVo;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * feign 调用服务
 * </p>
 *
 */
@FeignClient(name = "kobep-back", fallback = SysUserServiceFallback.class,configuration = SysUserService.UserFeignConfig.class)
public interface SysUserService {

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    @GetMapping("/user/loadUserByUsername/{username}")
    SysUserVo loadUserByUsername(@PathVariable(value = "username") String username);

    /**
     * 通过mobile查找用户
     * @param mobile
     * @return
     */
    @GetMapping("/user/loadUserByMobile/{mobile}")
    SysUserVo loadUserByMobile(@PathVariable(value = "mobile") String mobile);

    class UserFeignConfig {
        @Bean
        public Logger.Level logger() {
            return Logger.Level.FULL;
        }
    }


}
