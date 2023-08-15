package practice.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import practice.jwt.config.auth.LoginUser;
import practice.jwt.model.User;
import practice.jwt.model.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 시큐리티 필터중, BasicAuthenticationFilter가 있다.
 * 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있다.
 * 만약 권한이 인증이 필요한 주소가 아니라면 이 필터를 안 탄다.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰이 보내지 않았을 시
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            log.warn("authorization을 잘못 보냈습니다.");
            chain.doFilter(request, response);
            return;
        }

        // 토큰 꺼내기
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace(JwtProperties.TOKEN_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET))
                .build()
                .verify(token)
                .getClaim("username")
                .asString();

        // 정상 서명이 되었다면
        if (username != null) {
            User user = userRepository.findByUsername(username);

            LoginUser loginUser = new LoginUser(user);

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // Jwt 토큰 서명을 통해 서명이 정상이면 Authentication 객체를 만들어 주고 강제로 세션에 저장
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
