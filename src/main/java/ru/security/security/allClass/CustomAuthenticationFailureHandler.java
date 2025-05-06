package ru.security.security.allClass;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        loginAttemptService.attemptLogin(username);

        if(loginAttemptService.failedAttempts.get(username)>= LoginAttemptService.MAX_ATTEMPTS){
            response.sendError(HttpStatus.FORBIDDEN.value(), "Account locked due to multiple failed login attempts");
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
        }
    }
}
