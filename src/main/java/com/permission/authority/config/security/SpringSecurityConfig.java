package com.permission.authority.config.security;

import com.permission.authority.config.security.filter.CheckTokenFilter;
import com.permission.authority.config.security.handler.AnonymousAuthenticationHandler;
import com.permission.authority.config.security.handler.CustomerAccessDeniedHandler;
import com.permission.authority.config.security.handler.LoginFailureHandler;
import com.permission.authority.config.security.handler.LoginSuccessHandler;
import com.permission.authority.config.security.service.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
//开启权限注解控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    @Resource
    private CheckTokenFilter checkTokenFilter;

    /**
     * .. 测试登录认证接口
     * 注入加密处理类
     *
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * .. 测试登录认证接口
     * 注入加密处理类
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录前进行过来
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //登录过程处理
        http.formLogin() // 表单登录
                .loginProcessingUrl("/api/user/login")  //登录请求url地址，自定义即可
                .successHandler(loginSuccessHandler) //认证成功处理器
                .failureHandler(loginFailureHandler) //认证失败处理器
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不创建session
                .and()
                .authorizeRequests() //设置需要拦截的请求
                .antMatchers("/api/user/login").permitAll() //登录请求放行(不拦截)
                .anyRequest().authenticated() // 其他一律请求都需要进行身份认证
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationHandler)  //匿名无权限访问
                .accessDeniedHandler(customerAccessDeniedHandler)      //认证用户无权限
                .and()
                .cors(); //支持跨域请求
    }

    /**
     * 配置认证处理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(
                passwordEncoder());
    }
}

