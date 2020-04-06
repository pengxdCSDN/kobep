package com.xd.kobepauth.authentication;

import com.xd.kobepauth.domain.WpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @description: 默认的 UserDetailsService 实现
 * @author: pxd
 * @create: 2019-01-14 16:46
 **/

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);



/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("需要自行配置 UserDetailsService 接口的实现或自定义UserDetailsService类似的认证类");
        throw new UsernameNotFoundException("请自定义UserDetailsService的认证类！");
    }*/


    private BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new WpUser("123", passwordEncoder.encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"),"sky123456");
    }

}
