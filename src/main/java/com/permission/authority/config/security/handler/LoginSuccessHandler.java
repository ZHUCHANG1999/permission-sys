package com.permission.authority.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.permission.authority.entity.User;
import com.permission.authority.utils.JwtUtils;
import com.permission.authority.utils.LoginResult;
import com.permission.authority.utils.ResultCode;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
* 登录认证成功处理器类
* */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtils jwtUtils;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 设置响应的编码格式
        response.setContentType("application/json;charset=utf-8");
        // 获取当前登录用户信息
        User user = (User) authentication.getPrincipal();
        // 生成token
        String token= jwtUtils.generateToken(user);
        // 设置token签名密钥及过期时间
        long expireTime = Jwts.parser() //获取DefaultjwtParser对象
                .setSigningKey(jwtUtils.getSecret()) // 设置签名的密钥
                .parseClaimsJws(token.replace("jwt_",""))
                .getBody().getExpiration().getTime(); // 获取token过期时间
        // 创建loginResult登录结果对象啊
        LoginResult loginResult = new LoginResult(user.getId(),
                ResultCode.SUCCESS,token,expireTime);
        // 将对象转成JSON格式，并消除循环引用
        String result = JSON.toJSONString(user, SerializerFeature.DisableCircularReferenceDetect);
        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}