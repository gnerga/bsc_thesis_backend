package com.nerga.travelCreatorApp.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity builder) throws Exception {
        builder.csrf().disable();
        builder.headers().frameOptions().disable();
        builder
                .authorizeRequests()
                .antMatchers(
                        "/user",
                        "/user/**"
//                        "/location",
//                        "/location/**",
//                        "/trip",
//                        "/trip/**"
                )

                .permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                ;

    }
}
