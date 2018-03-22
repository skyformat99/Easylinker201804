package com.sucheon.box.server.app.config.securityconfig.filter;

import com.alibaba.fastjson.JSONObject;
import com.sucheon.box.server.app.config.securityconfig.handler.CustomAuthenticationManager;
import com.sucheon.box.server.app.config.securityconfig.handler.LoginFailureHandler;
import com.sucheon.box.server.app.config.securityconfig.handler.LoginSuccessHandler;
import com.sucheon.box.server.app.model.user.AppUser;
import com.sucheon.box.server.app.utils.LogPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 关于这个Filter的理解
 * 框架在处理登录的时候 如果你自定义认证过程 一般都是直接在attemptAuthentication 中实现
 * 如果你想用子类的loadUserByUsername 来实现
 * 就在下面加上代码:setDetails(request, usernamePasswordAuthenticationToken);
 */
public class CustomUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    LogPrinter logPrinter;
    private static final String DEFAULT_LOGIN_URL = "/userLogin";
    private static final String DEFAULT_LOGIN_METHOD = "POST";
    private Log logger = LogFactory.getLog(CustomUsernamePasswordFilter.class);
    LoginParameterCatcher loginParameterCatcher = null;


    public CustomUsernamePasswordFilter() {
        setAuthenticationManager(super.getAuthenticationManager());

        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(DEFAULT_LOGIN_URL, DEFAULT_LOGIN_METHOD));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(DEFAULT_LOGIN_URL, DEFAULT_LOGIN_METHOD));
        loginParameterCatcher = new LoginParameterCatcher(request);

        String loginParam = loginParameterCatcher.getLoginparam();
        String password = loginParameterCatcher.getPassword();


        Authentication authentication = null;

        if (true && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Only support post!");
        } else {

            try {
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginParam, password);
                this.setDetails(request, authRequest);
                authentication = this.getAuthenticationManager().authenticate(authRequest);

            } catch (Exception e) {
                if (e instanceof BadCredentialsException) {
                    logPrinter.log("登陆", "登录失败", loginParam);
                    JSONObject resultJson = new JSONObject();
                    resultJson.put("state", 0);
                    resultJson.put("message", "登录失败!用户不存在!");
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    try {
                        response.getWriter().write(resultJson.toJSONString());
                        response.getWriter().flush();
                    } catch (IOException e1) {
                        logger.error("输出流写入JSON失败!");
                        logPrinter.log("向输出流写入数据的时候", "写入失败", "HttpResponse");


                    }
                }


            }
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        logPrinter.log("登录", "登陆成功!", ((AppUser) authResult.getPrincipal()).getUsername());

    }

}
