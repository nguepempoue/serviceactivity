package com.b2i.activitiesorganisation.config;

import com.b2i.activitiesorganisation.Utils.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExceptionConfig {

    @Bean
    public CustomErrorDecoder myCustomErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
