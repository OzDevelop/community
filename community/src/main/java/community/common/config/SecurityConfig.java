package community.common.config;

import community.auth.domain.TokenProvider;
import community.common.security.JwtAccessDeniedHandler;
import community.common.security.JwtAuthenticationEntryPoint;
import community.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Rest API이기 때문에 ,jwt를 기반으로하기 때문에 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 http 인증 비활성화
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않으므로 stateless 설정
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**", "/email/**", "/signup/**", "/login").permitAll()
                        .anyRequest().authenticated()
                ) // /auth/**, /email/** 경로는 로그인 없이 접근 허용
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        .accessDeniedHandler(new JwtAccessDeniedHandler())
                ) // 인증 실패, 인가 실패 시 에러 응답을 위한 핸들러
                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider, customUserDetailService),
                        UsernamePasswordAuthenticationFilter.class);
        // 커스텀 필터를 spring security filter chain에 추가
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
