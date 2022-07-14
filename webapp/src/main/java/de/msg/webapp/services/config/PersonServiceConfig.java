package de.msg.webapp.services.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PersonServiceConfig {

    @Bean
    public List<String> blacklist() {
        return List.of("Attila", "Peter","Paul","Mary");
    }
}
