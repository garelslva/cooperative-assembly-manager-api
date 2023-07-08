package br.com.assembly.storage.redis;

import br.com.assembly.converter.Utils;
import br.com.assembly.storage.redis.entity.SessionValue;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

@Service
public class RedisRepositoryImpl implements RedisRepository{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisRepositoryImpl(){
    }

    public SessionValue get(Long sessionId, Long subjectId, Long limitTime){
        var key = generateRedisKey(sessionId, subjectId, limitTime);
        Map<String, Object> result = (Map<String, Object>) this.redisTemplate.opsForValue().get(key);
        if (Objects.isNull(result)){
            return (SessionValue) result;
        }
        return SessionValue.builder()
                .startAt(LocalDateTime.parse(result.get("startAt").toString(), DateTimeFormatter.ofPattern(Utils.DATE_FORMAT_PATTERN)))
                .limitTimeSeconds((int) result.get("limitTimeSeconds"))
                .build();
    }

    public SessionValue set(Long sessionId, Long subjectId, Long limitTime){
        var key = generateRedisKey(sessionId, subjectId, limitTime);
        var value = SessionValue.builder()
        .startAt(LocalDateTime.now())
        .limitTimeSeconds(limitTime)
        .build();
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, Duration.of(limitTime, ChronoUnit.SECONDS));
        return value;
    }

    public boolean delete(Long sessionId, Long subjectId, Long limitTime){
        var key = generateRedisKey(sessionId, subjectId, limitTime);
        return this.redisTemplate.delete(key);
    }

    @Override
    public long count(final String session) {
        return this.redisTemplate.keys(session.concat("*")).size();
    }

    private String generateRedisKey(Long sessionId, Long subjectId, Long limitTime) {
        return new StringBuilder("session").append(":")
                         .append("sessionId-").append(sessionId).append(":")
                         .append("subjectId-").append(subjectId).append(":")
                         .append("limitTime-").append(limitTime).toString();
    }
}
