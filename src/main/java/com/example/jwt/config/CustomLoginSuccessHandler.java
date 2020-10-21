package com.example.jwt.config;

import com.example.jwt.domain.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander
 * @date 2020-10-20
 **/
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        User userDetails = ((User) authentication.getPrincipal());
        String token = TokenUtils.generateJwtToken(userDetails);
        response.addHeader(AuthConstants.AUTH_HEADER,  AuthConstants.TOKEN_TYPE + " " + token);
    }
}
