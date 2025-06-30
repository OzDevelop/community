package community.common.config;

import community.auth.domain.token.TokenProvider;
import community.common.security.handler.JwtAccessDeniedHandler;
import community.common.security.JwtAuthenticationEntryPoint;
import community.common.security.JwtAuthenticationFilter;
import community.common.security.handler.OAuth2SuccessHandler;
import community.common.security.service.CustomOAuth2UserService;
import community.common.security.service.CustomUserDetailService;
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
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Rest API이기 때문에 ,jwt를 기반으로하기 때문에 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 http 인증 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // 세션을 사용하지 않으므로 stateless 설정
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**", "/email/**", "/signup/**", "/login/**", "/user").permitAll()
                        .anyRequest().authenticated()
                ) // /auth/**, /email/** 경로는 로그인 없이 접근 허용
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler( oAuth2SuccessHandler)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
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