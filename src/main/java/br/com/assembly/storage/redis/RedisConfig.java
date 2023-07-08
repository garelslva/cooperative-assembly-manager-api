package br.com.assembly.storage.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final Environment env;

    public RedisConfig(Environment env){
        this.env = env;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        var redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                env.getProperty("spring.redis.host"),
                env.getProperty("spring.redis.port", Integer.class));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        var host = env.getProperty("spring.redis.host");
//        var port = env.getProperty("spring.redis.port", Integer.class);
//        var factory = new JedisConnectionFactory(new RedisStandaloneConfiguration(host,port));
//        factory.setUsePool(true);
//        factory.getPoolConfig().setMaxIdle(100);
//        factory.getPoolConfig().setMaxTotal(3000);
//        factory.getPoolConfig().setMinIdle(100);
//        factory.getPoolConfig().setMaxWaitMillis(1000);
//        return factory;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}