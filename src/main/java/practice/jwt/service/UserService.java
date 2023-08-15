package practice.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.jwt.dto.UserJoinRequest;
import practice.jwt.dto.UserResponse;
import practice.jwt.model.User;
import practice.jwt.model.UserRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse join(UserJoinRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);
        return UserResponse.from(user);
    }
}
