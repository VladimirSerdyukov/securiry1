package ru.security.security.allClass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class LoginAttemptService {
    private final UserRepository userRepository;
    final Map<String, Integer> failedAttempts = new ConcurrentHashMap<>();
    static final int MAX_ATTEMPTS = 5;

    public void attemptLogin(String username){
        failedAttempts.compute(username, (u, attempts) -> {
            if(attempts == null) {
                return 1;
            }
            return attempts + 1;
        });

        if(failedAttempts.get(username) >= MAX_ATTEMPTS){
            userRepository.findByUsername(username).ifPresent(user -> {
                user.setAccountNonLocked(false);
                userRepository.save(user);
            });
        }
    }

    public void clearAttempts(String username) {
        failedAttempts.remove(username);
    }

    public void unlockAccount(String username) {
        userRepository.findByUsername(username).ifPresent(user ->{
            user.setAccountNonLocked(true);
            userRepository.save(user);
            clearAttempts(username);
        });
    }
}
