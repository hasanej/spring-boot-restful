package hsn.restful.sandbox.service;

import hsn.restful.sandbox.entity.User;
import hsn.restful.sandbox.model.requests.LoginUserRequest;
import hsn.restful.sandbox.model.responses.TokenResponse;
import hsn.restful.sandbox.repository.UserRepository;
import hsn.restful.sandbox.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Username or password is incorrect"
                ));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());

            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

    private Long next30Days() {
        int second, minute, hour;
        long day;

        // Millisecond converter
        second = 1000; // 1 second
        minute = second * 60; // 1 minute
        hour = minute * 60; // 1 hour
        day = hour * 24; // 1 day

        return System.currentTimeMillis() + (day * 30);
    }
}
