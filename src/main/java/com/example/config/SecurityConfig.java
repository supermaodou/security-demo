package com.example.config;

import com.example.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Autowired
//    private MyUserDetailsManager userDetailsManager;

//    @Autowired
//    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsManager);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

//    @Bean
//    public UserDetailsManager myUserDetailsManager() {
//        return new MyUserDetailsManager();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // 禁用csrf
                .csrf(csrf -> csrf.disable())
                // 禁用HTTP响应标头
                .headers((headersCustomizer) -> {
                    headersCustomizer.cacheControl(cache -> cache.disable()).frameOptions(options -> options.sameOrigin());
                })
                // 认证失败处理类
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // 配置请求权限
                .authorizeHttpRequests(authorize -> authorize
                        // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                        .requestMatchers("/login", "/register", "/captchaImage", "/user/**").permitAll()
                        // 静态资源，可匿名访问
                        .requestMatchers(HttpMethod.GET, "/*.html", "/**.html", "/**.css", "/**.js", "/profile/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/druid/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 基于token，所以不需要session，如果没有配置token会导致无法登录
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 自定义登录页面
                .formLogin(form ->
                        form.loginPage("/login")
                                // 登录成功默认跳转路由
                                .defaultSuccessUrl("/")
                )
                // 自定义登出页面
                .logout(logout -> logout
                        .logoutSuccessUrl("/login"))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
