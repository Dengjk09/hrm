package com.dengjk.system.config;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Dengjk
 * @create 2019-05-02 20:49
 * @desc 自己实现shiro的数据存储, 主要是用redisTemplate存储
 **/
public class ShiroRedisSessionDao extends AbstractSessionDAO {

    /**
     * key前缀
     */
    private static final String SHIRO_REDIS_SESSION_KEY_PREFIX = "hrm:session:";

    private RedisTemplate redisTemplate;

    private ValueOperations valueOperations;

    public ShiroRedisSessionDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        valueOperations.set(generateKey(sessionId), session, session.getTimeout(), TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) valueOperations.get(generateKey(sessionId));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        valueOperations.set(generateKey(session.getId()), session, session.getTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        redisTemplate.delete(generateKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Object> keySet = redisTemplate.keys(generateKey("*"));
        Set<Session> sessionSet = new HashSet<>();
        if (CollectionUtils.isEmpty(keySet)) {
            return Collections.emptySet();
        }
        for (Object key : keySet) {
            sessionSet.add((Session) valueOperations.get(key));
        }
        return sessionSet;
    }

    /**
     * 重组key
     * 区别其他使用环境的key
     *
     * @param key
     * @return
     */
    private String generateKey(Object key) {
        return SHIRO_REDIS_SESSION_KEY_PREFIX + key;
    }
}
