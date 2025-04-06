package com.permission.authority.controller;

import com.permission.authority.config.redis.RedisService;
import com.permission.authority.entity.Permission;
import com.permission.authority.entity.User;
import com.permission.authority.entity.dto.UserInfo;
import com.permission.authority.entity.vo.RouterVo;
import com.permission.authority.entity.vo.TokenVo;
import com.permission.authority.utils.JwtUtils;
import com.permission.authority.utils.MenuTree;
import com.permission.authority.utils.Result;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    @Resource
    private RedisService redisService;
    @Resource
    private JwtUtils jwtUtils;

    /**
     * 刷新token
     *
     * @param request
     * @return
     */
    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        //从header中获取前端提交的token
        String token = request.getHeader("token");
        //如果header中没有token，则从参数中获取
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        //从Spring Security上下文获取用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //获取身份信息
        UserDetails details = (UserDetails) authentication.getPrincipal();
        //重新生成token
        String reToken = "";
        //验证原来的token是否合法
        if (jwtUtils.validateToken(token, details)) {
        //生成新的token
            reToken = jwtUtils.refreshToken(token);
        }
        //获取本次token的到期时间，交给前端做判断
        long expireTime = Jwts.parser().setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(reToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token信息
        String oldTokenKey = "token_" + token;
        redisService.del(oldTokenKey);
        //存储新的token
        String newTokenKey = "token_" + reToken;
        redisService.set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000);
        //创建TokenVo对象
        TokenVo tokenVo = new TokenVo(expireTime, reToken);
        //返回数据
        return Result.ok(tokenVo).message("token生成成功");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo() {
        //从Spring Security上下文获取用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //判断authentication对象是否为空
        if (authentication == null) {
            return Result.error().message("用户信息查询失败");
        }
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //用户权限集合
        List<Permission> permissionList = user.getPermissionList();
        //获取角色权限编码字段
        Object[] roles =
                permissionList.stream()
                        .filter(Objects::nonNull)
                        .map(Permission::getCode).toArray();
        //创建用户信息对象
        UserInfo userInfo = new UserInfo(user.getId(), user.getNickName(),
                user.getAvatar(), null, roles);
        //返回数据
        return Result.ok(userInfo).message("用户信息查询成功");
    }


    /*
    *  获取登录用户的菜单权限
    * */
    @GetMapping("/getMenuList")
    public Result getMenuList(){
        // 从Spring Security上下文获取用户登录信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户信息
        User user = (User) authentication.getPrincipal();
        // 获取该用户拥有的权限信息
        List<Permission> permissionList = user.getPermissionList();
        // 筛选当前用户拥有的目录和菜单数据
        List<Permission> collect = permissionList.stream()
                .filter(item -> item != null && item.getType() != 2)
                .collect(Collectors.toList());
        // 生成路由数据
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, 0L);
        // 返回数据源
        return Result.ok(routerVoList).message("菜单数据获取成功");
    }

    /*
    *  退出登录
    * */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)){
            // 从参数值获取token
            token = request.getParameter("token");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
            redisService.del("token_"+token);
        }
        return Result.ok().message("用户退出成功");
    }
}
