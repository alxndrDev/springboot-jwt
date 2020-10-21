package com.example.jwt.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander
 * @date 2020-10-20
 **/
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        if (header != null) {
            String token = TokenUtils.getTokenFromHeader(header);
            if (TokenUtils.isValidToken(token)) {
                return true;
            }
        }
        response.sendRedirect("/error");
        return false;
    }
}
