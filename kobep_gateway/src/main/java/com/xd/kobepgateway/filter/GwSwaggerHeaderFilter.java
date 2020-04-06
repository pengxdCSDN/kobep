package com.xd.kobepgateway.filter;
import com.xd.kobepgateway.config.GwSwaggerProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * 描述: Swagger需要根据X-Forwarded-Prefix的Header来获取服务名，gateway默认不会在swagger进行接口调用时
 *       添加这个Header到Request，所以需要在gateway加过滤器添加再进行转发。
 *
 * knife4j-spring-boot-starter 2.0.2版本修复了需要加项目名前缀的bug，不需要再yml加该过滤器
 */
@Component
public class GwSwaggerHeaderFilter extends AbstractGatewayFilterFactory {

    private static final String ADD_HEADER_NAME = "X-Forwarded-Prefix";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, GwSwaggerProvider.API_URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(GwSwaggerProvider.API_URI));
            ServerHttpRequest newRequest = request.mutate().header(ADD_HEADER_NAME, basePath).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }
}