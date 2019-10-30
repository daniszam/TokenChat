package ru.itis.darzam.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.darzam.security.filter.JWTFilter;
import ru.itis.darzam.security.filter.JwtAuthenticationEntryPoint;
import ru.itis.darzam.security.provider.JWTTokenProvider;
import ru.itis.darzam.security.util.SkipPathRequestMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String TOKEN_PATH = "/auth";
    private static final String[] PERMIT_ALL = { "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", TOKEN_PATH};
    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";

    private final JwtAuthenticationEntryPoint entryPoint;
    private final TokenExtractor tokenExtractor;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(PERMIT_ALL).permitAll()
                .and()
                .authorizeRequests().antMatchers(TOKEN_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private JWTFilter jwtFilter() {
        SkipPathRequestMatcher skipPathRequestMatcher = new SkipPathRequestMatcher(Arrays.asList(PERMIT_ALL), "/**");
        JWTFilter filter = new JWTFilter(skipPathRequestMatcher, tokenExtractor);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
