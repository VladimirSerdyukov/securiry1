package ru.security.security.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.security.security.dto.AuthDto;
import ru.security.security.utils.JwtUtils;

@RestController
@AllArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationUser(@RequestBody AuthDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(userDetailsService.loadUserByUsername(dto.getUsername()));

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handlerExpiredToken(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT Token has expired");
    }


}
