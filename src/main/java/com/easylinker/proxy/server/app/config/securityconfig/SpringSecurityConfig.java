package com.easylinker.proxy.server.app.config.securityconfig;

import com.easylinker.proxy.server.app.config.securityconfig.filter.CustomUsernamePasswordFilter;
import com.easylinker.proxy.server.app.config.securityconfig.handler.AnonymousHandler;
import com.easylinker.proxy.server.app.config.securityconfig.handler.LoginFailureHandler;
import com.easylinker.proxy.server.app.config.securityconfig.handler.LoginSuccessHandler;
import com.easylinker.proxy.server.app.service.AppUserDetailService;
import com.easylinker.proxy.server.app.utils.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by wwhai on 2018/3/14.
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AppUserDetailService appUserDetailService;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    AnonymousHandler anonymousHandler;
    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    /**
     * WEB资源路径配置器
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(
                "/public.html",
                "/css/**",
                "/js/**",
                "/dest/**");//css路径放行
    }


    /**
     * HTTP资源配置
     * 默认以下地址不检查
     * /register 注册入口
     * /userLogin 登陆入口
     * /index 默认首页
     *
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(getCustomUsernamePasswordFilter());
        //配置不用过滤的路由
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/userLogin",
                        "/user/register",
                        "/data/exportExcel",

                        "/user/active"
                )
                .permitAll();


        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin().disable().httpBasic().disable()
                .logout().permitAll()
                .and().logout().logoutSuccessHandler(logoutSuccessHandler)
                .logoutUrl("/logOut")
                .and().rememberMe().alwaysRemember(true)
                .and().exceptionHandling()
                .authenticationEntryPoint(anonymousHandler)
                .and().csrf().disable();

    }

    @Bean
    public CustomUsernamePasswordFilter getCustomUsernamePasswordFilter() throws Exception {
        CustomUsernamePasswordFilter customUsernamePasswordFilter = new CustomUsernamePasswordFilter();
        customUsernamePasswordFilter.setAuthenticationManager(super.authenticationManager());
        customUsernamePasswordFilter.setAuthenticationFailureHandler(loginFailureHandler);
        customUsernamePasswordFilter.setAuthenticationSuccessHandler(loginSuccessHandler);

        return customUsernamePasswordFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 密码认证过程
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailService).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Generator.EncodingMD5((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Generator.EncodingMD5((String) rawPassword));
            }
        });
    }

}
