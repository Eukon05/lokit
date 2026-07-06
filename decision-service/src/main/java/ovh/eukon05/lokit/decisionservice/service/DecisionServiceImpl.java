package ovh.eukon05.lokit.decisionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean getDecision(UUID cardId, UUID deviceId) {
        return false;
    }
}
