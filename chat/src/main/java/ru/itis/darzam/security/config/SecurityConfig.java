package ru.itis.darzam.security.config;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.itis.darzam.security.filter.JWTFilter;
import ru.itis.darzam.security.filter.JwtAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String TOKEN_PATH = "/auth";
    private static final String[] PERMIT_ALL = {"/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", TOKEN_PATH, "/api/chat/websocket/**", "/hello"};
    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    private static final String SECURITY_PATH = "/api/chat/long-pooling";

    private final JwtAuthenticationEntryPoint entryPoint;
    private final TokenExtractor tokenExtractor;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(PERMIT_ALL).permitAll()
                .and()
                .cors()
//            .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JWTFilter jwtFilter() {
        JWTFilter filter = new JWTFilter(SECURITY_PATH, tokenExtractor);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
