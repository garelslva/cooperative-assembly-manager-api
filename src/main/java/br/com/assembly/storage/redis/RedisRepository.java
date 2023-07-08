package br.com.assembly.storage.redis;

import br.com.assembly.storage.redis.entity.SessionValue;

public interface RedisRepository {

    SessionValue get(Long sessionId, Long subjectId, Long limitTime);
    SessionValue set(Long sessionId, Long subjectId, Long limitTime);
    boolean delete(Long sessionId, Long subjectId, Long limitTime);
    long count(String session);
}
