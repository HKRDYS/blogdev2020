package com.example.demo.config;

import com.example.demo.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Web安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    //重写此方法，进行拦截配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/guest/**","/","/toLogin","/static/**").permitAll()//guest下的所有内容任何内容可访问
              .antMatchers("/user/**").hasAnyRole("USER","ADMIN")//user下的内容USER和ADMIN角色者可访问
              .antMatchers("/admin/**").hasRole("ADMIN")//admin下的内容仅ADMIN角色的用户可访问
              .anyRequest().authenticated();

            http.formLogin()
             .loginPage("/toLogin")
                    .usernameParameter("username")
                    .passwordParameter("password")

                    .loginProcessingUrl("/myLogin")
                    .defaultSuccessUrl("/myLogin")
            .failureUrl(("/toLogin?error"));
            http.logout().logoutSuccessUrl("/");

    }
    //重写以下方法，选择授权认证方式，内存身份认证，JDBC认证还是UserDetails认证
    //此项目使用UserDetails认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/satic/**");
    }
}
