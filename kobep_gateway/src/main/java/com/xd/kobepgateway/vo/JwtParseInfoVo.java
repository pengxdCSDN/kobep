package com.xd.kobepgateway.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * @description: jwt解析的信息
 * @author: pxd
 * @create: 2018-11-26 08:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class JwtParseInfoVo implements Serializable {

    private Long userId;

    @JSONField(name = "client_id")
    private String clientId;

    //jwt的唯一性
    private String jti;

    @JSONField(name = "userName")
    private String userName;

    //过期时间（时间戳）
    private String exp;

    private Set<String> authorities;

    private Set<String> aud;

    private Set<String> scope;






}
