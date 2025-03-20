package com.example.jooqtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VideoWatchService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveTime(Long userId, String videoId, Long timestamp) {
        String key = generateKey(userId, videoId);
        redisTemplate.opsForValue().set(key, String.valueOf(timestamp));
    }
    
    public Long getTime(Long userId, String videoId) {
        String key = generateKey(userId, videoId);
        String timestamp = (String) redisTemplate.opsForValue().get(key);
        return timestamp != null ? Long.parseLong(timestamp) : 0L;
    }
    
    private String generateKey(Long userId, String videoId) {
        return "videowatch:" + userId + ":" + videoId;
    }

}
