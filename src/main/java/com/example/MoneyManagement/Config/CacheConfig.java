package com.example.MoneyManagement.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
    public CacheConfig (){

        log.info("-----------------------------------------------------");
        log.info("Spring Cache has been enabled successfully.");
        log.info("Cache Provider : Redis");
        log.info("-----------------------------------------------------");

    }
}
