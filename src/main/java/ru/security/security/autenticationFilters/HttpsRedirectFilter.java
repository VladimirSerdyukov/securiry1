package ru.security.security.autenticationFilters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class HttpsRedirectFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!"https".equals(request.getScheme())){
            String redirectUrl = "https://" + request.getServerName() + ":" + 443 + request.getRequestURI();
            response.sendRedirect(redirectUrl);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
