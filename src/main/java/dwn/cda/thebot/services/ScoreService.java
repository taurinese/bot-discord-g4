package dwn.cda.thebot.services;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreService {

    private final Map<String, Long> scores = new HashMap<String, Long>();

    public Long getScore(String userId) {
        return scores.computeIfAbsent(userId, id -> 0L);
    }

    public void saveOrUpdateScore(String userId, Long score){
        scores.merge(userId, score, Long::sum);
    }
}
