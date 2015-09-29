package com.shuyun.sbd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Component:
 * Description:
 * The first step is to create our Spring Security Java Configuration.
 * The configuration creates a Servlet Filter known as the springSecurityFilterChain which is responsible for
 * all the security (protecting the application URLs, validating submitted username and passwords,
 * redirecting to the log in form, etc) within your application.
 * You can find the most basic example of a Spring Security Java configuration below
 *
 * Date: 15/9/17
 *
 * @author yue.zhang
 */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * Authentication(身份验证)
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobalByMemory(AuthenticationManagerBuilder auth) throws Exception{
        auth
            .inMemoryAuthentication()
            .withUser("user").password("password").roles("USER").and()
            .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Autowired
    public void configureGlobalByJDBC(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource).withDefaultSchema()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    /**
     * Thus far our SecurityConfig only contains information about how to authenticate our users.
     * How does Spring Security know that we want to require all users to be authenticated?
     * How does Spring Security know we want to support form based authentication?
     * The reason for this is that the WebSecurityConfigurerAdapter provides a default configuration in the configure(HttpSecurity http)
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/resources/**", "/signup", "/about").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_DBA')")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .httpBasic();
    }
}
