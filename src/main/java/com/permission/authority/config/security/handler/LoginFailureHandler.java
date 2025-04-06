package com.permission.authority.config.security.handler;


import com.alibaba.fastjson.JSON;

import com.permission.authority.config.security.exception.CustomerAuthenticationException;
import com.permission.authority.utils.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
/*
*  登录失败认证处理器
* */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 设置响应的编码格式
        response.setContentType("application/json;charset=utf-8");
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();

        int code = 500;
        String message = null;
        if (exception instanceof AccountExpiredException) {
            message = "账户过期,登录失败！";
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误,登录失败！";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期,登录失败！";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用,登录失败！";
        } else if (exception instanceof LockedException) {
            message = "账户被锁,登录失败！";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "账户不存在,登录失败！";
        } else if(exception instanceof CustomerAuthenticationException){
            message = exception.getMessage();
            code = 600;
        }
        else {
            message = "登录失败！";
        }

        String result = JSON.toJSONString(Result.error().code(code).message(message));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
