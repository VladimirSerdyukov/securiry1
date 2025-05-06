package ru.security.security.allClass;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitDatabase {

    private final UserRepository userRepository;

    @PostConstruct
    public void addUser(){
        User user = new User();
        user.setUsername("user"); // pass - user
        user.setPassword("$2a$10$UbuveQBBf5KD119bpPGaLeAPVRadD3ezPyYb3TnoK2fMW5pSV5nsi");
        user.setAccountNonLocked(true);
        user.setRole(User.Role.ROLE_USER);
        userRepository.save(user);

        User admin = new User();
        admin.setUsername("admin"); // pass - admin
        admin.setPassword("$2a$10$ixIlc2uVl9XyITIXdmLtjOpxTnyiOz.Pr1v5sL8jKjTpbYecGGhVi");
        admin.setAccountNonLocked(true);
        admin.setRole(User.Role.ROLE_SUPER_ADMIN);
        userRepository.save(admin);

        User moderator = new User();
        moderator.setUsername("moderator"); // pass - admin
        moderator.setPassword("$2a$10$ixIlc2uVl9XyITIXdmLtjOpxTnyiOz.Pr1v5sL8jKjTpbYecGGhVi");
        moderator.setAccountNonLocked(true);
        moderator.setRole(User.Role.ROLE_MODERATOR);
        userRepository.save(moderator);
    }
}
