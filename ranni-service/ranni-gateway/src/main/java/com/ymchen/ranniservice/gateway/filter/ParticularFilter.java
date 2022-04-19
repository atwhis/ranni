package com.ymchen.ranniservice.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Order(0)
@Component
@Slf4j
public class ParticularFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            log.info("gateway particular filter ,before filter ....... ");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("gateway particular filter ,after filter....... ")));
        };
    }
}
