package com.example.chatting.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/chat/**").hasRole("USER")
                .anyRequest().permitAll();
    }
    /** 테스트를 위해 In-memory에 계정을 임의로 생성한다.
     **/

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("happy")
                .password("{noop}1234")
                .roles("USER")
                .and()
                .withUser("cool")
                .password("{noop}1234")
                .roles("USER")
                .and()
                .withUser("guest")
                .password("{noop}1234")
                .roles("GUEST");
    }

}
