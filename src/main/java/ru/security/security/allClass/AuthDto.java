package ru.security.security.allClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AuthDto {

    private final String username;
    private final String password;

}
