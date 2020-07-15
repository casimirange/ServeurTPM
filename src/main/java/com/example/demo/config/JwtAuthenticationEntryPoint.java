///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.demo.security.jwt;
//
//import java.io.IOException;
//import java.io.Serializable;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// *
// * @author Casimir
// */
//@Component
//public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint, Serializable {
//    
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
//    
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException e) 
//                             throws IOException, ServletException {
//      
//        logger.error("Unauthorized error. Message - {}", e.getMessage());
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
//    }
//}
