package com.coolcook.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.coolcook.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.coolcook.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.IngredientMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.IngredientAtHome.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.Quantity.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.RecipeMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.coolcook.domain.RecipeMaster.class.getName() + ".ingredientQtyMaps", jcacheConfiguration);
            cm.createCache(com.coolcook.domain.IngredientQtyMapping.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
