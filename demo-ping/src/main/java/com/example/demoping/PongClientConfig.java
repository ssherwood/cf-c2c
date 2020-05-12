package com.example.demoping;

import com.example.ribbon.RibbonConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
//@RibbonClient(name = "pong", configuration = RibbonConfig.class)
public class PongClientConfig {

    @Bean
    //@LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .customizers((RestTemplateCustomizer) restTemplate -> restTemplate.setRequestFactory(clientHttpRequestFactory()))
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create()
                //.setSSLContext(SSLContext.getDefault())
                .setDnsResolver(SystemDefaultDnsResolver.INSTANCE)
                //.evictIdleConnections()
                .useSystemProperties()
                .build());
    }

	/*
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		HttpClient httpClient = HttpClientBuilder.create()
				.evictIdleConnections(10, TimeUnit.MILLISECONDS)
				//.setDnsResolver(SystemDefaultDnsResolver.INSTANCE)
				.build();

		return builder
                .customizers((RestTemplateCustomizer) restTemplate -> restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient)))
				.setConnectTimeout(Duration.ofMillis(1500))
				.setReadTimeout(Duration.ofMillis(1500))
				.build();
	}
	*/
}
