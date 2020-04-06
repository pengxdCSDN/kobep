package com.xd.kobepcommon.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * 缓存管理
 */
@AutoConfigureAfter({RedisAutoConfiguration.class})
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "xd.redis", name = "enable", havingValue = "true", matchIfMissing = true)
public class RedisConfig extends CachingConfigurerSupport {


    private static Logger log = LoggerFactory.getLogger(RedisConfig.class);

    public RedisConfig() {
    }


    /**
     * 在使用@Cacheable时，如果不指定key，则使用找个默认的key生成器生成的key
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                log.info("调用Redis缓存key:" + sb.toString());
                return sb.toString();
            }
        };
    }

    /**
     * 设置 redis 数据默认过期时间
     * 设置@cacheable 序列化方式
     *
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        //默认300分钟的缓存
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer)).entryTtl(Duration.ofMinutes(300L)).disableCachingNullValues();
        return configuration;
    }


    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {

        //默认使用有锁模式的读写缓存
        RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory));
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
        return builder.build();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean
    public CacheErrorHandler cacheErrorHandler() {
        return new RedisConfig.KobepRedisCacheErrorHandler();
    }

    //缓存失败时的处理
    public static class KobepRedisCacheErrorHandler implements CacheErrorHandler {
        public KobepRedisCacheErrorHandler() {
        }

        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
            RedisConfig.log.error("handleCacheGetError key = {}, value = {}", key, cache);
            RedisConfig.log.error("cache get error", exception);
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
            RedisConfig.log.error("handleCachePutError key = {}, value = {}", key, cache);
            RedisConfig.log.error("cache put error", exception);
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
            RedisConfig.log.error("handleCacheEvictError key = {}, value = {}", key, cache);
            RedisConfig.log.error("cache evict error", exception);
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
            RedisConfig.log.error("handleCacheClearError value = {}", cache);
            RedisConfig.log.error("cache clear error", exception);
        }
    }
}