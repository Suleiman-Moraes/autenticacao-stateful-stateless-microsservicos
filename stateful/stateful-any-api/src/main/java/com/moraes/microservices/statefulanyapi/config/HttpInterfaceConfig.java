package com.moraes.microservices.statefulanyapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.moraes.microservices.statefulanyapi.api.client.ITokenClient;

@Configuration
public class HttpInterfaceConfig {

    @Value("${app.client.base-url}")
    private String baseUrl;

    @Bean
    ITokenClient tokenClient() {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter
                        .forClient(WebClient
                                .builder()
                                .baseUrl(baseUrl)
                                .build()))
                .build()
                .createClient(ITokenClient.class);
    }
}
