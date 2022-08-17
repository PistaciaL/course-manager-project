package org.lr.interceptor;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.lr.handler.MyException;
import org.lr.utils.JWTUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局权限管理
 */

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token;
        try{
            token = request.getHeader("Authorization");
        } catch (Exception e){
            throw new MyException("令牌过期,请重新登录");
        }
        if(token!=null){
            Map map = JWTUtil.verifyToken(token);
            if (response.getHeader("Authorization") == null) {
                String newToken = JWTUtil.getTokenByClaimMap(map);
                response.setHeader("Access-Control-Expose-Headers","Authorization");
                response.setHeader("Authorization", newToken);
            }
            return true;
        }
        return false;
    }
}
