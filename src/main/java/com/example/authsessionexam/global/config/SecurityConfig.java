package com.example.authsessionexam.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String[] PERMIT_ALL_URLS = {
            /* 리소스 */
            "/public/**",
            "/favicon.ico",
            /* 스웨거 */
            "/v3/api-docs/**",
            "/api/v3/api-docs/**",
            "/api/swagger-ui/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            /* 헬스체크 */
            "/actuator/**",
            /* 인증 API */
            "/api/auth/**",
            /* 커스텀 API */
            "/external/**"
    };

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 사용하지 않는 보안 정책 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                // CORS 정책
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 세션 정책 등록
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                    session.maximumSessions(1).maxSessionsPreventsLogin(false);
                })

                //
                .authorizeHttpRequests(authorizeRequests -> {
                    // 공개 URL
                    authorizeRequests.requestMatchers(PERMIT_ALL_URLS).permitAll();
                    // 권한 필요 URL
                    authorizeRequests.requestMatchers("/api/**").hasRole("USER");
                    authorizeRequests.requestMatchers("/api/admin/**").hasRole("ADMIN");
                    // 인증 필요 URL
                    authorizeRequests.requestMatchers("/api/auth/status").authenticated(); // 사용자 상태 조회 API
                    authorizeRequests.anyRequest().authenticated();
                })

                // TODO 여기서부터 ㄱㄱ
                .exceptionHandling(configurer -> configurer
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setContentType("application/json;charset=UTF-8");

//                            ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
//                                    .error(ApiResponse.Error.of("UNAUTHORIZED", "Authentication required"))
//                                    .build();

//                                    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                                })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://www.your-frontend-domain.com"));

        // 허용할 HTTP 메서드 (OPTIONS는 보통 사전 요청을 위해 포함됩니다)
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 허용할 헤더 (Authorization 헤더 등을 포함해야 하는 경우 명시합니다)
        config.setAllowedHeaders(Arrays.asList(
                "Origin",
                "Accept",
                "X-Requested-With",
                "X-License-Key",
                "Content-Type",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Authorization",
                "X-CSRF-TOKEN",
                "sec-ch-ua",
                "sec-ch-ua-mobile",
                "sec-ch-ua-platform"
        ));

        // 추가적인 응답 헤더들은 클라이언트 측 JavaScript에서 접근할 수 있도록 허용
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "X-License-Key",
                "X-CSRF-TOKEN",
                "xsrf-token"
        ));

        // 자격 증명(쿠키, HTTP 인증)을 요청과 함께 보낼지 여부
        // true로 설정하면 allowedOrigins에 *를 사용할 수 없습니다. 정확한 출처를 명시해야 합니다.
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // 캐시 가능한 시간 (초 단위)

        // 모든 경로(/**)에 대해 위의 CORS 설정을 적용.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
