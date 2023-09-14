package com.sngular.skilltree.common.config;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfig {

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(Arrays.asList(
      new ConcurrentMapCache("skills"),
      new ConcurrentMapCache("people"),
      new ConcurrentMapCache("peopleView"),
            new ConcurrentMapCache("peopleNamesView"),
            new ConcurrentMapCache("projectNamesView"),
            new ConcurrentMapCache("positionView"),
      new ConcurrentMapCache("clients"),
      new ConcurrentMapCache("teams")));
    return cacheManager;
  }
}
