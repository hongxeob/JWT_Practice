package practice.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    /**
     * filter에 등록 해줘야 함
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 서버가 응답시 json을 js에서 처리할 수 있게 할지 설정
        config.addAllowedOrigin("*"); // 모든 IP에 응답을 허용
        config.addAllowedHeader("*"); // 모든 header에 응답 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 요청을 허용
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
