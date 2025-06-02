package ru.security.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public TomcatServletWebServerFactory webServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            if(connector.getScheme().equals("https")){
                connector.setSecure(true);
                connector.setPort(8443);
            }
        });

        return factory;
    }

    @Bean
    public FilterRegistrationBean<HttpsRedirectFilter> redirectFilter(){
        FilterRegistrationBean<HttpsRedirectFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpsRedirectFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpsRedirectFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    public static class HttpsRedirectFilter extends OncePerRequestFilter{

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if(!"https".equals(request.getScheme())){
                String redirectUrl = "https://" + request.getServerName() + ":8443" + request.getRequestURI();
                response.sendRedirect(redirectUrl);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }
}
