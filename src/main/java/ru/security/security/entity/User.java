package ru.security.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean isAccountNonLocked;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role implements GrantedAuthority {
        ROLE_USER, ROLE_MODERATOR, ROLE_SUPER_ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }

}
