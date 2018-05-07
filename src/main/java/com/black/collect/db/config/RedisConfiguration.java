package com.black.collect.db.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.black.collect.Constants;
import com.black.collect.entity.ProxyEntity;
import com.black.collect.utils.PropsUtil;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;

/**
 * Created by hcdeng on 17-7-3.
 */
public class RedisConfiguration {

    private static final Properties REDIS_PROPS = PropsUtil.loadProps(Constants.REDIS_PROPS);

    private static final RedisConfiguration config = new RedisConfiguration();

    public static RedisTemplate<String, ProxyEntity> getRedisTemplate(){
        return config.redisTemplate();
    }

    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);


    private JedisConnectionFactory jedisConnFactory() {
        try {

            String redistogoUrl = REDIS_PROPS.getProperty(Constants.REDIS_URL);
            URI redistogoUri = new URI(redistogoUrl);

            JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
            String hostName = redistogoUri.getHost();
            int port = redistogoUri.getPort();
            jedisConnFactory.setUsePool(true);
            jedisConnFactory.setHostName(hostName);
            jedisConnFactory.setPort(port);
            jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
            jedisConnFactory.setShardInfo(new JedisShardInfo(hostName, port));

            return jedisConnFactory;

        } catch (URISyntaxException e) {
            logger.warn("error when in jedisConnFactory: "+e.getMessage());
            return null;
        }
    }

    private StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    private JacksonJsonRedisSerializer<ProxyEntity> jacksonJsonRedisJsonSerializer() {
        return new JacksonJsonRedisSerializer<>(ProxyEntity.class);
    }

    private RedisTemplate<String, ProxyEntity> redisTemplate() {
        RedisTemplate<String, ProxyEntity> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
