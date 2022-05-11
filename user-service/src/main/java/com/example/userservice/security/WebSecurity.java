package com.example.userservice.security;


import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {

//     private static final String IP_ADDRESS = "192.168.45.119";    // G/W Server Address
     private static final String IP_ADDRESS = "127.0.0.1";    // G/W Server Address
    private PasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private Environment env;

    public WebSecurity(Environment env, UserService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 모든 사용자 요청에 대해 Filtering 적용
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("### spring.cloud.client.hostname=" + env.getProperty("spring.cloud.client.hostname"));
        System.out.println("### spring.cloud.client.ip-address=" + env.getProperty("spring.cloud.client.ip-address"));


        http.csrf().disable();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().antMatchers("/**")
                .access("hasIpAddress('" + IP_ADDRESS + "')")
//                .hasIpAddress(env.getProperty("spring.cloud.client.ip-address"))
//                .access("permitAll")
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authFilter =
                new AuthenticationFilter(authenticationManager(), userService, env);
//        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }

    /**
     * 사용자 검증
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userService)를 통해 user의 password를 알아온다.
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

}
