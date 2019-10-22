package ru.itis.darzam.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.darzam.security.filter.JWTFilter;
import ru.itis.darzam.security.provider.JWTTokenProvider;

import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JWTTokenProvider jwtProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests().antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**")
            .permitAll()
            .and()
            .authorizeRequests().antMatchers("/auth").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  protected JWTFilter jwtFilter(){
    JWTFilter filter = new JWTFilter();
    filter.setAuthenticationManager(authenticationManager());
    return filter;
  }

  @Bean
  protected AuthenticationManager authenticationManager(){
    return new ProviderManager(Collections.singletonList(jwtProvider));
  }
}
