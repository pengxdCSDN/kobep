package com.xd.kobepgateway.filter;


import com.xd.kobepgateway.config.properties.KobepPermitUrlProperties;
import com.xd.kobepgateway.constant.Oauth2Constant;
import com.xd.kobepgateway.vo.JwtParseInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description: 校验token
 * @author: pxd
 * @create: 2018-11-28 11:19
 **/
/*
@Component
public class JwtAccessPermissionGatewayFilter implements GlobalFilter, Ordered {


    private static Logger log = LoggerFactory.getLogger(JwtAccessPermissionGatewayFilter.class);

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KobepPermitUrlProperties kobepPermitUrlProperties;


    */
/**
     * 1.过滤不需要token请求的接口
     * 2.校验token的有效性（redis匹配）
     *
     * @param exchange
     * @param chain
     * @return
     *//*

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String url = request.getPath().value();
        ServerHttpRequest.Builder builder = request.mutate();
        MultiValueMap<String,String> multiValueMap =request.getQueryParams();


        //获取url白名单
        String[] whiteLists = kobepPermitUrlProperties.getOauth2Urls();
        if (StringUtils.isEmpty(whiteLists) && whiteLists.length == 0) {
            return unauthorized(exchange);
        }


        //如果在白名单内，直接转发
        for (String path : whiteLists) {
            if (pathMatcher.match(path, url)) {
                log.info("根据白名单转发！");
                return chain.filter(exchange);
            }
        }

        String token = request.getHeaders().getFirst(Oauth2Constant.AUTHORIZATION_HEARER);
        if (token == null) {
            return unauthorized(exchange);
        }
        boolean flag = JwtUtil.checkJwtInRedis(redisTemplate, token);
        //token不存在redis，则返回无权限
        if (!flag) {
            return unauthorized(exchange);
        }
        JwtParseInfoVo jwtParseInfoVo = JwtUtil.jwtParseToBean(token);
        if (jwtParseInfoVo == null) {
            return unauthorized(exchange);
        }
        //将userId,myTypeId放在header转发
        builder.header("userId", jwtParseInfoVo.getUserId().toString());
        builder.header("userName", jwtParseInfoVo.getUserName());
        log.info("根据权限Authorization转发！");
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }

    @Override
    public int getOrder() {
        return -27;
    }

    */
/**
     * 网关拒绝，返回401
     *
     * @param
     *//*

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


}
        */