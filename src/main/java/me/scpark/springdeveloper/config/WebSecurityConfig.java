package me.scpark.springdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        /* toH2Console과 /static 폴더 아래의 자바스크립트 요청은 인증하지 않도록 설정 */
        return (web) -> web.ignoring().requestMatchers(toH2Console())
                .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/static/**"));
    }
}