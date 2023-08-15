package practice.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/apu/v1/users/**")
                .access("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
                .antMatchers("/apu/v1/manager/**")
                .access("hasRole('ADMIN') or hasRole('MANAGER')")
                .antMatchers("/apu/v1/admin/**")
                .access("hasRole('ADMIN')")
                .anyRequest().permitAll()
                .and().build();


    }
}
