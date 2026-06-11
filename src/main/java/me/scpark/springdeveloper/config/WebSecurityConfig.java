package me.scpark.springdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/login"),
                                PathPatternRequestMatcher.withDefaults().matcher("/signup"),
                                PathPatternRequestMatcher.withDefaults().matcher("/user")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .defaultSuccessUrl("/articles")
                )
                .logout(logout -> logout.logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http,
                                                        BCryptPasswordEncoder bCryptPasswordEncoder,
                                                        UserDetailsService userDetailsService) throws Exception {
        /*
            1. 사용자 인증을 위해 생성
            2. 사용자 정보 읽어오기 위한 서비스 설정
            3. 사용자 암호를 암호화하기 위해 설정
            4. 위에서 생성 및 설정을 전달
         */

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}