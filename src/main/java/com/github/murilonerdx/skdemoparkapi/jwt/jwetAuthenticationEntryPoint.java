package com.github.murilonerdx.skdemoparkapi.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class jwetAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("www-authenticate", "Bearer realm=\""+request.getContextPath()+"\", error=\"invalid_token\", error_description=\""+authException.getMessage()+"\"");
        response.sendError(401,"Unauthorized");
    }
}
