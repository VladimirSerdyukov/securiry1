package ru.security.security.autenticationFilters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.security.security.utils.JwtUtils;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtUtils.extractUsername(token);


        if(response.getStatus() == HttpStatus.OK.value()){
            logger.info("Successful login for user: " + username);
        } else {
            logger.warn("Failed login attempt for user: " + username);
        }

        filterChain.doFilter(request, response);
    }
}
