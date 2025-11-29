package com.rushi.service;

import com.rushi.model.Registration;
import com.rushi.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository repo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY = "registrations";

    public List<Registration> getAll() {

        // Check Redis Cache
        List<Registration> cached = (List<Registration>) redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return cached;
        }

        // Fetch from DB
        List<Registration> list = repo.findAll();

        // Save to Redis Cache
        redisTemplate.opsForValue().set(CACHE_KEY, list);

        return list;
    }

    public Registration save(Registration reg) {
        Registration saved = repo.save(reg);

        // Clear cache
        redisTemplate.delete(CACHE_KEY);

        return saved;
    }

    public void delete(Long id) {
        repo.deleteById(id);

        // Clear cache
        redisTemplate.delete(CACHE_KEY);
    }
}
