package com.flight.flight_spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/showReg", "/", "/index.html", "/registerUser", "/login", "/showLogin", "/login/*")
                .permitAll()
                .antMatchers("/css/**","/lib/**","/images/**","/js/**").permitAll()
                .antMatchers("/admin/showAddFlight","/admin/admin/addFlight","/admin/*").hasAnyAuthority("ADMIN").anyRequest().authenticated()
                .and().csrf().disable();
    }
}
