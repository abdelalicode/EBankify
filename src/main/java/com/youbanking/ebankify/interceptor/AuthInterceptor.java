package com.youbanking.ebankify.interceptor;

import com.youbanking.ebankify.response.ResponseHandler;
import com.youbanking.ebankify.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private TokenUtil tokenUtil;

    public AuthInterceptor(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenHeader = request.getHeader("authorization");

        if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            ResponseHandler.errorBuilder("Authentication required", HttpStatus.UNAUTHORIZED, "401" );
            return false;
        }

        String token = tokenHeader.replace("Bearer ", "");

        if(!tokenUtil.isAuthenticated(token)) {
            ResponseHandler.errorBuilder("Invalid Token", HttpStatus.UNAUTHORIZED, "401" );
            return false;
        }

        return true;

    }


}
