package com.example.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Moved explicitly outside the Spring Boot default component scan
// This allows for different "config" classes per client
@Configuration
public class RibbonConfig {

    @Bean
    public IPing ribbonPing() {
        // todo under cf should this be some other URL check?
        return new PingUrl(true, "/pong");
        //return new PingUrl(false, "/pong");
    }

    @Bean
    public IRule ribbonRule() {
        // filter out any un-available entries
        return new AvailabilityFilteringRule();
    }

    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config) {
        return new BoshDnsInetServerList(config);
    }
}
