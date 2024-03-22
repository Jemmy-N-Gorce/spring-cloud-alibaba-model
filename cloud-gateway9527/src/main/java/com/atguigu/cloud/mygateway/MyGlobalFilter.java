package com.atguigu.cloud.mygateway;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GatewayFilter, Ordered {
    public static final String BEGIN_VISIT_TIME = "begin_visit_time";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(BEGIN_VISIT_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (beginVisitTime != null) {
                System.out.println("访问接口主机: " + exchange.getRequest().getURI().getHost());
                System.out.println("访问接口端口: " + exchange.getRequest().getURI().getPort());
                System.out.println("访问接口URL: " + exchange.getRequest().getURI().getPath());
                System.out.println("访问接口URL参数: " + exchange.getRequest().getURI().getRawQuery());
                System.out.println("访问接口时长: " + (System.currentTimeMillis() - beginVisitTime) + "ms");
                System.out.println("我是美丽分割线: ###################################################");
                System.out.println();
            }
        }));
    }

    //the smaller the num, the higher the priority
    @Override
    public int getOrder() {
        return 0;
    }
}
